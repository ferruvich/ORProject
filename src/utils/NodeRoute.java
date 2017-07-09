package utils;

/**
 * Created by ibbus on 27/06/2017.
 */
public class NodeRoute {
    private int indexFirst;
    private int indexSecond;
    private Double cost;


    public NodeRoute(int indexFirst, int indexSecond, Double cost){
        this.setIndexFirst(indexFirst);
        this.setIndexSecond(indexSecond);
        this.setCost(cost);
    }


    public int getIndexFirst() {
        return indexFirst;
    }

    public void setIndexFirst(int indexFirst) {
        this.indexFirst = indexFirst;
    }

    public int getIndexSecond() {
        return indexSecond;
    }

    public void setIndexSecond(int indexSecond) {
        this.indexSecond = indexSecond;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}
