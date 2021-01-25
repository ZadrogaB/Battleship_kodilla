import java.util.List;
import java.util.Set;

public class Shooter {
    private boolean hit;
    private int lastDeadShipComputer;
    private UnitPosition computerShot;
    ComputerAI computerAI = new ComputerAI();

    public UnitPosition getComputerShot() {
        return computerShot;
    }


    public boolean isHit() {
        return hit;
    }

    public void shootPlayer(List<Ship> playerShips, List<Ship> computerShips, Set<Ship> deadShipsPlayer, Set<Ship> deadShipsComputer, int row, int column){
        lastDeadShipComputer=deadShipsComputer.size();
        checkIfHit(row, column, false, playerShips, computerShips, deadShipsPlayer, deadShipsComputer);
    }

    public void shootComputer(char[][] playerBoard, List<Ship> playerShips, List<Ship> computerShips, Set<Ship> deadShipsPlayer, Set<Ship> deadShipsComputer){
        computerShot = computerAI.AIEasy(playerBoard);
        System.out.println("Computer AI wartości row i column: " + computerShot.getRow() +", " + computerShot.getColumn());
        checkIfHit(computerShot.getRow(), computerShot.getColumn(), true, playerShips, computerShips, deadShipsPlayer, deadShipsComputer);
    }

    public void checkIfHit(int row, int column, boolean isComputer, List<Ship> playerShips, List<Ship> computerShips, Set<Ship> deadShipsPlayer, Set<Ship> deadShipsComputer){
        if(isComputer) {
            checkIfHitCommon(playerShips, isComputer, row, column, deadShipsPlayer, deadShipsComputer);
            playerShips.removeIf(ship -> ship.getLife()==0);
            System.out.println("Graczowi pozostało " + playerShips.size() + " statków");
        } else {
            checkIfHitCommon(computerShips, isComputer, row, column, deadShipsPlayer, deadShipsComputer);
            computerShips.removeIf(ship -> ship.getLife()==0);
            System.out.println("Komputerowi pozostało " + computerShips.size() + " statków");
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
                    System.out.println("! ! ! HIT ! ! !");
                   //                    System.out.println("Trafionemu statkowi potało: " + ship.getLife() + " żyć!");
                    if (ship.getLife() == 0 && isComputer==false) {
                        deadShipsPlayer.add(ship);
                    } else if (ship.getLife() == 0 && isComputer==true){
                        deadShipsComputer.add(ship);
                    }
                }
            }
        }
    }
}
