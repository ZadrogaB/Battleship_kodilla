import java.util.HashSet;
import java.util.Set;

public class Ship {
    private int life;
    private int numberOfSquares;
    private Set<UnitPosition> positions = new HashSet<>();
    private Set<UnitPosition> neighbours = new HashSet<>();
    private boolean horizontal;
    private Set<UnitPosition> allPositionsSet = new HashSet<>();

    public Ship(int number) {
        this.life = number;
        this.numberOfSquares = number;
    }

    public int getLife() {
        return life;
    }

    public Set<UnitPosition> getPositions() {
        return positions;
    }

    public int getNumberOfSquares() {
        return numberOfSquares;
    }

    public Set<UnitPosition> getNeighbours() {
        return neighbours;
    }

    public void setPositions(int row, int column) {
        UnitPosition unitPosition = new UnitPosition(row, column);
        positions.add(unitPosition);
    }

    public void setNeighbours(int row, int column){
        UnitPosition unitPosition = new UnitPosition(row, column);
        neighbours.add(unitPosition);
    }

    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    public void createAllPositionsSet(){
        for(int i=0; i<20; i++){
            for(int j=0; j<20; j++){
                UnitPosition unitPosition = new UnitPosition(i,j);
                allPositionsSet.add(unitPosition);
            }
        }
    }

    public void deleteAllPositions(){
        createAllPositionsSet();
        positions.removeAll(allPositionsSet);
        neighbours.removeAll(allPositionsSet);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ship ship = (Ship) o;

        if (life != ship.life) return false;
        if (numberOfSquares != ship.numberOfSquares) return false;
        if (horizontal != ship.horizontal) return false;
        if (positions != null ? !positions.equals(ship.positions) : ship.positions != null) return false;
        if (neighbours != null ? !neighbours.equals(ship.neighbours) : ship.neighbours != null) return false;
        return allPositionsSet != null ? allPositionsSet.equals(ship.allPositionsSet) : ship.allPositionsSet == null;
    }

    @Override
    public int hashCode() {
        int result = life;
        result = 31 * result + numberOfSquares;
        result = 31 * result + (positions != null ? positions.hashCode() : 0);
        result = 31 * result + (neighbours != null ? neighbours.hashCode() : 0);
        result = 31 * result + (horizontal ? 1 : 0);
        result = 31 * result + (allPositionsSet != null ? allPositionsSet.hashCode() : 0);
        return result;
    }
}
