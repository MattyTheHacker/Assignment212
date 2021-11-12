import java.lang.reflect.Array;
import java.util.Arrays;

public class BingoCard {
    /*
      The two arrays are private and their structure is NEVER exposed to another
      class, which is why the getCardNumbers returns a String that needs
      further processing.

      While this is not computationally efficient, it is good programming
      practice to hide data structures (information hiding).
     */
    private int[][] numbers;
    private boolean[][] markedOff;

    private int numberOfRows;
    private int numberOfColumns;

    public BingoCard(int numberOfRows, int numberOfColumns) {
        setNumberOfRows(numberOfRows);
        setNumberOfColumns(numberOfColumns);
        numbers = new int[numberOfRows][numberOfColumns];
        markedOff = new boolean[numberOfRows][numberOfColumns];
        resetMarked();
    }

    public void resetMarked() {
        for (boolean[] row : markedOff) {
            Arrays.fill(row, false);
        }
    }

    public int getNumberOfRows() {
        return this.numberOfRows;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public int getNumberOfColumns() {
        return this.numberOfColumns;
    }

    public void setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }

    public String getCardNumbers() {
    /* TODO
        flatten the numbers array into a single string with each number separated by spaces but no leading or trailing copies of
        that character: that is no spaces before the first number nor after the last number.
     */

        StringBuilder sb = new StringBuilder();
    /* TODO
          all the cards are stored as a grid ([][] numbers) of rows / columns, so for example, numbers 3 4 5 6 will be
          printed as follows:
          3  4
          5  6
     */
    /* TODO
          return the grid as a string
     */
        return null;
    }

    public void setCardNumbers(String[] numbersAsStrings) {
    /* TODO
          the array of strings [] numbersAsStrings is cast to an integer as [] numbersList, for you
          set the grid from this list
     */
        int[] numbersList =
                Arrays.stream(numbersAsStrings).mapToInt(Integer::parseInt).toArray();

    /* TODO
          the goal of this method is to get the numbers entered into the [][] numbers format
     */
    }

    public void markNumber(int number) {
        for (int[] row : numbers){
            for(int x : row){
                if (x == number) {
                    markedOff[row][x] = true; //TODO: FIX THIS SHIT
                }
            }
        }

    /* TODO
          make use of the [][] markedOff to mark off numbers from [][] numbers as they match
          if not matching an appropriate message must be printed, verify against expected output files
     */
    }

    public boolean isWinner() {
        for (boolean[] row : markedOff) {
            for (boolean b : row) {
                if (!b) {
                    return false;
                }
            }
        }
        return true;
    }
}