import core.*;
import utils.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Danieru on 30/05/2017.
 */
public class prova{
    public static void main(String[] args) throws IOException {
        TSPInstance in = JsonReader.getInstance().getSpecifications("InstancesJSON/N6.json");
        System.out.println("Max carico " + in.getMaxCapacity());
        DistanceMatrix.getInstance().initialize(in);
        RouteList routeList = new RouteList();
        routeList.initialize(in);

        ArrayList<Route> routes = routeList.getRoutes();
        System.out.println("Route totali: " + routes.size());

        System.out.print("Nodi percorso 0: ");
        for(Node n : routes.get(0).getNodes()){
            System.out.print(n.getIndex() + " ");
        }

        BestRelocate bestRelocate = new BestRelocate(routes);
        bestRelocate.run();

        int i = 0;/*
        for(Route r: routes){
            System.out.print("Route " + i++ + ": ");
            for(Node n: r.getNodes()){
                //System.out.print(n.getIndex() + "; ");
            }
            System.out.print(" Costo totale: " + r.getCost() + ";\t");
            System.out.print("TotLinehaul: " + r.getTotLinehaul());
            System.out.println();
        }*/

//        core.BestRelocate bestRelocate = new core.BestRelocate(routes);
//        bestRelocate.run();
    }
}
