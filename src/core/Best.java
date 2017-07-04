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

    public Map<Node, List<NodeRoute>> bestOnSameRoute(Route route, int type) throws IOException {
        int i, j;
        Map<Node, List<NodeRoute>> swapMap = new HashMap<>();
        Double previousCost, newCost;

        List<Node> nodeList = route.getNodes();
        Route best = route;

        // Scambio ogni nodo con gli altri nodi presenti nella stesso route
        for (i = 1; i < nodeList.size() - 1; i++) {
            Node a = best.getNodes().get(i);
            List<NodeRoute> costList = new ArrayList<>();

//            System.out.println("Nodo a: " + a.getIndex());

            for (j = 1; j < nodeList.size() - 1; j++) {
                Node b = best.getNodes().get(j);

                // Se quel nodo non si sta paragonando a se stesso e se soddisfa il vincolo di uguaglianza
                // (non si possono scambiare nodi Backhaul con nodi Linehaul)
                if ((!a.equals(b)) && a.getType().equals(b.getType())) {
//                    System.out.println("\tNodo b: " + b.getIndex());

                    previousCost = getTotalCost();
                    newCost = type == BEST_EXCHANGE ? newExchangeRouteCost(route, route, i, j, a, b) : newRelocateRouteCost(route, route, i, j, a);

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
     * @return swapMap
     */

    public Map<Node, List<NodeRoute>> bestBetweenRoutes(Route route, int type) throws IOException {
        int i, j;
        ArrayList<Node> list = route.getNodes();
        Map<Node, List<NodeRoute>> swapMap = new HashMap<>();
        Double previousCost, newCost;

        for(i=1;i<list.size()-1;i++){
            Node a = list.get(i);
            List<NodeRoute> costList = new ArrayList<>();

            System.out.println("Nodo a: " + a.getIndex());


            for(Route r : routes){
                if(!route.equals(r)) {
                    for(j=1;j<r.getNodes().size()-1;j++){
                        Node b = r.getNodes().get(j);

                        if(b.getType().equals(a.getType())) {
//                            System.out.println("\tNodo b: " + b.getIndex());

                            previousCost = getTotalCost();
                            newCost = type == BEST_EXCHANGE ? newExchangeRouteCost(route, r, i, j, a, b) : newRelocateRouteCost(route, r, i, j, a);

                            if(newCost < previousCost){
                                costList.add(new NodeRoute(b.getIndex(), newCost));
//                                System.out.println("\t\tInserisco");
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
     * @param route1 First route interested in the swap
     * @param route2 Second route interested in the swap
     * @param indexA Index of the first node interested in the swap
     * @param indexB Index of the second node interested in the swap
     * @param a Node subject to the swaps
     * @param b Nodo candidate to reduce the cost
     * @return New cost after the swap
     */
    public Double newExchangeRouteCost(Route route1, Route route2, int indexA, int indexB, Node a, Node b){
        Double newCost = 0.0;
        updateRouteCost();

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
    public Double newRelocateRouteCost(Route route1, Route route2, int indexA, int indexB, Node a) throws IOException {
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
