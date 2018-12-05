import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Driver {
    // List of English letters, representative to American English
    public static char[] letters = {'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'b', 'b', 'c', 'c',
            'd', 'd', 'd', 'd', 'd', 'e', 'e', 'e', 'e', 'e', 'e', 'e', 'e',
            'e', 'e', 'e', 'e', 'e', 'f', 'f', 'g', 'g', 'g', 'h', 'h', 'h',
            'h', 'i', 'i', 'i', 'i', 'i', 'i', 'i', 'i', 'j', 'k', 'l', 'l',
            'l', 'l', 'm', 'm', 'n', 'n', 'n', 'n', 'n', 'o', 'o', 'o', 'o',
            'o', 'o', 'o', 'o', 'p', 'p', 'q', 'r', 'r', 'r', 'r', 'r', 'r',
            's', 's', 's', 's', 's', 't', 't', 't', 't', 't', 't', 't', 'u',
            'u', 'u', 'u', 'v', 'v', 'w', 'w', 'x', 'y', 'y', 'z'};
    public static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) throws FileNotFoundException, IOException {
        //@SuppressWarnings("unused")
        Dictionary dic = new Dictionary(new File("dictionary.txt"));

        ScrambleGrid grid = generateGrid(250, 10);

        System.out.println(grid);
        printSolution(grid.solve());

    }

    public static String readGrid() {
        System.out.println("Please enter the grid as a String, left to right and top to bottom.");
        String gridStr = scan.next();

        gridStr = gridStr.replaceAll(" ", "");
        int strLength = gridStr.length();

        while (strLength < 3 || (double) ((int) (Math.sqrt(strLength))) != Math.sqrt(strLength) && !gridStr.equals("zzz")) {
            System.out.println("Invalid grid - either too small or not a perfect square. Enter another string or zzz to quit.");
            gridStr = scan.next();
            strLength = gridStr.length();
        }
        if (!gridStr.equalsIgnoreCase("zzz"))
            return gridStr;
        else
            return null;
    }

    public static void printSolution(Set<String> words) {
        List<String> sortedByLength = new ArrayList<>(words);
        sortedByLength.sort(Comparator.comparing(String::length).reversed());
        sortedByLength.forEach(System.out::println);
    }


    public static ScrambleGrid generateGrid(int minWords, int minLength) throws FileNotFoundException, IOException {
        System.out.println("Generating grid...");
        String gridStr = "";
        Dictionary dic = new Dictionary(new File("dictionary.txt"));

        Set<String> words = Collections.emptySet();
        String longestWord = "";
        ScrambleGrid grid = null;
        int attemptCount = 0;
        while (words.size() < minWords || longestWord.length() < minLength) {
            gridStr = "";
            for (int i = 0; i < 16; i++) {
                gridStr += letters[(int) (Math.random() * letters.length)];
            }
            grid = new ScrambleGrid(4, dic, gridStr);

            words = grid.solve();
            longestWord = words.stream().max(Comparator.comparingInt(String::length)).get();
            attemptCount++;
            System.out.println("Attempt " + attemptCount + " has " + words.size() + " words, longest: " + longestWord + " size: " + longestWord.length());
        }

        return grid;
    }

}
