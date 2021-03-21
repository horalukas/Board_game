package thedrake;

import java.io.PrintWriter;

public enum PlayingSide implements JSONSerializable {
    ORANGE("ORANGE"), BLUE("BLUE");

    private String side;

    PlayingSide(String side) { this.side = side; }

    @Override
    public String toString() { return side; }

    @Override
    public void toJSON(PrintWriter writer) {
        writer.print("\"" + side + "\"");
    }
}
