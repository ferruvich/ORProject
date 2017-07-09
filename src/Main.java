import core.*;
import utils.JsonReader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by Danieru on 30/05/2017.
 */
public class Main {
    public static final int NUMBER_OF_ITERATION = 10;

    public static void main(String[] args) {
        TSPInstance in = JsonReader.getInstance().getSpecifications("InstancesJSON/N1.json");

        ExecutorService executor = Executors.newFixedThreadPool(2);

        List<FutureTask<RouteList>> algorithmOneFutures = new ArrayList<FutureTask<RouteList>>();
        for (int i = 0; i < Main.NUMBER_OF_ITERATION; i++) {
            Algorithm algorithmOne = new Algorithm(in, Algorithm.ALGORITHM_ONE, "Iteration " + (i + 1));
            FutureTask<RouteList> futureTask = new FutureTask<RouteList>(algorithmOne);
            algorithmOneFutures.add(futureTask);
            executor.execute(futureTask);
        }

        List<FutureTask<RouteList>> algorithmTwoFutures = new ArrayList<FutureTask<RouteList>>();
        for (int j = 0; j < Main.NUMBER_OF_ITERATION; j++) {
            Algorithm algorithmTwo = new Algorithm(in, Algorithm.ALGORITHM_TWO, "Iteration " + (j + 11));
            FutureTask<RouteList> futureTask = new FutureTask<RouteList>(algorithmTwo);
            algorithmTwoFutures.add(futureTask);
            executor.execute(futureTask);
        }

        getBestRouteList("Algorithm One", algorithmOneFutures);
        getBestRouteList("Algorithm Two", algorithmTwoFutures);

        executor.shutdown();
    }

    private static void getBestRouteList(String name, List<FutureTask<RouteList>> algorithmFutures) {
        List<RouteList> routeLists = new ArrayList<RouteList>();
        for (FutureTask<RouteList> task : algorithmFutures) {
            try {
                routeLists.add(task.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
        RouteList bestRouteList = new RouteList();
        for (RouteList routeList : routeLists) {

            if (routeList.getTotalCost() < bestRouteList.getTotalCost())
                bestRouteList = routeList;

        }
        System.out.print("\n" + name);
        print(bestRouteList);
    }

    private static void print(RouteList routeList) {
        int i = 0;

        System.out.println();
        for (Route route : routeList.getRoutes()) {
            System.out.print("Numero percorso: " + (i++) + ": ");
            for (Node n : route.getNodes()) {
                System.out.print(n.getIndex() + " ");
            }
            System.out.print("\n");
        }

    }
}
