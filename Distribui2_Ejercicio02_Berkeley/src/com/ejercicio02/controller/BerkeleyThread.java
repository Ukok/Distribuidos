package com.ejercicio02.controller;

import com.ejercicio02.model.Constantes;
import com.ejercicio02.model.MensajeBerkeley;
import com.ejercicio02.model.Nodo;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * BerkeleyThread se encarga de llevar a cabo el algoritmo de Berkeley
 *
 * @author mario
 */
public class BerkeleyThread extends Thread {

   private NodoController nodo;

   private int sumatoriaDeRelojes;
   private int relojesSumados;
   private double promedio;
   private int tiempo1, tiempo2;

   /**
    * Constructor de BerkeleyThread.
    *
    * @param nodo NodoController necesario para la ejecución berkeley.
    */
   public BerkeleyThread(NodoController nodo) {
      this.nodo = nodo;
      this.nodo.setId(this.getId());
      relojesSumados = 1;
   }

   /**
    *
    */
   @Override
   public void run() {
      while (true) {
         if (nodo.isMaster()) {
            master();
         } else {
            slave();
         }
      }
   }

   /**
    * Comportamiento cuando el nodo es Maestro.
    */
   private void master() {
      try {
         this.sleep(Constantes.TIEMPO_ESPERA_SINCRONIZACION);
         System.out.println("\n");
         // Enviar SolicitudDeRelojes a esclavos MC.
         nodo.enviarGrupo(solicitudDesfase());

         // Recibe relojes de cada nodo en la lista nodos.(Sumando los valores del mensaje)
         nodo.setSoTimeout(Constantes.TIEMPO_ESPERA_SOCKET);
         recibirDesfases();

         // Obtiene el promedio de los valores recibidos
         promedio = (double) sumatoriaDeRelojes / (double) relojesSumados;
         System.out.println(
                 "Sumatoria : " + sumatoriaDeRelojes
                 + " nRelojes:" + relojesSumados
                 + " Promedio:" + promedio);

         // Reliza y envia el ajuste para cada nodo. ( Lista ajustes )
         enviarAjustes();
         this.ajustar_clock((int) promedio);
         sumatoriaDeRelojes = 0;
         relojesSumados = 1;
         promedio = 0;

      } catch (InterruptedException ex) {
         Logger.getLogger(BerkeleyThread.class.getName()).log(Level.SEVERE, null, ex);
      }
   }

   /**
    * Comportamiento cuando el nodo es Esclavo.
    */
   private void slave() {
      recibirMensajes();
   }

   /**
    * Método de Maestro para realizar la solicitud de desfase.
    *
    * @return DatagramPacket con el mensaje.
    */
   private DatagramPacket solicitudDesfase() {
      MensajeBerkeley mensaje = new MensajeBerkeley(Constantes.SOLICITUD_DESFASE);
      mensaje.setMilisegundos(nodo.getMSClockValue());
      tiempo1 = mensaje.getMilisegundos();
      DatagramPacket p = new DatagramPacket(mensaje.toByteArrray(), mensaje.toByteArrray().length);

      return p;
   }

   /**
    * Método de Maestro para recibir desfases de esclavos.
    */
   private void recibirDesfases() {
      int aux = nodo.getMSClockValue();
      int tiempoLimite = aux + Constantes.TIEMPO_ESPERA;

      while (aux <= tiempoLimite) {

         try {
            //System.out.println("[" + this.getName() + "]Esperando DESFASE ");
            DatagramPacket p = nodo.recibir();
            MensajeBerkeley mb = MensajeBerkeley.fromByteArray(p.getData());

            if (mb.getCodigoOperacion() == Constantes.DESFASE) {
               System.out.println("[" + this.getName() + "] DESFASE recibido de: " + p.getAddress() + ":" + p.getPort());
               if (estaEnRango(mb.getMilisegundos())) {
                  //System.out.println("En RANGO OK");
                  sumatoriaDeRelojes += mb.getMilisegundos();
                  relojesSumados++;
               }
               nodo.agregarNodo(p, mb.getMilisegundos());
            } else {
               System.out.println("[" + this.getName() + "] recibirDesfases: Otra Mierda " + mb.getCodigoOperacion());
            }

            aux = nodo.getMSClockValue();
         } catch (SocketTimeoutException ex) {
            //System.err.println("Timeout");
            aux = nodo.getMSClockValue();
         } catch (IOException ex) {
            System.err.println("[" + this.getName() + "] IOException ");
            break;
         }

      }

   }

   /**
    * Evalua si un valor en milisegundos de reloj esta en un rango especificado
    * por RANGOMAX y RANGOMIN.
    *
    * @param milisegundos valor en milisegundos.
    * @return true si está en rango, false sino lo está.
    */
   public boolean estaEnRango(int milisegundos) {
      return (milisegundos < (Constantes.RANGO_MAX)
              && milisegundos > (Constantes.RANGO_MIN));
   }

   /**
    * Método de Esclavo para recibir los mensajes enviados por el maestro.
    */
   private void recibirMensajes() {
      System.out.println("[" + this.getName() + "] Esperando Mensajes");
      boolean desfaseEnviado = false;
      while (!nodo.isMaster()) {
         DatagramPacket p;
         try {
            if (!desfaseEnviado) {
               p = nodo.recibirGrupo();
            } else {
               p = nodo.recibir();
            }
         } catch (SocketTimeoutException ex) {
            System.err.println("[" + this.getName() + "] RecibirMensajes: " + ex.getLocalizedMessage());
            continue;
         } catch (IOException ex) {
            System.err.println("[" + this.getName() + "] IOException ");
            break;
         }

         if (p != null) {
            MensajeBerkeley mb = MensajeBerkeley.fromByteArray(p.getData());
            switch (mb.getCodigoOperacion()) {
               case Constantes.SOLICITUD_DESFASE: // Esclavo recibe de Master.
                  System.out.println("[" + this.getName() + "] Recibido SOLICITUD_DESFASE de" + p.getAddress() + ":" + p.getPort());
                  // Realiza la direfencia de tiempo y responde con Cod. DESFASE.
                  // desfase = Valor actual de reloj - Valor de reloj del Nodo Maestro.
                  int desfase = nodo.getMSClockValue() - mb.getMilisegundos();
                  mb.setMilisegundos(desfase);
                  mb.setCodigoOperacion(Constantes.DESFASE);
                  DatagramPacket dp = new DatagramPacket(mb.toByteArrray(), mb.toByteArrray().length);
                  dp.setAddress(p.getAddress());
                  dp.setPort(p.getPort());
                  System.out.println("[" + this.getName() + "] Enviando DESFASE a: " + dp.getAddress() + ":" + dp.getPort());
                  nodo.enviar(dp);
                  desfaseEnviado = true;
                  break;
               case Constantes.AJUSTE: // Esclavo recibe de Master.
                  System.out.println("[" + this.getName() + "] Recibido AJUSTE  de" + p.getAddress() + ":" + p.getPort());
                  ajustar_clock(mb.getMilisegundos());
                  desfaseEnviado = false;
                  break;
            }
         }
      }

   }

   /**
    * Ajusta el reloj con el valor de ms.
    *
    * @param ms Milisegundos de ajuste.
    */
   public void ajustar_clock(int ms) {
      nodo.setMSClockValue(nodo.getMSClockValue() + ms);
   }

   /**
    * Envia ajuste para cada nodo en la lista de nodos conocidos.
    */
   private void enviarAjustes() {
      ArrayList<Nodo> nodos = nodo.getNodos();
      for (Iterator<Nodo> iterator = nodos.iterator(); iterator.hasNext();) {
         Nodo next = iterator.next();
         MensajeBerkeley mb
                 = new MensajeBerkeley((int) ((next.getTimestamp() * -1) + promedio), Constantes.AJUSTE);
         DatagramPacket p = new DatagramPacket(mb.toByteArrray(), mb.toByteArrray().length);
         p.setAddress(next.getAddress());
         p.setPort(next.getPort());
         System.out.println("[" + this.getName() + "] Enviar_Ajuste: " + p.getAddress() + ":" + p.getPort());
         nodo.enviar(p);
      }
   }
}
