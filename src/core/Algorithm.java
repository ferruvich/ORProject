package core;

import utils.NodeRoute;
import utils.Pair;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class Algorithm implements Callable<Pair<RouteList, RouteList>> {
    public static final int ALGORITHM_ONE = 0;
    public static final int ALGORITHM_TWO = 1;
    private TSPInstance in;
    private int type;
    private String name;

    public Algorithm(TSPInstance in, int type, String name) {
        this.in = in;
        this.type = type;
        this.name = name;
    }


    @Override
    public Pair<RouteList, RouteList> call() {
        //System.out.println("Executing " + name);
        RouteList original = new RouteList();
        RouteList routeList = new RouteList();

        try {
            Best best;

            routeList.initialize(in);
            original = new RouteList(routeList);

            ArrayList<Route> routes = routeList.getRoutes();
            if (type == Algorithm.ALGORITHM_ONE) {
                for (int i = 0; i < routes.size(); i++) {
                    Route currentRoute = routes.get(i);

                    best = new Best(routeList, new BestExchange());
                    NodeRoute candidateBestExchange = best.estimateStrategy(currentRoute);
                    if (candidateBestExchange != null)
                        routeList = best.applyStrategy(candidateBestExchange);

                    best = new Best(routeList, new BestRelocate());
                    NodeRoute candidateBestRelocate = best.estimateStrategy(currentRoute);
                    if (candidateBestRelocate != null)
                        routeList = best.applyStrategy(candidateBestRelocate);
                }
            } else {
                for (int i = 0; i < routes.size(); i++) {
                    Route currentRoute = routes.get(i);

                    best = new Best(routeList, new BestRelocate());
                    NodeRoute candidateBestRelocate = best.estimateStrategy(currentRoute);
                    if (candidateBestRelocate != null)
                        routeList = best.applyStrategy(candidateBestRelocate);

                    best = new Best(routeList, new BestExchange());
                    NodeRoute candidateBestExchange = best.estimateStrategy(currentRoute);
                    if (candidateBestExchange != null)
                        routeList = best.applyStrategy(candidateBestExchange);
                }
            }
        } catch (Exception ex) {
            System.out.println("[" + name + "]" + " Exception Message: " + ex.getMessage());
        }
        return new Pair<RouteList, RouteList>(original, routeList);
    }
}
