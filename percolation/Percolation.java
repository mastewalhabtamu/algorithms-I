/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int n;
    private int numberOfOpenCells;
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF fullUf;  // for full checking
    private boolean[] openCells;
    private boolean[] backerCells;  // will backwash

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n for grid can't be negative");
        }
        this.n = n;
        this.numberOfOpenCells = 0;
        this.uf = new WeightedQuickUnionUF(n * n + 2);
        this.fullUf = new WeightedQuickUnionUF(n * n + 2);
        this.openCells = new boolean[n * n + 2];

        int topVirtualIndex = 0;
        int bottomVirtualIndex = n * n + 1;
        this.openCells[topVirtualIndex] = true;   // virtual top cell
        this.openCells[bottomVirtualIndex] = true;   // virtual bottom cell

        for (int i = 1; i <= n; i++) {
            uf.union(topVirtualIndex, i);   // connect top row cells with virtual top cell
            uf.union(bottomVirtualIndex, bottomVirtualIndex - i);

            fullUf.union(topVirtualIndex, i);
        }
    }

    private int zeroIndex(int gridIndex) {
        if ((gridIndex <= 0) || (gridIndex > this.n)) {
            throw new IllegalArgumentException("Grid index out of range");
        }

        return gridIndex - 1;
    }

    private int getUFIndex(int rowIndex, int colIndex) {
        return rowIndex * n + colIndex + 1;
    }

    private int getLastUFIndex() {
        return n * n + 1;
    }

    private void unionOpenNeighbours(int rowIndex, int colIndex) {
        int ufIndex = getUFIndex(rowIndex, colIndex);

        int topNeighbour = ufIndex - n;
        if (topNeighbour > 0 && openCells[topNeighbour]) {
            uf.union(topNeighbour, ufIndex);
            fullUf.union(topNeighbour, ufIndex);
        }

        int bottomNeighbour = ufIndex + n;
        if (bottomNeighbour < getLastUFIndex() && openCells[bottomNeighbour]) {
            uf.union(bottomNeighbour, ufIndex);
        }

        int leftNeighbour = ufIndex - 1;
        if (leftNeighbour % n != 0 && openCells[leftNeighbour]) {
            uf.union(leftNeighbour, ufIndex);
            fullUf.union(leftNeighbour, ufIndex);
        }

        int rightNeighbour = ufIndex + 1;
        if (ufIndex % n != 0 && openCells[rightNeighbour]) {
            uf.union(rightNeighbour, ufIndex);
            fullUf.union(rightNeighbour, ufIndex);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isOpen(row, col)) return;

        int rowIndex = zeroIndex(row);
        int colIndex = zeroIndex(col);

        this.openCells[getUFIndex(rowIndex, colIndex)] = true;
        this.numberOfOpenCells += 1;

        this.unionOpenNeighbours(rowIndex, colIndex);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        int rowIndex = zeroIndex(row);
        int colIndex = zeroIndex(col);

        return openCells[getUFIndex(rowIndex, colIndex)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int rowIndex = zeroIndex(row);
        int colIndex = zeroIndex(col);
        int ufIndex = getUFIndex(rowIndex, colIndex);

        return openCells[ufIndex] && fullUf.connected(0, ufIndex);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenCells;
    }

    // does the system percolate?
    public boolean percolates() {
        return numberOfOpenCells > 0 && uf.connected(0, getLastUFIndex());
    }

    // test client (optional)
    public static void main(String[] args) {
        /* empty body */
    }
}
