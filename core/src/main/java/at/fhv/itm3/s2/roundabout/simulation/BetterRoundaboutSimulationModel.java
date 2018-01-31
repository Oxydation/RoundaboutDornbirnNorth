package at.fhv.itm3.s2.roundabout.simulation;

import at.fhv.itm3.s2.roundabout.RoundaboutSimulationModel;
import at.fhv.itm3.s2.roundabout.api.entity.AbstractSource;
import at.fhv.itm3.s2.roundabout.api.entity.IRoundaboutStructure;
import at.fhv.itm3.s2.roundabout.api.entity.IRoute;
import at.fhv.itm3.s2.roundabout.controller.RouteController;
import desmoj.core.simulator.Model;

import java.util.LinkedList;
import java.util.List;

public class BetterRoundaboutSimulationModel extends RoundaboutSimulationModel {
    private IRoundaboutStructure _roundaboutStructure;

    public BetterRoundaboutSimulationModel(Model model, String name, boolean showInReport, boolean showInTrace) {
        super(model, name, showInReport, showInTrace);
    }

    @Override
    public void doInitialSchedules() {
        super.doInitialSchedules();
        for (AbstractSource source : _roundaboutStructure.getSources()) {
            source.startGeneratingCars(this.getRandomTimeBetweenCarArrivals());
        }

        // Only starting stuff for roundabout, if intersection has to be used, you must start all intersections controller like this:
        // intersection.getController().start();
    }

    public void setRoundaboutStructure(IRoundaboutStructure roundaboutStructure) {
        _roundaboutStructure = roundaboutStructure;
        initRoutes();
    }

    private void initRoutes() {
        for (AbstractSource source : _roundaboutStructure.getSources()) {
            List<IRoute> routes = new LinkedList<>();

            for (IRoute route : _roundaboutStructure.getRoutes()) {
                if (route.getSource() == source) {
                    routes.add(route);
                }
            }
            RouteController.getInstance(this).getSources().add(source);
            RouteController.getInstance(this).getRoutes().put(source, routes);
        }
    }
}
