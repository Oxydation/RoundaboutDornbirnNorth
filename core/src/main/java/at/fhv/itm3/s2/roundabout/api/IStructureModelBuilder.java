package at.fhv.itm3.s2.roundabout.api;

import at.fhv.itm3.s2.roundabout.api.entity.IRoundaboutStructure;
import desmoj.core.simulator.Model;

public interface IStructureModelBuilder {
    /**
     * Builds the roundabout structure via program code.
     * @param model The base simulation model.
     * @return The created roundabout structure.
     */
    IRoundaboutStructure build(Model model);
}
