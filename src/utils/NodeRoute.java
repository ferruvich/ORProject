package utils;

public class NodeRoute {
    private int indexFirst;
    private int indexSecond;
    private double gain;


    public NodeRoute(int indexFirst, int indexSecond, double gain) {
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

    public double getGain() {
        return gain;
    }

    public void setGain(double gain) {
        this.gain = gain;
    }
}
