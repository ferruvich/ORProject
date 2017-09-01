package core;

import exceptions.NodeNotDeletableException;
import exceptions.NodeNotSupportedException;
import org.apache.commons.lang3.SerializationUtils;

public class BestRelocate implements Strategy {

    @Override
    public double estimate(RouteList routeList, int firstRouteHash, int secondRouteHash, int firstNodeIndex, int secondNodeIndex) {
        double totalCost;

        RouteList current = SerializationUtils.clone(routeList);
        Route firstRoute = current.getRouteByHash(firstRouteHash);
        Route secondRoute = current.getRouteByHash(secondRouteHash);

        current.updateEachRouteCost();

        Node a = firstRoute.getNodeByIndex(firstNodeIndex);
        Node b = secondRoute.getNodeByIndex(secondNodeIndex);

        try{
            firstRoute.deleteNode(firstNodeIndex);
        }catch(NodeNotDeletableException e){
            return Double.MAX_VALUE;
        }
        try{
            secondRoute.addNode(secondNodeIndex, a);
        }catch(NodeNotSupportedException e){
            return Double.MAX_VALUE;
        }

        totalCost = current.updateTotalCost();

        return totalCost;

    }

    @Override
    public void apply(RouteList routeList, Route firstRoute, Route secondRoute, int firstNodeIndex, int secondNodeIndex) {

        routeList.updateEachRouteCost();

        Node a = firstRoute.getNodeByIndex(firstNodeIndex);
        firstRoute.deleteNode(firstNodeIndex);
        secondRoute.addNode(secondNodeIndex, a);

        routeList.updateTotalCost();
    }
}
