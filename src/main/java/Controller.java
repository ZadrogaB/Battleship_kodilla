import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    @FXML
    private GridPane gridPaneShips;
    @FXML
    private GridPane gridPaneTargets;
    @FXML
    private Button newGameButton;
    @FXML
    private Button horizontalButton;
    @FXML
    private ImageView backgroudPhoto;
    @FXML
    private Button visibleTargetsButton;
    @FXML
    private Label winLabel;
    @FXML
    private Label restartLabel;
    @FXML
    private Label infoLabel;
    @FXML
    private Label difficultLevelLabel;
    @FXML
    private ChoiceBox difficultLevelChoice;

    private GameController gameController = new GameController();
    private List<Ship> playerShips = gameController.playerShips;
    private List<Ship> computerShips = gameController.computerShips;
    private List<Rectangle> shotRectanglesPlayer = new ArrayList<>();
    private List<Rectangle> shotRectanglesComputer = new ArrayList<>();
    private int numberOfLoops = 0;
    private boolean isHorizontal=true;
    private boolean areTargetsVisible=false;
    private boolean isHard;

    public void initialize(){
        difficultLevelChoice.setValue("Easy");
        difficultLevelChoice.getItems().add("Easy");
        difficultLevelChoice.getItems().add("Hard");
    }

    public void onMouseClickedVisibleTargetsButton() {
        Color color;
        if(areTargetsVisible){
            areTargetsVisible=false;
            color=Color.web("#00c5ff");
        } else {
            areTargetsVisible=true;
            color=Color.web("002AFFFF");
        }
        paintSquaresShip(computerShips,color,gridPaneTargets, false);
    }

    public void onMouseClickedHorizontal(){
        if (isHorizontal){
            isHorizontal=false;
            horizontalButton.setText("vertical");
        } else {
            isHorizontal=true;
            horizontalButton.setText("horizontal");
        }
    }

    public void onMouseClickedNewGame(MouseEvent e) {
        gameController.startFunctions();
        System.out.println("StartWork");
        String level = (String) difficultLevelChoice.getValue();
        if (level.equals("Hard")){
            isHard=true;
        } else {
            isHard=false;
        }
        numberOfLoops = 0;
        newGameButton.setVisible(false);
        gridPaneShips.disableProperty().set(false);
        gridPaneTargets.disableProperty().set(false);
        setEverythingVisible(true);
        winLabel.disableProperty().set(true);
        restartLabel.disableProperty().set(true);
        winLabel.setText("");
        restartLabel.setText("");
        showInfo("Wybierz polozenie statku: 1");
    }

    public void onMouseClickedRectangle(MouseEvent e) {

        boolean testInterferencePlayer;
        UnitPosition nodePosition = getNode(e);
        int row = nodePosition.getRow();
        int column = nodePosition.getColumn();
        System.out.println("Click!");

        if (numberOfLoops < playerShips.size()) {
            System.out.println("playerShip.size() = " + playerShips.size() + "\ncomputerShips.size() = " + computerShips.size());
            Ship shipPlayer = playerShips.get(numberOfLoops);
            Ship shipComputer = computerShips.get(numberOfLoops);
            if(shipPlayer.getNumberOfSquares()<5) {
                showInfo("Wybierz polozenie statku: " + (shipPlayer.getNumberOfSquares() + 1));
            } else {
                showInfo("Oddaj strzal!");
            }
            testInterferencePlayer = gameController.placeShipsPlayer(shipPlayer, e, row, column, isHorizontal);
            gameController.placeShipComputer(shipComputer);

            if (testInterferencePlayer) {
                numberOfLoops--;
            } else {
                paintSquaresShip(playerShips, Color.BLUE, gridPaneShips, false);
            }
            if(numberOfLoops==playerShips.size()-1){
                horizontalButton.visibleProperty().set(false);
                gridPaneShips.disableProperty().set(true);
                gridPaneTargets.disableProperty().set(false);
                visibleTargetsButton.visibleProperty().set(true);
            }
            numberOfLoops++;
        }
    }

    public void onMouseClickedTarget(MouseEvent e){
        boolean isHit, isWin;
        UnitPosition nodePosition = getNode(e);
        int row = nodePosition.getRow();
        int column = nodePosition.getColumn();
        int beforeShotSize = computerShips.size();
        int afterShotSize;

        if (numberOfLoops>=playerShips.size()) {
            gameController.RunGame(row, column, isHard);
            isHit = gameController.isHitPlayer();
            afterShotSize=computerShips.size();
            if(isHit) {
                showInfo("Trafiles statek wroga");
                if(beforeShotSize-afterShotSize!=0){
                    showInfo("Zatopiles statek wroga");
                }
            } else {
                showInfo("Sproboj oddac kolejny strzal");
            }

            paintSquaresShoot(row, column, isHit);

            isWin = gameController.isWin();
            if (isWin){
                showInfo("");
                showWinLabel(gameController.getWinnerString());
            }
            isHit = gameController.isHitComputer();
            ObservableList<Node> childrenPlayer = gridPaneShips.getChildren();    //Kolorowanie pól gracza
            for (Node node : childrenPlayer) {
                Integer columnIndex = GridPane.getColumnIndex(node);
                Integer rowIndex = GridPane.getRowIndex(node);

                if (columnIndex == null)
                    columnIndex = 0;
                if (rowIndex == null)
                    rowIndex = 0;

                if (columnIndex == gameController.getComputerShot().getColumn() && rowIndex == gameController.getComputerShot().getRow()) {
                    try {
                        Rectangle rectangle = (Rectangle) node;
                        shotRectanglesComputer.add(rectangle);
                        if(isHit) {
                            rectangle.setFill(Color.RED);
                        } else {
                            rectangle.setFill(Color.BLACK);
                        }
                    } catch (RuntimeException exception) {
                    }
                }
            }
        }
    }

    public void onMouseClickedRestartButton() {

        Color color = Color.web("#00c5ff");
        paintSquaresShip(computerShips, color, gridPaneTargets, true);

        for (Rectangle rectangle : shotRectanglesPlayer) {
            rectangle.setFill(color);
            rectangle.disableProperty().set(false);
        }

        paintSquaresShip(playerShips, color, gridPaneShips, true);

        for (Rectangle rectangle : shotRectanglesComputer) {
            rectangle.setFill(color);
            rectangle.disableProperty().set(false);
        }

        gameController.restartGame();
        playerShips = gameController.playerShips;
        computerShips = gameController.computerShips;

        String level = (String) difficultLevelChoice.getValue();
        if (level.equals("Hard")){
            isHard=true;
        } else {
            isHard=false;
        }
        gameController.startFunctions();
        showInfo("Wybierz polozenie statku: 1");
        newGameButton.setVisible(false);
        gridPaneShips.disableProperty().set(false);
        gridPaneTargets.disableProperty().set(true);
        visibleTargetsButton.setVisible(false);
        winLabel.setText("");
        restartLabel.setText("");
        setEverythingVisible(true);
        numberOfLoops=0;
    }

    // Extra actions

    public void setEverythingVisible(boolean visible){
        backgroudPhoto.visibleProperty().set(visible);
        gridPaneShips.visibleProperty().set(visible);
        gridPaneTargets.visibleProperty().set(visible);
        horizontalButton.visibleProperty().set(visible);
        difficultLevelLabel.visibleProperty().set(!visible);
        difficultLevelChoice.visibleProperty().set(!visible);

        ObservableList<Node> gridPaneShipsChildren = gridPaneShips.getChildren();
        for (Node node : gridPaneShipsChildren) {
            try {
                Rectangle rectangle = (Rectangle) node;
                rectangle.visibleProperty().set(visible);
            } catch (RuntimeException e){
            }
        }

        ObservableList<Node> gridPaneTargetChildren = gridPaneTargets.getChildren();
        for (Node node : gridPaneTargetChildren) {
            try {
                Rectangle rectangle = (Rectangle) node;
                rectangle.visibleProperty().set(visible);
            }catch (RuntimeException e) {
            }
        }
    }

    public void paintSquaresShip(List<Ship> paintList, Color colorOne, GridPane gridPane, boolean isRestartButton) {
        for (int i = 0; i < paintList.size(); i++) {
            Ship ship = paintList.get(i);
            for (UnitPosition unitPosition : ship.getPositions()) {        ////////////////POLA KOMPUTERA KOLOROWE
                int shipRow = unitPosition.getRow();
                int shipColumn = unitPosition.getColumn();

                ObservableList<Node> childrenComputer = gridPane.getChildren();    //Kolorowanie pól komputera
                for (Node node : childrenComputer) {
                    Integer columnIndex = GridPane.getColumnIndex(node);
                    Integer rowIndex = GridPane.getRowIndex(node);

                    if (columnIndex == null)
                        columnIndex = 0;
                    if (rowIndex == null)
                        rowIndex = 0;

                    if (columnIndex == shipColumn && rowIndex == shipRow) {
                        try {
                            Rectangle rectangle = (Rectangle) node;
                            rectangle.disableProperty().set(false);

                            if(!isRestartButton && !rectangle.getFill().equals(Color.RED)) {
                                rectangle.setFill(colorOne);
                            } else if (isRestartButton && !rectangle.getFill().equals(Color.RED)) {
                                rectangle.setFill(colorOne);
                            } else {
                            }
                        } catch (RuntimeException exception) {
                        }
                    }
                }
            } ////////////////POLA KOMPUTERA KOLOROWE
        }
    }

    public void paintSquaresShoot(int row, int column, boolean isHit){
        ObservableList<Node> childrenTarget = gridPaneTargets.getChildren();    //Kolorowanie pól w które strzelał gracz
        for (Node node : childrenTarget) {
            Integer columnIndex = GridPane.getColumnIndex(node);
            Integer rowIndex = GridPane.getRowIndex(node);

            if (columnIndex == null)
                columnIndex = 0;
            if (rowIndex == null)
                rowIndex = 0;

            if (columnIndex == column && rowIndex == row) {
                try {
                    Rectangle rectangle = (Rectangle) node;
                    shotRectanglesPlayer.add(rectangle);
                    if(isHit) {
                        rectangle.setFill(Color.RED);
                        rectangle.disableProperty().set(true);
                    } else {
                        rectangle.setFill(Color.BLACK);
                        rectangle.disableProperty().set(true);
                    }
                } catch (RuntimeException exception) {
                }
            }
        }
    }

    public UnitPosition getNode(MouseEvent e){
        int row, column;
        Node source = (Node) e.getSource();
        try {
            row = GridPane.getRowIndex(source);
        } catch (RuntimeException exception) {
            row = 0;
        }
        try {
            column = GridPane.getColumnIndex(source);
        } catch (RuntimeException exception) {
            column = 0;
        }
        UnitPosition result = new UnitPosition(row, column);
        return result;
    }

    public void showWinLabel(String loser){
        setEverythingVisible(false);
        winLabel.disableProperty().set(false);
        restartLabel.disableProperty().set(false);
        winLabel.setText(loser + " win!");
        restartLabel.setText("Click restart to start new game.");
    }

    public void showInfo(String info){
        infoLabel.setText(info);
    }

}