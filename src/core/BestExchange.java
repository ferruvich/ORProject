package core;

public class BestExchange implements Strategy {

    @Override
    public double estimate(RouteList routeList, int firstRouteHash, int secondRouteHash, int firstNodeIndex, int secondNodeIndex) {
        RouteList current = new RouteList(routeList);
        Route firstRoute = current.getRouteByHash(firstRouteHash);
        Route secondRoute = current.getRouteByHash(secondRouteHash);

        Node firstNode = firstRoute.getNodeByIndex(firstNodeIndex);
        Node secondNode = secondRoute.getNodeByIndex(secondNodeIndex);
        firstRoute.getNodes().set(firstNodeIndex, secondNode);
        secondRoute.getNodes().set(secondNodeIndex, firstNode);
        return current.updateTotalCost();
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
