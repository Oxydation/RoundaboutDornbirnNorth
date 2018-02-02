package at.fhv.itm3.s2.roundabout.simulation;

import at.fhv.itm3.s2.roundabout.api.entity.AbstractSink;
import at.fhv.itm3.s2.roundabout.api.entity.IRoundaboutStructure;
import at.fhv.itm3.s2.roundabout.api.entity.Street;
import at.fhv.itm3.s2.roundabout.dornbirnnorth.DornbirnNorthModelBuilder;
import at.fhv.itm3.s2.roundabout.entity.RoundaboutStructure;
import at.fhv.itm3.s2.roundabout.util.ConfigParser;
import at.fhv.itm3.s2.roundabout.util.ConfigParserException;
import at.fhv.itm3.s2.roundabout.util.ILogger;
import at.fhv.itm3.s2.roundabout.util.dto.ModelConfig;
import desmoj.core.simulator.Experiment;
import desmoj.core.simulator.TimeInstant;
import desmoj.core.statistic.TimeSeries;
import desmoj.extensions.grafic.util.Plotter;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SimulationRunner implements ILogger {
    private static final String DEFAULT_ROUNDABOUT_CONFIG_PATH = SimulationRunner.class.getResource("/dornbirn-nord.xml").getPath();
    private static final long SimulationDuration = 3 * 60 * 60;
//    private static final long ExecutionSpeed = 50000L;
    private static Map<Street, TimeSeries> timeSeriesMap;

    public static void main(String[] args) throws ConfigParserException {
        Double minTimeBetweenCarArrival = 4.0;
        Double maxTimeBetweenCarArrival = 4.0;

        BetterRoundaboutSimulationModel model = new BetterRoundaboutSimulationModel(null, "", true, false, minTimeBetweenCarArrival, maxTimeBetweenCarArrival);
        Experiment exp = new Experiment("Roundabout Experiment");
        Experiment.setEpsilon(model.getModelTimeUnit());
        Experiment.setReferenceUnit(model.getModelTimeUnit());
        exp.setSeedGenerator((long) (Long.MAX_VALUE * Math.random()));
        exp.getDistributionManager().newSeedAll();
        exp.setShowProgressBar(true);
        exp.setSilent(true);

       model.connectToExperiment(exp);

        IRoundaboutStructure roundaboutStructure = null; // = getRoundaboutStructureFromConfigFile(args, model);

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

        for (AbstractSink sink : roundaboutStructure.getSinks()) {
            printStatisticsForSink(sink);
        }

        try {
            saveQueueLengthsToTextFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
