import core.*;
import utils.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Danieru on 30/05/2017.
 */
public class prova{
    public static void main(String[] args) throws IOException {
        TSPInstance in = JsonReader.getInstance().getSpecifications("InstancesJSON/N1.json");
        System.out.println("Max carico " + in.getMaxCapacity());
        DistanceMatrix.getInstance().initialize(in);
        RouteList routeList = new RouteList();
        routeList.initialize(in);

        int i=0;

        ArrayList<Route> routes = routeList.getRoutes();
        System.out.println("Route totali: " + routes.size());

        stampa(routes);

        Best best = new Best(routes);
        System.out.print("\n");

//        Map<Node, List<NodeRoute>> result1 = best.bestOnSameRoute(routes.get(0), Best.BEST_EXCHANGE);
//        Map<Node, List<NodeRoute>> result2 = best.bestOnSameRoute(routes.get(0), Best.BEST_RELOCATE);

//        stampa(routes);

//        System.out.print("\n");
//        Map<Node, List<NodeRoute>> result3 = best.bestBetweenRoutes(routes.get(0), Best.BEST_EXCHANGE);
        Map<Node, List<NodeRoute>> result4 = best.run(routes.get(0), Best.BEST_RELOCATE);

        stampa(routes);
//
//
//        System.out.println("Dimensione mappa: " + result.keySet().size());
//
//        Map<Node, List<NodeRoute>> newres = best.bestBetweenRoutes(routes.get(0), Best.BEST_RELOCATE);
//        System.out.println("Costo pre-relocate: " + best.getCost());
//
//        for(Node node : newres.keySet()){
//            List<NodeRoute> nodeRoutes = newres.get(node);
//            System.out.println("Lista del nodo " + node.getIndex() + ": ");
//
//            for(NodeRoute nodeRoute : nodeRoutes){
//                System.out.println("\tlo scambio con " + nodeRoute.getIndex() + " ha ridotto il costo a " + nodeRoute.getCost());
//            }
//        }

//        for(Node n : result.keySet()){
//            System.out.println("\nCosti nodo " + n.getIndex());
//
//            for(NodeRoute nodeRoute : result.get(n)){
//                System.out.println("\t" + nodeRoute.getIndex() + ": " + nodeRoute.getCost());
//            }
//            System.in.read();
//        }

    }

    public static void stampa(List<Route> routes){
        int i=0;

        System.out.println();
        for(Route route : routes){
            System.out.print("Numero percorso: " + (i++) + ": ");
            for(Node n : route.getNodes()) {
                System.out.print(n.getIndex() + " ");
            }
            System.out.print("\n");
        }

    }
}
