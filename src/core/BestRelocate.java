package core;

import exceptions.NodeNotDeletableException;
import exceptions.NodeNotSupportedException;

/**
 * TODO verificare l'aggiunta di nodi, essa non deve far superare il carico massimo del camion (in A1 è 1500)
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

        firstRoute.deleteNode(firstNodeIndex);

        try{
            secondRoute.addNode(secondNodeIndex, a);
        }catch(NodeNotSupportedException e){
            firstRoute.addNode(firstNodeIndex, a);
            throw e;
        }

        totalCost = current.updateTotalCost();

        secondRoute.deleteNode(secondNodeIndex);
        firstRoute.addNode(firstNodeIndex, a);


        current.updateTotalCost();


        return totalCost;

    }

    @Override
    public void apply(RouteList routeList, Route firstRoute, Route secondRoute, int firstNodeIndex, int secondNodeIndex) {
        RouteList current = routeList;

        current.updateEachRouteCost();

        Node a = firstRoute.getNodeByIndex(firstNodeIndex);
        firstRoute.deleteNode(firstNodeIndex);
        secondRoute.addNode(secondNodeIndex, a);

        current.updateTotalCost();
    }
}
