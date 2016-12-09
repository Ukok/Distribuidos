package com.ejercicio07.model;

import java.io.Serializable;

/**
 *
 * @author mario
 */
public class Matriz implements Serializable {

    private final int M;
    private final int N;
    final double[][] mat;

    public Matriz(int M, int N) {
        this.M = M;
        this.N = N;
        mat = new double[M][N];
    }

    public Matriz(double[][] mat) {
        M = mat.length;
        N = mat[0].length;
        this.mat = new double[M][N];
        for (int i = 0; i < M; i++) {
            System.arraycopy(mat[i], 0, this.mat[i], 0, N);
        }
    }

    public int getM() {
        return M;
    }

    public int getN() {
        return N;
    }

    public double get_at(int i, int j) {
        if (i >= 0 && j >= 0 && i < M && j < N) {
            return mat[i][j];
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public void set_at(int i, int j, double val) {
        if (i >= 0 && j >= 0 && i < M && j < N) {
            mat[i][j] = val;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public double[] row(int i) {
        if (i >= 0 && i < M) {
            return mat[i];
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public double[] col(int j) {
        Matriz aux = this.t();
        return aux.row(j);
    }

    /**
     * Transpuesta
     *
     * @return Transpuesta de la matriz.
     */
    public Matriz t() {
        Matriz A = new Matriz(N, M);
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                A.set_at(j, i, this.get_at(i, j));
            }
        }
        return A;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < M; i++) {
            sb.append("\t");
            for (int j = 0; j < N; j++) {
                sb.append(mat[i][j]).append("\t");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
