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
public class DistanceMatrix {
    private static DistanceMatrix instance = null;
    private static Double[][] distanceMatrix = null;

    public static DistanceMatrix getInstance(){
        if(instance == null){
            instance = new DistanceMatrix();
            return instance;
        }
        return instance;
    }

    public void initialize(TSPInstance tspInstance) {
        this.distanceMatrix = new Double[tspInstance.getTotNodes() + 1][tspInstance.getTotNodes() + 1];
        List<Node> nodes = tspInstance.getNodes();
        for (Node n : nodes) {
            for (Node no : nodes) {
                Double xPart = Math.pow(n.getXcoord() - no.getXcoord(), 2);
                Double yPart = Math.pow(n.getYcoord() - no.getYcoord(), 2);
                Double distance = Math.sqrt(xPart + yPart);
                distanceMatrix[n.getIndex()][no.getIndex()] = distance;
            }
        }
    }

    public Double getDistance(Node a, Node b) throws DistanceMapException {
        if(distanceMatrix == null){
            throw new DistanceMapException("Matrix not initialized properly");
        } else{
            return distanceMatrix[a.getIndex()][b.getIndex()];
        }
    }

    public Double[] getFullDistances(Node n) throws DistanceMapException {
        if(distanceMatrix == null){
            throw new DistanceMapException("Matrix not initialized properly");
        } else{
            return distanceMatrix[n.getIndex()];
        }
    }
}
