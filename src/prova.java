import core.Node;
import core.Route;
import core.RouteList;
import core.TSPInstance;
import utils.*;

import java.util.ArrayList;

/**
 * Created by Danieru on 30/05/2017.
 */
public class prova{
    public static void main(String[] args){
        TSPInstance in = JsonReader.getInstance().getSpecifications("InstancesJSON/N6.json");
        System.out.println("Max carico " + in.getMaxCapacity());
        DistanceMatrix.getInstance().initialize(in);
        RouteList routeList = new RouteList();
        routeList.initialize(in);

        ArrayList<Route> routes = routeList.getRoutes();
        int i = 0;
        for(Route r: routes){
            System.out.print("Route " + i++ + ": ");
            for(Node n: r.getNodes()){
                System.out.print(n.getIndex() + "; ");
            }
            System.out.print(" Costo totale: " + r.getCost() + ";\t");
            System.out.print("TotLinehaul: " + r.getTotLinehaul());
            System.out.println();
        }
    }
}
