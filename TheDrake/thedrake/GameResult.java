package thedrake;

import java.io.PrintWriter;

public enum GameResult implements JSONSerializable {
	VICTORY("VICTORY"), DRAW("DRAW"), IN_PLAY("IN_PLAY");
	private String result;
	GameResult(String result) { this.result = result; }

	@Override
	public String toString() { return result; }

	@Override
	public void toJSON(PrintWriter writer) {
		writer.print("\"" + result + "\"");
	}
}
