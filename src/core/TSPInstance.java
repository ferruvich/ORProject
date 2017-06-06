package core;

import java.util.ArrayList;

/**
 * Created by Danieru on 30/05/2017.
 */
public class TSPInstance {

    private int totNodes;
    private int totRoutes;
    private int maxCapacity;
    private ArrayList<Node> nodes;

    public TSPInstance(){
        this.totNodes = 0;
        this.totRoutes = 0;
        this.maxCapacity = 0;
        this.nodes = new ArrayList<>();
    }

    public TSPInstance(int totNodes, int totRoutes, int maxCapacity){
        this.totNodes = totNodes;
        this.totRoutes = totRoutes;
        this.maxCapacity = maxCapacity;
        this.nodes = new ArrayList<>();
    }

    public int getTotNodes() {
        return totNodes;
    }

    public void setTotNodes(int totNodes) {
        this.totNodes = totNodes;
    }

    public int getTotRoutes() {
        return totRoutes;
    }

    public void setTotRoutes(int totRoutes) {
        this.totRoutes = totRoutes;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }

    public void addNode(Node node){
        this.nodes.add(node);
    }
}
