package core;

import java.io.IOException;

public class BestExchange implements Strategy {

    @Override
    public double estimate(RouteList routeList, int firstRouteHash, int secondRouteHash, int firstNodeIndex, int secondNodeIndex) {

        double totalCost;

        RouteList current = new RouteList(routeList);
        Route firstRoute = current.getRouteByHash(firstRouteHash);
        Route secondRoute = current.getRouteByHash(secondRouteHash);

        Node firstNode = firstRoute.getNodeByIndex(firstNodeIndex);
        Node secondNode = secondRoute.getNodeByIndex(secondNodeIndex);
        firstRoute.getNodes().set(firstNodeIndex, secondNode);
        secondRoute.getNodes().set(secondNodeIndex, firstNode);

        totalCost = current.updateTotalCost();

        firstRoute.getNodes().set(firstNodeIndex, firstNode);
        secondRoute.getNodes().set(secondNodeIndex, secondNode);

        current.updateTotalCost();

        return totalCost;
    }

    @Override
    public void apply(RouteList routeList, Route firstRoute, Route secondRoute, int firstNodeIndex, int secondNodeIndex) {
        Node firstNode = firstRoute.getNodeByIndex(firstNodeIndex);
        Node secondNode = secondRoute.getNodeByIndex(secondNodeIndex);
        firstRoute.getNodes().set(firstNodeIndex, secondNode);
        secondRoute.getNodes().set(secondNodeIndex, firstNode);
        routeList.updateTotalCost();
    }
}
