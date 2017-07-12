package core;

import utils.NodeRoute;

import java.util.ArrayList;

public class Best {

    public static final int BEST_RELOCATE = 0;
    public static final int BEST_EXCHANGE = 1;
    public RouteList routeList;

    public Best(RouteList routeList) {
        this.routeList = routeList;
    }

    /**
     * This method takes in input a route, it takes every node of that route and it constructs
     * the Map<Node, List<NodeRoute>> swapping or relocating the node that takes with everyone in the other routeList
     *
     * @return swapMap A map with the Node as key and a List of NodeRoute as value. The NodeRoute has as attributes
     * the index of the node subject to the swap or the relocate with the key and the cost updated
     */
    public NodeRoute run(Route route, int type) {
        NodeRoute nodeRoute = null;
        ArrayList<Node> nodes = route.getNodes();

        for (int i = 1; i < nodes.size() - 1; i++) {
            Node a = nodes.get(i);

            //System.out.println("Nodo a: " + a.getIndex());

            for (Route r : routeList.getRoutes()) {
                for (int j = 1; j < r.getNodes().size() - 1; j++) {
                    Node b = r.getNodes().get(j);

                    if (b.getType().equals(a.getType()) && (!a.equals(b))) {
                        //System.out.println("\tNodo b: " + b.getIndex());

                        double previousCost = routeList.updateTotalCost();
                        double newCost = type == Best.BEST_EXCHANGE ? newExchangeRouteCost(route, r, i, j) : newRelocateRouteCost(route, r, i, j);

                        if (newCost < previousCost) {
                            double gain = previousCost - newCost;

                            if (nodeRoute == null) {
                                nodeRoute = new NodeRoute(a.getIndex(), b.getIndex(), gain);
                            } else {
                                if (gain < nodeRoute.getGain()) {
                                    nodeRoute = new NodeRoute(a.getIndex(), b.getIndex(), gain);
                                }
                            }
                            //System.out.println("\t\tInserisco");
                        }
                    }
                }
            }
        }

        return nodeRoute;
    }

    /**
     * @param firstRoute First route interested in the swap
     * @param secondRoute Second route interested in the swap
     * @param firstNodeIndex Index of the first node interested in the swap
     * @param secondNodeIndex Index of the second node interested in the swap
     * @return New cost after the swap
     */
    public double newExchangeRouteCost(Route firstRoute, Route secondRoute, int firstNodeIndex, int secondNodeIndex) {
        double gain;

        routeList.updateEachRouteCost();

        Node firstNode = firstRoute.getNodeByIndex(firstNodeIndex);
        Node secondNode = secondRoute.getNodeByIndex(secondNodeIndex);
        firstRoute.getNodes().set(firstNodeIndex, secondNode);
        secondRoute.getNodes().set(secondNodeIndex, firstNode);

        gain = routeList.updateTotalCost();

        firstRoute.getNodes().set(firstNodeIndex, firstNode);
        secondRoute.getNodes().set(secondNodeIndex, secondNode);

        routeList.updateEachRouteCost();

        return gain;
    }

    /**
     * @param firstRoute First route interested in relocating
     * @param secondRoute Second route interested in relocating
     * @param firstNodeIndex Index of the node subject to relocating
     * @param secondNodeIndex Index of the node where the node a has to be relocated
     * @return New cost after the swap
     */
    public double newRelocateRouteCost(Route firstRoute, Route secondRoute, int firstNodeIndex, int secondNodeIndex) {
        double gain;

        routeList.updateEachRouteCost();

        Node a = firstRoute.getNodeByIndex(firstNodeIndex);
        firstRoute.getNodes().remove(firstNodeIndex);
        secondRoute.getNodes().add(secondNodeIndex, a);

        gain = routeList.updateTotalCost();

        secondRoute.getNodes().remove(secondNodeIndex);
        firstRoute.getNodes().add(firstNodeIndex, a);

        routeList.updateEachRouteCost();

        return gain;
    }

    public void printRoute(Route route) {
        System.out.print("Route: ");
        for (Node node : route.getNodes()) {
            System.out.print(node.getIndex() + " ");
        }
        System.out.println();
    }


}
