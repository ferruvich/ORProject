package utils;

/**
 * Created by ibbus on 27/06/2017.
 */
public class NodeRoute {
    private int indexFirst;
    private int indexSecond;
    private Double gain;


    public NodeRoute(int indexFirst, int indexSecond, Double gain) {
        this.indexFirst = indexFirst;
        this.indexSecond = indexSecond;
        this.gain = gain;
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

    public Double getGain() {
        return gain;
    }

    public void setGain(Double gain) {
        this.gain = gain;
    }
}
