package utils;

import core.Node;
import core.TSPInstance;
import exceptions.DistanceMapException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Danieru on 06/06/2017.
 */
public class DistanceMap {
    private static DistanceMap instance = null;
    private static Map<Integer, Map<Integer, Double>> distanceMap;

    private DistanceMap(){
        distanceMap = new HashMap<>();
    }

    public static DistanceMap getInstance(){
        if(instance == null){
            instance = new DistanceMap();
            return instance;
        }
        return instance;
    }

    public void initialize(TSPInstance tspInstance){
        List<Node> nodes = tspInstance.getNodes();
        for(Node n: nodes){
            Map<Integer, Double> mapOfN = distanceMap.get(n.getIndex());
            if(mapOfN == null){
                mapOfN = new HashMap<>();
            }
            for(Node no: nodes){
                Double xPart = Math.pow(n.getXcoord() - no.getXcoord(), 2);
                Double yPart = Math.pow(n.getYcoord() - no.getYcoord(), 2);
                Double distance = Math.sqrt(xPart + yPart);
                mapOfN.put(no.getIndex(), distance);
            }
            distanceMap.put(n.getIndex(), mapOfN);
        }
    }

    public Double getDistance(Node a, Node b) throws DistanceMapException {
        Map<Integer, Double> mapOfA = distanceMap.get(a.getIndex());
        if(mapOfA == null){
            throw new DistanceMapException("Map not initialized properly");
        }
        Double distance = mapOfA.get(b.getIndex());
        if(distance == null){
            throw new DistanceMapException("Map not initialized properly");
        }
        return distance;
    }
}
