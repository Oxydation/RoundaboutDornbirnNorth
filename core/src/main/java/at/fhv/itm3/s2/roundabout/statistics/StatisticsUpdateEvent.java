package at.fhv.itm3.s2.roundabout.statistics;

import at.fhv.itm3.s2.roundabout.api.entity.Street;
import at.fhv.itm3.s2.roundabout.simulation.BetterRoundaboutSimulationModel;
import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.Entity;
import desmoj.core.simulator.Event;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeSpan;

import java.util.concurrent.TimeUnit;


public class StatisticsUpdateEvent extends Event<Street>  {

    private BetterRoundaboutSimulationModel model;

    public StatisticsUpdateEvent(Model model, String s, boolean b) {
        super(model, s, b);
        this.model = (BetterRoundaboutSimulationModel)getModel();
    }

    @Override
    public void eventRoutine(Street entity) throws SuspendExecution {
        for (Street street: model.getRoundaboutStructure().getRoundaboutInlets()) {
            model.updateTimeSeries(street, street.getCarQueue().size());
        }
        StatisticsUpdateEvent event = new StatisticsUpdateEvent(model, "StatisticsUpdateEvent", false);
        event.schedule(entity, new TimeSpan(model.getTimeBetweenStatisticsUpdate(), TimeUnit.SECONDS));
    }
}
