import java.util.ArrayList;

public class ImpartialGameTree {
    private ArrayList<ImpartialGameTree> left;
    private ArrayList<ImpartialGameTree> right;
    private ImpartialGame game;
    private OutcomeClass treeClass;

    public ImpartialGameTree(ImpartialGame game){
        this.game = game;
        left = new ArrayList<>();
        right = new ArrayList<>();
        buildTree(this.game);
        resolveTree(this);
    }

    private void buildTree(ImpartialGame game){
        ArrayList<ImpartialGame> gameMoves = game.getValidMoves();
        if (gameMoves.size() == 0){                                         // base case
            return;
        }
        for( ImpartialGame move : gameMoves){                               // else
            ImpartialGameTree moveTree = new ImpartialGameTree(move);
            if (move.lastMoveType().equals(MoveType.left)){
                left.add(moveTree);
            } else if (move.lastMoveType().equals(MoveType.right)){
                right.add(moveTree);
            }
        }
    }

    private OutcomeClass resolveTree(ImpartialGameTree tree){
        if (left.size() > 0 || right.size() > 0) {
            ArrayList<OutcomeClass> leftOutcomes = new ArrayList<>();
            ArrayList<OutcomeClass> rightOutcomes = new ArrayList<>();
            for (ImpartialGameTree lTree : left) {
                leftOutcomes.add(lTree.resolveTree(lTree));
            }
            for (ImpartialGameTree rTree : right) {
                rightOutcomes.add(rTree.resolveTree(rTree));
            }
            boolean leftLP = false;         // L union P for left games
            boolean rightRP = false;        // R union P for right games

            // initialize boolean values for truth table
            if (leftOutcomes.contains(OutcomeClass.L) || leftOutcomes.contains(OutcomeClass.P)){
                leftLP = true;
            }
            if (rightOutcomes.contains(OutcomeClass.R) || rightOutcomes.contains(OutcomeClass.P)){
                rightRP = true;
            }

            // based on boolean values resolve tree to one outcome class
            if (leftLP && rightRP){
                treeClass = OutcomeClass.N;
            }
            if (leftLP && !rightRP){
                treeClass = OutcomeClass.L;
            }
            if (!leftLP && rightRP){
                treeClass = OutcomeClass.R;
            }
            if (!leftLP && !rightRP){
                treeClass = OutcomeClass.P;
            }
            return treeClass;
        }
        treeClass = OutcomeClass.P;
        return treeClass;                                                  // previous wins if no move available
    }

    public String toString(){
        String out = "";
        if (game == null){
            return out;
        }
        out = out + "This: " + game.toString();
        for (ImpartialGameTree gameTree : left) {
            out = out + "Left: " + gameTree.toString() + "\n";
        }
        for (ImpartialGameTree gameTree : right){
            out = out + "Right: " + gameTree.toString() + "\n";
        }
        return out;
    }

    public OutcomeClass getTreeClass() {
        return treeClass;
    }
}
