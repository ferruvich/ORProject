package core;

import utils.NodeRoute;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ibbus on 22/06/2017.
 * AsyncTask, Listenable Future
 */
public class BestRelocate {

    List<Route> routes;
    Map<NodeRoute, Double> mapCost;

    public BestRelocate(List<Route> route){
        this.routes = route;
        mapCost = new HashMap<>();
    }

    public Map<Node, List<NodeRoute>> run() throws IOException {
        int i, j, numRoute = 0;
        Map<Node, List<NodeRoute>> swapMap = new HashMap<>();

        for(Route r : routes){
            List<Node> nodeList = r.getNodes();
            Route best = r;

            System.out.println("\nNodeList size: " + nodeList.size());

            System.out.println("Numero percorso: " + numRoute);

            for(i=1; i<r.getNodes().size() - 1; i++){
                Node a = r.getNodes().get(i);
                List<NodeRoute> costList = new ArrayList<>();

                System.out.println("Nodo oggetto degli scambi: " + a.getIndex());

                for(j=1;j<r.getNodes().size()-1;j++){
                    Node b = r.getNodes().get(j);

                    if((!a.equals(b)) && a.getType().equals(b.getType())) {
                        System.out.println("\tscambio con nodo " + b.getIndex());
                        System.out.println("\tcosto pre-scambio: " + best.getCost());

                        best.swapNodes(i, j);
                        best.updateCost();

                        System.out.println("\tcosto post-scambio: " + best.getCost());

                        costList.add(new NodeRoute(j, numRoute, best.getCost()));
                        best.swapNodes(i, j);
                    }
                }
                swapMap.put(a, costList);
                System.in.read();
            }

            numRoute++;

            System.out.print("\n");
            break;
        }

        return swapMap;
    }

    public List<Node> swap(Node a, Node b, List<Node> r){
        // Scambio i nodi a e b di posizione
        r.set(a.getIndex(), b);
        r.set(b.getIndex(), a);

        return r;
    }
}
