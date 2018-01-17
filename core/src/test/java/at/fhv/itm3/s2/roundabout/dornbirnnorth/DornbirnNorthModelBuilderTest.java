package at.fhv.itm3.s2.roundabout.dornbirnnorth;

import at.fhv.itm3.s2.roundabout.RoundaboutSimulationModel;
import at.fhv.itm3.s2.roundabout.api.IStructureModelBuilder;
import at.fhv.itm3.s2.roundabout.api.entity.IRoundaboutStructure;
import org.junit.jupiter.api.Test;

class DornbirnNorthModelBuilderTest {

    @Test
    void build_roundabout_dornbirnnorth() {
        RoundaboutSimulationModel model = new RoundaboutSimulationModel(null, "", false, false);
        IStructureModelBuilder builder = new DornbirnNorthModelBuilder();
        IRoundaboutStructure result = builder.build(model);
    }
}