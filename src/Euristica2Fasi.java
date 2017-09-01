import core.*;
import utils.DistanceMatrix;
import utils.JsonReader;
import utils.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class Euristica2Fasi {
    public static final int NUMBER_OF_ITERATION = 10;

    public static void main(String[] args) {
        TSPInstance in = TSPInstance.getInstance("InstancesJSON/N4.json");
        DistanceMatrix.getInstance().initialize(in);

        ExecutorService executor = Executors.newFixedThreadPool(2);


        List<FutureTask<Pair<RouteList, RouteList>>> algorithmOneFutures = new ArrayList<FutureTask<Pair<RouteList, RouteList>>>();

        Algorithm algorithmOne = new Algorithm(in, Algorithm.ALGORITHM_ONE, "Iteration 1");// + (i + 1)); // exchange
        FutureTask<Pair<RouteList, RouteList>> futureTask = new FutureTask<Pair<RouteList, RouteList>>(algorithmOne);
        algorithmOneFutures.add(futureTask);
        executor.execute(futureTask);
        getBestRouteList("Algorithm One", algorithmOneFutures);

        executor.shutdown();
    }

    private static void getBestRouteList(String name, List<FutureTask<Pair<RouteList, RouteList>>> algorithmFutures) {
        List<Pair<RouteList, RouteList>> pairs = new ArrayList<Pair<RouteList, RouteList>>();
        for (FutureTask<Pair<RouteList, RouteList>> task : algorithmFutures) {
            try {
                pairs.add(task.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        Pair<RouteList, RouteList> bestPair = new Pair<RouteList, RouteList>(new RouteList(), new RouteList());
        for (Pair<RouteList, RouteList> routeList : pairs) {

            if (routeList.getR().getTotalCost() < bestPair.getR().getTotalCost())
                bestPair = routeList;

        }
        System.out.println(name);
        printPair(bestPair);
    }

    private static void printPair(Pair<RouteList, RouteList> routeList) {
        System.out.println("Better Route List");
        for(Route r: routeList.getR().getRoutes()){
            Best.printRoute(r);
        }
        routeList.getR().updateTotalCost();
        System.out.println("TotalCost: " + routeList.getR().getTotalCost());
    }

    private static void printRouteList(RouteList routeList) {
        int i = 0;

        for (Route route : routeList.getRoutes()) {
            System.out.print("Numero percorso: " + (i++) + ": ");
            for (Node n : route.getNodes()) {
                System.out.print(n.getIndex() + " ");
            }
            System.out.println();
        }
    }
}
