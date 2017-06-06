package core;

import utils.DistanceMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by ibbus on 06/06/2017.
 */
public class TSP {
    private String type;
    private DistanceMap distanceMap;
    private TSPInstance tspInstance;

    public TSP(String type, TSPInstance tspInstance, DistanceMap distanceMap){
        this.type = type;
        this.distanceMap = distanceMap;
        this.tspInstance = tspInstance;
    }

    public List<Integer> getTSP(){
        // List to be returned
        ArrayList<Integer> visitOrder = new ArrayList();

        // Initialization of variables needed
        int numberNodes = tspInstance.getTotNodes();
        Stack<Integer> stack = new Stack();
        int visited[] = new int[numberNodes+1];
        int element, i;
        int dst = 0;
        Double min = Double.parseDouble(Integer.MAX_VALUE + "");
        boolean minFlag = false;

        // Visit of the first node
        visited[1] = 1;
        stack.push(1);
        visitOrder.add(1);

        while(!stack.isEmpty()) {
            element = stack.peek();
            i = 1;
            min = Double.parseDouble(Integer.MAX_VALUE + "");

            while(i <= numberNodes) {
                Double distance = distanceMap.getDistance(tspInstance.getNodes().get(element), tspInstance.getNodes().get(i));
                System.out.println("Sto calcolando la distanza tra " + tspInstance.getNodes().get(element).getIndex() + " e " +
                        tspInstance.getNodes().get(i).getIndex()
                );
                if (distance != 0 && visited[i] == 0) {

                    if (min > distance) {
                        min = distance;
                        dst = i;
                        minFlag = true;
                    }
                }
                i++;
            }

            if(minFlag){
                visited[dst] = 1;
                System.out.println("Ho visitato " + dst);
                stack.push(dst);
                visitOrder.add(dst);
                minFlag = false;

            }

            stack.pop();
        }

        return visitOrder;
    }
}
//1 24 9 20 22 5 17 2 8 15 11 21 14 10 12 3 25 13 4 16 7 18 6 23 19