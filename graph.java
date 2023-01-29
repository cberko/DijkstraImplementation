import java.util.*;
public class graph {
    int numOfPoints;
    int numOfFlags;
    ArrayList<Node>[] AdjList;
    HashMap<String,Integer> flags;
    HashMap<String,Integer> Points;
    public graph(int numOfPoints, int numOfFlags){
        this.numOfPoints = numOfPoints;
        this.numOfFlags = numOfFlags;
        AdjList = new ArrayList[numOfPoints];
        flags = new HashMap<>();
        Points = new HashMap<>();
    }
    public void addEdge(int start, int end, int weight) {
        if(AdjList[(start)] == null){
            AdjList[(start)] = new ArrayList<>();
        }
        if(start != end) {
            AdjList[(start)].add(new Node((end), weight));
        }
        if(AdjList[(end)] == null){
            AdjList[(end)] = new ArrayList<>();
        }
        if(start!=(end)){
            AdjList[(end)].add(new Node((start),weight));
        }

    }
    public int raceShortestPath(String start, String end) {
        try {
            ArrayList<Integer> distance = new ArrayList<>();
            Comparator<Node> comparator = new Comparator<Node>() {
                @Override
                public int compare(Node o1, Node o2) {
                    return o1.distance - o2.distance;
                }
            };

            PriorityQueue<Node> queue = new PriorityQueue<Node>(numOfPoints, comparator);
            for (int i = 0; i < numOfPoints; i++) {
                distance.add(Integer.MAX_VALUE);
            }
            distance.set(Points.get(start), 0);
            queue.add(new Node(Points.get(start), 0));
            while (!queue.isEmpty()) {
                Node node = queue.poll();
                int u = node.indexOfPoint;
                for (int i = 0; i < AdjList[u].size(); i++) {
                    int v = AdjList[u].get(i).indexOfPoint;
                    int weight = AdjList[u].get(i).distance;
                    if (distance.get(v) > distance.get(u) + weight) {
                        distance.set(v, distance.get(u) + weight);
                        queue.add(new Node(v, distance.get(v)));
                    }
                }
            }
            if(distance.get(Points.get(end)) == Integer.MAX_VALUE){
                return -1;
            }
            return distance.get(Points.get(end));
        } catch (Exception e) {
            return -1;
        }
    }
    public ArrayList<Integer> fakeRaceShortestPath(String start){
        ArrayList<Integer> distance = new ArrayList<>();
        Comparator<Node> comparator = new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.distance - o2.distance;
            }
        };

        PriorityQueue<Node> queue = new PriorityQueue<Node>(numOfPoints, comparator);
        for (int i = 0; i < numOfPoints; i++) {
            distance.add(Integer.MAX_VALUE);
        }
        distance.set(Points.get(start), 0);
        try{
        queue.add(new Node(Points.get(start), 0));
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            int u = node.indexOfPoint;
            for (int i = 0; i < AdjList[u].size(); i++) {
                int v = AdjList[u].get(i).indexOfPoint;
                int weight = AdjList[u].get(i).distance;
                if (distance.get(v) > distance.get(u) + weight) {
                    distance.set(v, distance.get(u) + weight);
                    queue.add(new Node(v, distance.get(v)));
                }
            }
        }
        }
        catch (Exception e){
            return distance;
        }
        //System.out.println(distance);
        return distance;

    }

    public int flagShortestPath(graph graph) {
        graph flagGraph = new graph(graph.numOfFlags, 0);
        int totalDistance = 0;
        int count = 0;
        for (String flag : graph.flags.keySet()) {
            flagGraph.Points.put(flag, count);
            count++;
        }
        for (String flag : graph.flags.keySet()) {
            ArrayList<Integer> distance = graph.fakeRaceShortestPath(flag);
            for(int i = 0; i < distance.size(); i++){
                if(graph.flags.containsValue(i)){
                    flagGraph.addEdge(graph.flags.get(flag), i, distance.get(i));
                }

            }
        }
        PriorityQueue<Node> queue = new PriorityQueue<Node>(flagGraph.numOfPoints, new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.distance - o2.distance;
            }
        });
        boolean visited[] = new boolean[numOfFlags];
        for (int i = 0; i < numOfFlags; i++) {
            visited[i] = false;
        }
        queue.add(new Node(0, 0));
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            int u = node.indexOfPoint;
            if (visited[u]) {
                continue;
            }
            visited[u] = true;
            //System.out.println(node.distance);
            totalDistance += node.distance;
            for (int i = 0; i < flagGraph.AdjList[u].size(); i++) {
                int v = flagGraph.AdjList[u].get(i).indexOfPoint;
                int weight = flagGraph.AdjList[u].get(i).distance;
                if(!visited[v]){
                    queue.add(new Node(v, weight));
                }
            }
        }
        if(totalDistance ==Integer.MAX_VALUE){
            return -1;
        }
        return totalDistance;
    }
    }




