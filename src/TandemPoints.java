import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class is the representation of the Tandem Points game.
 * It contains a validate method, which checks to make sure
 * each distance is unique.
 */

public class TandemPoints {
    private ArrayList<Integer> board;               // ArrayList to make it easier for removing elements
    private int lastDistance;                       // distance of the last move, each move must decrease
    private int lastNumber;                         // last number to be removed for calculating distance
    private int movesMade;                          // number of moves made so far, checks for necessity of distance

    public TandemPoints(String game){
        board = new ArrayList<>();

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

    public boolean makeMove(int index){
        int number = board.get(index);
        if (movesMade != 0){                                    // this means distance can be calculated
            int distance = Math.abs(lastNumber - number);
            if (movesMade != 1 || distance >= lastDistance) {
                return false;
            }
            lastDistance = distance;
            lastNumber = number;
            movesMade++;
            board.remove(number);
            return true;
        } else {                                    // 0 moves have been made, so distance is not a factor
            lastNumber = number;
            movesMade++;
            board.remove(number);
            return true;
        }
    }

    public void printBoard(){
        System.out.print("The input board is: {");
        String comma = "";
        for (int i = 0; i < board.size(); i++){
            String printValue = comma + board.get(i);
            comma = ", ";
            System.out.print(printValue);
        }
        System.out.println("}");
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
