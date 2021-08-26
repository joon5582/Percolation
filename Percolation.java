/* *****************************************************************************
 *  Name: Junwoo Lee
 *  Date: 6/3/2020
 *  Description: https://coursera.cs.princeton.edu/algs4/assignments/percolation/specification.php
 *  I have done all the coding by myself
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF uf;
    private byte[][] status;
    private int[][] id;
    private int num;
    private int virtualtop;
    private boolean isPencolate = false;


    private int opennum = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("n is under 0");
        uf = new WeightedQuickUnionUF(n * n + 1);
        status = new byte[n + 1][n + 1];
        id = new int[n + 1][n + 1];
        num = n;

        virtualtop = n * n;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                id[i][j] = (i - 1) * n + j - 1;
            }
        }


    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row <= 0 || col <= 0 || row > num || col > num)
            throw new IllegalArgumentException("wrong row or col value");
        if (!isOpen(row, col)) {

            if (status[row][col] == 0) {
                status[row][col] = 1;
            }
            connectgrid(row, col);
            opennum += 1;
            int p;
            p = uf.find(id[row][col]);
            if (isFull(row, col)) {
                if (status[row][col] == 2 || status[(p / num) + 1][(p % num) + 1] == 2)
                    isPencolate = true;
            }
        }

    }

    private void connectgrid(int row, int col) {
        if (row <= 0 || col <= 0 || row > num || col > num)
            throw new IllegalArgumentException("wrong row or col value");
        else {


            if (row == num) {

                status[row][col] = 2;

            }


            if (col > 1) {
                if (status[row][col - 1] != 0) {
                    int p;
                    p = uf.find(id[row][col - 1]);
                    if (status[row][col - 1] == 2 || status[(p / num) + 1][(p % num) + 1] == 2) {
                        status[row][col] = 2;
                        status[(p / num) + 1][(p % num) + 1] = 2;
                        p = uf.find(id[row][col]);
                        status[(p / num) + 1][(p % num) + 1] = 2;
                    }
                    uf.union(id[row][col], id[row][col - 1]);

                    if (status[row][col] == 2) {

                        p = uf.find(id[row][col]);
                        if (p == num * num)
                            isPencolate = true;
                        else
                            status[(p / num) + 1][(p % num) + 1] = 2;
                    }

                }


            }
            if (row < num) {
                if (status[row + 1][col] != 0) {

                    int p;
                    p = uf.find(id[row + 1][col]);
                    if (status[row + 1][col] == 2 || status[(p / num) + 1][(p % num) + 1] == 2) {
                        status[row][col] = 2;
                        status[(p / num) + 1][(p % num) + 1] = 2;
                        p = uf.find(id[row][col]);
                        status[(p / num) + 1][(p % num) + 1] = 2;
                    }
                    uf.union(id[row][col], id[row + 1][col]);
                    if (status[row][col] == 2) {


                        p = uf.find(id[row][col]);
                        if (p == num * num)
                            isPencolate = true;
                        else
                            status[(p / num) + 1][(p % num) + 1] = 2;
                    }

                }


            }
            if (col < num) {
                if (status[row][col + 1] != 0) {

                    int p;
                    p = uf.find(id[row][col + 1]);
                    if (status[row][col + 1] == 2 || status[(p / num) + 1][(p % num) + 1] == 2) {
                        status[row][col] = 2;
                        status[(p / num) + 1][(p % num) + 1] = 2;
                        p = uf.find(id[row][col]);
                        status[(p / num) + 1][(p % num) + 1] = 2;
                    }
                    uf.union(id[row][col], id[row][col + 1]);
                    if (status[row][col] == 2) {


                        p = uf.find(id[row][col]);
                        if (p == num * num)
                            isPencolate = true;
                        else
                            status[(p / num) + 1][(p % num) + 1] = 2;
                    }

                }


            }
            if (row > 1) {
                if (status[row - 1][col] != 0) {

                    int p;

                    p = uf.find(id[row - 1][col]);


                    if (status[row - 1][col] == 2 || status[(p / num) + 1][(p % num) + 1] == 2) {
                        status[row][col] = 2;
                        status[(p / num) + 1][(p % num) + 1] = 2;
                        p = uf.find(id[row][col]);
                        status[(p / num) + 1][(p % num) + 1] = 2;
                    }
                    uf.union(id[row][col], id[row - 1][col]);
                    if (status[row][col] == 2) {


                        p = uf.find(id[row][col]);
                        if (p == num * num)
                            isPencolate = true;
                        else
                            status[(p / num) + 1][(p % num) + 1] = 2;
                    }
                }


            }
            if (row == 1) {
                uf.union(id[row][col], virtualtop);
            }
            if (status[row][col] == 2) {

                int p;
                p = uf.find(id[row][col]);
                if (p == num * num)
                    isPencolate = true;
                else
                    status[(p / num) + 1][(p % num) + 1] = 2;
            }


        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 || col <= 0 || row > num || col > num)
            throw new IllegalArgumentException("wrong row or col value");
        if (status[row][col] != 0)
            return true;
        return false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || col <= 0 || row > num || col > num)
            throw new IllegalArgumentException("wrong row or col value");
        if (uf.connected(id[row][col], virtualtop))
            return true;

        else return false;


    }


    // returns the number of open sites
    public int numberOfOpenSites() {
        return opennum;
    }

    // does the system percolate?
    public boolean percolates() {

        return isPencolate;


    }


}


