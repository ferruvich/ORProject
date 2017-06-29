package core;

import utils.NodeRoute;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ibbus on 22/06/2017.
 * AsyncTask, Listenable Future
 */
public class BestExchange {

    List<Route> routes;

    public BestExchange(List<Route> route){
        this.routes = route;
    }

    public Map<Node, List<NodeRoute>> exchangeSameRoute(Route route) {
        int i, j;
        Map<Node, List<NodeRoute>> swapMap = new HashMap<>();
        Double previousCost, newCost;

        List<Node> nodeList = route.getNodes();
        Route best = route;

        // Scambio ogni nodo con gli altri nodi presenti nella stesso route
        for (i = 1; i < nodeList.size() - 1; i++) {
            Node a = best.getNodes().get(i);
            List<NodeRoute> costList = new ArrayList<>();

            for (j = 1; j < nodeList.size() - 1; j++) {
                Node b = best.getNodes().get(j);

                // Se quel nodo non si sta paragonando a se stesso e se soddisfa il vincolo di uguaglianza
                // (non si possono scambiare nodi Backhaul con nodi Linehaul)
                if ((!a.equals(b)) && a.getType().equals(b.getType())) {
                    previousCost = route.getCost();
                    newCost = newRouteCost(route, i, a, b);
                    if(newCost < previousCost){
                        costList.add(new NodeRoute(b.getIndex(), newCost));
                    }
                }
            }
            swapMap.put(a, costList);
        }

        return swapMap;
    }

    /**
     * This method takes in input a route, it takes every node of that route and it constructs
     * the Map<Node, List<NodeRoute>> swapping the node that takes with everyone in the other routes
     * @return
     */

    public Map<Node, List<NodeRoute>> exchangeBetweenRoute(Route route) {
        int i, j;
        ArrayList<Node> list = route.getNodes();
        Map<Node, List<NodeRoute>> swapMap = new HashMap<>();
        Double previousCost, newCost;

        for(i=1;i<list.size()-1;i++){
            Node a = list.get(i);
            List<NodeRoute> costList = new ArrayList<>();

            for(Route r : routes){
                if(!route.equals(r)) {
                    for(j=1;j<r.getNodes().size()-1;j++){
                        Node b = r.getNodes().get(j);

                        if(b.getType().equals(a.getType())) {
                            previousCost = route.getCost();
                            newCost = newRouteCost(route, i, a, b);
                            if(newCost < previousCost){
                                costList.add(new NodeRoute(b.getIndex(), newCost));
                            }
                        }
                    }
                }
            }
            swapMap.put(a, costList);
        }

        return swapMap;
    }

    /**
     *
     * @param route Route where to execute the swaps
     * @param index Index of the node who is taken to make the swap
     * @param a Node subject to the swaps
     * @param b Nodo candidate to reduce the cost
     * @return New cost after the swap
     */
    public Double newRouteCost(Route route, int index, Node a, Node b){
        Double newCost;

        route.updateCost();
        route.getNodes().set(index, b);
        route.updateCost();
        newCost = route.getCost();
        route.getNodes().set(index, a);

        return newCost;
    }

}
