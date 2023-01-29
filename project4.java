import java.io.*;


public class project4 {
    public static void main(String[] args) throws IOException {
        //measure time in miliseconds
        long startTime = System.currentTimeMillis();
        //read input file


        BufferedReader reader = new BufferedReader(new FileReader(args[0]));
        BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]));
        String line = reader.readLine();

        int numOfPoints = Integer.parseInt(line);
        line = reader.readLine();
        int numOfFlags = Integer.parseInt(line);
        graph Marathon = new graph(numOfPoints, numOfFlags);
        line = reader.readLine();
        String[] StartEnd = line.split(" ");
        String start = StartEnd[0];
        String finish = StartEnd[1];
        line = reader.readLine();
        String[] flagx = line.split(" ");
        for (int i = 0; i < flagx.length; i++) {
            Marathon.flags.put(flagx[i], i);
            Marathon.Points.put(flagx[i], i);
        }
        line = reader.readLine();
        while (line != null) {
            String[] edges = line.split(" ");
            String first = edges[0];
            Marathon.Points.computeIfAbsent(first, k -> Marathon.Points.size());

            for (int i = 1; i < edges.length; i+=2) {
                String end = edges[i];
                Marathon.Points.computeIfAbsent(end, k -> Marathon.Points.size());
                int weight = Integer.parseInt(edges[i+1]);
                Marathon.addEdge(Marathon.Points.get(first),Marathon.Points.get(end), weight);
                }
            line = reader.readLine();
            }
        writer.write(Marathon.raceShortestPath(start, finish)+"\n");
        writer.write(Marathon.flagShortestPath(Marathon)+"\n");
        long endTime = System.currentTimeMillis();

        reader.close();
        writer.close();
    }
}















