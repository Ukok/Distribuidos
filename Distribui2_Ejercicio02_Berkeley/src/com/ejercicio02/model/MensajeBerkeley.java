package com.ejercicio02.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mario
 */
public class MensajeBerkeley extends Mensaje {

   private int milisegundos;

   /**
    * Constructor con un código de operación de Mensaje.
    *
    * @param codigoOperacion Código de operacion de mensaje.
    */
   public MensajeBerkeley(int codigoOperacion) {
      super(codigoOperacion);
   }

   /**
    * Constructor con un código de operación de Mensaje y un valor int en
    * milisegundos.
    *
    * @param ms Milisegundos.
    * @param codigoOperacion Código de operación.
    */
   public MensajeBerkeley(int ms, int codigoOperacion) {
      super(codigoOperacion);
      this.milisegundos = ms;
   }

   /**
    * Obtiene el valor int en milisegundos.
    *
    * @return atributo milisegundos.
    */
   public int getMilisegundos() {
      return milisegundos;
   }

   /**
    * Establece un valor de atributo milisegundos.
    *
    * @param milisegundos Valor en milisegundos.
    */
   public void setMilisegundos(int milisegundos) {
      this.milisegundos = milisegundos;
   }

   /**
    * Intenta convertir un arreglo de bytes en un MensajeBerkeley.
    *
    * @param buf Arreglo de bytes.
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
