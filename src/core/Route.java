package core;

import exceptions.NodeNotDeletableException;
import exceptions.NodeNotSupportedException;
import utils.DistanceMatrix;

import java.io.Serializable;
import java.util.ArrayList;

public class Route implements Serializable{
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
        if((n.getType().equals("Linehaul")) && ((totLinehaul + n.getCapacity()) > TSPInstance.getInstance().getMaxCapacity())){
            throw new NodeNotSupportedException("Il nodo non è aggiungibile, si supererebbe la capacità");
        } else if((n.getType().equals("Linehaul")) && nodes.get(index-1).getType().equals("Backhaul")) {
            throw new NodeNotSupportedException("Il nodo non è aggiungibile, un nodo Linehaul non può stare dopo un backhaul");
        } else if((n.getType().equals("Backhaul")) && nodes.get(index).getType().equals("Linehaul")){
            throw new NodeNotSupportedException("Il nodo non è aggiungibile, un nodo Backhaul non può stare prima di un Linehaul");
        } else {
            this.nodes.add(index, n);
            if(n.getType().equals("Linehaul")){
                totLinehaul += n.getCapacity();
            }else if(n.getType().equals("Backhaul")){
                totBackhaul += n.getCapacity();
            }
        }
    }

    public void setNode(int index, Node n) throws NodeNotSupportedException{
        if((n.getType().equals("Linehaul")) && ((totLinehaul-nodes.get(index).getCapacity()) + n.getCapacity()) > TSPInstance.getInstance().getMaxCapacity()){
            throw new NodeNotSupportedException("Il nodo non è aggiungibile per superamento capacità");
        } else if((n.getType().equals("Linehaul")) && nodes.get(index-1).getType().equals("Backhaul")) {
            throw new NodeNotSupportedException("Il nodo non è aggiungibile, un nodo Linehaul non può stare dopo un Backhaul");
        } else if((n.getType().equals("Backhaul")) && nodes.get(index+1).getType().equals("Linehaul")){
            throw new NodeNotSupportedException("Il nodo non è aggiungibile, un nodo Backhaul non può stare prima di un Linehaul");
        } else {
            Node nn = this.nodes.get(index);
            if(nn.getType().equals("Linehaul")){
                totLinehaul -= nn.getCapacity();
            }else if(nn.getType().equals("Backhaul")){
                totBackhaul -= nn.getCapacity();
            }
            this.nodes.set(index, n);
            if(n.getType().equals("Linehaul")){
                totLinehaul += n.getCapacity();
            }else if(n.getType().equals("Backhaul")){
                totBackhaul += n.getCapacity();
            }
        }
    }

    public void deleteNode(int index) throws NodeNotDeletableException{
        if(this.nodes.size() == 0){
            throw new NodeNotDeletableException("Route vuota");
        } else if(this.nodes.get(index) == null){
            throw new NodeNotDeletableException("Non esiste il nodo richiesto");
        } else if(this.nodes.size() == 3 && this.nodes.get(0).getType().equals("Warehouse") && this.nodes.get(2).getType().equals("Warehouse")) {
            throw new NodeNotDeletableException("Nodo non eliminabile, si svuoterebbe la route");
        } else if( this.nodes.stream().filter((node -> node.getType().equals("Linehaul") && nodes.indexOf(node) != index)).count() == 0 ){
            throw new NodeNotDeletableException("Nodo non eliminabile, Si avrebbero solo nodi Backhaul");
        }else{
            Node n = this.nodes.get(index);
            this.nodes.remove(index);
            if(n.getType().equals("Linehaul")){
                totLinehaul -= n.getCapacity();
            }else if(n.getType().equals("Backhaul")){
                totBackhaul -= n.getCapacity();
            }
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

    public String toString(){
        String result = "";

        for(Node node : nodes){
            result += node.getIndex() + node.getType().substring(0, 1) + " ";
        }

        return result;
    }
}
