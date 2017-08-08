package core;

import utils.NodeRoute;
import utils.Pair;

import java.util.ArrayList;
import java.util.List;
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

    /**
     * ALGORITHM_ONE applies the Best Exchange strategy until there is no more gain, then applies Best Relocate in the same way
     * @return
     */

    @Override
    public Pair<RouteList, RouteList> call() {
        RouteList original = new RouteList();
        RouteList routeList = new RouteList();
        List<NodeRoute> nodeToApply = new ArrayList<>();

        try {
            Best best = null;

            routeList.initialize(in);
            routeList.updateTotalCost();
            original = new RouteList(routeList);

            ArrayList<Route> routes = routeList.getRoutes();
            if (type == Algorithm.ALGORITHM_ONE) {
                System.out.println("Best exchange start");
                int i = 0;
                while (true) {
                    System.out.println("RouteList cost before: " + routeList.getTotalCost());
                    for (Route currentRoute : routes) {
                        best = new Best(routeList, new BestExchange());
                        NodeRoute candidateBestExchange = best.estimateStrategy(currentRoute);
//                        System.out.println("\tCandidate: " + candidateBestExchange.getGain());
                        if (candidateBestExchange != null) {
                            nodeToApply.add(candidateBestExchange);
                        }
//                        System.out.println("\t\tRouteList cost after candidate: " + routeList.getTotalCost());
                    }
                    routeList.updateTotalCost();
//                    System.out.println("RouteList cost before: " + routeList.getTotalCost());
                    if(!nodeToApply.isEmpty()) {
                        NodeRoute n = bestNodeRoute(nodeToApply);
                        System.out.println("\tBestCandidate: " + n.getGain());
                        best.applyStrategy(n);
                    } else {
                        break;
                    }
                    nodeToApply.clear();
                    System.out.println("\t\tRouteList cost after: " + routeList.getTotalCost());
                }

                for(Route r : routeList.getRoutes()){
                    Best.printRoute(r);
                }

                System.out.println("Best relocate start");
                while (true) {
                    System.out.println("RouteList cost before: " + routeList.getTotalCost());
                    for (Route currentRoute : routes) {
                        best = new Best(routeList, new BestRelocate());

//                        System.out.println("\nPercorsi pre-estimate: ");
//
//                        for(Route r : routeList.getRoutes()){
//                            Best.printRoute(r);
//                        }

                        NodeRoute candidateBestRelocate = best.estimateStrategy(currentRoute);
//                        System.out.println("\tCandidate: " + candidateBestRelocate.getGain());

//                        System.out.println("\nPercorsi post-estimate: ");
//
//                        for(Route r : routeList.getRoutes()){
//                            Best.printRoute(r);
//                        }
//
//                        System.in.read();

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
                    System.out.println("\t\tRouteList cost after: " + routeList.getTotalCost());
                }

            } else {
                while (true) {
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
            System.out.println("[" + name + "]" + " Exception Message: " + ex.getMessage());
        }
        return new Pair<RouteList, RouteList>(original, routeList);
    }

    public NodeRoute bestNodeRoute(List<NodeRoute> list){
//        System.out.println("max: " + list.stream().max((fn, sn) -> Double.compare(fn.getGain(), sn.getGain())).get().getGain());
        return list.stream().max((fn, sn) -> Double.compare(fn.getGain(), sn.getGain())).get();
    }


}
