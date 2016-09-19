package com.ejercicio02.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mario
 */
public class MensajeBerkeley extends Mensaje {

    private int milisegundos;

    public MensajeBerkeley(int codigoOperacion) {
        super(codigoOperacion);
    }

    public MensajeBerkeley(int ms, int codigoOperacion) {
        super(codigoOperacion);
        this.milisegundos = ms;
    }

    public int getMilisegundos() {
        return milisegundos;
    }

    public void setMilisegundos(int milisegundos) {
        this.milisegundos = milisegundos;
    }

    /**
     * Intenta convertir un arreglo de bytes en un MensajeBerkeley.
     *
     * @param buf
     * @return MensajeBerkeley
     */
    public static MensajeBerkeley fromByteArray(byte[] buf) {
        try {
            ObjectInputStream ois = null;
            ByteArrayInputStream bis = new ByteArrayInputStream(buf);
            ois = new ObjectInputStream(bis);
            MensajeBerkeley aux = (MensajeBerkeley) ois.readObject();
            ois.close();
            return aux;
        } catch (IOException ex) {
            Logger.getLogger(Mensaje.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Mensaje.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
