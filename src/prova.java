import core.Node;
import core.Route;
import core.RouteList;
import core.TSPInstance;
import utils.*;

import java.util.List;

/**
 * Created by Danieru on 30/05/2017.
 */
public class prova{
    public static void main(String[] args){
        TSPInstance in = JsonReader.getInstance().getSpecifications("InstancesJSON/A1.json");
        DistanceMap.getInstance().initialize(in);
        RouteList rl = new RouteList();
        rl.initialize(in);
        int i = 0;
        for(Route r : rl){
            System.out.print("La route " + i + " contiene i nodi ");
            for(Node n: r){
                System.out.print(n.getIndex() + ";");
            }
            System.out.println(" Con costi Linehaul: " + r.getTotLinehaul() + " e Backhaul: " + r.getTotBackhaul());
            i++;
        }
    }
}
