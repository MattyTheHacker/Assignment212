import java.util.Arrays;

public class BingoCard {
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
        StringBuilder sb = new StringBuilder();
        for (int[] rows : numbers) {
            for (int i : rows) {
                sb.append(i);
                sb.append(Defaults.getNumberSeparator());
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public void setCardNumbers(String[] numbersAsStrings) {
        int[] numbersList = Arrays.stream(numbersAsStrings).mapToInt(Integer::parseInt).toArray();

        int r = 0;
        int n = 0;
        int count = 0;
        for (int[] row : numbers) {
            for (int ignored : row) {
                numbers[r][n] = numbersList[count];
                count++;
                n++;
            }
            r++;
            n = 0;
        }
    }

    public void markNumber(int number) {
        boolean found = false;
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers[i].length; j++) {
                if (numbers[i][j] == number) {
                    markedOff[i][j] = true;
                    System.out.printf("Marked off %d" + System.lineSeparator(), number);
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.printf("Number %d not on this card" + System.lineSeparator(), number);
        }
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