import core.*;
import utils.DistanceMatrix;
import utils.JsonReader;
import utils.Pair;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class Euristica2Fasi {
    public static final int NUMBER_OF_ITERATION = 10;

    public static void main(String[] args) {
        String fileName = "InstancesJSON/F2.json";

        ExecutorService executor = Executors.newFixedThreadPool(3);


        List<FutureTask<Pair<RouteList, RouteList>>> algorithmOneFutures = new ArrayList<FutureTask<Pair<RouteList, RouteList>>>();

        for(int i = 0; i< NUMBER_OF_ITERATION; i++) {
            TSPInstance in = TSPInstance.getInstance(fileName);
            DistanceMatrix.getInstance().initialize(in);
            Algorithm algorithmOne = new Algorithm(in, Algorithm.ALGORITHM_ONE, "Iteration 1");// + (i + 1)); // exchange
            FutureTask<Pair<RouteList, RouteList>> futureTask = new FutureTask<Pair<RouteList, RouteList>>(algorithmOne);
            algorithmOneFutures.add(futureTask);
            executor.execute(futureTask);
        }
        getBestRouteList("Algorithm One", algorithmOneFutures, fileName);

        executor.shutdown();
    }

    private static void getBestRouteList(String name, List<FutureTask<Pair<RouteList, RouteList>>> algorithmFutures, String fileName) {
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
        saveBest(bestPair.getR(), fileName);
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

    private static void saveBest(RouteList routeList, String fileName){
        String nameFile = "Results/" + fileName.substring(fileName.indexOf('/')+1, fileName.indexOf('.')) + "-best.txt";
        BufferedWriter writer = null;
        int i = 0;

        System.out.println("Saving...");

        try {
            File file = new File(nameFile);
            writer = new BufferedWriter(new FileWriter(file));

            for (Route route : routeList.getRoutes()) {
                writer.write("Route " + (++i) + " " + route.toString());
                writer.newLine();
            }

            writer.newLine();
            writer.write("Total cost: " + Double.toString(routeList.getTotalCost()));
        }catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Saved!");

    }
}
