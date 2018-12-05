import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Node {
    private Node parent;
    private Map<Character, Node> children;
    private boolean endsWord;
    char point;

    public Node(char c, boolean endsWord) {
        this.point = c;
        this.endsWord = endsWord;
        this.children = new HashMap<>();
        this.parent = null;
    }

    public void addWord(String s) {
        if (s.length() == 0) {
            return;
        } else if (s.length() == 1) {
            Node child = this.children.getOrDefault(s.charAt(0), new Node(s.charAt(0), true));
            child.setEnd();
            this.children.put(s.charAt(0), child);
        } else {
            Node child = this.children.getOrDefault(s.charAt(0), new Node(s.charAt(0), false));
            child.addWord(s.substring(1));
            this.children.put(s.charAt(0), child);
        }
    }

    public boolean containsPrefix(String s) {
        if (s.length() == 0) {
            return true;
        }
        return this.children.containsKey(s.charAt(0)) &&
                this.children.get(s.charAt(0)).containsPrefix(s.substring(1));
    }

    /**
     * Check if the given word is contained within this Node.
     *
     * @param s
     * @return
     */
    public boolean containsWord(String s) {
        if (s.length() == 0) {
            return this.endsWord;
        }
        char first = s.charAt(0);
        return this.children.containsKey(first) &&
                this.children.get(first).containsWord(s.substring(1));
    }

    public Set<String> allWords(String prefix) {
        Set<String> wordsFromHere = new HashSet<>();
        String wordToHere = prefix + this.point;
        if (this.endsWord) {
            wordsFromHere.add(wordToHere);
        }
        this.children.values().forEach(node -> wordsFromHere.addAll(node.allWords(wordToHere)));
        return wordsFromHere;
    }

    //number of words that contain this node in the Dictionary
    public int numWords() {
        int length = 0;
        if (endsWord) {
            length++;
        }
        for (Node n : children.values()) {
            length += n.numWords();
        }
        return length;
    }

    //length of the word this node ends - essentially numParents
    public int length() {
        return this.hasParent() ? this.getParent().length() + 1 : 1;
    }

    public void setEnd() {
        endsWord = true;
    }

    public char getChar() {
        return point;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node node) {
        parent = node;
    }

    public boolean hasParent() {
        return (parent != null);
    }

    public boolean endsWord() {
        return endsWord;
    }

    //returns true if they contain the same character
    public boolean equals(Object o) {
        if (!(o instanceof Node)) {
            return false;
        }
        Node n = (Node) o;
        return (this.getChar() == n.getChar());
    }

    //prints the characters in all nodes in the branch above and including this node
    public String toString() {
        if (this.hasParent()) {
            return this.parent.toString() + this.getChar();
        } else {
            return Character.toString(this.getChar());
        }
    }
}
