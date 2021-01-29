import java.util.List;
import java.util.Set;

public class Shooter {
    private boolean hit;
    private UnitPosition computerShot;
    ComputerAI computerAI = new ComputerAI();

    public UnitPosition getComputerShot() {
        return computerShot;
    }

    public boolean isHit() {
        return hit;
    }

    public void shootPlayer(List<Ship> playerShips, List<Ship> computerShips, Set<Ship> deadShipsPlayer, Set<Ship> deadShipsComputer, int row, int column){
        checkIfHit(row, column, false, playerShips, computerShips, deadShipsPlayer, deadShipsComputer);
    }

    public void shootComputer(char[][] playerBoard, List<Ship> playerShips, List<Ship> computerShips, Set<Ship> deadShipsPlayer, Set<Ship> deadShipsComputer, boolean isHard){
        if(!isHard) {
            computerShot = computerAI.AIEasy(playerBoard);
        } else {
            computerShot = computerAI.AIHard(playerBoard, playerShips);
        }
        checkIfHit(computerShot.getRow(), computerShot.getColumn(), true, playerShips, computerShips, deadShipsPlayer, deadShipsComputer);
    }

    public void checkIfHit(int row, int column, boolean isComputer, List<Ship> playerShips, List<Ship> computerShips, Set<Ship> deadShipsPlayer, Set<Ship> deadShipsComputer){
        if(isComputer) {
            checkIfHitCommon(playerShips, isComputer, row, column, deadShipsPlayer, deadShipsComputer);
            playerShips.removeIf(ship -> ship.getLife()==0);
        } else {
            checkIfHitCommon(computerShips, isComputer, row, column, deadShipsPlayer, deadShipsComputer);
            computerShips.removeIf(ship -> ship.getLife()==0);
        }
    }

    private void checkIfHitCommon(List<Ship> shipList, boolean isComputer, int row, int column, Set<Ship> deadShipsPlayer, Set<Ship> deadShipsComputer) {
        hit = false;
        for (Ship ship : shipList) {
            Set<UnitPosition> positions = ship.getPositions();
            for (UnitPosition unitPosition : positions) {
                if (unitPosition.getColumn() == column && unitPosition.getRow() == row) {
                    hit = true;
                    ship.setLife(ship.getLife() - 1);
                    if (ship.getLife() == 0 && isComputer==false) {
                        deadShipsPlayer.add(ship);
                    } else if (ship.getLife() == 0 && isComputer==true){
                        deadShipsComputer.add(ship);
                    }
                }
            }
        }
    }

    public void clearHitInAI(){
        computerAI.clearHitPositions();
    }
}
