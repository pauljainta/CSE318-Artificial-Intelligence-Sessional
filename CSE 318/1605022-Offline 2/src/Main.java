// Java program to implement Graph
// with the help of Generics

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class Comp {
    //Method for sorting the TreeMap based on values
    public static <K, V extends Comparable<V>> Map<K, V>
    sortByValues(final Map<K, V> map) {
        Comparator<K> valueComparator =
                new Comparator<K>() {
                    public int compare(K k1, K k2) {
                        int compare =
                                map.get(k2).compareTo(map.get(k1));
                        if (compare == 0)
                            return 1;
                        else
                            return compare;
                    }
                };

        Map<K, V> sortedByValues =
                new TreeMap<K, V>(valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }
}


    class Graph {
        private int V; // No. of vertices
        private LinkedList<Integer> adj[];
        private int EdgeCount;
        int result[];

        ArrayList<Integer> chainList;

        //Constructor
        Graph(int v) {
            V = v;
            adj = new LinkedList[v + 1];
            adj[0] = null;
            for (int i = 1; i < v + 1; ++i)
                adj[i] = new LinkedList();

            result = new int[V + 1];

            chainList=new ArrayList<>();
        }


        void addEdge(int v, int w) {
            if (!isEdge(v, w)) {
                adj[v].add(w);
                adj[w].add(v);
                EdgeCount++;
            }

        }

        public boolean isEdge(int x, int y) {
            for (int i = 0; i < adj[x].size(); i++) {
                if (adj[x].get(i) == y) return true;
            }
            for (int i = 0; i < adj[y].size(); i++) {
                if (adj[y].get(i) == x) return true;
            }

            return false;
        }

        public int getEdgeCount() {
            return EdgeCount;
        }

        public int getEdgeCount(int vertex) {
            return adj[vertex].size();
        }

        public int getVertexCount() {
            return V;
        }


        public float calculatePenalty(String stufilePath) throws Exception {
           // String filePath = "/home/jainta/IdeaProjects/1605022/src/yor-f-83.stu";
            float penalty = (float) 0.0;


            BufferedReader lineReader = new BufferedReader(new FileReader(stufilePath));
            String lineText = null;

            while ((lineText = lineReader.readLine()) != null) {
                List<Integer> slots = new ArrayList<>();

                Matcher matcher = Pattern.compile("\\d+").matcher(lineText);
                List<Integer> list = new ArrayList<Integer>();
                while (matcher.find()) {
                    list.add(Integer.parseInt(matcher.group()));
                }

                // Collections.sort(list);
                for (int i = 0; i < list.size(); i++) {
                    slots.add(result[list.get(i)]);

                }
                Collections.sort(slots);
                //   System.out.println(slots.toString());


                for (int i = 0, j = 1; i < slots.size() - 1; i++, j++) {

                    int x = slots.get(i);
                    int y = slots.get(j);

                    if (y - x == 5) penalty += 1;
                    else if (y - x == 4) penalty += 2.0;
                    else if (y - x == 3) penalty += 4.0;
                    else if (y - x == 2) penalty += 8.0;
                    else if (y - x == 1) penalty += 16.0;
                    else  {
                       // penalty += 10;
                    }


                }


            }

            lineReader.close();


            BufferedReader reader =
                    new BufferedReader(new FileReader(stufilePath));
            float students = 0;
            while (reader.readLine() != null) students++;
            reader.close();


            return penalty / students;
        }


        public int LargestEnrollmentColor(String solfilePath,String crsfilePath,String stufilePath) throws Exception {

           // String filePath = "/home/jainta/IdeaProjects/1605022/src/yor-f-83.crs";
            BufferedReader lineReader = new BufferedReader(new FileReader(crsfilePath));
            String lineText = null;
            Map<Integer, Integer> map = new TreeMap<>();


            while ((lineText = lineReader.readLine()) != null) {

                Matcher matcher = Pattern.compile("\\d+").matcher(lineText);
                List<Integer> list = new ArrayList<Integer>();
                while (matcher.find()) {
                    list.add(Integer.parseInt(matcher.group()));
                }

                map.put(list.get(0), list.get(1));


            }


            map = Comp.sortByValues(map);





            result = new int[V + 1];

            // Initialize all vertices as unassigned
            Arrays.fill(result, -1);


            Set<Map.Entry<Integer, Integer>> entries = map.entrySet();
            // Assign the first color to first vertex
            Iterator<Map.Entry<Integer, Integer>> iterator = entries.iterator();

            List<Integer> order = new ArrayList<>();
            order.add(-1);
            while (iterator.hasNext()) order.add(iterator.next().getKey());


            result[order.get(1)] = 1;

            // A temporary array to store the available colors. False
            // value of available[cr] would mean that the color cr is
            // assigned to one of its adjacent vertices
            boolean available[] = new boolean[V + 1];

            // Initially, all colors are available
            Arrays.fill(available, true);

            // Assign colors to remaining V-1 vertices



       for (int u = 2; u < V+1; u++) {


            Iterator<Integer> it = adj[order.get(u)].iterator();

            while (it.hasNext()) {
                int i = it.next();
                if (result[i] != -1)
                    available[result[i]] = false;
            }

            // Find the first available color
            int cr;
            for (cr = 1; cr < V+1; cr++) {
                if (available[cr])
                    break;
            }

            result[order.get(u)] = cr; // Assign the found color

            // Reset the values back to true for the next iteration
            Arrays.fill(available, true);
        }

        // print the result
        int colorCount=1;


        FileOutputStream fos =
                new FileOutputStream(new File(solfilePath));

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        for (int u = 1; u < V+1; u++) {
            bw.write(u+" "+result[order.get(u)]);
            bw.newLine();
        }

        bw.close();


        for (int i = 2; i < V; i++)
        {
            int j = 1;
            for (j = 1; j < i; j++)
                if (result[order.get(i)] == result[order.get(j)])
                    break;

            if (i == j)
                colorCount++;
        }





            System.out.println("For LargestEnrollment Coloring--->" +
                    ""+"Average Penalty= " + calculatePenalty(stufilePath)+", Exam slots="+colorCount);
        return colorCount;

        }


        public int MaxEdgeColor(String solfilePath,String stufilePath) throws Exception {


            LinkedList<Integer>[] vertexList = adj;

            int maxEdgedVertex = 1;
            boolean[] isSerialized = new boolean[V + 1];


            Arrays.fill(isSerialized, false);

            List<Integer> order = new ArrayList<>();
            order.add(-1);

            while (order.size() != vertexList.length) {
                for (int i = 1; i < vertexList.length; i++) {
                    if (getEdgeCount(i) > getEdgeCount(maxEdgedVertex) && !isSerialized[i]) {
                        maxEdgedVertex = i;
                    }
                }
                order.add(maxEdgedVertex);
                isSerialized[maxEdgedVertex] = true;
                for (int i = 1; i < isSerialized.length; i++) {
                    if (!isSerialized[i]) {
                        maxEdgedVertex = i;
                        break;
                    }
                }


            }


            result = new int[V + 1];

            // Initialize all vertices as unassigned
            Arrays.fill(result, -1);


            // Assign the first color to first vertex

            result[order.get(1)] = 1;


            boolean available[] = new boolean[V + 1];

            // Initially, all colors are available
            Arrays.fill(available, true);

            // Assign colors to remaining V-1 vertices
            for (int u = 2; u < V + 1; u++) {


                Iterator<Integer> it = adj[order.get(u)].iterator();
                while (it.hasNext()) {
                    int i = it.next();
                    if (result[i] != -1)
                        available[result[i]] = false;
                }

                // Find the first available color
                int cr;
                for (cr = 1; cr < V + 1; cr++) {
                    if (available[cr])
                        break;
                }

                result[order.get(u)] = cr; // Assign the found color

                // Reset the values back to true for the next iteration
                Arrays.fill(available, true);
            }

            // print the result
            int colorCount = 1;


            FileOutputStream fos
                    = new FileOutputStream(new File(solfilePath));

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

            for (int u = 1; u < V + 1; u++) {
                bw.write(u + " " + result[order.get(u)]);
                bw.newLine();
            }

            bw.close();


            for (int i = 2; i < V; i++) {
                int j = 1;
                for (j = 1; j < i; j++)
                    if (result[order.get(i)] == result[order.get(j)])
                        break;

                if (i == j)
                    colorCount++;
            }


            System.out.println("For max edge coloring--->" +
                    ""+"Average Penalty= " + calculatePenalty(stufilePath)+", Exam slots="+colorCount);
            //System.out.println(colorCount);
            return colorCount;


        }

        public int RandomColor(String solfilePath,String stufilePath) throws Exception {


            result = new int[V + 1];

            // Initialize all vertices as unassigned
            Arrays.fill(result, -1);


            // Assign the first color to first vertex

            result[1] = 1;

            // A temporary array to store the available colors. False
            // value of available[cr] would mean that the color cr is
            // assigned to one of its adjacent vertices
            boolean available[] = new boolean[V + 1];

            // Initially, all colors are available
            Arrays.fill(available, true);

            // Assign colors to remaining V-1 vertices
            for (int u = 2; u < V + 1; u++) {


                Iterator<Integer> it = adj[u].iterator();
                while (it.hasNext()) {
                    int i = it.next();
                    if (result[i] != -1)
                        available[result[i]] = false;
                }

                // Find the first available color
                int cr;
                for (cr = 1; cr < V + 1; cr++) {
                    if (available[cr])
                        break;
                }

                result[u] = cr; // Assign the found color

                // Reset the values back to true for the next iteration
                Arrays.fill(available, true);
            }

            // print the result
            int colorCount = 1;


            FileOutputStream fos =
                    new FileOutputStream(new File(solfilePath));

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

            for (int u = 1; u < V + 1; u++) {
                bw.write(u + " " + result[u]);
                bw.newLine();
            }

            bw.close();


            for (int i = 2; i < V; i++) {
                int j = 1;
                for (j = 1; j < i; j++)
                    if (result[i] == result[j])
                        break;

                if (i == j)
                    colorCount++;
            }


            System.out.println("For Random Coloring---->" +
                    "Average Penalty= " + calculatePenalty(stufilePath)+", Exam slots="+colorCount);

            return colorCount;
        }



        //Kempe Chain



      public void MakeChain(int u, int color_v)
        {
            chainList.add(u);

            for (int i=0;i<adj[u].size();i++)
            {

                if (result[adj[u].get(i)] == color_v && !chainList.contains(adj[u].get(i)))
                {
                    MakeChain(adj[u].get(i), result[u]);
                }
            }
        }

      public void KempeUtil()
        {
            chainList.clear();
            Random random=new Random();


           int u=random.ints(1,V+1).findFirst().getAsInt();
           while (adj[u].size()==0)
           {
               u=random.ints(1,V+1).findAny().getAsInt();
           }

            int len = adj[u].size();

            int v =  adj[u].get(0);


            MakeChain(u,result[v]);

            int slot1 = result[u];
            int slot2 = result[v];

            for(int i=0;i<chainList.size();i++)
            {
                int exam=chainList.get(i);
                if(result[exam]==slot2)
                {
                    result[exam] = slot1;

                }
                else result[exam]=slot2;

            }


        }

       public float KEMPE(String stufilePath,String solfilePath) throws Exception
        {


            float currentPenalty=calculatePenalty(stufilePath);

            for(int i=1;i<20;i++)
            {
                KempeUtil();

                FileOutputStream fos =
                        new FileOutputStream(new File(solfilePath));

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

                for (int u = 1; u < V + 1; u++) {
                    bw.write(u + " " + result[u]);
                    bw.newLine();
                }

                bw.close();
                float newPenalty=calculatePenalty(stufilePath);
                if(currentPenalty>newPenalty)
                {
                    currentPenalty=newPenalty;
                }

            }

            System.out.println("After processing with Kempe Chain,Average Penalty= "+currentPenalty);

            return currentPenalty;

        }





    }




public class Main {


    public static Graph consTructGraph(String stufilePath,String crsfilePath) throws Exception {

        BufferedReader reader =
                new BufferedReader(new FileReader(crsfilePath));
        int lines = 0;
        while (reader.readLine() != null) lines++;
        reader.close();


        Graph g = new Graph(lines);

        try {
            BufferedReader lineReader = new BufferedReader(new FileReader(stufilePath));
            String lineText = null;

            while ((lineText = lineReader.readLine()) != null) {

                Matcher matcher = Pattern.compile("\\d+").matcher(lineText);
                List<Integer> list = new ArrayList<Integer>();
                while (matcher.find()) {
                    list.add(Integer.parseInt(matcher.group()));
                }
                for (int i = 0; i < list.size(); i++) {
                    for (int j = i + 1; j < list.size(); j++) {
                        g.addEdge(list.get(i), list.get(j));
                    }

                }


            }

            lineReader.close();
        } catch (IOException ex) {
            System.err.println(ex);
        }

        return g;


    }

    public static void main(String args[]) throws Exception
    {
        PrintStream o = new PrintStream(new File("output.txt"));

        PrintStream console = System.out;


        System.setOut(o);

        String stufilePath,crsfilePath,solfilePath;

         stufilePath = "C:\\Users\\User\\Desktop\\1605022\\src\\yor-f-83.stu";
         crsfilePath="C:\\Users\\User\\Desktop\\1605022\\src\\yor-f-83.crs";
         solfilePath="C:\\Users\\User\\Desktop\\1605022\\src\\yor83.sol";

        System.out.println("For yor-f-83 Dataset");
        Graph graph=Main.consTructGraph(stufilePath,crsfilePath);
        graph.RandomColor(solfilePath,stufilePath);
        graph.KEMPE(stufilePath,solfilePath);
        System.out.println("\n");
        graph.MaxEdgeColor(solfilePath,stufilePath);
        graph.KEMPE(stufilePath,solfilePath);
        System.out.println("\n");
        graph.LargestEnrollmentColor(solfilePath,crsfilePath,stufilePath);
        graph.KEMPE(stufilePath,solfilePath);

        System.out.println("\n\n\n");

        System.out.println("For ute-s-92 dataset");

        stufilePath="C:\\Users\\User\\Desktop\\1605022\\src\\ute-s-92.stu";
        crsfilePath="C:\\Users\\User\\Desktop\\1605022\\src\\ute-s-92.crs";
        solfilePath="C:\\Users\\User\\Desktop\\1605022\\src\\ute-s-92.sol";

         graph=Main.consTructGraph(stufilePath,crsfilePath);
        graph.RandomColor(solfilePath,stufilePath);
        graph.KEMPE(stufilePath,solfilePath);
        System.out.println("\n");
        graph.MaxEdgeColor(solfilePath,stufilePath);
        graph.KEMPE(stufilePath,solfilePath);
        System.out.println("\n");
        graph.LargestEnrollmentColor(solfilePath,crsfilePath,stufilePath);
        graph.KEMPE(stufilePath,solfilePath);

        System.out.println("\n\n\n");


        System.out.println("For car-f-92 dataset");

        stufilePath="C:\\Users\\User\\Desktop\\1605022\\src\\car-f-92.stu";
        crsfilePath="C:\\Users\\User\\Desktop\\1605022\\src\\car-f-92.crs";
        solfilePath="C:\\Users\\User\\Desktop\\1605022\\src\\car-f-92.sol";

        graph=Main.consTructGraph(stufilePath,crsfilePath);
        graph.RandomColor(solfilePath,stufilePath);
        graph.KEMPE(stufilePath,solfilePath);
        System.out.println("\n");
        graph.MaxEdgeColor(solfilePath,stufilePath);
        graph.KEMPE(stufilePath,solfilePath);
        System.out.println("\n");
        graph.LargestEnrollmentColor(solfilePath,crsfilePath,stufilePath);
        graph.KEMPE(stufilePath,solfilePath);

        System.out.println("\n\n\n");

        System.out.println("For car-s-91 dataset");

        stufilePath="C:\\Users\\User\\Desktop\\1605022\\src\\car-s-91.stu";
        crsfilePath="C:\\Users\\User\\Desktop\\1605022\\src\\car-s-91.crs";
        solfilePath="C:\\Users\\User\\Desktop\\1605022\\src\\car-s-91.sol";

        graph=Main.consTructGraph(stufilePath,crsfilePath);
        graph.RandomColor(solfilePath,stufilePath);
        graph.KEMPE(stufilePath,solfilePath);
        System.out.println("\n");
        graph.MaxEdgeColor(solfilePath,stufilePath);
        graph.KEMPE(stufilePath,solfilePath);
        System.out.println("\n");
        graph.LargestEnrollmentColor(solfilePath,crsfilePath,stufilePath);
        graph.KEMPE(stufilePath,solfilePath);

        System.out.println("\n\n\n");



        System.out.println("For ear-f-83 dataset");

        stufilePath="C:\\Users\\User\\Desktop\\1605022\\src\\ear-f-83.stu";
        crsfilePath="C:\\Users\\User\\Desktop\\1605022\\src\\ear-f-83.crs";
        solfilePath="C:\\Users\\User\\Desktop\\1605022\\src\\ear-f-83.sol";

        graph=Main.consTructGraph(stufilePath,crsfilePath);
        graph.RandomColor(solfilePath,stufilePath);
        graph.KEMPE(stufilePath,solfilePath);
        System.out.println("\n");
        graph.MaxEdgeColor(solfilePath,stufilePath);
        graph.KEMPE(stufilePath,solfilePath);
        System.out.println("\n");
        graph.LargestEnrollmentColor(solfilePath,crsfilePath,stufilePath);
        graph.KEMPE(stufilePath,solfilePath);

        System.out.println("\n\n\n");

        System.out.println("For hec-s-92 dataset");

        stufilePath="C:\\Users\\User\\Desktop\\1605022\\src\\hec-s-92.stu";
        crsfilePath="C:\\Users\\User\\Desktop\\1605022\\src\\hec-s-92.crs";
        solfilePath="C:\\Users\\User\\Desktop\\1605022\\src\\hec-s-92.sol";

        graph=Main.consTructGraph(stufilePath,crsfilePath);
        graph.RandomColor(solfilePath,stufilePath);
        graph.KEMPE(stufilePath,solfilePath);
        System.out.println("\n");
        graph.MaxEdgeColor(solfilePath,stufilePath);
        graph.KEMPE(stufilePath,solfilePath);
        System.out.println("\n");
        graph.LargestEnrollmentColor(solfilePath,crsfilePath,stufilePath);
        graph.KEMPE(stufilePath,solfilePath);

        System.out.println("\n\n\n");

        System.out.println("For kfu-s-93 dataset");

        stufilePath="C:\\Users\\User\\Desktop\\1605022\\src\\kfu-s-93.stu";
        crsfilePath="C:\\Users\\User\\Desktop\\1605022\\src\\kfu-s-93.crs";
        solfilePath="C:\\Users\\User\\Desktop\\1605022\\src\\kfu-s-93.sol";

        graph=Main.consTructGraph(stufilePath,crsfilePath);
        graph.RandomColor(solfilePath,stufilePath);
        graph.KEMPE(stufilePath,solfilePath);
        System.out.println("\n");
        graph.MaxEdgeColor(solfilePath,stufilePath);
        graph.KEMPE(stufilePath,solfilePath);
        System.out.println("\n");
        graph.LargestEnrollmentColor(solfilePath,crsfilePath,stufilePath);
        graph.KEMPE(stufilePath,solfilePath);

        System.out.println("\n\n\n");


        System.out.println("For Ise-f-91 dataset");

        stufilePath="C:\\Users\\User\\Desktop\\1605022\\src\\lse-f-91.stu";
        crsfilePath="C:\\Users\\User\\Desktop\\1605022\\src\\lse-f-91.crs";
        solfilePath="C:\\Users\\User\\Desktop\\1605022\\src\\lse-f-91.sol";

        graph=Main.consTructGraph(stufilePath,crsfilePath);
        graph.RandomColor(solfilePath,stufilePath);
        graph.KEMPE(stufilePath,solfilePath);
        System.out.println("\n");
        graph.MaxEdgeColor(solfilePath,stufilePath);
        graph.KEMPE(stufilePath,solfilePath);
        System.out.println("\n");
        graph.LargestEnrollmentColor(solfilePath,crsfilePath,stufilePath);
        graph.KEMPE(stufilePath,solfilePath);

        System.out.println("\n\n\n");



        System.out.println("For pur-s-93 Dataset");
        stufilePath = "C:\\Users\\User\\Desktop\\1605022\\src\\pur-s-93.stu";
        crsfilePath="C:\\Users\\User\\Desktop\\1605022\\src\\pur-s-93.crs";
        solfilePath="C:\\Users\\User\\Desktop\\1605022\\src\\pur-s-93.sol";


         graph=Main.consTructGraph(stufilePath,crsfilePath);
        graph.RandomColor(solfilePath,stufilePath);
        graph.KEMPE(stufilePath,solfilePath);
        System.out.println("\n");
        graph.MaxEdgeColor(solfilePath,stufilePath);
        graph.KEMPE(stufilePath,solfilePath);
        System.out.println("\n");
        graph.LargestEnrollmentColor(solfilePath,crsfilePath,stufilePath);
        graph.KEMPE(stufilePath,solfilePath);

        System.out.println("\n\n\n");

        System.out.println("For rye-s-93 dataset");

        stufilePath="C:\\Users\\User\\Desktop\\1605022\\src\\rye-s-93.stu";
        crsfilePath="C:\\Users\\User\\Desktop\\1605022\\src\\rye-s-93.crs";
        solfilePath="C:\\Users\\User\\Desktop\\1605022\\src\\rye-s-93.sol";

        graph=Main.consTructGraph(stufilePath,crsfilePath);
        graph.RandomColor(solfilePath,stufilePath);
        graph.KEMPE(stufilePath,solfilePath);
        System.out.println("\n");
        graph.MaxEdgeColor(solfilePath,stufilePath);
        graph.KEMPE(stufilePath,solfilePath);
        System.out.println("\n");
        graph.LargestEnrollmentColor(solfilePath,crsfilePath,stufilePath);
        graph.KEMPE(stufilePath,solfilePath);

        System.out.println("\n\n\n");


        System.out.println("For sta-f-83 dataset");

        stufilePath="C:\\Users\\User\\Desktop\\1605022\\src\\sta-f-83.stu";
        crsfilePath="C:\\Users\\User\\Desktop\\1605022\\src\\sta-f-83.crs";
        solfilePath="C:\\Users\\User\\Desktop\\1605022\\src\\sta-f-83.sol";

        graph=Main.consTructGraph(stufilePath,crsfilePath);
        graph.RandomColor(solfilePath,stufilePath);
        graph.KEMPE(stufilePath,solfilePath);
        System.out.println("\n");
        graph.MaxEdgeColor(solfilePath,stufilePath);
        graph.KEMPE(stufilePath,solfilePath);
        System.out.println("\n");
        graph.LargestEnrollmentColor(solfilePath,crsfilePath,stufilePath);
        graph.KEMPE(stufilePath,solfilePath);

        System.out.println("\n\n\n");

        System.out.println("For tre-s-92 dataset");

        stufilePath="C:\\Users\\User\\Desktop\\1605022\\src\\tre-s-92.stu";
        crsfilePath="C:\\Users\\User\\Desktop\\1605022\\src\\tre-s-92.crs";
        solfilePath="C:\\Users\\User\\Desktop\\1605022\\src\\tre-s-92.sol";

        graph=Main.consTructGraph(stufilePath,crsfilePath);
        graph.RandomColor(solfilePath,stufilePath);
        graph.KEMPE(stufilePath,solfilePath);
        System.out.println("\n");
        graph.MaxEdgeColor(solfilePath,stufilePath);
        graph.KEMPE(stufilePath,solfilePath);
        System.out.println("\n");
        graph.LargestEnrollmentColor(solfilePath,crsfilePath,stufilePath);
        graph.KEMPE(stufilePath,solfilePath);

        System.out.println("\n\n\n");



        System.out.println("For uta-s-92 dataset");

        stufilePath="C:\\Users\\User\\Desktop\\1605022\\src\\uta-s-92.stu";
        crsfilePath="C:\\Users\\User\\Desktop\\1605022\\src\\uta-s-92.crs";
        solfilePath="C:\\Users\\User\\Desktop\\1605022\\src\\uta-s-92.sol";

        graph=Main.consTructGraph(stufilePath,crsfilePath);
        graph.RandomColor(solfilePath,stufilePath);
        graph.KEMPE(stufilePath,solfilePath);
        System.out.println("\n");
        graph.MaxEdgeColor(solfilePath,stufilePath);
        graph.KEMPE(stufilePath,solfilePath);
        System.out.println("\n");
        graph.LargestEnrollmentColor(solfilePath,crsfilePath,stufilePath);
        graph.KEMPE(stufilePath,solfilePath);

        System.out.println("\n\n\n");



    }
}
