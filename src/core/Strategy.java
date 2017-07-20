package core;

public interface Strategy {
    double estimate(RouteList routeList, Route firstRoute, Route secondRoute, int firstNodeIndex, int secondNodeIndex);

    RouteList apply(RouteList routeList, Route firstRoute, Route secondRoute, int firstNodeIndex, int secondNodeIndex);
}