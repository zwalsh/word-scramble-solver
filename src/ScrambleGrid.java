import java.util.HashSet;
import java.util.Set;


public class ScrambleGrid {
    private Cell[][] grid;
    int length;
    private Dictionary dic;

    public ScrambleGrid(Dictionary d, String str) {
        dic = d;
        str = str.toUpperCase();
        if (str.length() == 16) {
            grid = new Cell[4][4];
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    grid[i][j] = new Cell(str.charAt(4 * i + j), this, i, j);
                }
            }
            length = 4;

        } else {
            System.out.println("String is not of proper length.");
        }
    }

    public ScrambleGrid(int l, Dictionary d, String str) {
        dic = d;
        str = str.toUpperCase();
        if (str.length() == l * l) {
            grid = new Cell[l][l];
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    grid[i][j] = new Cell(str.charAt(l * i + j), this, i, j);
                }
            }
            length = l;
        } else {
            System.out.println("String is not of proper length.");
        }
    }

    public ScrambleGrid(Dictionary d) {
        dic = d;
        grid = new Cell[length][length];

        char[] letters = {'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'B', 'B', 'C',
                'C', 'D', 'D', 'D', 'D', 'D', 'E', 'E', 'E', 'E', 'E', 'E',
                'E', 'E', 'E', 'E', 'E', 'E', 'E', 'F', 'F', 'G', 'G', 'G',
                'H', 'H', 'H', 'H', 'I', 'I', 'I', 'I', 'I', 'I', 'I', 'I',
                'J', 'K', 'L', 'L', 'L', 'L', 'M', 'M', 'N', 'N', 'N', 'N',
                'N', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'P', 'P', 'Q',
                'R', 'R', 'R', 'R', 'R', 'R', 'S', 'S', 'S', 'S', 'S', 'T',
                'T', 'T', 'T', 'T', 'T', 'T', 'U', 'U', 'U', 'U', 'V', 'V',
                'W', 'W', 'X', 'Y', 'Y', 'Z'};

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = new Cell(letters[(int) (Math.random() * letters.length)], this, i, j);
            }
        }
    }

    //solves the grid using the dictionary, Cell solve mtd
    public Set<String> solve() {
        Set<String> words = new HashSet<>();

        for (Cell[] cells : grid) {
            for (Cell c : cells) {
                words.addAll(c.solve(dic, ""));
                unvisit();
            }
        }

        return words;
    }

    public void unvisit() {
        for (Cell[] row : grid) {
            for (Cell cell : row) {
                cell.setVisited(false);
            }
        }
    }

    public Cell getCell(int i, int j) {
        return grid[i][j];
    }

    public int length() {
        return length;
    }

    public String toString() {
        String str = "";

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                str += grid[i][j] + " ";
            }
            str += "\n";
        }

        return str;
    }
}
