package com.ejercicio04.server.controller;

import com.ejercicio01.model.Clock;
import com.ejercicio04.model.Constantes;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mario
 */
public class ServidorUTC extends Thread {

   ServerSocket socket;
   ObjectOutputStream oos;

   Clock reloj;

   /**
    * Constructor de servidor UTC.
    *
    * @param reloj Clock.
    * @param puerto Puerto para ServerSocket.
    */
   public ServidorUTC(Clock reloj, int puerto) {
      try {
         this.reloj = reloj;
         socket = new ServerSocket(puerto);
         socket.setSoTimeout(Constantes.TIEMPO_ESPERA);
      } catch (IOException ex) {
         Logger.getLogger(ServidorUTC.class.getName()).log(Level.SEVERE, null, ex);
      }
   }

   /**
    * Envia el valor del reloj.
    */
   public synchronized void enviar() {
      try {
         oos.writeInt(reloj.getMilisecondsValue());
         oos.flush();
         System.out.println("[OK]");
      } catch (IOException ex) {
         System.err.println("\nError al enviar: " + ex.getMessage());
      }
   }

   /**
    * Funcionamiento de servidor.
    */
   @Override
   public void run() {
      System.out.println("Servidor iniciado.");
      Socket sc = null;
      while (true) {
         try {
            sc = socket.accept();
            oos = new ObjectOutputStream(sc.getOutputStream());
            System.out.print("Peticion aceptada ... Enviando Datos a:"
                    + sc.getInetAddress() + ":" + sc.getPort() + " ");
            enviar();
         } catch (SocketTimeoutException ex) {
            System.err.println("Tiempo de espera expiró");
            break;
         } catch (IOException ex) {
            Logger.getLogger(ServidorUTC.class.getName()).log(Level.SEVERE, null, ex);
         } finally {
            try {
               if (oos != null) {
                  oos.close();
               }
               if (sc != null) {
                  sc.close();
               }
            } catch (IOException ex) {
               Logger.getLogger(ServidorUTC.class.getName()).log(Level.SEVERE, null, ex);
            }
         }

      }
      System.out.println("Servidor finalizado.");
   }

}
