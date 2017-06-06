package core;

/**
 * Created by Danieru on 30/05/2017.
 */
public class Node {

    private int index;
    private int xcoord;
    private int ycoord;
    private String type;
    private int capacity;

    public Node(int index, int xcoord, int ycoord, String type, int capacity){
        this.index = index;
        this.xcoord = xcoord;
        this.ycoord = ycoord;
        this.type = type;
        this.capacity = capacity;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getXcoord() {
        return xcoord;
    }

    public void setXcoord(int xcoord) {
        this.xcoord = xcoord;
    }

    public int getYcoord() {
        return ycoord;
    }

    public void setYcoord(int ycoord) {
        this.ycoord = ycoord;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
