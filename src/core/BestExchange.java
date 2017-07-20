package core;

public class BestExchange implements Strategy {

    @Override
    public double estimate(RouteList routeList, Route firstRoute, Route secondRoute, int firstNodeIndex, int secondNodeIndex) {
        double gain;
        RouteList current = routeList;

        current.updateEachRouteCost();

        Node firstNode = firstRoute.getNodeByIndex(firstNodeIndex);
        Node secondNode = secondRoute.getNodeByIndex(secondNodeIndex);
        firstRoute.getNodes().set(firstNodeIndex, secondNode);
        secondRoute.getNodes().set(secondNodeIndex, firstNode);

        gain = current.updateTotalCost();

        firstRoute.getNodes().set(firstNodeIndex, firstNode);
        secondRoute.getNodes().set(secondNodeIndex, secondNode);

        current.updateEachRouteCost();


        return gain;
    }

    @Override
    public RouteList apply(RouteList routeList, Route firstRoute, Route secondRoute, int firstNodeIndex, int secondNodeIndex) {
        RouteList current = routeList;

        current.updateEachRouteCost();

        Node firstNode = firstRoute.getNodeByIndex(firstNodeIndex);
        Node secondNode = secondRoute.getNodeByIndex(secondNodeIndex);
        firstRoute.getNodes().set(firstNodeIndex, secondNode);
        secondRoute.getNodes().set(secondNodeIndex, firstNode);

        current.updateTotalCost();


        return current;
    }
}
