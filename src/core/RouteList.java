package core;

import utils.DistanceMatrix;

import java.util.*;

public class RouteList {

    private ArrayList<Route> routes;
    private Double totalCost = Double.MAX_VALUE;

    public RouteList() {
        this.routes = new ArrayList<>();
    }

    public void initialize(TSPInstance instance) {
        List<Integer> linehaul = instance.getLineHaulTsp();
        List<Integer> backhaul = instance.getBackHaulTsp();
        ArrayList<Node> nodes = instance.getNodes();
        ArrayList<Node> linehaulNodes = new ArrayList<>();
        ArrayList<Node> backhaulNodes = new ArrayList<>();

        Random rand = new Random();

        for (Integer in : linehaul) {
            linehaulNodes.add(nodes.get(in));
        }
        for (Integer in : backhaul) {
            backhaulNodes.add(nodes.get(in));
        }

        //Inseriamo il primo elemento
        for (int i = 0; i < instance.getTotRoutes(); i++) {
            Route r = new Route();
            r.addToRoute(nodes.get(0));
            int index = rand.nextInt(linehaulNodes.size());
            int distanceSupported = (linehaulNodes.size() / instance.getTotRoutes()) - 1;

            //Controlliamo che il nodo abbia almeno la distanza prefissata
            for (Route other : routes) {
                boolean ok = false;
                int loops = 0;
                while (!ok) {
                    loops++;
                    if (loops > 10) {
                        distanceSupported--;
                    }
                    Node nodeOfOther = other.getNodes().get(1);
                    Integer indexOfNodeOther = nodes.indexOf(nodeOfOther);
                    int actualDistance = Math.abs(index - indexOfNodeOther);
                    if (actualDistance > distanceSupported) {
                        ok = true;
                    } else {
                        index = rand.nextInt(linehaulNodes.size());
                    }
                }
            }

            r.addToRoute(linehaulNodes.get(index));
            linehaulNodes.remove(index);
            routes.add(r);
        }

        //Inseriamo gli altri nodi linehaul
        for (Node n : linehaulNodes) {
            Map<Node, Integer> lastNodes = new HashMap<>();
            Map<Node, Integer> lastNodesSorted = new TreeMap<>(
                    new Comparator<Node>() {
                        @Override
                        public int compare(Node o1, Node o2) {
                            double dist1 = DistanceMatrix.getInstance().getDistance(o1, n);
                            double dist2 = DistanceMatrix.getInstance().getDistance(o2, n);
                            return (int) (dist1 - dist2);
                        }
                    }
            );
            int i = 0;
            for (Route r : routes) {
                lastNodes.put(r.getNodes().get(r.getNodes().size() - 1), i++);
            }
            lastNodesSorted.putAll(lastNodes);
            for (Node toCkeck : lastNodesSorted.keySet()) {
                if (routes.get(lastNodes.get(toCkeck)).getTotLinehaul() + n.getCapacity() <= instance.getMaxCapacity()) {
                    routes.get(lastNodes.get(toCkeck)).addToRoute(n);
                    break;
                }
            }
        }

        //Aggiungiamo i nodi backhaul
        for (Node n : backhaulNodes) {
            Map<Node, Integer> lastNodes = new HashMap<>();
            Map<Node, Integer> lastNodesSorted = new TreeMap<>(
                    new Comparator<Node>() {
                        @Override
                        public int compare(Node o1, Node o2) {
                            double dist1 = DistanceMatrix.getInstance().getDistance(o1, n);
                            double dist2 = DistanceMatrix.getInstance().getDistance(o2, n);
                            return (int) (dist1 - dist2);
                        }
                    }
            );
            int i = 0;
            for (Route r : routes) {
                lastNodes.put(r.getNodes().get(r.getNodes().size() - 1), i++);
            }
            lastNodesSorted.putAll(lastNodes);
            for (Node toCkeck : lastNodesSorted.keySet()) {
                if (routes.get(lastNodes.get(toCkeck)).getTotBackhaul() + n.getCapacity() <= instance.getMaxCapacity()) {
                    routes.get(lastNodes.get(toCkeck)).addToRoute(n);
                    break;
                }
            }
        }

        //Finiamo, aggiungendo il magazzino a fine route
        for (Route r : routes) {
            r.addToRoute(nodes.get(0));
        }
    }

    public ArrayList<Route> getRoutes() {
        return this.routes;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public double updateTotalCost() {
        this.totalCost = 0.0;

        for (Route route : routes) {
            route.updateCost();
            this.totalCost += route.getCost();
        }

        return this.totalCost;
    }

    public void updateEachRouteCost() {
        for (Route route : routes) {
            route.updateCost();
        }
    }
}
