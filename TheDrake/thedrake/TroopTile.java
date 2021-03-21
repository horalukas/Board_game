package thedrake;

import javax.swing.*;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;

public class TroopTile implements Tile, JSONSerializable{

    private final Troop troop;
    private final PlayingSide side;
    private final TroopFace face;

    public TroopTile(Troop troop, PlayingSide side, TroopFace face) {
        this.troop=troop;
        this.side=side;
        this.face=face;
    }

    public PlayingSide side(){
        return this.side;
    }

    public TroopFace face(){
        return this.face;
    }

    public Troop troop(){
        return this.troop;
    }

    @Override
    public boolean canStepOn() {
        return false;
    }

    @Override
    public boolean hasTroop() {
        return true;
    }

    public TroopTile flipped(){
       if(this.face==TroopFace.AVERS){
           TroopTile newTroopTile = new TroopTile(this.troop, this.side, TroopFace.REVERS);
           return newTroopTile;
       }
        TroopTile newTroopTile = new TroopTile(this.troop, this.side, TroopFace.AVERS);
       return newTroopTile;
    }

    public List<Move> movesFrom(BoardPos pos, GameState state) {
        List<Move> result = new ArrayList<>();
        List<TroopAction> aList = troop.actions(face);
        for(TroopAction actions : aList){
            result.addAll(actions.movesFrom(pos,side,state));
        }
        return result;
    }

    @Override
    public void toJSON(PrintWriter writer) {
        writer.print("{\"troop\":");
        troop.toJSON(writer);
        writer.print(",\"side\":");
        side.toJSON(writer);
        writer.print(",\"face\":");
        face.toJSON(writer);
        writer.print("}");
    }
}
