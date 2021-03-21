package thedrake.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import thedrake.*;

public class TheDrakeApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        GameState gameState = createSampleGameState();

        Label blueCapturedLbl = new Label("Blue Captured: ");
        Label orangeCapturedLbl = new Label("Orange Captured: ");
        Label blueStackLbl = new Label("Blue Stack: ");
        Label orangeStackLbl = new Label("Orange Stack: ");

        Button bluePlace = new Button("Place");
        Button orangePlace = new Button("Place");

        ListView<String> OrangeCaptured = new ListView<String>();
        ListView<String> BlueCaptured = new ListView<String>();
        ListView<String> OrangeStack = new ListView<String>();
        ListView<String> BlueStack = new ListView<String>();

        VBox blueCapturedBox = new VBox();
        blueCapturedBox.getChildren().addAll(blueCapturedLbl,BlueCaptured);
        blueCapturedBox.setMaxWidth(150);

        VBox orangeCapturedBox = new VBox();
        orangeCapturedBox.getChildren().addAll(orangeCapturedLbl,OrangeCaptured);
        orangeCapturedBox.setMaxWidth(150);

        VBox blueStackBox = new VBox();
        blueStackBox.setSpacing(5);
        blueStackBox.getChildren().add(bluePlace);
        blueStackBox.getChildren().addAll(blueStackLbl,BlueStack);
        blueStackBox.setMaxWidth(150);

        VBox orangeStackBox = new VBox();
        orangeStackBox.setSpacing(5);
        orangeStackBox.getChildren().addAll(orangeStackLbl,OrangeStack);
        orangeStackBox.getChildren().add(orangePlace);
        orangeStackBox.setMaxWidth(150);

        BoardView boardView = new BoardView(gameState, BlueStack, OrangeStack, BlueCaptured, OrangeCaptured);

        bluePlace.setOnAction(e-> onBlue(boardView));
        orangePlace.setOnAction(e-> onOrange(boardView));

        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(5);
        pane.setPadding(new Insets(15));
        pane.add( blueStackBox,2,1);
        pane.add( orangeStackBox,2,2);
        pane.add( blueCapturedBox,0,1);
        pane.add( orangeCapturedBox,0,2);
        pane.add( boardView,1,1, 1, 3);

        primaryStage.setScene(new Scene(pane, 850, 600));
        primaryStage.setTitle("The Drake");
        primaryStage.show();
    }

    public void onBlue(BoardView boardView){
        if(boardView.gameState.sideOnTurn()==PlayingSide.BLUE) {
            GameState gameState = boardView.gameState;
            ValidMoves validMoves = new ValidMoves(gameState);
            boardView.clearMoves();
            boardView.showMoves(validMoves.movesFromStack());
        }
    }

    public void onOrange(BoardView boardView){
        if(boardView.gameState.sideOnTurn()==PlayingSide.ORANGE) {
            GameState gameState = boardView.gameState;
            ValidMoves validMoves = new ValidMoves(gameState);
            boardView.clearMoves();
            boardView.showMoves(validMoves.movesFromStack());
        }
    }

    private static GameState createSampleGameState(){
        Board board = new Board(4);
        PositionFactory positionFactory = board.positionFactory();
        board = board.withTiles(new Board.TileAt(positionFactory.pos(1,1), BoardTile.MOUNTAIN));
        return new StandardDrakeSetup().startState(board);
    }
}
