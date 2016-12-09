package com.ejercicio07;

import com.ejercicio07.master.Master;
import com.ejercicio07.model.Matriz;
import com.ejercicio07.slave.Slave;

/**
 *
 * @author mario
 */
public class Principal {

    //Matriz A
    static double[][] a = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    //Matriz B
    static double[][] b = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

    /*
    //Matriz A
    staticdouble[][] a = {{1, 2, 3, 4, 5, 6}, {1, 2, 3, 4, 5, 6}};
    //Matriz B
    static double[][] b = {{1, 2, 3}, {3, 4, 5}, {5, 6, 7}, {7, 8, 9}, {9, 10, 11}, {10, 11, 12}};
     */
    public static void main(String[] args) {
        int master;
        if (args.length == 0) { // Esclavo
            Slave s1 = new Slave(1191);
            s1.start();
        } else {
            Master m;
            m = new Master(new Matriz(a), new Matriz(b));
            m.start();
        }
    }

}
