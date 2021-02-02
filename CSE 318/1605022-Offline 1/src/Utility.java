import java.util.Comparator;

public class Utility implements Comparator<Node> {

    @Override
    public int compare(Node o1, Node o2) {

        if(o1.displacement_heuristic()>o2.displacement_heuristic())
            return 1;
        else if(o1.displacement_heuristic()<o2.displacement_heuristic())
            return -1;


        return 0;
    }
}
