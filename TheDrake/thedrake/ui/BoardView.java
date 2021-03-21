package thedrake.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import thedrake.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BoardView extends GridPane implements TileViewContext{

    public GameState gameState;

    private TileView selected;

    private ValidMoves validMoves;

    private ListView<String> blueCaptured;
    private ListView<String> orangeCaptured;
    private ListView<String> blueStack;
    private ListView<String> orangeStack;

    public BoardView(GameState gameState, ListView<String> blueStack, ListView<String> orangeStack, ListView<String> blueCaptured, ListView<String> orangeCaptured){
        this.gameState = gameState;
        this.validMoves = new ValidMoves(gameState);
        this.orangeCaptured = orangeCaptured;
        this.blueCaptured = blueCaptured;
        this.orangeStack = orangeStack;
        this.blueStack = blueStack;
        PositionFactory positionFactory = gameState.board().positionFactory();
        for(int y = 0; y < 4; y++){
            for(int x=0; x < 4; x++){
                BoardPos boardPos = positionFactory.pos(x,3-y);
                add(new TileView(boardPos, gameState.tileAt(boardPos), this), x, y);
            }
        }
        setHgap(5);
        setVgap(5);
        setPadding(new Insets(15));
        setAlignment(Pos.CENTER);
        newLists();
    }
    @Override
    public void tileViewSelected(TileView tileView) {
        if(selected != null && selected != tileView) {
            selected.unselect();
        }
        selected = tileView;
        clearMoves();
        showMoves(validMoves.boardMoves(tileView.position()));
    }
    public void showMoves(List<Move> moves){
        for(Move move : moves){
            tileViewAt(move.target()).setMove(move);
        }
    }
    private TileView tileViewAt(BoardPos target){
        int index = (3 - target.j())*4 + target.i();
        return (TileView) getChildren().get(index);
    }
    @Override
    public void executeMove(Move move) {
        if(selected!=null)
            selected.unselect();
        selected=null;
        clearMoves();
        gameState = move.execute(gameState);
        validMoves = new ValidMoves(gameState);
        updateTiles();
    }
    private void updateTiles(){
        List<Move> list = new ArrayList<>();
        for (Node node : getChildren()) {   //gets all valid moves into a list
            TileView tileView = (TileView) node;
            tileView.setTile(gameState.tileAt(tileView.position()));
            tileView.update();
            list.addAll(validMoves.allMoves());
        }
        if(list.isEmpty() && gameState.result()!=GameResult.VICTORY){   //if no moves are possible and noone won, its a draw
            gameState=gameState.draw();
            Stage stage = (Stage) getScene().getWindow();
            stage.close();
            End newEnd= new End();
            try {
                newEnd.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(gameState.result()==GameResult.VICTORY){     //sets endscreen css to winning colors
            Stage stage = (Stage) getScene().getWindow();
            stage.close();
            End newEnd= new End();
            newEnd.setEndStyle(gameState.armyNotOnTurn().side());
            try {
                newEnd.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (Node node : getChildren()) {       //updates tiles
            TileView tileView = (TileView) node;
            tileView.setTile(gameState.tileAt(tileView.position()));
            tileView.update();
        }
        newLists();
    }
    public void clearMoves(){
        for(Node node: getChildren()){
            TileView tileView = (TileView) node;
            tileView.clearMove();
        }
    }
    private void newLists() {
        if(gameState.armyOnTurn().side() == PlayingSide.BLUE) {     //sets background color for player on turn, previously used for debugging but left in because its a nice touch
            setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        }
        else {
            setBackground(new Background(new BackgroundFill(Color.ORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
        }
        blueCaptured.getItems().clear();
        orangeCaptured.getItems().clear();
        blueStack.getItems().clear();
        orangeStack.getItems().clear();
        blueCaptured.getItems().addAll(gameState.army(PlayingSide.BLUE).captured().stream().map(Troop::name).collect(Collectors.toList()));
        orangeCaptured.getItems().addAll(gameState.army(PlayingSide.ORANGE).captured().stream().map(Troop::name).collect(Collectors.toList()));
        blueStack.getItems().addAll(gameState.army(PlayingSide.BLUE).stack().stream().map(Troop::name).collect(Collectors.toList()));
        orangeStack.getItems().addAll(gameState.army(PlayingSide.ORANGE).stack().stream().map(Troop::name).collect(Collectors.toList()));
    }
}
