public class Down implements Operation{
    @Override
    public int[][] move(State s) {
        int[][] movedView= new int[s.lengthI][s.lengthJ];
        for(int i=0; i<s.lengthI; i++) {
            for(int j=0; j<s.lengthJ; j++) {
                movedView[i][j] = s.view[i][j];
            }
        }
        movedView[s.zeroI][s.zeroJ] = s.view[s.zeroI-1][s.zeroJ];
        movedView[s.zeroI-1][s.zeroJ] = 0;

        return movedView;
    }
}
