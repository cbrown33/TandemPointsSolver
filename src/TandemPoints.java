import java.util.Arrays;

/**
 * This class is the representation of the Tandem Points game.
 */

public class TandemPoints {
    private int board[] = new int[0];

    public TandemPoints(String game){
        game = game.substring(1, game.length()-1);
        game = game.replaceAll(" ", "");
        String values[];
        if (!(game.length() == 0)) {
            values = game.split(",");
            board = new int[values.length];
            for (int i = 0; i < board.length; i++) {
                board[i] = Integer.parseInt(values[i]);
            }
        }
        System.out.print("The input board is: {");
        String comma = "";
        for (int i = 0; i < board.length; i++){
            String printValue = comma + board[i];
            comma = ", ";
            System.out.print(printValue);
        }
        System.out.println("}");
    }

    public boolean validate(){
        if (board.length == 0){
            return false;
        }
        int numDistances = 0;
        for(int i = board.length-1; i >= 0; i--){
            numDistances += i;
        }
        int distances[] = new int[numDistances];
        int index = 0;                                  // index keeps track of the distances array
        for(int i = 0; i < board.length; i++){          // i is the index of the current number
            int numGreater = board.length - i;
            for(int j = 1; j < numGreater; j++){        // j is the distance between the two numbers
                distances[index] = board[i+j] - board[i];
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
