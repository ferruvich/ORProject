package core;

import exceptions.NodeNotSupportedException;
import utils.DistanceMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Danieru on 06/06/2017.
 */
public class RouteList extends ArrayList<Route>{

    public void initialize(TSPInstance tspInstance){
        List<Node> nodes = tspInstance.getNodes();
        Random rand = new Random();
        for(int i = 0; i < tspInstance.getTotRoutes(); i++){
            int currentNodeIndex = rand.nextInt(nodes.size() -1 ) + 1;
            Route r = new Route();
            r.addToRoute(nodes.get(0));
            Node n = nodes.get(currentNodeIndex);
            if(n.getType().equals("Backhaul")){
                i--;
            }else {
                try {
                    r.addToRoute(n);
                    nodes.remove(n);
                    this.add(i, r);
                } catch (NodeNotSupportedException e) {
                    System.out.println(e.getMessage());
                    i--;
                }
            }
        }
        for(Node n: nodes){
            if(n.getIndex() != 0 && n.getType().equals("Linehaul")) {
                Double minDistance = Double.MAX_VALUE;
                int j = 0;
                for (Route r : this) {
                    Node node = r.get(r.size() - 1);
                    Double dist = DistanceMap.getInstance().getDistance(n, node);
                    if (dist < minDistance) {
                        if(r.getTotLinehaul()+n.getCapacity() <= tspInstance.getMaxCapacity()) {
                            minDistance = dist;
                            j = this.indexOf(r);
                        }
                    }
                }
                Route r = this.get(j);
                try {
                    r.addToRoute(n);
                } catch (Exception e) {}
            }
        }
        for(Node n: nodes){
            if(n.getIndex() != 0 && n.getType().equals("Backhaul")) {
                Double minDistance = Double.MAX_VALUE;
                int j = 0;
                for (Route r : this) {
                    Node node = r.get(r.size() - 1);
                    Double dist = DistanceMap.getInstance().getDistance(n, node);
                    if (dist < minDistance) {
                        if(r.getTotLinehaul() - (r.getTotBackhaul() + n.getCapacity()) >= 0) {
                            minDistance = dist;
                            j = this.indexOf(r);
                        }
                    }
                }
                Route r = this.get(j);
                try {
                    r.addToRoute(n);
                } catch (Exception e) {}
            }
        }

        for(Route r: this){
            r.addToRoute(nodes.get(0));
        }
    }
}
