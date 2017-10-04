import java.util.ArrayList;

public interface ImpartialGame {
    String toString();
    ArrayList<ImpartialGame> getValidMoves();
    MoveType lastMoveType();
}
