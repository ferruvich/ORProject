import core.Node;
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
        List<Node> nodes = in.getNodes();
        for(Node n: nodes){
            for(Node no: nodes){
                System.out.print("Distance between node " + n.getIndex() + " and node " + no.getIndex() + ": ");
                System.out.println(DistanceMap.getInstance().getDistance(n, no));
            }
        }
    }
}
