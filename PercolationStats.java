/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] sv;
    private int tria;
    private double mean;
    private double stdsv;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {

        int temprow, tempcol;
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException("n or trials is error");
        tria = trials;

        sv = new double[trials];

        Percolation per;
        for (int i = 0; i < trials; i++) {
            int opensum = 0;
            per = new Percolation(n);

            while (!per.percolates()) {
                temprow = StdRandom.uniform(1, n + 1);
                tempcol = StdRandom.uniform(1, n + 1);
                if (!per.isOpen(temprow, tempcol)) {
                    per.open(temprow, tempcol);
                    opensum += 1;
                }

            }

            sv[i] = (double) opensum / (n * n);

        }
        mean = StdStats.mean(sv);
        stdsv = StdStats.stddev(sv);

    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stdsv;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean - 1.96 * stdsv / Math.sqrt(tria);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean + 1.96 * stdsv / Math.sqrt(tria);
    }

    // test client (see below)
    public static void main(String[] args) {


        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, t);

        String confidence = ps.confidenceLo() + ", " + ps.confidenceHi();
        System.out.println("mean                  = " + ps.mean());
        System.out.println("stddev                = " + ps.stddev());
        System.out.println("95% confidence interval = " + confidence);
    }

}
