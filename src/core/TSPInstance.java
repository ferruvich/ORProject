package core;

import java.util.ArrayList;

public class TSPInstance {

    private int totNodes;
    private int totRoutes;
    private int maxCapacity;
    private ArrayList<Integer> completeTsp;
    private ArrayList<Integer> lineHaulTsp;
    private ArrayList<Integer> backHaulTsp;
    private ArrayList<Node> nodes;

    public TSPInstance() {
        this.totNodes = 0;
        this.totRoutes = 0;
        this.maxCapacity = 0;
        this.completeTsp = new ArrayList<>();
        this.lineHaulTsp = new ArrayList<>();
        this.backHaulTsp = new ArrayList<>();
        this.nodes = new ArrayList<>();
    }

    public TSPInstance(int totNodes, int totRoutes, int maxCapacity) {
        this.totNodes = totNodes;
        this.totRoutes = totRoutes;
        this.maxCapacity = maxCapacity;
        this.completeTsp = new ArrayList<>();
        this.lineHaulTsp = new ArrayList<>();
        this.backHaulTsp = new ArrayList<>();
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

    public ArrayList<Integer> getCompleteTsp() {
        return completeTsp;
    }

    public void setCompleteTsp(ArrayList<Integer> completeTsp) {
        this.completeTsp = completeTsp;
    }

    public ArrayList<Integer> getLineHaulTsp() {
        return lineHaulTsp;
    }

    public void setLineHaulTsp(ArrayList<Integer> lineHaulTsp) {
        this.lineHaulTsp = lineHaulTsp;
    }

    public ArrayList<Integer> getBackHaulTsp() {
        return backHaulTsp;
    }

    public void setBackHaulTsp(ArrayList<Integer> backHaulTsp) {
        this.backHaulTsp = backHaulTsp;
    }
}
