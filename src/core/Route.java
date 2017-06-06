package core;

import exceptions.NodeNotSupportedException;
import utils.DistanceMap;

import java.util.ArrayList;

/**
 * Created by Danieru on 06/06/2017.
 */
public class Route extends ArrayList<Node> {
    private Double cost = 0.0;
    private int totLinehaul = 0;
    private int totBackhaul = 0;
    private int totCustomers = 0;

    private boolean closed = false;

    public boolean addToRoute(Node n) throws NodeNotSupportedException {
        boolean added = false;
        switch(n.getType()){
            case "Warehouse":{
                if(this.size() == 0){
                    this.add(n);
                    added = true;
                    break;
                }else if(this.get(this.size()-1).getType().equals("Warehouse") || closed){
                    throw new NodeNotSupportedException("You cannot put the warehouse right after itself");
                }else{
                    this.add(n);
                    this.closed = true;
                    added = true;
                    break;
                }
            }
            case "Linehaul": {
                if (this.size() == 0) {
                    throw new NodeNotSupportedException("You have to put the warehouse first");
                } else if (this.get(this.size() - 1).getType().equals("Backhaul")) {
                    throw new NodeNotSupportedException("You cannot put linehaul after backhaul");
                }else if(closed){
                    throw new NodeNotSupportedException("Route already closed");
                }else{
                    this.add(n);
                    this.totLinehaul = this.getTotLinehaul() + n.getCapacity();
                    this.totCustomers++;
                    added = true;
                    break;
                }
            }
            case "Backhaul":{
                if(this.size() == 0){
                    throw new NodeNotSupportedException("You have to put the warehouse first");
                }else if(closed){
                    throw new NodeNotSupportedException("Route already closed");
                }else{
                    this.add(n);
                    this.totBackhaul = this.getTotBackhaul() + n.getCapacity();
                    this.totCustomers++;
                    added = true;
                    break;
                }
            }
            default:{
                throw new NodeNotSupportedException("Node type not supported, type must be [Warehouse, Linehaul, Backhaul]");
            }
        }

        updateCost();
        return added;
    }

    public void updateCost(){
        if(this.size() > 1) {
            Node last = this.get(this.size() - 1);
            Node prev = this.get(this.size() - 2);
            this.cost += DistanceMap.getInstance().getDistance(last, prev);
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

    public int getTotCustomers(){
        return this.totCustomers;
    }
}
