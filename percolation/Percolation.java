/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    public int n;
    public int numberOfOpenCells;
    public WeightedQuickUnionUF uf;
    public boolean[] openCells;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        this.n = n;
        this.numberOfOpenCells = 0;
        this.uf = new WeightedQuickUnionUF(n * n + 2);
        this.openCells = new boolean[n * n + 2];

        int topVirtualIndex = 0;
        int bottomVirtualIndex = n * n + 1;
        this.openCells[topVirtualIndex] = true;   // virtual top cell
        this.openCells[bottomVirtualIndex] = true;   // virtual bottom cell

        for (int i = 1; i <= n; i++) {
            uf.union(topVirtualIndex, i);   // connect top row cells with virtual top cell
            uf.union(bottomVirtualIndex, bottomVirtualIndex - i);
        }
    }

    private void checkIndexes(int row, int col) {
        if ((row < 0) || (row > this.n) || (col < 0) || (col > this.n)) {
            throw new IllegalArgumentException("row or col number out of range");
        }
    }

    private void unionOpenNeighbours(int row, int col) {
        int cellIndex = row * n + col + 1;

        int topNeighbour = cellIndex - this.n;
        if (topNeighbour > 0 && this.openCells[topNeighbour]) {
            uf.union(topNeighbour, cellIndex);
        }

        int bottomNeighbour = cellIndex + this.n;
        if (bottomNeighbour < n * n + 1 && this.openCells[bottomNeighbour]) {
            uf.union(bottomNeighbour, cellIndex);
        }

        int leftNeighbour = cellIndex - 1;
        if (leftNeighbour % n != 0 && openCells[leftNeighbour]) {
            uf.union(leftNeighbour, cellIndex);
        }

        int rightNeighbour = cellIndex + 1;
        if (cellIndex % n != 0 && openCells[rightNeighbour]) {
            uf.union(rightNeighbour, cellIndex);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkIndexes(row, col);

        this.openCells[row * n + col + 1] = true;
        this.numberOfOpenCells += 1;

        this.unionOpenNeighbours(row, col);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkIndexes(row, col);

        return this.openCells[row * n + col + 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        checkIndexes(row, col);

        return uf.connected(0, row * n + col + 1);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenCells;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(0, n * n + 1);
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
