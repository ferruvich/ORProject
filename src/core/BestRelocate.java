package core;

/**
 * TODO verificare che non vengano fatti scambi se uno dei route resta senza elementi, lanciare eventualmente eccezione e catturare il caso
 */

public class BestRelocate implements Strategy {

    @Override
    public double estimate(RouteList routeList, int firstRouteHash, int secondRouteHash, int firstNodeIndex, int secondNodeIndex) {
        double totalCost;

        RouteList current = new RouteList(routeList);
        Route firstRoute = current.getRouteByHash(firstRouteHash);
        Route secondRoute = current.getRouteByHash(secondRouteHash);

        current.updateEachRouteCost();

        Node a = firstRoute.getNodeByIndex(firstNodeIndex);
        Node b = secondRoute.getNodeByIndex(secondNodeIndex);

        firstRoute.getNodes().remove(firstNodeIndex);
        secondRoute.getNodes().add(secondNodeIndex, a);

        totalCost = current.updateTotalCost();

        secondRoute.getNodes().remove(secondNodeIndex);
        firstRoute.getNodes().add(firstNodeIndex, a);


        current.updateTotalCost();


        return totalCost;

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
