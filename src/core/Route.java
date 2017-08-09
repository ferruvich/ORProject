package core;

import exceptions.NodeNotDeletableException;
import exceptions.NodeNotSupportedException;
import utils.DistanceMatrix;

import java.util.ArrayList;

public class Route {
    private ArrayList<Node> nodes;
    private double cost = 0.0;
    private int totLinehaul = 0;
    private int totBackhaul = 0;
    private boolean closed = false;

    public Route() {
        nodes = new ArrayList<>();
    }

    public void addToRoute(Node n) {
        if (nodes.size() > 0) {
            updateCost(nodes.get(nodes.size() - 1), n);
        }
        this.nodes.add(n);
    }

    public void addNode(int index, Node n) throws NodeNotSupportedException{
        if(totLinehaul + n.getCapacity() > TSPInstance.getInstance().getMaxCapacity()){
            throw new NodeNotSupportedException("Il nodo non è aggiungibile");
        }else {
            this.nodes.add(index, n);
        }
    }

    public void setNode(int index, Node n) throws NodeNotSupportedException{
        if((totLinehaul-nodes.get(index).getCapacity()) + n.getCapacity() > TSPInstance.getInstance().getMaxCapacity()){
            throw new NodeNotSupportedException("Il nodo non è aggiungibile");
        }else{
            this.nodes.set(index, n);
        }
    }

    public void deleteNode(int index) throws NodeNotDeletableException{
        if(this.nodes.size() == 0){
            throw new NodeNotDeletableException("Route vuota");
        }
        else if(this.nodes.get(index) == null){
            throw  new NodeNotDeletableException("Non esiste il nodo richiesto");
        }
        else if(this.nodes.size() == 3 && this.nodes.get(0).getType().equals("Warehouse") && this.nodes.get(2).getType().equals("Warehouse")){
            throw new NodeNotDeletableException("Nodo non eliminabile, si svuoterebbe la route");
        }else{
            this.nodes.remove(index);
        }
    }

    private void updateCost(Node previous, Node n) {
        cost += DistanceMatrix.getInstance().getDistance(previous, n);
        if (n.getType().equals("Linehaul")) {
            totLinehaul += n.getCapacity();
        } else if (n.getType().equals("Backhaul")) {
            totBackhaul += n.getCapacity();
        }
    }

    public void updateCost() {
        this.cost = 0.0;

        for (int i = 0; i < nodes.size() - 1; i++) {
            cost += DistanceMatrix.getInstance().getDistance(nodes.get(i), nodes.get(i + 1));
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

    public boolean isClosed() {
        return this.closed;
    }

    public Double getCost() {
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

    public Node getNodeByIndex(int index) {
        return this.nodes.get(index);
    }
}
