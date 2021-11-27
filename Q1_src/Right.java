public class Right implements Operation{
    @Override
    public int[][] move(State s) {
        int[][] movedView= new int[s.lengthI][s.lengthJ];
        for(int i=0; i<s.lengthI; i++) {
            for(int j=0; j<s.lengthJ; j++) {
                movedView[i][j] = s.view[i][j];
            }
        }
        movedView[s.zeroI][s.zeroJ] = s.view[s.zeroI][s.zeroJ-1];
        movedView[s.zeroI][s.zeroJ-1] = 0;

        return movedView;
    }
}
