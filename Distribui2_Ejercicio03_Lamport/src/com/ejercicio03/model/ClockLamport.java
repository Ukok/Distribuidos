package com.ejercicio03.model;

/**
 *
 * @author mario
 */
public class ClockLamport {

    private int tiempo;
    private int incremento;

    public ClockLamport() {
        tiempo = 0;
        incremento = 1;
    }

    public ClockLamport(int tiempo, int incremento) {
        this.tiempo = tiempo;
        this.incremento = incremento;

    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public int getIncremento() {
        return incremento;
    }

    public void setIncremento(int incremento) {
        this.incremento = incremento;
    }

    public void reset() {
        tiempo = 0;
    }

    @Override
    public String toString() {
        return "ClockLamport{" + "tiempo=" + tiempo + ", incremento=" + incremento + '}';
    }

    public void clickClack() {
        tiempo += incremento;
    }

}
