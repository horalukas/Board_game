package thedrake.ui;

import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import thedrake.BoardPos;
import thedrake.Move;
import thedrake.Tile;

public class TileView extends Pane {

    private Tile tile;

    private TileBackgrounds backgrounds = new TileBackgrounds();

    private BoardPos boardPos;

    private Move move;

    private final ImageView moveImage;

    private TileViewContext tileViewContext;

    private Border selectBorder = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3)));

    public TileView(BoardPos boardPos, Tile tile, TileViewContext tileViewContext) {
        this.boardPos=boardPos;
        this.tile=tile;
        this.tileViewContext=tileViewContext;
        setPrefSize(100, 100);
        update();
        setOnMouseClicked(e-> onClick());
        moveImage = new ImageView(getClass().getResource("/assets/move.png").toString());
        moveImage.setVisible(false);
        getChildren().add(moveImage);
    }

    public void setTile(Tile tile){
        this.tile=tile;
        update();
    }

    public void update(){
        setBackground(backgrounds.get(tile));
    }

    public void setMove(Move move){
        this.move=move;
        moveImage.setVisible(true);
    }

    public void clearMove(){
        this.move = null;
        moveImage.setVisible(false);
    }

    private void onClick(){
        if(move != null)
            tileViewContext.executeMove(move);
        else if(tile.hasTroop())
            select();
    }

    private void select(){
        setBorder(selectBorder);
        tileViewContext.tileViewSelected(this);
    }

    public void unselect(){
        setBorder(null);
    }
    public BoardPos position(){
        return boardPos;
    }
}
