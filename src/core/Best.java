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
     */
    public NodeRoute estimateStrategy(Route route) {
        NodeRoute candidate = null;
        ArrayList<Node> nodes = route.getNodes();

        for (int i = 1; i < nodes.size() - 1; i++) {
            Node a = nodes.get(i);

            for (Route r : routeList.getRoutes()) {
                for (int j = 1; j < r.getNodes().size() - 1; j++) {
                    Node b = r.getNodes().get(j);

                    if (b.getType().equals(a.getType()) && (!a.equals(b))) {
                        double previousCost = routeList.updateTotalCost();
                        double newCost = strategy.estimate(routeList, route.hashCode(), r.hashCode(), i, j);
                        if (newCost < previousCost) {
                            double gain = previousCost - newCost;

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

    public void printRoute(Route route) {
        System.out.print("Route: ");
        for (Node node : route.getNodes()) {
            System.out.print(node.getIndex() + " ");
        }
        System.out.println();
    }


}
