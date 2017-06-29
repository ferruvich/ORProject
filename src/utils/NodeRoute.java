package utils;

/**
 * Created by ibbus on 27/06/2017.
 */
public class NodeRoute {
    private int index;
    private Double cost;

    public NodeRoute(int index, Double cost){
        this.setIndex(index);
        this.setCost(cost);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}
