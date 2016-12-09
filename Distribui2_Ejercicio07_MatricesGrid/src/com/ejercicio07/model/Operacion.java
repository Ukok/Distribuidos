
package com.ejercicio07.model;

import java.io.Serializable;

/**
 *
 * @author mario
 */
public class Operacion implements Serializable{
    private int fila;
    private int columna;
    private double resultado;
    
    public Operacion(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }
    
    public double getResultado(){
        return resultado;
    }
    
    public void setResultado(double resultado) {
        this.resultado = resultado;
    }

    @Override
    public String toString() {
        return "Operacion{" + "fila=" + fila + ", columna=" + columna + ", resultado=" + resultado + '}';
    }
        
    
}
