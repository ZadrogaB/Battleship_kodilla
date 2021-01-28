import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ComputerAI {
    private Utils utils = new Utils();
    private  boolean wasHitLastTime=false;
    private boolean isHit;
    private  int numberOfLoops = 0;
    private UnitPosition computerShot;
    private Ship shipUnderFire=null;
    private Set<UnitPosition> hitPositions = new HashSet<>();

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

    public UnitPosition AIHard(char[][] playerBoard, List<Ship> playerShips){
        Set<UnitPosition> positions;

        if(shipUnderFire==null){
            do {
                computerShot = AIEasy(playerBoard);
            } while (hitPositions.contains(computerShot));
            hitPositions.add(computerShot);
            for(Ship ship : playerShips){                   //sprawdzanie czy w coś trafił
                positions = ship.getPositions();
                if(positions.contains(computerShot)){
                    shipUnderFire=ship;

//                    System.out.println("Hit positions1: " + hitPositions);
                }
            }
        } else if (shipUnderFire.getLife()!=0){
            positions = shipUnderFire.getPositions();
            for (UnitPosition unitPosition : positions) {
                    computerShot = unitPosition;
                if(!hitPositions.contains(computerShot)) {
                    break;
                }
            }
            hitPositions.add(computerShot);
//            System.out.println("Hit positions2: " + hitPositions);
        }

        if(shipUnderFire!=null && shipUnderFire.getLife()==1){
            shipUnderFire=null;
        }

        if(shipUnderFire!=null) {
            System.out.println("Ship under fire life = " + shipUnderFire.getLife() + "    Ship under fire number of squares = " + shipUnderFire.getNumberOfSquares() + "\n" + computerShot);
        } else {
            System.out.println(shipUnderFire);
        }
        return  computerShot;
    }

    public void clearHitPositions(){
        hitPositions.clear();
        shipUnderFire=null;
    }
}
