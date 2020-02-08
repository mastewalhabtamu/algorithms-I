import java.util.ArrayList;
import java.util.List;

public class Board {
    private int[][] tiles;
    private int dimension;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null) {
            throw new IllegalArgumentException();
        }

        this.dimension = tiles.length;

        this.tiles = new int[this.dimension][this.dimension];

        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(String.format("%s\n", this.dimension)); // first line

        for (int i = 0; i < this.dimension; i++) {
            stringBuilder.append(String.format("%s\t%s\t%s\n", tiles[i]));   // board line
        }

        return stringBuilder.toString();
    }

    // board dimension n
    public int dimension() {
        return this.dimension;
    }

    // number of tiles out of place
    public int hamming() {
        int goalNumber = 1;
        int hammingNumber = 0;
        int lastIndex = this.dimension * this.dimension;

        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                if (this.tiles[i][j] != goalNumber) {
                    hammingNumber++;
                }

                if (goalNumber == lastIndex - 1) {  // last index should contain 0
                    goalNumber = 0;
                }
                else {
                    goalNumber++;
                }
            }
        }

        return hammingNumber;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int current, correctRow, correctColumn;
        int manhattanNumber = 0;
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                current = this.tiles[i][j];

                // get correct position row index
                correctRow = (int) Math.ceil(current / (double) this.dimension) - 1;

                // get correct position column index
                correctColumn = (current % this.dimension) - 1;
                if (correctColumn == -1) correctColumn = this.dimension - 1;

                manhattanNumber += Math.abs(correctRow - i) + Math.abs(correctColumn - j);
            }
        }

        return manhattanNumber;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return this.hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y.getClass() != this.getClass()) {
            return false;
        }

        return this.toString().equals(y.toString());
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int blankRow = -1;
        int blankColumn = -1;

        // search for blank i.e 0
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                if (this.tiles[i][j] == 0) {
                    blankRow = i;
                    blankColumn = j;

                    i = this.dimension; // break outer loop
                    break;
                }
            }
        }

        List<Board> nears = new ArrayList<>();

        // left exchange
        int leftIndex = blankColumn - 1;
        if (leftIndex >= 0) {
            int[][] copyTiles = this.clonedTiles();

            copyTiles[blankRow][blankColumn] = copyTiles[blankRow][leftIndex];
            copyTiles[blankRow][leftIndex] = 0;
            Board leftNeighbour = new Board(copyTiles);
            nears.add(leftNeighbour);
        }

        // right exchange
        int rightIndex = blankColumn + 1;
        if (rightIndex < this.dimension) {
            int[][] copyTiles = this.clonedTiles();

            copyTiles[blankRow][blankColumn] = copyTiles[blankRow][rightIndex];
            copyTiles[blankRow][rightIndex] = 0;
            Board rightNeighbour = new Board(copyTiles);
            nears.add(rightNeighbour);
        }

        // top exchange
        int topIndex = blankRow - 1;
        if (topIndex >= 0) {
            int[][] copyTiles = this.clonedTiles();

            copyTiles[blankRow][blankColumn] = copyTiles[topIndex][blankColumn];
            copyTiles[topIndex][blankColumn] = 0;
            Board topNeighbour = new Board(copyTiles);
            nears.add(topNeighbour);
        }

        // bottom exchange
        int bottomIndex = blankRow - 1;
        if (bottomIndex < this.dimension) {
            int[][] copyTiles = this.clonedTiles();

            copyTiles[blankRow][blankColumn] = copyTiles[bottomIndex][blankColumn];
            copyTiles[bottomIndex][blankColumn] = 0;
            Board bottomNeighbour = new Board(copyTiles);
            nears.add(bottomNeighbour);
        }

        return nears;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] copyTiles = this.clonedTiles();

        int temp = copyTiles[0][0];
        copyTiles[0][0] = copyTiles[0][1];
        copyTiles[0][1] = temp;

        return new Board(copyTiles);
    }

    private int[][] clonedTiles() {
        int[][] newTiles = new int[this.dimension][this.dimension];

        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                newTiles[i][j] = this.tiles[i][j];
            }
        }

        return newTiles;
    }

    // unit testing (not graded)
    public static void main(String[] args) {

    }
}
