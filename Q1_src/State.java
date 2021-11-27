import java.util.ArrayList;
import java.util.List;

public class State {
    State parent = null; // for BFS
    int[][]view;
    List<Operation> legalOperations = new ArrayList<>();
    List<int[][]> reachableViews = new ArrayList<>();
    int zeroI;
    int zeroJ;
    int lengthI;
    int lengthJ;

    public State(int[][]view) {
        this.view = view;
        this.lengthI = view.length;
        this.lengthJ = view[0].length;
        this.findZeros();
        this.findLegalOperations();
        this.findReachableViews();
    }

    public State(State toCopy) {
        this.view = toCopy.view.clone();
        this.legalOperations = new ArrayList<>(toCopy.legalOperations);
        this.reachableViews = new ArrayList<>(toCopy.reachableViews);
        this.zeroI = toCopy.zeroI;
        this.zeroJ = toCopy.zeroJ;
        this.lengthI = toCopy.lengthI;
        this.lengthJ = toCopy.lengthJ;
    }

    private void findReachableViews() {
        for(Operation o : legalOperations)
            this.reachableViews.add(o.move(new State(this)));
    }

    private void findZeros() {
        for(int i=0; i<lengthI; i++)
            for (int j = 0; j < lengthJ; j++)
                if (view[i][j] == 0) {
                    zeroI = i;
                    zeroJ = j;
                    return;
                }
    }

    private void findLegalOperations() {
        if(zeroI != lengthI-1) legalOperations.add(new Up());
        if(zeroI != 0) legalOperations.add(new Down());
        if(zeroJ != lengthJ-1) legalOperations.add(new Left());
        if(zeroJ != 0) legalOperations.add(new Right());
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof State)) return false;

        State s = (State) o;
        for(int i=0; i<this.lengthI; i++)
            for(int j=0; j<lengthJ; j++)
                if(this.view[i][j] != s.view[i][j])
                    return false;
        return true;
    }

    public int costTo(State s) {
        for(int i=0; i<this.lengthI; i++)
            for(int j=0; j<lengthJ; j++)
                if(this.view[i][j] != s.view[i][j])
                    return Math.abs(this.view[i][j] - s.view[i][j]);
        return 0;
    }

    public List<State> sortByCost(List<State> list) {
        list = new ArrayList<>(list);
        List<State>sorted = new ArrayList<>();
        List<Integer>costs = new ArrayList<>();
        for(State s : list)
            costs.add(this.costTo(s));
        while(list.size() > 0) {
            int min = 10;
            for (Integer cost : costs)
                if (cost < min) min = cost;
            sorted.add(list.remove(costs.indexOf(min)));
            costs.remove((Integer)min);

        }
        return sorted;
    }
//    public List<State> sortByCost(List<State> list) {
//        List<State>sorted = new ArrayList<>();
//        int[] costs = new int[list.size()];
//        for(int i=0; i<costs.length; i++)
//            costs[i] = this.costTo(list.get(i));
//        while(sorted.size() < list.size()) {
//            int min = 10;
//            int mindex = -1;
//            for(int i=0; i<costs.length; i++)
//                if (costs[i] < min) {
//                    min = costs[i];
//                    mindex = i;
//                }
//            sorted.add(list.get(mindex));
//        }
//        return sorted;
//    }

    public void print() {
    border();
    for(int i=0; i<lengthI; i++) {
        content(i);
        border();
    }
    System.out.println();
    }
    private void border() {
        System.out.print('+');
        for(int i=0; i<lengthJ; i++) System.out.print("-+");
        System.out.println();
    }
    private void content(int index) {
        System.out.print('|');
        int[]row = view[index];
        for(int i=0; i<lengthJ; i++)
            if(row[i] > 0) System.out.print(""+row[i]+'|');
            else if(row[i] == 0) System.out.print(" "+'|');
        System.out.println();
    }

    public static void main(String[]args) {
        State s1 = new State(new int[][]{{1,4,2},{5,0,3}});
        List<State>children = new ArrayList<>();
        for(int[][]v : s1.reachableViews) {
            children.add(new State(v));
            System.out.println(s1.costTo(new State(v)));
        }
        List<State>sorted = s1.sortByCost(children);

        System.out.println();
    }
}
