/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejercicio07.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mario
 */
public class Utileria {

    /**
     * Convierte el objeto o en un arreglo de bytes.
     *
     * @param o Objeto
     * @return Arreglo de bytes de Mensaje.
     */
    public static byte[] toByteArrray(Object o) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        try {
            oos = new ObjectOutputStream(buf);
            oos.writeObject(o);
            oos.close();
        } catch (IOException ex) {
            Logger.getLogger(Operacion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return buf.toByteArray();
    }

    /**
     * Intenta convertir un arreglo de bytes en un Objeto.
     *
     * @param buf Arreglo de bytes.
     * @return Objeto obtenido por el arreglo de bytes.
     */
    public static Object fromByteArray(byte[] buf) {
        try {
            ObjectInputStream ois;
            ByteArrayInputStream bis = new ByteArrayInputStream(buf);
            ois = new ObjectInputStream(bis);
            Object aux = ois.readObject();
            ois.close();
            return aux;
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Operacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
