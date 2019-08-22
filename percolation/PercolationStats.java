/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */


import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double mean;
    private double stddev;
    private int trials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n or trials are negative");
        }

        this.trials = trials;
        int numberOfCells = n * n;
        double[] thresholds = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);

            for (int cell = 0; cell < numberOfCells; cell++) {
                int randomCellRow = StdRandom.uniform(n);
                int randomCellCol = StdRandom.uniform(n);

                percolation.open(randomCellRow, randomCellCol);
                if (percolation.percolates()) {
                    thresholds[i] = (cell + 1) / (double) numberOfCells;
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
        return mean - (1.96d * stddev) / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean + (1.96d * stddev) / Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt((args[1]));

        PercolationStats stats = new PercolationStats(n, T);

        StdOut.printf("mean = %f\n", stats.mean());
        StdOut.printf("stddev = %f\n", stats.stddev());
        StdOut.printf("95percent confidence interval = [%f, %f]\n",
                      stats.confidenceLo(), stats.confidenceHi());
    }
}
