package thedrake;

import java.io.PrintWriter;
import java.util.*;

public class BoardTroops implements JSONSerializable {
	private final PlayingSide playingSide;
	private final Map<BoardPos, TroopTile> troopMap;
	private final TilePos leaderPosition;
	private final int guards;

	public BoardTroops(PlayingSide playingSide) { 
		this.playingSide=playingSide;
		this.guards=0;
		this.troopMap=Collections.EMPTY_MAP;
		this.leaderPosition=TilePos.OFF_BOARD;
	}
	
	public BoardTroops(PlayingSide playingSide, Map<BoardPos, TroopTile> troopMap, TilePos leaderPosition, int guards) {
		this.playingSide=playingSide;
		this.troopMap=troopMap;
		this.leaderPosition=leaderPosition;
		this.guards=guards;
	}

	public Optional<TroopTile> at(TilePos pos) {
		if(troopMap.containsKey(pos)){
			TroopTile newTile= troopMap.get(pos);
			return Optional.of(newTile);
		}
		return Optional.empty();
	}
	
	public PlayingSide playingSide() {
		return this.playingSide;
	}
	
	public TilePos leaderPosition() {
		if(isLeaderPlaced()==true){
			return this.leaderPosition;
		}
		return TilePos.OFF_BOARD;
	}

	public int guards() {
		return this.guards;
	}
	
	public boolean isLeaderPlaced() {
		if(leaderPosition!=TilePos.OFF_BOARD){
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean isPlacingGuards() {
		if(isLeaderPlaced()==true && guards<2){
			return true;
		}
		else{
			return false;
		}
	}	
	
	public Set<BoardPos> troopPositions() {
		return troopMap.keySet();
	}

	public BoardTroops placeTroop(Troop troop, BoardPos target) {
		if(at(target).isPresent())
			throw new IllegalArgumentException();

		Map<BoardPos, TroopTile> newTroops = new HashMap<>(troopMap);
		TroopTile newTile= new TroopTile(troop, this.playingSide(), TroopFace.AVERS);
		newTroops.put(target, newTile);
		if(isLeaderPlaced()==false){
			return new BoardTroops(playingSide(), newTroops, target, guards);
		}
		if(isPlacingGuards()==true){
			int i=guards+1;
			return new BoardTroops(playingSide(), newTroops, leaderPosition, i);
		}
		return new BoardTroops(playingSide(), newTroops, leaderPosition, guards);
	}
	
	public BoardTroops troopStep(BoardPos origin, BoardPos target) {
		if(!isLeaderPlaced()) {
			throw new IllegalStateException(
					"Cannot move troops before the leader is placed.");
		}

		if(isPlacingGuards()) {
			throw new IllegalStateException(
					"Cannot move troops before guards are placed.");
		}

		if(!at(origin).isPresent())
			throw new IllegalArgumentException();

		if(at(target).isPresent())
			throw new IllegalArgumentException();

		Map<BoardPos, TroopTile> newTroops = new HashMap<>(troopMap);
		BoardPos oTarget = target;
		BoardPos oOrigin = origin;
		TroopTile tile = newTroops.remove(target);
		TroopTile oriTile = newTroops.get(origin);
		oriTile=oriTile.flipped();
		TroopTile newTile= new TroopTile(oriTile.troop(), oriTile.side(), oriTile.face());
		newTroops.put(target, newTile);
		newTroops.remove(origin);
		if(oOrigin.equals(leaderPosition)){
			return new BoardTroops(playingSide(), newTroops, oTarget, guards);
		}
		return new BoardTroops(playingSide(), newTroops, leaderPosition, guards);
	}
	
	public BoardTroops troopFlip(BoardPos origin) {
		if(!isLeaderPlaced()) {
			throw new IllegalStateException(
					"Cannot move troops before the leader is placed.");			
		}
		
		if(isPlacingGuards()) {
			throw new IllegalStateException(
					"Cannot move troops before guards are placed.");			
		}
		
		if(!at(origin).isPresent()) {
			throw new IllegalArgumentException();
		}
		
		Map<BoardPos, TroopTile> newTroops = new HashMap<>(troopMap);
		TroopTile tile = newTroops.remove(origin);
		newTroops.put(origin, tile.flipped());

		return new BoardTroops(playingSide(), newTroops, leaderPosition, guards);
	}
	
	public BoardTroops removeTroop(BoardPos target) {
		if(!isLeaderPlaced()) {
			throw new IllegalStateException(
					"Cannot move troops before the leader is placed.");
		}

		if(isPlacingGuards()) {
			throw new IllegalStateException(
					"Cannot move troops before guards are placed.");
		}

		if(!at(target).isPresent())
			throw new IllegalArgumentException();

		Map<BoardPos, TroopTile> newTroops = new HashMap<>(troopMap);
		TroopTile tile = newTroops.remove(target);
		if(target.equals(leaderPosition)){
			return new BoardTroops(playingSide(), newTroops, TilePos.OFF_BOARD, guards);
		}
		return new BoardTroops(playingSide(), newTroops, leaderPosition, guards);
	}

	@Override
	public void toJSON(PrintWriter writer) {
		writer.print("{\"side\":\"" + playingSide + "\",\"leaderPosition\":\"" + leaderPosition + "\",\"guards\":" + guards + ",");
		mapJSON(writer);
		writer.print("}");
	}
	private void mapJSON(PrintWriter writer) {
		Map<BoardPos, TroopTile> sortedMap = new TreeMap<>(troopMap);
		writer.print("\"troopMap\":{");
		boolean first = true;
		for (Map.Entry<BoardPos, TroopTile> entry : sortedMap.entrySet()) {
			if (!first) writer.print(",");
			first = false;
			writer.print("\"" + entry.getKey() + "\":");
			entry.getValue().toJSON(writer);
		}
		writer.print("}");

	}
}
