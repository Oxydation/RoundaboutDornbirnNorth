package at.fhv.itm3.s2.roundabout.simulation;

import at.fhv.itm3.s2.roundabout.api.entity.IRoundaboutStructure;
import at.fhv.itm3.s2.roundabout.dornbirnnorth.DornbirnNorthModelBuilder;
import at.fhv.itm3.s2.roundabout.util.ConfigParser;
import at.fhv.itm3.s2.roundabout.util.ConfigParserException;
import at.fhv.itm3.s2.roundabout.util.ILogger;
import at.fhv.itm3.s2.roundabout.util.dto.ModelConfig;
import desmoj.core.simulator.Experiment;
import desmoj.core.simulator.TimeInstant;

import java.util.concurrent.TimeUnit;

public class SimulationRunner implements ILogger {
    private static final String DEFAULT_ROUNDABOUT_CONFIG_FILENAME = "roundabout.xml";
    private static final long SimulationDurationInMinutes = 1440L;

    public static void main(String[] args) {
        Experiment exp = new Experiment("Roundabout Experiment");
        Experiment.setEpsilon(TimeUnit.SECONDS);
        Experiment.setReferenceUnit(TimeUnit.SECONDS);
        exp.setSeedGenerator((long) (Long.MAX_VALUE * Math.random()));
        exp.getDistributionManager().newSeedAll();
        exp.setShowProgressBar(false);
        exp.setSilent(true);

        BetterRoundaboutSimulationModel model = new BetterRoundaboutSimulationModel(null, "", true, false);
        model.connectToExperiment(exp);

        IRoundaboutStructure roundaboutStructure = getRoundaboutStructureFromConfigFile(args, exp);

        if (roundaboutStructure == null) {
            roundaboutStructure = new DornbirnNorthModelBuilder().build(model);
        }

        model.setRoundaboutStructure(roundaboutStructure);

        TimeInstant stopTime = new TimeInstant(SimulationDurationInMinutes, TimeUnit.MINUTES);
        exp.tracePeriod(new TimeInstant(0L), stopTime);
        exp.stop(stopTime);
        System.out.println("Starting simulation.");
        exp.start();
        System.out.println("Simulation finished. Creating reports.");
        exp.finish();
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
