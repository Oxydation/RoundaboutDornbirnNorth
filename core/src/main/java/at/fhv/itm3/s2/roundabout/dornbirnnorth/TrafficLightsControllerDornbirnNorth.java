package at.fhv.itm3.s2.roundabout.dornbirnnorth;

import at.fhv.itm3.s2.roundabout.RoundaboutSimulationModel;
import at.fhv.itm3.s2.roundabout.api.controller.ITrafficLightController;
import at.fhv.itm3.s2.roundabout.api.entity.Street;
import at.fhv.itm3.s2.roundabout.event.RoundaboutEventFactory;
import at.fhv.itm3.s2.roundabout.event.ToggleTrafficLightStateEvent;
import desmoj.core.simulator.TimeInstant;
import desmoj.core.simulator.TimeSpan;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TrafficLightsControllerDornbirnNorth implements ITrafficLightController {
    private final RoundaboutSimulationModel model;
    private final double minTimeToNextRed;
    protected RoundaboutEventFactory roundaboutEventFactory = RoundaboutEventFactory.getInstance();

    private final int maxQueueSize;
    private Map<String, Street> inlets;
    private double redPhaseTimeSpan;
    private TimeInstant inletLauterachRedUntil;
    private TimeInstant inletA14RedUntil;

    public TrafficLightsControllerDornbirnNorth(RoundaboutSimulationModel model, double redPhaseTimeSpan, double minTimeToNextRed, int maxQueueSize) {
        this.model = model;
        this.redPhaseTimeSpan = redPhaseTimeSpan;
        this.minTimeToNextRed = minTimeToNextRed;
        this.maxQueueSize = maxQueueSize;
        this.inletLauterachRedUntil = new TimeInstant(0);
        this.inletA14RedUntil = new TimeInstant(0);
    }

    public void setInlets(Map<String, Street> inlets) {
        this.inlets = inlets;
    }

    public void controlTrafficLights() {
        if (inlets == null || inlets.size() == 0) {
            throw new IllegalStateException("inlets needs to be set for TrafficLightControllerDornbirnNorth");
        }

        if (inlets.get("section_lauterach_inlet#1").getCarQueue().size() > maxQueueSize) {
            if (model.presentTime().compareTo(inletLauterachRedUntil) >= 0) {
                Street streetToToggle = inlets.get("section_achrain_inlet#1");

                ToggleTrafficLightStateEvent toggleTrafficLightStateEventRed = roundaboutEventFactory.createToggleTrafficLightStateEvent(model);
                toggleTrafficLightStateEventRed.schedule(streetToToggle, new TimeSpan(0.1, TimeUnit.SECONDS));

                ToggleTrafficLightStateEvent toggleTrafficLightStateEventGreen = roundaboutEventFactory.createToggleTrafficLightStateEvent(model);
                toggleTrafficLightStateEventGreen.schedule(streetToToggle, new TimeSpan(this.redPhaseTimeSpan, TimeUnit.SECONDS));

                inletLauterachRedUntil = new TimeInstant(toggleTrafficLightStateEventGreen.scheduledNext().getTimeAsDouble() + minTimeToNextRed, TimeUnit.SECONDS);
            }
        }

        if (inlets.get("section_schwefel_inlet#1").getCarQueue().size() > maxQueueSize) {
            if (model.presentTime().compareTo(inletA14RedUntil) >= 0) {
                Street streetToToggle = inlets.get("section_a14_inlet#1");

                ToggleTrafficLightStateEvent toggleTrafficLightStateEventRed = roundaboutEventFactory.createToggleTrafficLightStateEvent(model);
                toggleTrafficLightStateEventRed.schedule(streetToToggle, new TimeSpan(0.1, TimeUnit.SECONDS));

                ToggleTrafficLightStateEvent toggleTrafficLightStateEventGreen = roundaboutEventFactory.createToggleTrafficLightStateEvent(model);
                toggleTrafficLightStateEventGreen.schedule(streetToToggle, new TimeSpan(this.redPhaseTimeSpan, TimeUnit.SECONDS));

                inletA14RedUntil = new TimeInstant(toggleTrafficLightStateEventGreen.scheduledNext().getTimeAsDouble() + minTimeToNextRed);
            }
        }
    }
}
