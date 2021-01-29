import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ComputerAI {
    private Utils utils = new Utils();
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
        }

        if(shipUnderFire!=null && shipUnderFire.getLife()==1){
            shipUnderFire=null;
        }
        return  computerShot;
    }

    public void clearHitPositions(){
        hitPositions.clear();
        shipUnderFire=null;
    }
}
