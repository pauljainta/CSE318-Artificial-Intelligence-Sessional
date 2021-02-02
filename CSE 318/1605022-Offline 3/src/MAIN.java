import com.sun.tools.javac.Main;

import java.util.*;

class State
{
    Variable matrix[][];

    List<Variable> unassignedVariables;

    public State(Variable[][] matrix) {
        this.matrix=new Variable[MAIN.N][MAIN.N];
        for(int i=0;i<MAIN.N;i++)
        {
            for(int j=0;j<MAIN.N;j++)
            {
              this.matrix[i][j]=
                      new Variable(matrix[i][j].getValue(),matrix[i][j].getRow(),matrix[i][j].getCol());
            }
        }


        unassignedVariables=new ArrayList<>();
        for(int i=0;i<MAIN.N;i++)
        {
            for(int j=0;j<MAIN.N;j++)
            {
                getDomain(this.matrix[i][j]);
               if(this.matrix[i][j].getValue()==0) unassignedVariables.add(this.matrix[i][j]);
            }
        }
        for(int i=0;i<MAIN.N;i++)
        {
            for(int j=0;j<MAIN.N;j++)
            {
                getDynamicDegree(this.matrix[i][j]);
            }
        }


    }

    public Variable[][] getMatrix() {
        return matrix;
    }

    public List<Variable> getUnassignedVariables() {
        return unassignedVariables;
    }

    public List<Integer> getDomain(Variable variable)
    {

        List<Integer> dom=new ArrayList<>();
        List<Integer> dom_compliment=new ArrayList<>();

        //add rowwise non-domain

        for(int j = 0; j<MAIN.N; j++)
        {
            int val=matrix[variable.getRow()][j].getValue();
            if(val!=0) dom_compliment.add(val);
        }

        //add colwise non-domain
        for(int i = 0; i<MAIN.N; i++)
        {
            int val=matrix[i][variable.getCol()].getValue();
            if(val!=0) dom_compliment.add(val);
        }

        //create domain now

        for(int i=1;i<=MAIN.N;i++)
        {
            if(!dom_compliment.contains(i))
            {
                dom.add(i);
            }
        }

        variable.setDomain(dom);

        return dom;
    }

    public int getDynamicDegree(Variable variable)
    {
        int dydeg=0;
        //get rowwise unassigned variable number
        for(int j = 0; j<MAIN.N; j++)
        {
            int val=matrix[variable.getRow()][j].getValue();
            if(val==0) dydeg++;
        }

        //add colwise unassigned variable number
        for(int i = 0; i<MAIN.N; i++)
        {
            int val=matrix[i][variable.getCol()].getValue();
            if(val==0) dydeg++;
        }

        variable.setDynamicDegree(dydeg);


        return dydeg;

    }

    public boolean updateState(Variable variable)
    {
//        State state=new State(this.matrix);
//        state.matrix[variable.getRow()][variable.getCol()]=variable;
//        state=new State(state.matrix);

        int row=variable.getRow();
        int col=variable.getCol();
        this.matrix[row][col]=variable;

        unassignedVariables=new ArrayList<>();
        for(int i=0;i<MAIN.N;i++)
        {
            for(int j=0;j<MAIN.N;j++)
            {
                if(this.matrix[i][j].getValue()==0)
                {
                    unassignedVariables.add(this.matrix[i][j]);

                }
            }
        }

        for(Variable v:unassignedVariables)
        {
            getDomain(this.matrix[v.getRow()][v.getCol()]);
            getDynamicDegree(this.matrix[v.getRow()][v.getCol()]);
        }




//        for(int i=0;i<MAIN.N;i++)
//        {
//            for(int j=0;j<MAIN.N;j++)
//            {
//                getDynamicDegree(this.matrix[i][j]);
//            }
//        }


        for(int i=0;i<MAIN.N;i++)
        {
            if(matrix[row][i].getDomain().size()==0 && i!=col) return false;
        }

        for(int i=0;i<MAIN.N;i++)
        {
            if(matrix[i][col].getDomain().size()==0 && i!=row) return false;

        }


        return true;



    }



    public void printState()
    {
        for(int i=0;i<MAIN.N;i++)
        {
            for(int j=0;j<MAIN.N;j++)
            {
                System.out.print(matrix[i][j].getValue()+" ");
            }
            System.out.println("\n");
        }
        System.out.println("\n\n");

    }


}

public class MAIN {

    static int N, no_of_nodes,no_of_backtracks;



    public static boolean recursive_BT(State state,int type)
    {
        no_of_nodes++;

        if(state.unassignedVariables.size()==0)
        {
           // state.printState();
            System.out.println("Number of Nodes="+ no_of_nodes +", "+"Number of BackTracks="+no_of_backtracks);

            return true;
        }

        if(type==1) //for smallest domain first
        {
            state.unassignedVariables.sort(new DomainComp());
        }

        else if(type==2) // for random
        {
          //  Collections.shuffle(state.unassignedVariables,new Random(System.nanoTime()));
        }
        else if(type==3) //for breluz
        {
            state.unassignedVariables.sort(new Breluz() );
        }

        else if(type==4) //for domddeg
        {
            state.unassignedVariables.sort(new Domddeg() );
        }

        else return false ;




          Variable variable = state.unassignedVariables.get(0);

          for (int x : variable.getDomain()) {
              variable.setValue(x);
              state.updateState(variable);
              boolean result = recursive_BT(state, type);
              if (result) return result;
              no_of_backtracks++;


              variable.setValue(0);
              state.updateState(variable);

          }

        return false;
    }


    public static boolean Forward_Checking(State state,int type)
    {
        no_of_nodes++;

        if(state.unassignedVariables.size()==0)
        {

           // state.printState();
            System.out.println("Number of Nodes="+ no_of_nodes +", "+"Number of BackTracks="+no_of_backtracks);
            return true;
        }



        if(type==1) //for smallest domain first
        {
            state.unassignedVariables.sort(new DomainComp());
        }

        else if(type==2) // for random
        {
//            state.unassignedVariables.sort(new DynamicDegreeComp());
        }
        else if(type==3) //for breluz
        {
            state.unassignedVariables.sort(new Breluz() );
        }

        else if(type==4) //for domddeg
        {
            state.unassignedVariables.sort(new Domddeg() );
        }
        else
        {

        }




        Variable variable = state.unassignedVariables.get(0);

        if(variable.getDomain().size()==0) return false;

        for (int x : variable.getDomain()) {
             variable.setValue(x);

             boolean checkIfOk=state.updateState(variable);
             if(checkIfOk)
             {
                 boolean result = Forward_Checking(state, type);
                 if(result) return result;
             }

            no_of_backtracks++;


            variable.setValue(0);
            state.updateState(variable);

        }




        return false;
    }











    public static void main(String[] args) {

        while(true) {
            Variable matrix[][];
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter n:");
            N = scanner.nextInt();
            if(N==-10) break;
            matrix = new Variable[N][N];

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    Variable variable = new Variable(scanner.nextInt(), i, j);
                    matrix[i][j] = variable;
                }
            }


            no_of_nodes = 0;
            no_of_backtracks = 0;
            System.out.print("For recursive BT with Smallest domain first:");
            recursive_BT(new State(matrix.clone()), 1);

            no_of_nodes = 0;
            no_of_backtracks = 0;
            System.out.print("For recursive BT with Random selection:");
            recursive_BT(new State(matrix.clone()), 2);

            no_of_nodes = 0;
            no_of_backtracks = 0;
            System.out.print("For recursive BT with Breluz heuristics:");
            recursive_BT(new State(matrix.clone()), 3);

            no_of_nodes = 0;
            no_of_backtracks = 0;
            System.out.print("For recursive BT with Domddeg heuristics:");
            recursive_BT(new State(matrix.clone()), 4);

            no_of_nodes = 0;
            no_of_backtracks = 0;
            System.out.print("\nFor Forward Check with Smallest domain first:");
            Forward_Checking(new State(matrix.clone()), 1);

            no_of_nodes = 0;
            no_of_backtracks = 0;
            System.out.print("\nFor Forward Check with Random Selection:");
            Forward_Checking(new State(matrix.clone()), 2);

            no_of_nodes = 0;
            no_of_backtracks = 0;
            System.out.print("\nFor Forward Check with Breluz heuristics:");
            Forward_Checking(new State(matrix.clone()), 3);

            no_of_nodes = 0;
            no_of_backtracks = 0;
            System.out.print("\nFor Forward Check with Domddeg heuristics:");
            Forward_Checking(new State(matrix.clone()), 4);
        }




    }

}
