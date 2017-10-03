import java.util.ArrayList;

public class ImpartialGameTree {
    private ArrayList<ImpartialGameTree> left;
    private ArrayList<ImpartialGameTree> right;
    private ImpartialGame game;

    public ImpartialGameTree(ImpartialGame game){
        this.game = game;
        BuildTree(this.game);
    }

    private void BuildTree(ImpartialGame game){
        ArrayList<ImpartialGame> gameMoves = game.getValidMoves();
        if (gameMoves.size() == 0){                                         // base case
            return;
        }
        for( ImpartialGame move : gameMoves){                               // else
            ImpartialGameTree moveTree = new ImpartialGameTree(move);
            if (move.lastMoveType().equals(moveType.left)){
                left.add(moveTree);
            } else {
                right.add(moveTree);
            }
        }
    }

}
