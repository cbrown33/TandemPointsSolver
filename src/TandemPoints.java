import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * This class is the representation of the Tandem Points game.
 * It contains a validate method, which checks to make sure
 * each distance is unique.
 */

public class TandemPoints implements ImpartialGame{
    private ArrayList<Integer> board;               // ArrayList to make it easier for removing elements
    private int lastDistance;                       // distance of the last move, each move must decrease
    private int lastNumber;                         // last number to be removed for calculating distance
    private int movesMade;                          // number of moves made so far, checks for necessity of distance
    private ArrayList<MoveType> moveList;           // keeps track of old moves to resolve game tree, auto resized

    public TandemPoints(String game){
        board = new ArrayList<>();
        moveList = new ArrayList<>();

        game = game.substring(1, game.length()-1);
        game = game.replaceAll(" ", "");
        String values[];
        if (!(game.length() == 0)) {
            values = game.split(",");
            for (int i = 0; i < values.length; i++) {
                board.add(Integer.parseInt(values[i]));
            }
        }
    }

    private TandemPoints(ArrayList<Integer> board){
        this.board = new ArrayList<>();
        this.board.addAll(board);
        moveList = new ArrayList<>();
    }

    @Override
    public ArrayList<ImpartialGame> getValidMoves() {
        ArrayList<ImpartialGame> validMoves = new ArrayList<>();
        for(Integer i : board){
            TandemPoints game = new TandemPoints(board);
            if (movesMade == 0){                            // left to move first
                if (game.makeMove(board.indexOf(i), MoveType.left)){
                    validMoves.add(game);
                }
            }
            if (lastMoveType() == MoveType.left){
                if (game.makeMove(board.indexOf(i), MoveType.right)){
                    validMoves.add(game);
                }
            }
            if (lastMoveType() == MoveType.right){
                if (game.makeMove(board.indexOf(i), MoveType.left)){
                    validMoves.add(game);
                }
            }
        }
        return validMoves;
    }

    public boolean makeMove(int index, MoveType type){
        int number = board.get(index);
        if (movesMade != 0){                                    // this means distance can be calculated
            int distance = Math.abs(lastNumber - number);
            if (movesMade != 1 || distance >= lastDistance) {
                return false;
            }
            lastDistance = distance;
            lastNumber = number;
            movesMade++;
            board.remove(new Integer(number));      // insure it is the Object method
            moveList.add(type);
            return true;
        } else {                                    // 0 moves have been made, so distance is not a factor
            lastNumber = number;
            movesMade++;
            board.remove(new Integer(number));
            moveList.add(type);
            return true;
        }
    }

    @Override
    public String toString(){
        String output = "{";
        String comma = "";
        for (int i = 0; i < board.size(); i++){
            String printValue = comma + board.get(i);
            comma = ", ";
            output = output.concat(printValue);
        }
        output = output.concat("}");
        return output;
    }

    public MoveType lastMoveType(){
        if (moveList.size() == 0){
            return null;
        }
        return moveList.get(moveList.size()-1);
    }

    public boolean validate(){
        if (board.size() == 0){
            return false;
        }
        int numDistances = 0;
        for(int i = board.size()-1; i >= 0; i--){
            numDistances += i;
        }
        int distances[] = new int[numDistances];
        int index = 0;                                  // index keeps track of the distances array
        for(int i = 0; i < board.size(); i++){          // i is the index of the current number
            int numGreater = board.size() - i;
            for(int j = 1; j < numGreater; j++){        // j is the array distance between the two numbers
                distances[index] = board.get(i+j) - board.get(i);
                index++;
            }
        }
        Arrays.sort(distances);                         // sort in ascending order, then check for unique distance
        int last = 0;
        for(int i : distances){
            if (i == last){
                System.out.println("There are two or more distances of: " + i + ".");
                return false;
            }
            last = i;
        }
        return true;
    }

}
