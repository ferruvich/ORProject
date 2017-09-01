package core;

import exceptions.NodeNotSupportedException;
import org.apache.commons.lang3.SerializationUtils;

public class BestExchange implements Strategy {

    @Override
    public double estimate(RouteList routeList, int firstRouteHash, int secondRouteHash, int firstNodeIndex, int secondNodeIndex) {

        double totalCost;

        RouteList current = SerializationUtils.clone(routeList);

        Route firstRoute = current.getRouteByHash(firstRouteHash);
        Route secondRoute = current.getRouteByHash(secondRouteHash);

        Node firstNode = firstRoute.getNodeByIndex(firstNodeIndex);
        Node secondNode = secondRoute.getNodeByIndex(secondNodeIndex);


        try{
            firstRoute.setNode(firstNodeIndex, secondNode);
            secondRoute.setNode(secondNodeIndex, firstNode);
        }catch(NodeNotSupportedException e){
            return Double.MAX_VALUE;
        }

        totalCost = current.updateTotalCost();

        return totalCost;
    }

    @Override
    public void apply(RouteList routeList, Route firstRoute, Route secondRoute, int firstNodeIndex, int secondNodeIndex) {
        Node firstNode = firstRoute.getNodeByIndex(firstNodeIndex);
        Node secondNode = secondRoute.getNodeByIndex(secondNodeIndex);
        firstRoute.setNode(firstNodeIndex, secondNode);
        secondRoute.setNode(secondNodeIndex, firstNode);
        routeList.updateEachRouteCost();
        routeList.updateTotalCost();
    }
}
