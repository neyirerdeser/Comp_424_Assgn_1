import java.util.ArrayList;
import java.util.List;

public class Puzzle {
    State initial;
    State goal;

    //https://stackoverflow.com/questions/8922060/how-to-trace-the-path-in-a-breadth-first-search
    public List<State> BFS() {
        List<State> visited = new ArrayList<>();
        List<State> queue = new ArrayList<>();
        queue.add(this.initial);
        while(queue.size()>0) {
            State curr = queue.remove(0);
            if(curr.equals(this.goal)) {
                List<State> path = new ArrayList<>();
                while(curr != null) {
                    path.add(0,curr);
                    curr = curr.parent;
                }
                return path;
            }
            else if(!visited.contains(curr)) {
                List<State>neighborhood = new ArrayList<>();
                for(int[][] view : curr.reachableViews) {
                    State neighbor = new State(view);
                    if(!queue.contains(neighbor) && !visited.contains(neighbor)) {
                        neighbor.parent = curr;
                        neighborhood.add(neighbor);
                    }
                }
                neighborhood = curr.sortByCost(neighborhood);
                queue.addAll(neighborhood);
                visited.add(curr);
            }
        }
        return null;
    }

    public List<State> UCS() {
        // same as bfs under given circumstances
        return this.BFS();
    }

    //https://www.educative.io/edpresso/how-to-implement-depth-first-search-in-python
    private void DFSiter(List<State>visited, List<State>path, State s, int limit) {
        if(s.equals(this.goal)) {
            while(s!=null) {
                path.add(0,s);
                s = s.parent;
            }
            return;
        }
        if(!visited.contains(s)) {
            visited.add(s);
            List<State>neighbor = new ArrayList<>();
            for(int[][]v : s.reachableViews)
                neighbor.add(new State(v));
            neighbor = s.sortByCost(neighbor);
            int depth = 0;
            for (State n : neighbor) {
                if (!visited.contains(n)) n.parent = s;
                if(++depth >= limit) break;
                DFSiter(visited, path, n, limit);
            }
        }
    }
    public List<State> DFS() {
        List<State>path = new ArrayList<>();
        DFSiter(new ArrayList<>(), path, this.initial, Integer.MAX_VALUE);
        return path;
    }

    public List<State> IDS(int deptLimit) {
        List<State>path = new ArrayList<>();
        int limit = 1;
        while(path.isEmpty() && limit <= deptLimit)
            DFSiter(new ArrayList<>(), path, this.initial, limit++);
        return path;
    }

    public static void main(String[]args) {
        Puzzle six = new Puzzle();
        six.initial =new State(new int[][]{{1,4,2},{5,3,0}});
        six.goal = new State(new int[][]{{0,1,2},{5,4,3}});

//        System.out.println("BFS path length : "+six.BFS().size());
//        for(State s : six.BFS()) s.print();
//        System.out.println("UCS path length : "+six.UCS().size());
//        for(State s : six.UCS()) s.print();
//        System.out.println("DFS path length : "+six.DFS().size());
//        for(State s : six.DFS()) s.print();
//        System.out.println("IDS path length : "+six.IDS(50).size());
        for(State s : six.IDS(50)) s.print();

    }
}
