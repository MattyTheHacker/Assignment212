import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Integer.parseInt;

public class BingoController {

    private final String[] mainMenuItems = {"Exit",
            "Play bingo",
            "Set number separator",
            "Create a bingo card",
            "List existing cards",
            "Set bingo card size"};

    private final String OPTION_EXIT = "0";
    private final String OPTION_PLAY = "1";
    private final String OPTION_SEPARATOR = "2";
    private final String OPTION_CREATE_CARD = "3";
    private final String OPTION_LIST_CARDS = "4";
    private final String OPTION_SIZE = "5";

    private int currentRowSize = Defaults.DEFAULT_NUMBER_OF_ROWS;
    private int currentColumnSize = Defaults.DEFAULT_NUMBER_OF_COLUMNS;

    ArrayList<BingoCard> cards = new ArrayList<>();

    public int getCurrentRowSize() {
        return this.currentRowSize;
    }

    public void setCurrentRowSize(int currentRowSize) {
        this.currentRowSize = currentRowSize;
    }

    public int getCurrentColumnSize() {
        return this.currentColumnSize;
    }

    public void setCurrentColumnSize(int currentColumnSize) {
        this.currentColumnSize = currentColumnSize;
    }

    public void addNewCard(BingoCard card) {
        cards.add(card);
    }

    public void setSize() {
        setCurrentRowSize(parseInt(Toolkit.getInputForMessage(
                "Enter the number of rows for the card")));
        setCurrentColumnSize(parseInt(Toolkit.getInputForMessage(
                "Enter the number of columns for the card")));
        System.out.printf("The bingo card size is set to %d rows X %d columns%n",
                getCurrentRowSize(),
                getCurrentColumnSize());
    }

    public void createCard() {
        int numbersRequired = getCurrentRowSize() * getCurrentColumnSize();
        String[] numbers;
        boolean correctAmountOfNumbersEntered;

        do {
            numbers = Toolkit.getInputForMessage(
                            String.format(
                                    "Enter %d numbers for your card (separated by " +
                                            "'%s')",
                                    numbersRequired,
                                    Defaults.getNumberSeparator()))
                    .trim()
                    .split(Defaults.getNumberSeparator());
            try {
                int[] ints = Arrays.stream(numbers).mapToInt(Integer::parseInt).toArray();
                correctAmountOfNumbersEntered = ints.length == numbersRequired;
                for (int i : ints) {
                    if (i > 99 || i < 0) {
                        correctAmountOfNumbersEntered = false;
                        break;
                    }
                }
            } catch (Exception e) {
                correctAmountOfNumbersEntered = false;
            }
            if (!correctAmountOfNumbersEntered) {
                System.out.printf("Try again: you entered %d numbers instead of %d" + System.lineSeparator(), numbers.length, numbersRequired);
            }
        } while (!correctAmountOfNumbersEntered);

        System.out.print("You entered" + System.lineSeparator() + Toolkit.printArray(numbers) + System.lineSeparator());
        BingoCard b = new BingoCard(currentRowSize, currentColumnSize);
        b.setCardNumbers(numbers);
        addNewCard(b);
    }

    public void listCards() {
        int count = 0;
        for (BingoCard b : cards) {
            System.out.printf("Card %2d numbers:" + System.lineSeparator(), count);
            printCardAsGrid(b.getCardNumbers());
            count++;
        }
    }

    public void printCardAsGrid(String numbers) {
        String[] numberArray = numbers.split(Defaults.getNumberSeparator());
        List<Integer> numberListAsInts = new ArrayList<>();
        for (String s : numberArray) {
            numberListAsInts.add(Integer.parseInt(s));
        }

        int count = 0;
        for (Integer numberListAsInt : numberListAsInts) {
            if (count != currentColumnSize - 1) { // are we at the end of the row? No? do this...
                System.out.printf("%2d" + Defaults.getNumberSeparator(), numberListAsInt); //print the number with the comma
                count++; // increment the count to indicate moving to the next column
            } else { // are we at the end of the row? Yes? do this...
                System.out.printf("%2d", numberListAsInt); // print the number but without a comma
                System.out.print(System.lineSeparator()); // add a new line
                count = 0; // reset count to 0 for new row
            }
        }
    }

    public void setSeparator() {
        String sep = Toolkit.getInputForMessage("Enter the new separator");
        Defaults.setNumberSeparator(sep);
        System.out.println("Separator is '" + Defaults.getNumberSeparator() + "'");
    }

    public void resetAllCards() {
        for (BingoCard bc : cards) {
            bc.resetMarked();
        }
    }

    public void markNumbers(int number) {
        int count = 0;
        for (BingoCard bc : cards) {
            System.out.printf("Checking card %d for %d" + System.lineSeparator(), count, number);
            bc.markNumber(number);
            count++;
        }
    }

    public int getWinnerId() {
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).isWinner()) {
                return i;
            }
        }
        return -1;
    }

    public void play() {
        System.out.println("Eyes down, look in!");
        resetAllCards();

        boolean weHaveAWinner;
        do {
            markNumbers(parseInt(
                    Toolkit.getInputForMessage("Enter the next number")
                            .trim()));

            int winnerID = getWinnerId();
            weHaveAWinner = winnerID != Defaults.NO_WINNER;
            if (weHaveAWinner)
                System.out.printf("And the winner is card %d%n", winnerID);
        } while (!weHaveAWinner);
    }

    public String getMenu(String[] menuItems) {
        StringBuilder menuText = new StringBuilder();
        final String[] mainMenuNumbers = {OPTION_EXIT,
                OPTION_PLAY,
                OPTION_SEPARATOR,
                OPTION_CREATE_CARD,
                OPTION_LIST_CARDS,
                OPTION_SIZE};

        for (int i = 0; i < menuItems.length; i++) {
            menuText.append(" ");
            menuText.append(mainMenuNumbers[i]);
            menuText.append(": ");
            menuText.append(menuItems[i]);
            menuText.append(System.lineSeparator());
        }
        return menuText.toString();
    }

    public void run() {
        boolean finished = false;
        do {
            switch (Toolkit.getInputForMessage(getMenu(mainMenuItems))) {
                case "0":
                    finished = true;
                    break;
                case "1":
                    play();
                    break;
                case "2":
                    setSeparator();
                    break;
                case "3":
                    createCard();
                    break;
                case "4":
                    listCards();
                    break;
                case "5":
                    setSize();
                    break;
                default:
                    break;
            }
        } while (!finished);
    }
}
