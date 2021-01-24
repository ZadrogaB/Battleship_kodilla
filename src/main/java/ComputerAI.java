import java.util.List;
import java.util.Set;

public class ComputerAI {
    private Utils utils = new Utils();
    private  boolean wasHitLastTime=false;
    private boolean isHit;
    private  int numberOfLoops = 0;
    private UnitPosition computerShot;


    public UnitPosition AIEasy(char[][] playerBoard){
        int row, column;
        do {
            row = utils.getRandomNumberInRange(0, 9);
            column = utils.getRandomNumberInRange(0, 9);
            computerShot = new UnitPosition(row, column);
        }while(playerBoard[row][column]=='X');
        playerBoard[row][column]='X';
        return computerShot;
    }

    public UnitPosition AIHard(char[][] playerBoard, List<Ship> playerShips, List<Ship> computerShips, Set<Ship> deadShipsPlayer, Set<Ship> deadShipsComputer){
        int row, column;

        if (wasHitLastTime){
            switch (numberOfLoops){


            }
        } else {
            switch (numberOfLoops){
                case 0:

            }
        }



//        if((numberOfLoops==0)||(numberOfLoops!=0 && !wasHitLastTime)){
//            row = utils.getRandomNumberInRange(0,9);
//            column = utils.getRandomNumberInRange(0,9);
//            computerShot = new UnitPosition(row,column);
//        } else if(numberOfLoops!=0 && wasHitLastTime){
//
//        }
//        numberOfLoops++;

        return computerShot;
    }
}
