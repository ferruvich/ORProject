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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Route route = (Route) o;

        if (getTotLinehaul() != route.getTotLinehaul()) return false;
        if (getTotBackhaul() != route.getTotBackhaul()) return false;
        if (isClosed() != route.isClosed()) return false;
        if (getNodes() != null ? !getNodes().equals(route.getNodes()) : route.getNodes() != null) return false;
        return getCost() != null ? getCost().equals(route.getCost()) : route.getCost() == null;
    }

    @Override
    public int hashCode() {
        int result = getNodes() != null ? getNodes().hashCode() : 0;
        result = 31 * result + (getCost() != null ? getCost().hashCode() : 0);
        result = 31 * result + getTotLinehaul();
        result = 31 * result + getTotBackhaul();
        result = 31 * result + (isClosed() ? 1 : 0);
        return result;
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
}
