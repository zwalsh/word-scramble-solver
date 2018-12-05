import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Dictionary {
    // for each character, holds the Node at the root of that trie.
    private Map<Character, Node> firstCharacters;

    public Dictionary(File f) throws IOException {
        this(read(f));
        System.out.println("Finished reading.");
    }

    public Dictionary(List<String> words) {
        this.firstCharacters = new HashMap<>();
        words.forEach(this::addToWords);
    }

    //cycles through words in file, calling the method to add them as nodes
    private static List<String> read(File f) throws FileNotFoundException {
        System.out.println("Reading from file " + f.getName() + "...");
        Scanner reader = new Scanner(f);
        List<String> words = new ArrayList<>();
        while (reader.hasNext()) {
            words.add(reader.next());
        }
        return words;
    }

    //turns this String into Nodes, creating the trie
    //each Node holds one character, has children underneath it of type Node
    //by following Nodes all the way down until Node that endsWord, words are formed
    private void addToWords(String s) {
        if (s.length() == 0) {
            return;
        }
        s = s.toLowerCase();
        char first = s.charAt(0);
        Node current = this.firstCharacters.getOrDefault(first, new Node(first, s.length() == 1));
        current.addWord(s.substring(1));
        this.firstCharacters.put(first, current);
    }

    public boolean isPrefix(String s) {
        s = s.toLowerCase();
        if (s.length() == 0) {
            return true;
        }
        char first = s.charAt(0);
        return this.firstCharacters.containsKey(first) &&
                this.firstCharacters.get(first).containsPrefix(s.substring(1));
    }

    public boolean exists(String s) {
        s = s.toLowerCase();
        if (s.length() == 0) {
            return true;
        }
        char first = s.charAt(0);
        return this.firstCharacters.containsKey(first) &&
                this.firstCharacters.get(first).containsWord(s.substring(1));
    }
}
