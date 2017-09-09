import core.*;
import utils.DistanceMatrix;
import utils.JsonReader;
import utils.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class Euristica2Fasi {
    private static final int NUMBER_OF_ITERATION = 10;
    private static final FilenameFilter filenameFilter = (dir, name) -> name.contains(".json");

    public static void main(String[] args) {
        //for(File f : new File("InstancesJSON").listFiles(filenameFilter)) {
            //String fileName = f.getPath().replace("\\", "/");
            String fileName = "InstancesJSON/K2.json";

            ExecutorService executor = Executors.newFixedThreadPool(4);


            List algorithmOneFutures = new ArrayList<>();
            List algorithmsOne = new ArrayList<>();
            List algorithmTwoFutures = new ArrayList<>();
            List algorithmsTwo = new ArrayList<>();

            for (int i = 0; i < NUMBER_OF_ITERATION; i++) {
                TSPInstance in = TSPInstance.getInstance(fileName);
                DistanceMatrix.getInstance().initialize(in);
                Algorithm algorithmOne = new Algorithm(in, Algorithm.ALGORITHM_ONE, "Iteration 1");
                algorithmsOne.add(algorithmOne)
                ;
                FutureTask futureTask = new FutureTask<>(algorithmOne);
                algorithmOneFutures.add(futureTask);
                executor.execute(futureTask);
            }
            for (int i = 0; i < NUMBER_OF_ITERATION; i++) {
                TSPInstance in = TSPInstance.getInstance(fileName);
                DistanceMatrix.getInstance().initialize(in);
                Algorithm algorithmTwo = new Algorithm(in, Algorithm.ALGORITHM_TWO, "Iteration 2");
                algorithmsTwo.add(algorithmTwo);
                FutureTask futureTask = new FutureTask<>(algorithmTwo);
                algorithmTwoFutures.add(futureTask);
                executor.execute(futureTask);
            }
            getBestRouteList("Algorithm One", algorithmOneFutures, algorithmsOne, fileName);
            getBestRouteList("Algorithm Two", algorithmTwoFutures, algorithmsTwo, fileName);

            executor.shutdown();
        //}
    }

    private static void getBestRouteList(String algorithmName, List<FutureTask<Pair<RouteList, RouteList>>> algorithmFutures, List<Algorithm> algorithms,  String fileName) {
        List<Pair<RouteList, RouteList>> pairs = new ArrayList<>();
        List futuresCompleted = new ArrayList<>();
        Map timeOfFutures = new HashMap<>(algorithms.size());
        while(!(futuresCompleted.size() == algorithmFutures.size())) {
            for (FutureTask<Pair<RouteList, RouteList>> task : algorithmFutures) {
                try {
                    if (task.isDone() && !futuresCompleted.contains(task)) {
                        pairs.add(task.get());
                        futuresCompleted.add(task);
                        timeOfFutures.put(algorithmFutures.indexOf(task),
                                algorithms.get(algorithmFutures.indexOf(task)).getTime());
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
        Pair<RouteList, RouteList> bestPair = new Pair<>(new RouteList(), new RouteList());
        for (Pair<RouteList, RouteList> routeList : pairs) {

            if (routeList.getR().getTotalCost() < bestPair.getR().getTotalCost())
                bestPair = routeList;

        }
        System.out.println(algorithmName);
        printPair(bestPair);
        int index = pairs.indexOf(bestPair);
        saveBest(bestPair.getR(), (Long) timeOfFutures.get(index), algorithmName, fileName);
    }

    private static void printPair(Pair<RouteList, RouteList> routeList) {
        System.out.println("Better Route List");
        for(Route r: routeList.getR().getRoutes()){
            Best.printRoute(r);
        }
        routeList.getR().updateTotalCost();
        System.out.println("TotalCost: " + routeList.getR().getTotalCost());
    }

    private static void saveBest(RouteList routeList, Long time, String algorithmName, String fileName){
        String nameFile = "Results/" + fileName.substring(fileName.indexOf('/')+1, fileName.indexOf('.')) + "-best_" + algorithmName.trim() + ".txt";
        BufferedWriter writer = null;
        int i = 0;

        System.out.println("Saving...");

        try {
            File file = new File(nameFile);
            writer = new BufferedWriter(new FileWriter(file));

            writer.write("Soluzione del problema " + fileName.substring(fileName.indexOf('/')+1, fileName.indexOf('.')) + "\n\n");
            writer.write("Dettagli della soluzione: \n\tCosto totale: " + routeList.getTotalCost());
            writer.write("\n\tTempo Trascorso: "+ time + "\n\n");

            for (Route route : routeList.getRoutes()) {
                writer.write("Route " + (++i) + ":\n");
                writer.write(route.toString());
                writer.newLine();
                writer.newLine();
            }
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
