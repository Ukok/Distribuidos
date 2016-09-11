package com.ejercicio03.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mario
 */
public class Mensaje implements Serializable {

    private int value;
    private int tiempo;

    public Mensaje() {
        value = 0;
        tiempo = 0;
    }

    public Mensaje(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public byte[] toByteArray() {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        try {
            oos = new ObjectOutputStream(buf);
            oos.writeObject(this);
            oos.close();
        } catch (IOException ex) {
            Logger.getLogger(Mensaje.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return buf.toByteArray();
    }

    public static Mensaje fromByteArray(byte[] bytes) {

        try {
            // Se realiza la conversi�n usando un ByteArrayInputStream y un
            // ObjectInputStream
            ByteArrayInputStream byteArray = new ByteArrayInputStream(bytes);
            ObjectInputStream is = new ObjectInputStream(byteArray);
            Mensaje aux = (Mensaje) is.readObject();
            is.close();
            return aux;
        } catch (IOException ex) {
            Logger.getLogger(Mensaje.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Mensaje.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;

    }
}
