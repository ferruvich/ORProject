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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (getIndex() != node.getIndex()) return false;
        if (getXcoord() != node.getXcoord()) return false;
        if (getYcoord() != node.getYcoord()) return false;
        if (getCapacity() != node.getCapacity()) return false;
        return getType() != null ? getType().equals(node.getType()) : node.getType() == null;
    }

    @Override
    public int hashCode() {
        int result = getIndex();
        result = 31 * result + getXcoord();
        result = 31 * result + getYcoord();
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        result = 31 * result + getCapacity();
        return result;
    }
}
