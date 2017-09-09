package core;

import utils.NodeRoute;
import utils.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;

public class Algorithm implements Callable<Pair<RouteList, RouteList>> {
    public static final int ALGORITHM_ONE = 0;
    public static final int ALGORITHM_TWO = 1;
    private TSPInstance in;
    private int type;
    private String name;
    private Long time;

    public Algorithm(TSPInstance in, int type, String name) {
        this.in = in;
        this.type = type;
        this.name = name;
    }

    /**
     * ALGORITHM_ONE applies the Best Exchange strategy until there is no more gain, then applies Best Relocate in the same way
     * @return
     */

    @Override
    public Pair<RouteList, RouteList> call() {
        Long start = System.currentTimeMillis();
        RouteList original = new RouteList();
        RouteList routeList = new RouteList();
        List<NodeRoute> nodeToApply = new ArrayList<>();

        try {
            Best best = null;
            routeList.initialize(in);
            original = new RouteList(routeList);

            if (type == Algorithm.ALGORITHM_ONE) {
                while (true){
                    ArrayList<Route> routes = routeList.getRoutes();
                    for (Route currentRoute : routes) {
                        best = new Best(routeList, new BestExchange());
                        NodeRoute candidateBestExchange = best.estimateStrategy(currentRoute);
                        if (candidateBestExchange != null) {
                            nodeToApply.add(candidateBestExchange);
                        }
                    }
                    routeList.updateTotalCost();
                    if(!nodeToApply.isEmpty()) {
                        NodeRoute n = bestNodeRoute(nodeToApply);
                        best.applyStrategy(n);
                    } else {
                        break;
                    }
                    nodeToApply.clear();
                }

                while (true) {
                    ArrayList<Route> routes = routeList.getRoutes();
                    for (Route currentRoute : routes) {
                        best = new Best(routeList, new BestRelocate());
                        NodeRoute candidateBestRelocate = best.estimateStrategy(currentRoute);
                        if (candidateBestRelocate != null) {
                            nodeToApply.add(candidateBestRelocate);
                        }
                    }
                    if(!nodeToApply.isEmpty()) {
                        best.applyStrategy(bestNodeRoute(nodeToApply));
                    } else {
                        break;
                    }
                    nodeToApply.clear();
                }

            } else {
                while (true) {
                    ArrayList<Route> routes = routeList.getRoutes();
                    for (Route currentRoute : routes) {
                        best = new Best(routeList, new BestRelocate());
                        NodeRoute candidateBestRelocate = best.estimateStrategy(currentRoute);

                        if (candidateBestRelocate != null) {
                            nodeToApply.add(candidateBestRelocate);
                        }
                    }
                    if(!nodeToApply.isEmpty()) {
                        best.applyStrategy(bestNodeRoute(nodeToApply));
                    } else {
                        break;
                    }
                    nodeToApply.clear();
                }
                while (true) {
                    ArrayList<Route> routes = routeList.getRoutes();
                    for (Route currentRoute : routes) {
                        best = new Best(routeList, new BestExchange());
                        NodeRoute candidateBestExchange = best.estimateStrategy(currentRoute);

                        if (candidateBestExchange != null) {
                            nodeToApply.add(candidateBestExchange);
                        }
                    }
                    if(!nodeToApply.isEmpty()) {
                        best.applyStrategy(bestNodeRoute(nodeToApply));
                    } else {
                        break;
                    }
                    nodeToApply.clear();
                }
            }
        } catch (Exception ex) {
            //System.out.println("[" + name + "]" + " Exception Message: " + ex.getMessage());
        }
        Long end = System.currentTimeMillis();
        this.time = end-start;
        return new Pair<>(original, routeList);
    }

    public NodeRoute bestNodeRoute(List<NodeRoute> list){
        return list.stream().max(Comparator.comparingDouble(NodeRoute::getGain)).get();
    }

    public Long getTime(){
        return this.time;
    }
}
