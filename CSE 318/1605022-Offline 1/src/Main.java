import java.io.File;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {


   public static PriorityQueue<Node> priorityQueue=new PriorityQueue<Node>(new Utility());
   public static PriorityQueue<Node> priorityQueue_manhattan=new PriorityQueue<>(new Utility_Manhattann());

    public static void main(String[] args) {

        Scanner scanner= null;
        try {
            scanner = new Scanner(new File("input.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int [][] puzzle=new int[4][4];
        int n=scanner.nextInt();

        for(int itr=0;itr<n;itr++) {

            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    //if (i != 0)
                        puzzle[j][k] = scanner.nextInt();
                }


            }

            if(itr==0) continue;
            Node root_node = new Node();
            root_node.setArr(puzzle);

            boolean solve_flag = false;
            int inversion_count = 0;


            int k = 0;
            int arr[] = new int[4 * 4];

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    arr[k++] = puzzle[i][j];

                }

            }

            for (int  i = 0; i < 4 * 4; i++) {
                for (int j = i; j < 4 * 4; j++) {
                    if (arr[i] > arr[j] && arr[i] != 0 && arr[j] != 0) inversion_count++;
                }

            }


            int blank = 4 - 1;

            for (int  i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {

                    if (puzzle[i][j] == 0) {
                        blank = 4 - i;
                    }

                }

            }


            if (blank % 2 == 0 && inversion_count % 2 != 0) solve_flag = true;
            else if (blank % 2 != 0 && inversion_count % 2 == 0) solve_flag = true;


            if (solve_flag) {

                System.out.println("Solvable");
                priorityQueue.add(root_node);
                priorityQueue_manhattan.add(root_node);

                System.out.println("\nManhattan Heuristic");

                long start = -1;
                long finish = -1;
                long elapsed_time;

                start = System.currentTimeMillis();
                root_node.solve_puzzle_manhattan();
                finish = System.currentTimeMillis();
                elapsed_time = finish - start;
                System.out.println("Elapsed time: " + elapsed_time + " ms");


                System.out.println("1.Displacement heuristic");


                start = System.currentTimeMillis();
                root_node.solve_puzzle();
                finish = System.currentTimeMillis();
                elapsed_time = finish - start;
                System.out.println("Elapsed time: " + elapsed_time + " ms");
                priorityQueue_manhattan.clear();
                priorityQueue.clear();


            } else System.out.println("not solvable");


        }

    }



}
