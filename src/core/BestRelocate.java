package core;

public class BestRelocate implements Strategy {

    @Override
    public double estimate(RouteList routeList, Route firstRoute, Route secondRoute, int firstNodeIndex, int secondNodeIndex) {
        double gain;
        RouteList current = routeList;

        current.updateEachRouteCost();

        Node a = firstRoute.getNodeByIndex(firstNodeIndex);
        firstRoute.getNodes().remove(firstNodeIndex);
        secondRoute.getNodes().add(secondNodeIndex, a);

        gain = current.updateTotalCost();

        secondRoute.getNodes().remove(secondNodeIndex);
        firstRoute.getNodes().add(firstNodeIndex, a);

        current.updateEachRouteCost();


        return gain;
    }

    @Override
    public RouteList apply(RouteList routeList, Route firstRoute, Route secondRoute, int firstNodeIndex, int secondNodeIndex) {
        RouteList current = routeList;

        current.updateEachRouteCost();

        Node a = firstRoute.getNodeByIndex(firstNodeIndex);
        firstRoute.getNodes().remove(firstNodeIndex);
        secondRoute.getNodes().add(secondNodeIndex, a);

        current.updateTotalCost();


        return current;
    }
}
