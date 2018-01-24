package at.fhv.itm3.s2.roundabout.simulation;

import at.fhv.itm3.s2.roundabout.RoundaboutSimulationModel;
import at.fhv.itm3.s2.roundabout.api.entity.IRoundaboutStructure;
import at.fhv.itm3.s2.roundabout.entity.RoundaboutSource;
import desmoj.core.simulator.Model;

public class BetterRoundaboutSimulationModel extends RoundaboutSimulationModel {
    private IRoundaboutStructure _roundaboutStructure;

    public BetterRoundaboutSimulationModel(Model model, String name, boolean showInReport, boolean showInTrace) {
        super(model, name, showInReport, showInTrace);
    }

    @Override
    public void doInitialSchedules() {
        super.doInitialSchedules();
        for (RoundaboutSource source : _roundaboutStructure.get
             ) {

        }
    }

    public void setRoundaboutStructure(IRoundaboutStructure roundaboutStructure) {
        _roundaboutStructure = roundaboutStructure;
    }
}
