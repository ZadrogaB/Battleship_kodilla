public class Board {

    public void fillBoardWithWater(char[][] board){
        for (int row=0; row<board.length; row++){
            for (int column=0; column<board.length; column++){
                board[row][column]='W';
            }
        }
    }
}
