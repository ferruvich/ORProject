package core;

import exceptions.NodeNotSupportedException;

/**
 * TODO verificare l'aggiunta di nodi, essa non deve far superare il carico massimo del camion (in A1 è 1500)
 */

public class BestExchange implements Strategy {

    @Override
    public double estimate(RouteList routeList, int firstRouteHash, int secondRouteHash, int firstNodeIndex, int secondNodeIndex) {

        double totalCost;

        RouteList current = new RouteList(routeList);
        Route firstRoute = current.getRouteByHash(firstRouteHash);
        Route secondRoute = current.getRouteByHash(secondRouteHash);

        Node firstNode = firstRoute.getNodeByIndex(firstNodeIndex);
        Node secondNode = secondRoute.getNodeByIndex(secondNodeIndex);

        firstRoute.setNode(firstNodeIndex, secondNode);

        try{
            secondRoute.setNode(secondNodeIndex, firstNode);
        }catch(NodeNotSupportedException e){
            firstRoute.setNode(firstNodeIndex, firstNode);
            throw e;
        }

        totalCost = current.updateTotalCost();

        firstRoute.setNode(firstNodeIndex, firstNode);
        secondRoute.setNode(secondNodeIndex, secondNode);

        current.updateTotalCost();

        return totalCost;
    }

    @Override
    public void apply(RouteList routeList, Route firstRoute, Route secondRoute, int firstNodeIndex, int secondNodeIndex) {
        Node firstNode = firstRoute.getNodeByIndex(firstNodeIndex);
        Node secondNode = secondRoute.getNodeByIndex(secondNodeIndex);
        firstRoute.setNode(firstNodeIndex, secondNode);
        secondRoute.setNode(secondNodeIndex, firstNode);
        routeList.updateTotalCost();
    }
}
