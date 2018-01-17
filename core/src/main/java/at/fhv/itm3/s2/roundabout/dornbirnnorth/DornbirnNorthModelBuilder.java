package at.fhv.itm3.s2.roundabout.dornbirnnorth;

import at.fhv.itm3.s2.roundabout.api.IStructureModelBuilder;
import at.fhv.itm3.s2.roundabout.api.entity.IRoundaboutStructure;
import at.fhv.itm3.s2.roundabout.entity.RoundaboutStructure;
import desmoj.core.simulator.Model;

public class DornbirnNorthModelBuilder implements IStructureModelBuilder {
    @Override
    public IRoundaboutStructure build(Model model) {

        IRoundaboutStructure structure = new RoundaboutStructure(model);

        // TODO implement

        return structure;
    }
}
