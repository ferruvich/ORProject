package core;

import exceptions.NodeNotSupportedException;
import utils.DistanceMatrix;

import java.util.ArrayList;

/**
 * Created by Danieru on 06/06/2017.
 */
public class Route{
    private ArrayList<Node> nodes;
    private Double cost = 0.0;
    private int totLinehaul = 0;
    private int totBackhaul = 0;
    private boolean closed = false;

    public Route(){
        nodes = new ArrayList<>();
    }

    public void addToRoute(Node n){
        if(nodes.size() > 0) {
            updateCost(nodes.get(nodes.size() - 1), n);
        }
        this.nodes.add(n);
    }

    public void closeRoute(){
        Node warehouse = nodes.get(0);
        Node last = nodes.get(nodes.size()-1);
        this.nodes.add(nodes.size(), warehouse);
        updateCost(warehouse, last);
        closed = true;
    }

    private void updateCost(Node previous, Node n){
        cost += DistanceMatrix.getInstance().getDistance(previous, n);
        if(n.getType().equals("Linehaul")){
            totLinehaul+=n.getCapacity();
        }else if(n.getType().equals("Backhaul")){
            totBackhaul+=n.getCapacity();
        }
    }

    public void updateCost(){
        this.cost = 0.0;
        
        for(int i=0;i<nodes.size()-1;i++){
            cost += DistanceMatrix.getInstance().getDistance(nodes.get(i), nodes.get(i+1));
        }
    }

    public boolean isClosed(){
        return this.closed;
    }

    public Double getCost(){
        return this.cost;
    }

    public int getTotLinehaul() {
        return this.totLinehaul;
    }

    public int getTotBackhaul() {
        return this.totBackhaul;
    }

    public ArrayList<Node> getNodes() {
        return this.nodes;
    }

    public void swapNodes(int indexA, int indexB){
        Node a = this.nodes.get(indexA);
        Node b = this.nodes.get(indexB);

        nodes.set(indexA, b);
        nodes.set(indexB, a);
    }
}
