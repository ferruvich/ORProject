package utils;

import core.Route;

public class NodeRoute {
    private Route firstRoute;
    private Route secondRoute;
    private int firstNodeIndex;
    private int secondNodeIndex;
    private double gain;


    public NodeRoute(Route firstRoute, Route secondRoute, int indexFirst, int indexSecond, double gain) {
        this.firstRoute = firstRoute;
        this.secondRoute = secondRoute;
        this.firstNodeIndex = indexFirst;
        this.secondNodeIndex = indexSecond;
        this.gain = gain;
    }


    public int getFirstNodeIndex() {
        return firstNodeIndex;
    }

    public void setFirstNodeIndex(int firstNodeIndex) {
        this.firstNodeIndex = firstNodeIndex;
    }

    public int getSecondNodeIndex() {
        return secondNodeIndex;
    }

    public void setSecondNodeIndex(int secondNodeIndex) {
        this.secondNodeIndex = secondNodeIndex;
    }

    public double getGain() {
        return gain;
    }

    public void setGain(double gain) {
        this.gain = gain;
    }

    public Route getFirstRoute() {
        return firstRoute;
    }

    public void setFirstRoute(Route firstRoute) {
        this.firstRoute = firstRoute;
    }

    public Route getSecondRoute() {
        return secondRoute;
    }

    public void setSecondRoute(Route secondRoute) {
        this.secondRoute = secondRoute;
    }
}
