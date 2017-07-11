package core;

import utils.NodeRoute;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class Algorithm implements Callable<RouteList> {
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
    public RouteList call() {
        System.out.println("Executing " + name);
        RouteList routeList = new RouteList();

        try {
            routeList.initialize(in);
            ArrayList<Route> routes = routeList.getRoutes();

            Best best = new Best(routeList);
            if (type == Algorithm.ALGORITHM_ONE) {
                for (int i = 0; i < routes.size(); i++) {
                    NodeRoute result = best.run(routes.get(i), Best.BEST_RELOCATE);
                    // Apply Relocate
                    NodeRoute result2 = best.run(routes.get(i), Best.BEST_EXCHANGE);
                    // Apply Exchange
                }
            } else {
                for (int i = 0; i < routes.size(); i++) {
                    NodeRoute result = best.run(routes.get(i), Best.BEST_EXCHANGE);
                    // Apply Exchange
                    NodeRoute result2 = best.run(routes.get(i), Best.BEST_RELOCATE);
                    // Apply Relocate
                }
            }
        } catch (Exception ex) {
            System.out.println("[" + name + "]" + " Exception Message" + ex.getMessage());
        }

        return routeList;
    }

    private void print(List<Route> routes) {
        int i = 0;

        System.out.println();
        for (Route route : routes) {
            System.out.print("Numero percorso: " + (i++) + ": ");
            for (Node n : route.getNodes()) {
                System.out.print(n.getIndex() + " ");
            }
            System.out.print("\n");
        }

    }
}
