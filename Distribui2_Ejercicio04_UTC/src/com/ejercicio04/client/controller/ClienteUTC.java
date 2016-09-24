package com.ejercicio04.client.controller;

import com.ejercicio01.model.Clock;
import com.ejercicio04.model.Constantes;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mario
 */
public class ClienteUTC extends Thread {

   Socket socket;
   ObjectInputStream ois;
   int puerto;
   String direccion;
   Clock reloj;
   int time, time1, time2;

   /**
    * Constructor
    *
    * @param reloj Clock.
    * @param direccion String de dirección del Servidor UTC.
    * @param puerto Puerto del Servidor UTC.
    */
   public ClienteUTC(Clock reloj, String direccion, int puerto) {
      this.reloj = reloj;
      this.puerto = puerto;
      this.direccion = direccion;
      time = 0;
      time1 = 0;
      time2 = 0;
   }

   /**
    * Funcionamiento de Cliente
    */
   @Override
   public void run() {
      while (true) {
         try {
            time1 = reloj.getMilisecondsValue();
            socket = new Socket(direccion, puerto);
            ois = new ObjectInputStream(socket.getInputStream());
            time = ois.readInt();
            time2 = reloj.getMilisecondsValue();
            time += (time2 - time1) / 2;
            reloj.setWithMilisecondsValue(time);
            try {
               Thread.sleep(Constantes.TIEMPO_SINCRONIZACION);
            } catch (InterruptedException ex) {
               Logger.getLogger(ClienteUTC.class.getName()).log(Level.SEVERE, null, ex);
            }
         } catch (IOException ex) {
            Logger.getLogger(ClienteUTC.class.getName()).log(Level.SEVERE, null, ex);
         } finally {
            try {
               if (ois != null) {
                  ois.close();
               }
               if (socket != null) {
                  socket.close();
               }
            } catch (IOException e) {
               System.err.println("Error al cerrar Socket.");
            }

         }
      }
   }

}
