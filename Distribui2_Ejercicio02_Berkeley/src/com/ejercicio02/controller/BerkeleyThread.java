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
   private int tiempo;

   public BerkeleyThread(NodoController nodo) {
      this.nodo = nodo;
      this.nodo.setId(this.getId());
   }

   public NodoController getNodo() {
      return nodo;
   }

   public void setNodo(NodoController nodo) {
      this.nodo = nodo;
   }

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
         // Enviar SolicitudDeRelojes a esclavos MC.
         nodo.enviarGrupo(solicitudDesfase());
         // Recibe relojes de cada nodo en la lista nodos.(Sumando los valores del mensaje)
         nodo.setSoTimeout(Constantes.TIEMPO_ESPERA_SOCKET);
         recibirDesfases();
         nodo.setSoTimeout(0);
         // Obtiene el promedio de los valores recibidos
         if (relojesSumados != 0) {
            promedio = sumatoriaDeRelojes / relojesSumados;
         } else {
            promedio = 0;
         }
         // Reliza y envia el ajuste para cada nodo. ( Lista ajustes )
         enviarAjustes();
         this.ajustar_clock((int) promedio);
         sumatoriaDeRelojes = 0;
         relojesSumados = 0;
         promedio = 0;

         System.out.println("\n");
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

   private DatagramPacket solicitudDesfase() {
      MensajeBerkeley mensaje = new MensajeBerkeley(Constantes.SOLICITUD_DESFASE);
      mensaje.setMilisegundos(nodo.getMSClockValue());
      tiempo = mensaje.getMilisegundos();
      DatagramPacket p = new DatagramPacket(mensaje.toByteArrray(), mensaje.toByteArrray().length);

      return p;
   }

   private void recibirDesfases() { // Detalle en esta funcion. 
      int aux = nodo.getMSClockValue();
      int tiempoLimite = aux + Constantes.TIEMPO_ESPERA;

      while (aux <= tiempoLimite) {

         try {
            System.out.print("[" + this.getName() + "]Esperando DESFASE ");
            DatagramPacket p = nodo.recibir();
            System.out.println("...OK");
            MensajeBerkeley mb = MensajeBerkeley.fromByteArray(p.getData());
            
            if (mb.getCodigoOperacion() == Constantes.DESFASE) {
               System.out.println("[" + this.getName() + "] DESFASE recibido de: " + p.getAddress() + ":" + p.getPort());
               if (estaEnRango(mb.getMilisegundos())) {
                  System.out.println("En RANGO OK");
                  sumatoriaDeRelojes += mb.getMilisegundos();
                  relojesSumados++;
               }
               nodo.agregarNodo(p, mb.getMilisegundos());
            } else {
               System.out.println("[" + this.getName() + "] recibirDesfases: Otra Mierda " + mb.getCodigoOperacion());
            }

            aux = nodo.getMSClockValue();
         } catch (SocketTimeoutException ex) {
            System.err.println("Timeout");
            aux = nodo.getMSClockValue();
            continue;
         } catch (IOException ex) {
            System.err.println("[" + this.getName() + "] IOException ");
            break;
         }

      }

   }

   public boolean estaEnRango(int milisegundos) {
      if (milisegundos > (tiempo + Constantes.RANGO_MAX)
              || milisegundos < (tiempo + Constantes.RANGO_MIN)) {
         return false;
      }
      return true;
   }

   private void recibirMensajes() {
      System.out.println("[" + this.getName() + "] Esperando Mensajes");
      boolean desfaseEnviado = false;
      while (!nodo.isMaster()) {
         DatagramPacket p = null;
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
               // En teoría en este case nunca debe entrar
               case Constantes.DESFASE: //Master recibe de esclavo.
                  System.err.println("[" + this.getName() + "] recibirMensajes: DESFASE de" + p.getAddress() + ":" + p.getPort());
                  if (estaEnRango(mb.getMilisegundos())) {
                     sumatoriaDeRelojes += mb.getMilisegundos();
                     relojesSumados++;
                  }
                  nodo.agregarNodo(p, mb.getMilisegundos());
                  break;
               case Constantes.SOLICITUD_DESFASE: // Esclavo recibe de Master.
                  System.out.println("[" + this.getName() + "] recibirMensajes: SOLICITUD_DESFASE de" + p.getAddress() + ":" + p.getPort());
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
                  System.out.println("[" + this.getName() + "] recibirMensajes: AJUSTE  de" + p.getAddress() + ":" + p.getPort());
                  ajustar_clock(mb.getMilisegundos());
                  desfaseEnviado = false;
                  break;
            }
         }
      }

   }

   public void ajustar_clock(int ms) {
      //Ajusta el reloj.
      nodo.setMSClockValue(nodo.getMSClockValue() + ms);
   }

   int getRelojesSumados() {
      return this.relojesSumados;
   }

   public int getSumatoriaDeRelojes() {
      return sumatoriaDeRelojes;
   }

   public void setSumatoriaDeRelojes(int sumatoriaDeRelojes) {
      this.sumatoriaDeRelojes = sumatoriaDeRelojes;
   }

   void setRelojesSumados(int relojesSumados) {
      this.relojesSumados = relojesSumados;
   }

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
/*
class RecibeMensajes implements Runnable {

   BerkeleyThread bt;

   public RecibeMensajes(BerkeleyThread bt) {
      this.bt = bt;
   }

   @Override
   public void run() {
      while (true) {
         DatagramPacket p = bt.getNodo().recibir();
         MensajeBerkeley mb = MensajeBerkeley.fromByteArray(p.getData());
         switch (mb.getCodigoOperacion()) {
            case Constantes.DESFASE: //Master recibe de esclavo.
               if (bt.estaEnRango(mb.getMilisegundos())) {
                  bt.setSumatoriaDeRelojes(bt.getSumatoriaDeRelojes() + mb.getMilisegundos());
                  bt.setRelojesSumados(bt.getRelojesSumados() + 1);
               }
               bt.getNodo().agregarNodo(p, mb.getMilisegundos());
               break;
            case Constantes.SOLICITUD_DESFASE: // Esclavo recibe de Master.
               // Realiza la direfencia de tiempo y responde con Cod. DESFASE.
               // desfase = Valor actual de reloj - Valor de reloj del Nodo Maestro.
               int desfase = bt.getNodo().getMSClockValue() - mb.getMilisegundos();
               mb.setMilisegundos(desfase);
               mb.setCodigoOperacion(Constantes.DESFASE);
               DatagramPacket dp = new DatagramPacket(mb.toByteArrray(), mb.toByteArrray().length);
               dp.setAddress(p.getAddress());
               dp.setPort(p.getPort());
               bt.getNodo().enviar(dp);
               break;
            case Constantes.AJUSTE: // Esclavo recibe de Master.
               bt.ajustar_clock(mb.getMilisegundos());
               break;
         }
      }

   }

}
 */
