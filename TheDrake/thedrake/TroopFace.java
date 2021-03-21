package thedrake;

import java.io.PrintWriter;

public enum TroopFace implements JSONSerializable {
    AVERS("AVERS"), REVERS("REVERS");

    private String face;

    TroopFace(String face) { this.face = face; }

    @Override
    public String toString() { return face; }

    @Override
    public void toJSON(PrintWriter writer) {
        writer.print("\"" + face + "\"");
    }
}
