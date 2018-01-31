package at.fhv.itm3.s2.roundabout.simulation;

import at.fhv.itm3.s2.roundabout.api.entity.AbstractSink;
import at.fhv.itm3.s2.roundabout.api.entity.IRoundaboutStructure;
import at.fhv.itm3.s2.roundabout.dornbirnnorth.DornbirnNorthModelBuilder;
import at.fhv.itm3.s2.roundabout.util.ConfigParser;
import at.fhv.itm3.s2.roundabout.util.ConfigParserException;
import at.fhv.itm3.s2.roundabout.util.dto.ModelConfig;
import desmoj.core.simulator.Experiment;
import desmoj.core.simulator.TimeInstant;

public class SimulationRunner {
    private static final String DEFAULT_ROUNDABOUT_CONFIG_FILENAME = "roundabout.xml";
    private static final long SimulationDuration = 1440 * 60;
    private static final long ExecutionSpeed = 50000L;

    public static void main(String[] args) {

        BetterRoundaboutSimulationModel model = new BetterRoundaboutSimulationModel(null, "", true, false);
        Experiment exp = new Experiment("Roundabout Experiment");
        Experiment.setEpsilon(model.getModelTimeUnit());
        Experiment.setReferenceUnit(model.getModelTimeUnit());
        exp.setSeedGenerator((long) (Long.MAX_VALUE * Math.random()));
        exp.getDistributionManager().newSeedAll();
        exp.setShowProgressBar(false);
        exp.setSilent(true);

         model.connectToExperiment(exp);

        IRoundaboutStructure roundaboutStructure = getRoundaboutStructureFromConfigFile(args, exp);

        if (roundaboutStructure == null) {
            roundaboutStructure = new DornbirnNorthModelBuilder().build(model);
        }

        model.setRoundaboutStructure(roundaboutStructure);

        TimeInstant stopTime = new TimeInstant(SimulationDuration, model.getModelTimeUnit());
        exp.tracePeriod(new TimeInstant(0L), stopTime);
        exp.stop(stopTime);
        exp.setExecutionSpeedRate(ExecutionSpeed);
        System.out.println("Starting simulation.");
        exp.start();
        System.out.println("Simulation finished. Creating reports.");
        exp.finish();

        for (AbstractSink sink : roundaboutStructure.getSinks()) {
            printStatisticsForSink(sink);
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

    private static IRoundaboutStructure getRoundaboutStructureFromConfigFile(String[] args, Experiment exp) {

        IRoundaboutStructure roundaboutStructure = null;
        try {
            String roundaboutConfigFileName = getArgOrDefault(args, 0, DEFAULT_ROUNDABOUT_CONFIG_FILENAME);
            ConfigParser configParser = new ConfigParser(roundaboutConfigFileName);
            ModelConfig modelConfig = configParser.loadConfig();
            roundaboutStructure = configParser.generateRoundaboutStructure(modelConfig, exp);
        } catch (ConfigParserException e) {
            // LOGGER.error(e);
            // does not work?
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
