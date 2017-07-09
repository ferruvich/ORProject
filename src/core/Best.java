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
public class Best {

    public static final int BEST_RELOCATE = 0;
    public static final int BEST_EXCHANGE = 1;
    private List<Route> routes;
    private double totalCost;

    public Best(List<Route> route){
        this.routes = route;
        this.totalCost = getTotalCost();
    }

    private double getTotalCost(){
        this.totalCost = 0.0;

        for(Route route : routes){
            route.updateCost();
            this.totalCost += route.getCost();
        }

        return this.totalCost;
    }

    public double getCost(){
        return this.totalCost;
    }

    private void updateRouteCost(){
        for(Route route : routes){
            route.updateCost();
        }
    }

    /**
     * This method takes in input a route, it takes every node of that route and it constructs
     * the Map<Node, List<NodeRoute>> swapping or relocating the node that takes with everyone in the other routes
     * @return swapMap A map with the Node as key and a List of NodeRoute as value. The NodeRoute has as attributes
     * the index of the node subject to the swap or the relocate with the key and the cost updated
     */

    public NodeRoute run(Route route, int type) {
        int i, j;
        NodeRoute nodeRoute = null;
        ArrayList<Node> list = route.getNodes();
        Double previousCost, newCost;

        for(i=1;i<list.size()-1;i++){
            Node a = list.get(i);

            System.out.println("Nodo a: " + a.getIndex());

            for(Route r : routes){
                for (j = 1; j < r.getNodes().size() - 1; j++) {
                    Node b = r.getNodes().get(j);

                    if (b.getType().equals(a.getType()) && (!a.equals(b))) {
                        System.out.println("\tNodo b: " + b.getIndex());

                        previousCost = getTotalCost();
                        newCost = type == BEST_EXCHANGE ? newExchangeRouteCost(route, r, i, j, a, b) : newRelocateRouteCost(route, r, i, j, a);
                        double costToUpdate = previousCost - newCost;

                        if (newCost < previousCost) {
                            if(nodeRoute == null){
                                nodeRoute = new NodeRoute(a.getIndex(), b.getIndex(), (previousCost - newCost));
                            } else {
                                if(costToUpdate < nodeRoute.getCost()){
                                    nodeRoute = new NodeRoute(a.getIndex(), b.getIndex(), costToUpdate);
                                }
                            }
                            System.out.println("\t\tInserisco");
                        }
                    }
                }
            }
        }

        return nodeRoute;
    }

    /**
     *
     * @param route1 First route interested in the swap
     * @param route2 Second route interested in the swap
     * @param indexA Index of the first node interested in the swap
     * @param indexB Index of the second node interested in the swap
     * @param a Node subject to the swaps
     * @param b Nodo candidate to reduce the cost
     * @return New cost after the swap
     */
    public Double newExchangeRouteCost(Route route1, Route route2, int indexA, int indexB, Node a, Node b) {
        Double newCost = 0.0;
        updateRouteCost();

//        printRoute(route1);
//        printRoute(route2);
//        System.in.read();

        route1.getNodes().set(indexA, b);
        route2.getNodes().set(indexB, a);

        newCost = getTotalCost();

        route1.getNodes().set(indexA, a);
        route2.getNodes().set(indexB, b);

        updateRouteCost();

        return newCost;
    }

    /**
     *
     * @param route1 First route interested in relocating
     * @param route2 Second route interested in relocating
     * @param indexA Index of the node subject to relocating
     * @param indexB Index of the node where the node a has to be relocated
     * @param a Node subject to relocating
     * @return
     */
    public Double newRelocateRouteCost(Route route1, Route route2, int indexA, int indexB, Node a) {
        Double newCost = 0.0;
        updateRouteCost();

        route1.getNodes().remove(indexA);
        route2.getNodes().add(indexB, a);

        newCost = getTotalCost();

        route2.getNodes().remove(indexB);
        route1.getNodes().add(indexA, a);

        updateRouteCost();

        return newCost;
    }

    public void printRoute(Route route){
        System.out.print("Route: ");
        for(Node node : route.getNodes()){
            System.out.print(node.getIndex() + " ");
        }
        System.out.println();
    }


}
