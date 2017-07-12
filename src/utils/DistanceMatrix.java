package utils;

import core.Node;
import core.TSPInstance;
import exceptions.DistanceMatrixException;

import java.util.List;

public class DistanceMatrix {
    private static DistanceMatrix instance = null;
    private static double[][] distanceMatrix = null;

    public static DistanceMatrix getInstance() {
        if (instance == null) {
            instance = new DistanceMatrix();
            return instance;
        }
        return instance;
    }

    public void initialize(TSPInstance tspInstance) {
        this.distanceMatrix = new double[tspInstance.getTotNodes() + 1][tspInstance.getTotNodes() + 1];
        List<Node> nodes = tspInstance.getNodes();
        for (Node n : nodes) {
            for (Node no : nodes) {
                double xPart = Math.pow(n.getXcoord() - no.getXcoord(), 2);
                double yPart = Math.pow(n.getYcoord() - no.getYcoord(), 2);
                double distance = Math.sqrt(xPart + yPart);
                distanceMatrix[n.getIndex()][no.getIndex()] = distance;
            }
        }
    }

    public double getDistance(Node a, Node b) throws DistanceMatrixException {
        if (distanceMatrix == null) {
            throw new DistanceMatrixException("Matrix not initialized properly");
        } else {
            return distanceMatrix[a.getIndex()][b.getIndex()];
        }
    }

    public double[] getFullDistances(Node n) throws DistanceMatrixException {
        if (distanceMatrix == null) {
            throw new DistanceMatrixException("Matrix not initialized properly");
        } else {
            return distanceMatrix[n.getIndex()];
        }
    }
}
