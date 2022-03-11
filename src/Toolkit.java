import java.util.Scanner;

public class Toolkit {
    private static final Scanner stdIn = new Scanner(System.in);

    public static final String GOODBYEMESSAGE = "Thank you for playing";

    public static String getInputForMessage(String message) {
        System.out.println(message);
        return stdIn.nextLine().trim();

//        String str = stdIn.nextLine();
//        str = str.strip();
//        if (str.isEmpty()) {
//            return stdIn.nextLine();
//        } else {
//            return stdIn.nextLine().trim();
//        }
    }

    public static String printArray(String[] array) {
        StringBuilder sb = new StringBuilder();
        for (String s : array) {
            sb.append(s.trim()).append(", ");
        }
        sb.deleteCharAt(sb.length() - 1).deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
