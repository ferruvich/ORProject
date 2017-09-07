package core;

import utils.NodeRoute;

import java.util.ArrayList;

public class Best {
    private RouteList routeList;
    private Strategy strategy;


    public Best(RouteList routeList, Strategy strategy) {
        this.routeList = routeList;
        this.strategy = strategy;
    }

    /**
     * This method takes in input a route, it takes every node of that route and it constructs
     * the Map<Node, List<NodeRoute>> swapping or relocating the node that takes with everyone in the other routeList
     *
     * @return swapMap A map with the Node as key and a List of NodeRoute as value. The NodeRoute has as attributes
     * the index of the node subject to the swap or the relocate with the key and the cost updated
     *
     * Avvengono degli scambi che non dovrebbero avvenire e il nodeRoute scelto non è quello con il guadagno maggiore
     */
    public NodeRoute estimateStrategy(Route route) {
        NodeRoute candidate = null;
        ArrayList<Node> nodes = route.getNodes();


        for (int i = 1; i < nodes.size() - 1; i++) {
            Node a = nodes.get(i);

            for (Route r : routeList.getRoutes()) {
                for (int j = 1; j < r.getNodes().size() - 1; j++) {
                    Node b = r.getNodes().get(j);

                    if ((!a.equals(b))) {
                        double previousCost = routeList.updateTotalCost();
                        double newCost = previousCost;

                        newCost = strategy.estimate(routeList, route.hashCode(), r.hashCode(), i, j);

                        double gain = previousCost - newCost;

                        if (gain > 0) {
                            if (candidate == null) {
                                candidate = new NodeRoute(route, r, i, j, gain);
                            } else {
                                if (gain > candidate.getGain()) {
                                    candidate = new NodeRoute(route, r, i, j, gain);
                                }
                            }
                        }
                    }
                }
            }
        }

        return candidate;
    }

    public void applyStrategy(NodeRoute nodeRoute) {
        strategy.apply(routeList, nodeRoute.getFirstRoute(), nodeRoute.getSecondRoute(), nodeRoute.getFirstNodeIndex(), nodeRoute.getSecondNodeIndex());
    }

    /**
     * @param firstRoute      First route interested in the swap
     * @param secondRoute     Second route interested in the swap
     * @param firstNodeIndex  Index of the first node interested in the swap
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
     * @param firstRoute      First route interested in relocating
     * @param secondRoute     Second route interested in relocating
     * @param firstNodeIndex  Index of the node subject to relocating
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

    public static void printRoute(Route route) {
        System.out.print("Route: ");
        for (Node node : route.getNodes()) {
            String type = node.getType().substring(0,1);
            System.out.print(node.getIndex() + type + " ");
        }
        route.updateCost();
        System.out.print(" totLinehaul ->" + route.getTotLinehaul());
        System.out.println(" costo Totale -> " + route.getCost());
    }


}
