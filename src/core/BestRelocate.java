package core;

public class BestRelocate implements Strategy {

    @Override
    public double estimate(RouteList routeList, int firstRouteHash, int secondRouteHash, int firstNodeIndex, int secondNodeIndex) {
        RouteList current = new RouteList(routeList);
        Route firstRoute = current.getRouteByHash(firstRouteHash);
        Route secondRoute = current.getRouteByHash(secondRouteHash);

        current.updateEachRouteCost();

        Node a = firstRoute.getNodeByIndex(firstNodeIndex);
        firstRoute.getNodes().remove(firstNodeIndex);
        secondRoute.getNodes().add(secondNodeIndex, a);

        return current.updateTotalCost();

    }

    @Override
    public void apply(RouteList routeList, Route firstRoute, Route secondRoute, int firstNodeIndex, int secondNodeIndex) {
        RouteList current = routeList;

        current.updateEachRouteCost();

        Node a = firstRoute.getNodeByIndex(firstNodeIndex);
        firstRoute.getNodes().remove(firstNodeIndex);
        secondRoute.getNodes().add(secondNodeIndex, a);

        current.updateTotalCost();
    }
}
