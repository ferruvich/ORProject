package utils;

/**
 * Created by ibbus on 27/06/2017.
 */
public class NodeRoute {
    private int index;
    private int numRoute;
    private Double cost;

    public NodeRoute(int index, int numRoute, Double cost){
        this.setIndex(index);
        this.setNumRoute(numRoute);
        this.setCost(cost);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getNumRoute() {
        return numRoute;
    }

    public void setNumRoute(int numRoute) {
        this.numRoute = numRoute;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}
