import java.util.Stack;

public class Node {
    public int [][] arr;
     Node parent;
     int depth;
      int move_flag;

    public int getDepth() {
        return depth;
    }

    public Node() {
        depth=0;
        parent=null;
       move_flag=0;

    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public Node getParent() {
        return parent;

    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int[][] getArr() {
        return arr;
    }

    public void setArr(int[][] arr) {
       this.arr=arr;

    }

    void print()
    {
        for(int i=0;i<arr.length;i++)
        {
            for(int j=0;j<arr.length;j++)
            {
                System.out.print(arr[i][j]+" ");
            }
            System.out.println(" ");
        }


    }


    int displacement_heuristic()
    {

        int [][] goal_arr=new int[arr.length][arr.length];
        int k=1;
        for(int i=0;i<goal_arr.length;i++)
        {
            for(int j=0;j<goal_arr.length;j++)
            {
                goal_arr[i][j]=k++;

            }
        }
        goal_arr[goal_arr.length-1][goal_arr.length-1]=0;



        int displacement=0;

        for(int i=0;i<goal_arr.length;i++)
        {
            for(int j=0;j<goal_arr.length;j++)
            {
                if(goal_arr[i][j]!=arr[i][j] && arr[i][j]!=0) displacement++;

            }
        }



          return displacement+this.depth;

    }


    int manhattan_heuristic()
    {
        int manhattan=0;

        int [][] goal_arr=new int[arr.length][arr.length];
        int k=1;
        for(int i=0;i<goal_arr.length;i++)
        {
            for(int j=0;j<goal_arr.length;j++)
            {
                goal_arr[i][j]=k++;

            }
        }
        goal_arr[goal_arr.length-1][goal_arr.length-1]=0;

        for(int i=0;i<goal_arr.length*goal_arr.length;i++)
        {
            int goal_j=0,goal_k=0;
            int state_j=0,state_k=0;

            for(int j=0;j<goal_arr.length;j++)
            {
                for( k=0;k<goal_arr.length;k++)
                {
                    if(i==goal_arr[j][k])
                    {
                        goal_j=j;
                        goal_k=k;
                    }

                }
            }
            for(int j=0;j<goal_arr.length;j++)
            {
                for( k=0;k<goal_arr.length;k++)
                {
                    if(i==this.arr[j][k])
                    {
                        state_j=j;
                        state_k=k;
                    }

                }
            }


            manhattan+=(Math.abs(goal_j-state_j)+Math.abs(state_k-goal_k));



        }



        return  this.depth+manhattan;
    }







    public Node down()
    {

        int [][] new_arr=new int[arr.length][arr.length];
        for(int i=0;i<arr.length;i++)
        {
            for(int j=0;j<arr.length;j++)
            {
                new_arr[i][j]=arr[i][j];
            }
        }

        int empty_row_pos=-1;
        int empty_col_pos=-1;
        for(int i=0;i<arr.length;i++)
        {
            for(int j=0;j<arr.length;j++)
            {
                if(arr[i][j]==0){
                    empty_row_pos=i;
                    empty_col_pos=j;
                }

            }
        }

        if(empty_row_pos==arr.length-1) return null;

        int temp=new_arr[empty_row_pos+1][empty_col_pos];
        new_arr[empty_row_pos][empty_col_pos]=temp;
        new_arr[empty_row_pos+1][empty_col_pos]=0;
        Node node=new Node();
        node.setArr(new_arr);
        node.setDepth(this.depth+1);

        node.setParent(this);
        node.move_flag=1;
        return node;
    }



    public Node up()
    {
        int [][] new_arr=new int[arr.length][arr.length];
        for(int i=0;i<arr.length;i++)
        {
            for(int j=0;j<arr.length;j++)
            {
                new_arr[i][j]=arr[i][j];
            }
        }

        int empty_row_pos=-1;
        int empty_col_pos=-1;
        for(int i=0;i<arr.length;i++)
        {
            for(int j=0;j<arr.length;j++)
            {
                if(arr[i][j]==0){
                    empty_row_pos=i;
                    empty_col_pos=j;
                }

            }
        }

        if(empty_row_pos==0) return null;

        int temp=new_arr[empty_row_pos-1][empty_col_pos];
        new_arr[empty_row_pos][empty_col_pos]=temp;
        new_arr[empty_row_pos-1][empty_col_pos]=0;
        Node node=new Node();
        node.setArr(new_arr);
        node.setParent(this);
        node.setDepth(this.depth+1);
         node.move_flag=2;


        return node;
    }


    public Node right()
    {
        int [][] new_arr=new int[arr.length][arr.length];
        for(int i=0;i<arr.length;i++)
        {
            for(int j=0;j<arr.length;j++)
            {
                new_arr[i][j]=arr[i][j];
            }
        }

        int empty_row_pos=-1;
        int empty_col_pos=-1;
        for(int i=0;i<arr.length;i++)
        {
            for(int j=0;j<arr.length;j++)
            {
                if(arr[i][j]==0){
                    empty_row_pos=i;
                    empty_col_pos=j;
                }

            }
        }

        if(empty_col_pos==arr.length-1) return null;

        int temp=new_arr[empty_row_pos][empty_col_pos+1];
        new_arr[empty_row_pos][empty_col_pos]=temp;
        new_arr[empty_row_pos][empty_col_pos+1]=0;
        Node node=new Node();
        node.setArr(new_arr);
        node.setDepth(this.depth+1);
        node.setParent(this);
      node.move_flag=3;
        return node;
    }



    public Node left()
    {
        int [][] new_arr=new int[arr.length][arr.length];
        for(int i=0;i<arr.length;i++)
        {
            for(int j=0;j<arr.length;j++)
            {
                new_arr[i][j]=arr[i][j];
            }
        }

        int empty_row_pos=-1;
        int empty_col_pos=-1;
        for(int i=0;i<arr.length;i++)
        {
            for(int j=0;j<arr.length;j++)
            {
                if(arr[i][j]==0){
                    empty_row_pos=i;
                    empty_col_pos=j;
                }

            }
        }

        if(empty_col_pos==0) return null;

        int temp=new_arr[empty_row_pos][empty_col_pos-1];
        new_arr[empty_row_pos][empty_col_pos]=temp;
        new_arr[empty_row_pos][empty_col_pos-1]=0;
        Node node=new Node();
        node.setArr(new_arr);
        node.setDepth(this.depth+1);
        node.setParent(this);
        node.move_flag=4;
        return node;
    }


    void path()
    {

        Stack<Node> stack=new Stack<>();
        Node node=this;

        while(node.parent!=null)
        {
            stack.push(node);
            node=node.parent;


        }

        while (!stack.isEmpty())
        {
            Node n=stack.pop();
            n.print();
            System.out.println("\n\n");


        }



    }

    void solve_puzzle_manhattan()
    {

        int explored_node_count=1;


        int [][] goal_arr=new int[arr.length][arr.length];
        int k=1;
        for(int i=0;i<goal_arr.length;i++)
        {
            for(int j=0;j<goal_arr.length;j++)
            {
                goal_arr[i][j]=k++;

            }
        }
        goal_arr[goal_arr.length-1][goal_arr.length-1]=0;


        while(true)
        {

            Node node= Main.priorityQueue_manhattan.poll();
          ;

            boolean matched=true;


            for(int i=0;i<goal_arr.length;i++)
            {
                for(int j=0;j<goal_arr.length;j++)
                {
                    if(node.arr[i][j]!=goal_arr[i][j])
                    {

                        matched=false;
                    }
                }
            }

            if(matched)
            {


                System.out.println("\n\n Explored node count == "+explored_node_count);
                node.path();
                System.out.println("No of swaps: "+node.depth);
                break;

            }


                Node node1 = node.up();

                if (node1 != null && node.move_flag!=1) {

                    explored_node_count++;
                    Main.priorityQueue_manhattan.add(node1);
                }


                Node node2 = node.down();

                if (node2 != null  && node.move_flag!=2) {

                    explored_node_count++;

                    Main.priorityQueue_manhattan.add(node2);
                }



                Node node3 = node.right();

                if (node3 != null && node.move_flag!=4) {

                    explored_node_count++;

                    Main.priorityQueue_manhattan.add(node3);
                }


                Node node4 = node.left();

                if (node4 != null && node.move_flag!=3) {

                    explored_node_count++;

                    Main.priorityQueue_manhattan.add(node4);
                }


        }




    }






    void solve_puzzle()
    {

       int explored_node_count=1;
        int [][] goal_arr=new int[arr.length][arr.length];
        int k=1;
        for(int i=0;i<goal_arr.length;i++)
        {
            for(int j=0;j<goal_arr.length;j++)
            {
                goal_arr[i][j]=k++;

            }
        }
        goal_arr[goal_arr.length-1][goal_arr.length-1]=0;


        while(true)
        {

           Node node= Main.priorityQueue.poll();

           boolean matched=true;


           for(int i=0;i<goal_arr.length;i++)
           {
               for(int j=0;j<goal_arr.length;j++)
               {
                   if(node.arr[i][j]!=goal_arr[i][j])
                   {

                       matched=false;
                   }
               }
           }

           if(matched)
           {

               System.out.println("Explored Node count== "+explored_node_count);
               node.path();
               System.out.println("No of swaps: "+node.depth);
               break;

           }



               Node node1 = node.up();

               if (node1 != null && node.move_flag!=1) {

                   explored_node_count++;
                   Main.priorityQueue.add(node1);
               }

                Node node2 = node.down();

                if (node2 != null && node.move_flag!=2) {

                    explored_node_count++;


                    Main.priorityQueue.add(node2);
                }

                Node node3 = node.right();

                if (node3 != null && node.move_flag!=4) {

                    explored_node_count++;

                    Main.priorityQueue.add(node3);
                }





                Node node4 = node.left();

                if (node4 != null && node.move_flag!=3) {

                    explored_node_count++;

                    Main.priorityQueue.add(node4);
                }

        }




    }




}
