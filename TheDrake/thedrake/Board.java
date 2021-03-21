package thedrake;

import java.io.PrintWriter;

public class Board implements JSONSerializable {

	private final int dimension;

	private final BoardTile tiles[][];

	// Konstruktor. Vytvoří čtvercovou hrací desku zadaného rozměru, kde všechny dlaždice jsou prázdné, tedy BoardTile.EMPTY
	public Board(int dimension) {
		tiles = new BoardTile[dimension][dimension];
		this.dimension=dimension;
		for (int i = 0; i<dimension; i++){
			for (int j=0; j<dimension; j++){
				this.tiles[i][j]= BoardTile.EMPTY;
			}
		}
	}

	// Rozměr hrací desky
	public int dimension() {
		return this.dimension;
	}

	// Vrací dlaždici na zvolené pozici.
	public BoardTile at(BoardPos pos) {
		return this.tiles[pos.i()][pos.j()];
	}

	// Vytváří novou hrací desku s novými dlaždicemi. Všechny ostatní dlaždice zůstávají stejné
	public Board withTiles(TileAt ...ats) {
		Board newBoard = new Board(this.dimension);
		for (int i = 0; i<dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				newBoard.tiles[i][j] = this.tiles[i][j];
			}
		}
		for (TileAt changes: ats) {
			newBoard.tiles[changes.pos.i()][changes.pos.j()]=changes.tile;
		}
		return newBoard;
	}

	// Vytvoří instanci PositionFactory pro výrobu pozic na tomto hracím plánu
	public PositionFactory positionFactory() {
		PositionFactory positions =new PositionFactory(this.dimension);
		return positions;
	}

	@Override
	public void toJSON(PrintWriter writer) {
		writer.print("{\"dimension\":" + dimension + ",\"tiles\":[");
		boolean first = true;
		for(int i = 0; i < dimension; i++) {
			for(int j = 0; j < dimension; j++) {
				if (!first) writer.print(",");
				first = false;
				tiles[j][i].toJSON(writer);
			}
		}
		writer.print("]}");

	}

	public static class TileAt {
		public final BoardPos pos;
		public final BoardTile tile;
		
		public TileAt(BoardPos pos, BoardTile tile) {
			this.pos = pos;
			this.tile = tile;
		}
	}
}

