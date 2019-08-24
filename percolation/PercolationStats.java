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
    private final double confidenceLo;
    private final double confidenceHi;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n or trials are negative");
        }

        int numberOfCells = n * n;
        double[] thresholds = new double[trials];
        for (int t = 0; t < trials; t++) {
            Percolation percolation = new Percolation(n);

            int[] perm = StdRandom.permutation(numberOfCells);
            for (int i = 0; i < numberOfCells; i++) {
                int ufIndex = perm[i] + 1;
                int randomCellRow = (int) Math.ceil(ufIndex / (double) n);
                int randomCellCol = ufIndex - ((randomCellRow - 1) * n);

                percolation.open(randomCellRow, randomCellCol);
                if (percolation.percolates()) {
                    thresholds[t] = percolation.numberOfOpenSites() / (double) numberOfCells;
                    break;
                }
            }
        }

        mean = StdStats.mean(thresholds);
        stddev = StdStats.stddev(thresholds);
        confidenceLo = mean - (1.96 * stddev) / Math.sqrt(trials);
        confidenceHi = mean + (1.96 * stddev) / Math.sqrt(trials);
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
        return confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return confidenceHi;
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
