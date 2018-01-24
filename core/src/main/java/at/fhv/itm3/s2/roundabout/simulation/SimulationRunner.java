package at.fhv.itm3.s2.roundabout.simulation;

import at.fhv.itm3.s2.roundabout.api.entity.IRoundaboutStructure;
import at.fhv.itm3.s2.roundabout.dornbirnnorth.DornbirnNorthModelBuilder;
import at.fhv.itm3.s2.roundabout.util.ConfigParser;
import at.fhv.itm3.s2.roundabout.util.ConfigParserException;
import at.fhv.itm3.s2.roundabout.util.ILogger;
import at.fhv.itm3.s2.roundabout.util.dto.RoundAboutConfig;
import desmoj.core.simulator.Experiment;
import desmoj.core.simulator.TimeInstant;

import java.util.concurrent.TimeUnit;

public class SimulationRunner implements ILogger {
    private static final String DEFAULT_ROUNDABOUT_CONFIG_FILENAME = "roundabout.xml";

    public static void main(String[] args) {

        BetterRoundaboutSimulationModel model = new BetterRoundaboutSimulationModel(null, "", true, false);

        Experiment exp = new Experiment("Roundabout Experiment");
        Experiment.setReferenceUnit(TimeUnit.SECONDS);
        Experiment.setEpsilon(TimeUnit.SECONDS);
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

        TimeInstant stopTime = new TimeInstant(1440L, TimeUnit.MINUTES);
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
            RoundAboutConfig roundAboutConfig = configParser.loadConfig();
            roundaboutStructure = configParser.generateRoundaboutStructure(roundAboutConfig, exp);
        } catch (ConfigParserException e) {
            // LOGGER.error(e);
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
