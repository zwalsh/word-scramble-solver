import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Cell {
    private char point;
    private ScrambleGrid grid;
    private int x;
    private int y;
    private boolean visited = false;

    public Cell(char c, ScrambleGrid g, int xPos, int yPos) {
        point = c;
        grid = g;
        setX(xPos);
        setY(yPos);
    }

    public List<Cell> getNeighbors() {
        List<Cell> neighbors = new ArrayList<>();
        for (int i = 0; i < grid.length(); i++) {
            for (int j = 0; j < grid.length(); j++) {
                Cell c = grid.getCell(i, j);
                int xDiff = Math.abs(this.getX() - c.getX());
                int yDiff = Math.abs(this.getY() - c.getY());
                if (!this.equals(c) && xDiff <= 1 && yDiff <= 1) {
                    neighbors.add(c);
                }
            }
        }
        return neighbors;
    }


    public String toString() {
        return Character.toString(point);
    }

    public boolean equals(Cell c) {
        return (c.getX() == this.getX() && c.getY() == this.getY() && c.getChar() == this.getChar());
    }

    public char getChar() {
        return point;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void visit() {
        this.visited = true;
    }

    public List<String> solve(Dictionary dic, String accumulatedWord) {
        this.visit();

        List<String> words = new ArrayList<>();
        accumulatedWord += point;

        if (accumulatedWord.length() > 1 && dic.exists(accumulatedWord)) {
            words.add(accumulatedWord);
        }

        if (accumulatedWord.length() <= 1 || dic.isPrefix(accumulatedWord)) {
            for (Cell c : getUnvisited()) {
                words.addAll(c.solve(dic, accumulatedWord));
            }
        }
        this.setVisited(false);
        return words;
    }

    public List<Cell> getUnvisited() {
        return this.getNeighbors().stream().filter((Cell c) -> !c.visited).collect(Collectors.toList());
    }
}
