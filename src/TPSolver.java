/**
 * This is the main class for the Tandem Points Solver.  This class takes in one string argument
 * in the form of a Tandem Points game and then passes that string to the Tandem Points class
 * to construct and validate the game.
 */
public class TPSolver {
    public static void main(String[] args) {
        if(args.length != 1 || !args[0].startsWith("{") || !args[0].endsWith("}")){
            System.out.println("Usage: java TPSolver \"{x1, x2, ...}\"");
            return;
        }
        TandemPoints game = new TandemPoints(args[0]);
        System.out.println("The input board is: " + game.toString());
        if (game.validate()){
            System.out.println("Validated.");
        } else {
            System.out.println("Game is invalid.");
        }
        ImpartialGameTree tree = new ImpartialGameTree(game);
        System.out.println(tree.getTreeClass());
    }
}
