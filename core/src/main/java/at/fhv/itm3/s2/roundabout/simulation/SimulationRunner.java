package at.fhv.itm3.s2.roundabout.simulation;

import at.fhv.itm14.trafsim.model.entities.Car;
import at.fhv.itm14.trafsim.model.events.CarDepartureEvent;
import at.fhv.itm14.trafsim.persistence.model.DTO;
import at.fhv.itm3.s2.roundabout.api.entity.AbstractSink;
import at.fhv.itm3.s2.roundabout.api.entity.AbstractSource;
import at.fhv.itm3.s2.roundabout.api.entity.IRoundaboutStructure;
import at.fhv.itm3.s2.roundabout.api.entity.Street;
import at.fhv.itm3.s2.roundabout.dornbirnnorth.DornbirnNorthModelBuilder;
import at.fhv.itm3.s2.roundabout.entity.RoundaboutStructure;
import at.fhv.itm3.s2.roundabout.util.ConfigParser;
import at.fhv.itm3.s2.roundabout.util.ConfigParserException;
import at.fhv.itm3.s2.roundabout.util.ILogger;
import at.fhv.itm3.s2.roundabout.util.drawer.ResultDrawer;
import at.fhv.itm3.s2.roundabout.util.drawer.Statistics;
import at.fhv.itm3.s2.roundabout.util.drawer.StatisticsValue;
import at.fhv.itm3.s2.roundabout.util.dto.ModelConfig;
import desmoj.core.simulator.Experiment;
import desmoj.core.simulator.TimeInstant;
import desmoj.core.statistic.TimeSeries;
import desmoj.extensions.grafic.util.Plotter;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.List;

public class SimulationRunner implements ILogger {
    private static final String DEFAULT_ROUNDABOUT_CONFIG_PATH = SimulationRunner.class.getResource("/dornbirn-nord.xml").getPath();
    private static final long SimulationDuration = 3 * 60 * 60;
//    private static final long ExecutionSpeed = 50000L;
    private static Map<Street, TimeSeries> timeSeriesMap;

    public static void main(String[] args) throws ConfigParserException {
        Double minTimeBetweenCarArrival = 5.0;
        Double maxTimeBetweenCarArrival = 7.0;
        Double standardCarSpeed = 6.0;
        Double standardCarLength = 2.0;

        BetterRoundaboutSimulationModel model = new BetterRoundaboutSimulationModel(null, "", true, false, minTimeBetweenCarArrival, maxTimeBetweenCarArrival, standardCarSpeed, standardCarLength);
        Experiment exp = new Experiment("Roundabout Experiment");
        Experiment.setEpsilon(model.getModelTimeUnit());
        Experiment.setReferenceUnit(model.getModelTimeUnit());
        exp.setSeedGenerator((long) (Long.MAX_VALUE * Math.random()));
        exp.getDistributionManager().newSeedAll();
        exp.setShowProgressBar(true);
        exp.setSilent(true);

       model.connectToExperiment(exp);

        IRoundaboutStructure roundaboutStructure = getRoundaboutStructureFromConfigFile(args, model);

        if (roundaboutStructure == null) {
            roundaboutStructure = new DornbirnNorthModelBuilder().build(model);
        }

        model.setRoundaboutStructure(roundaboutStructure);

        timeSeriesMap = new HashMap<>();
        for (Street roundaboutInlet: model.getRoundaboutStructure().getRoundaboutInlets()) {
            timeSeriesMap.put(roundaboutInlet, new TimeSeries(model, "RoundaboutInlet Timeseries", new TimeInstant(0), new TimeInstant(SimulationDuration, model.getModelTimeUnit()), true, true));
        }
        model.setTimeSeriesMap(timeSeriesMap);
        Plotter plotter = new Plotter("", new Dimension(1200, 800));
        plotter.setOnScreen(true);

        TimeInstant stopTime = new TimeInstant(SimulationDuration, model.getModelTimeUnit());
        exp.tracePeriod(new TimeInstant(0L), stopTime);
        exp.stop(stopTime);
//        exp.setExecutionSpeedRate(ExecutionSpeed);
        System.out.println("Starting simulation.");
        exp.start();
        System.out.println("Simulation finished. Creating reports.");
        exp.finish();

        roundaboutStructure.getStreets();

        for (AbstractSink sink : roundaboutStructure.getSinks()) {
            printStatisticsForSink(sink);
        }

        showStatisticsForSink(roundaboutStructure.getSinks(), roundaboutStructure.getStreets());

        try {
            saveQueueLengthsToTextFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void showStatisticsForSink(Set<AbstractSink> sinks, Set<Street> streets) {
        Statistics enteredCarsStatistics = new Statistics("Entered Cars", "#FF0000");
        Statistics meanIntersectionPassTimeForEnteredCarsStatistics = new Statistics("MeanIntersectionPassTimeForEnteredCars", "#00FF00");
        Statistics meanRoundaboutPassTimeForEnteredCars = new Statistics("MeanRoundaboutPassTimeForEnteredCars", "#0000FF");
        Statistics meanStopCountForEnteredCarsStatistics = new Statistics("MeanStopCountForEnteredCars", "#F0F000");
        Statistics meanTimeSpentInSystemForEnteredCarsStatistics = new Statistics("MeanTimeSpentInSystemForEnteredCars", "#FF5733");
        Statistics meanWaitingTimePerStopForEnteredCarsStatistics = new Statistics("MeanWaitingTimePerStopForEnteredCars", "#00F0F0");

        for(AbstractSink sink : sinks) {
            int xPosition = 0;
            int yPosition = 0;
            if (sink.getName().contains("sink_lauterach")) {
                xPosition = 390;
                yPosition = 130;
            } else if (sink.getName().contains("sink_achrain")) {
                xPosition = 700;
                yPosition = 430;
            } else if (sink.getName().contains("sink_schwefel")) {
                xPosition = 460;
                yPosition = 700;
            } else if (sink.getName().contains("sink_a14")) {
                xPosition = 100;
                yPosition = 420;
            }

            enteredCarsStatistics.addValue(new StatisticsValue(Integer.toString(sink.getEnteredCars().size()),xPosition,yPosition));
            yPosition+=13;
            meanIntersectionPassTimeForEnteredCarsStatistics.addValue(new StatisticsValue(String.format("%3.2f" , sink.getMeanIntersectionPassTimeForEnteredCars()),xPosition,yPosition));
            yPosition+=13;
            meanRoundaboutPassTimeForEnteredCars.addValue(new StatisticsValue(String.format("%3.2f" , sink.getMeanRoundaboutPassTimeForEnteredCars()),xPosition,yPosition));
            yPosition+=13;
            meanStopCountForEnteredCarsStatistics.addValue(new StatisticsValue(String.format("%3.2f" , sink.getMeanStopCountForEnteredCars()),xPosition,yPosition));
            yPosition+=13;
            meanTimeSpentInSystemForEnteredCarsStatistics.addValue(new StatisticsValue(String.format("%3.2f" , sink.getMeanTimeSpentInSystemForEnteredCars()),xPosition,yPosition));
            yPosition+=13;
            meanWaitingTimePerStopForEnteredCarsStatistics.addValue(new StatisticsValue(String.format("%3.2f" , sink.getMeanWaitingTimePerStopForEnteredCars()),xPosition,yPosition));
        }

        Statistics waitingCarsStatistics = new Statistics("Waiting Cars", "#FF00FF");

        for(Street street : streets){
            int xPosition = 0;
            int yPosition = 0;
            if (street.getName().contains("section_lauterach_inlet")) {
                xPosition = 340;
                yPosition = 180;
            } else if (street.getName().contains("section_achrain_inlet")) {
                xPosition = 700;
                yPosition = 400;
            } else if (street.getName().contains("section_schwefel_inlet")) {
                xPosition = 530;
                yPosition = 700;
            } else if (street.getName().contains("section_a14_inlet")) {
                xPosition = 100;
                yPosition = 520;
            }

            waitingCarsStatistics.addValue(new StatisticsValue(Integer.toString(street.getCarQueue().size()),xPosition,yPosition));
        }

        List<Statistics> statistics = Arrays.asList(enteredCarsStatistics, meanIntersectionPassTimeForEnteredCarsStatistics,
                meanRoundaboutPassTimeForEnteredCars, meanStopCountForEnteredCarsStatistics, meanTimeSpentInSystemForEnteredCarsStatistics,
                meanWaitingTimePerStopForEnteredCarsStatistics, waitingCarsStatistics);

        ResultDrawer window = new ResultDrawer("Kreisverkehr Dornbirn Nord", "/dornbirn-nord.png", statistics);

        window.setVisible(true);
    }

    private static void saveQueueLengthsToTextFile() throws FileNotFoundException {
        for (Map.Entry<Street, TimeSeries> timeSeriesEntry : timeSeriesMap.entrySet()) {
            PrintWriter pw = new PrintWriter(new FileOutputStream("queueLengths_" + timeSeriesEntry.getKey().getName() + ".csv"));
            pw.println(timeSeriesEntry.getValue().getDataValues());
            pw.close();
        }

    }

    private static void printStatisticsForSink(AbstractSink sink) {
        System.out.println(String.format("Sink %s", sink));
        System.out.println(String.format("Entered cars: %s", sink.getEnteredCars().size()));
        System.out.println(String.format("MeanIntersectionPassTimeForEnteredCars: %s", sink.getMeanIntersectionPassTimeForEnteredCars()));
        System.out.println(String.format("MeanRoundaboutPassTimeForEnteredCars: %s", sink.getMeanRoundaboutPassTimeForEnteredCars()));
        System.out.println(String.format("MeanStopCountForEnteredCars: %s", sink.getMeanStopCountForEnteredCars()));
        System.out.println(String.format("MeanTimeSpentInSystemForEnteredCars: %s", sink.getMeanTimeSpentInSystemForEnteredCars()));
        System.out.println(String.format("MeanWaitingTimePerStopForEnteredCars: %s", sink.getMeanWaitingTimePerStopForEnteredCars()));
        System.out.println("---------\n");
    }

    private static IRoundaboutStructure getRoundaboutStructureFromConfigFile(String[] args, BetterRoundaboutSimulationModel model) {

        IRoundaboutStructure roundaboutStructure = null;
        try {
            String roundaboutConfigFileName = getArgOrDefault(args, 0, DEFAULT_ROUNDABOUT_CONFIG_PATH);
            ConfigParser configParser = new ConfigParser(roundaboutConfigFileName);
            ModelConfig modelConfig = configParser.loadConfig();
            roundaboutStructure = configParser.generateRoundaboutStructure(modelConfig, model);
        } catch (ConfigParserException e) {
            System.err.println(e);
        }
        return roundaboutStructure;
    }

    private static String getArgOrDefault(String[] array, int index, String defaultValue) {
        if (array.length > index) {
            return array[index];
        }
        return defaultValue;
    }
}
