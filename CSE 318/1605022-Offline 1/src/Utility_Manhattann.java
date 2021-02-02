import java.util.Comparator;

public class Utility_Manhattann implements Comparator<Node> {

    @Override
    public int compare(Node o1, Node o2) {

        if(o1.manhattan_heuristic()>o2.manhattan_heuristic())
            return 1;
        else if(o1.manhattan_heuristic()<o2.manhattan_heuristic())
            return -1;

        return 0;
    }
}
