package at.fhv.itm3.s2.roundabout.simulation;

import at.fhv.itm3.s2.roundabout.RoundaboutSimulationModel;
import at.fhv.itm3.s2.roundabout.api.entity.AbstractSource;
import at.fhv.itm3.s2.roundabout.api.entity.IRoundaboutStructure;
import at.fhv.itm3.s2.roundabout.api.entity.IRoute;
import at.fhv.itm3.s2.roundabout.api.entity.Street;
import at.fhv.itm3.s2.roundabout.controller.RouteController;
import at.fhv.itm3.s2.roundabout.statistics.StatisticsUpdateEvent;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeSpan;
import desmoj.core.statistic.TimeSeries;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class BetterRoundaboutSimulationModel extends RoundaboutSimulationModel {

    private final static double TIME_BETWEEN_STATISTICS_UPDATE_IN_SECONDS = 60;
    private IRoundaboutStructure _roundaboutStructure;
    private Map<Street, TimeSeries> timeSeriesMap;

    public BetterRoundaboutSimulationModel(Model model, String name, boolean showInReport, boolean showInTrace) {
        super(model, name, showInReport, showInTrace);
    }

    @Override
    public void doInitialSchedules() {
        super.doInitialSchedules();
        for (AbstractSource source : _roundaboutStructure.getSources()) {
            source.startGeneratingCars(this.getRandomTimeBetweenCarArrivals());
        }
        StatisticsUpdateEvent statisticsUpdateEvent = new StatisticsUpdateEvent(this, "StatisticsUpdateEvent", false);
        statisticsUpdateEvent.schedule(this.getRoundaboutStructure().getStreets().iterator().next(), new TimeSpan(0, TimeUnit.SECONDS));

        // Only starting stuff for roundabout, if intersection has to be used, you must start all intersections controller like this:
        // intersection.getController().start();
    }

    public void setRoundaboutStructure(IRoundaboutStructure roundaboutStructure) {
        _roundaboutStructure = roundaboutStructure;
        initRoutes();
    }

    public IRoundaboutStructure getRoundaboutStructure() {
        return _roundaboutStructure;
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

    public void setTimeSeriesMap(Map<Street, TimeSeries> seriesMap) { timeSeriesMap = seriesMap; }

    public void updateTimeSeries(Street street, double queueLength) {
        if (timeSeriesMap.containsKey(street)) {
            timeSeriesMap.get(street).update(queueLength);
        } else {
            throw new IllegalStateException("No TimeSeries defined for this street");
        }
    }

    public double getTimeBetweenStatisticsUpdate() {
        return TIME_BETWEEN_STATISTICS_UPDATE_IN_SECONDS;
    }
}
