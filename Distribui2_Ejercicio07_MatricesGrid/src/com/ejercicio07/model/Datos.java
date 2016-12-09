package com.ejercicio07.model;

import java.io.Serializable;

/**
 *
 * @author mario
 */
public class Datos implements Serializable {

    private Matriz a;
    private Matriz b;

    public Datos(Matriz a, Matriz b) {
        this.a = a;
        this.b = b;
    }

    public Matriz getA() {
        return a;
    }

    public void setA(Matriz a) {
        this.a = a;
    }

    public Matriz getB() {
        return b;
    }

    public void setB(Matriz b) {
        this.b = b;
    }

    @Override
    public String toString() {
        return "Datos{" + "A =\n" + a + "B =\n" + b + '}';
    }

}
