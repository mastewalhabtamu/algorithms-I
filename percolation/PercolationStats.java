/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */


import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double mean;
    private final double stddev;
    private final int trials;

    private final double confidenceConstant = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n or trials are negative");
        }

        this.trials = trials;
        int numberOfCells = n * n;
        double[] thresholds = new double[trials];
        for (int t = 0; t < trials; t++) {
            Percolation percolation = new Percolation(n);

            int[] perm = StdRandom.permutation(numberOfCells);
            for (int i = 0; i < numberOfCells; i++) {
                int ufIndex = perm[i] + 1;
                int randomCellRow = (ufIndex / (n + 1)) + 1;
                int randomCellCol = ((ufIndex - 1) % n) + 1;

                percolation.open(randomCellRow, randomCellCol);
                if (percolation.percolates()) {
                    thresholds[t] = percolation.numberOfOpenSites() / (double) numberOfCells;
                    break;
                }
            }
        }

        mean = StdStats.mean(thresholds);
        stddev = StdStats.stddev(thresholds);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean - (confidenceConstant * stddev) / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean + (confidenceConstant * stddev) / Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt((args[1]));

        PercolationStats stats = new PercolationStats(n, trials);

        StdOut.printf("mean = %f\n", stats.mean());
        StdOut.printf("stddev = %f\n", stats.stddev());
        StdOut.printf("95percent confidence interval = [%f, %f]\n",
                      stats.confidenceLo(), stats.confidenceHi());
    }
}
