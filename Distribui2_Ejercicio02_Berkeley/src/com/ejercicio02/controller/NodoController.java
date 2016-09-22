package com.ejercicio02.controller;

import com.ejercicio01.model.Clock;
import com.ejercicio02.model.Constantes;
import com.ejercicio02.model.Nodo;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mario
 */
public class NodoController implements Constantes {

   private MulticastSocket mcs;
   private DatagramSocket ds;

   private InetAddress gpo;
   private boolean masterFlag;
   private Clock reloj;

   private ArrayList<Nodo> nodos;
   private Nodo master;// ??
   private long id;    // Id del hilo BerkeleyThread

   public NodoController(Clock reloj, int puerto) {

      try {
         mcs = new MulticastSocket(PUERTO_MULTICAST);
      } catch (IOException ex) {
         Logger.getLogger(NodoController.class.getName()).log(Level.SEVERE, null, ex);
      }
      try {
         ds = new DatagramSocket(puerto);
      } catch (SocketException ex) {
         Logger.getLogger(NodoController.class.getName()).log(Level.SEVERE, null, ex);
      }
      try {
         gpo = InetAddress.getByName(DIRECCION_DE_GRUPO);
      } catch (UnknownHostException u) {
         System.err.println("Direccion no valida");
      }//catch
      try {
         mcs.joinGroup(gpo);
         System.out.println("Unido a grupo:" + gpo);
      } catch (IOException ex) {
         Logger.getLogger(NodoController.class.getName()).log(Level.SEVERE, null, ex);
      }

      masterFlag = ESCLAVO;
      this.reloj = reloj;
      nodos = new ArrayList<>();

   }

   public Clock estimarDesfase(Clock reloj) {
      int hh, mm, ss, ms = 0;

      ms = this.reloj.get_ms() - reloj.get_ms();
      ss = this.reloj.get_ss() - reloj.get_ss();
      mm = this.reloj.get_mm() - reloj.get_mm();
      hh = this.reloj.get_hh() - reloj.get_hh();

      if (ms < 0) {
         ss -= 1;
         ms += 1000;
      }
      if (ss < 0) {
         mm -= 1;
         ss += 60;
      }
      if (mm < 0) {
         hh -= 1;
         mm += 60;
      }
      if (hh < 0) {
         hh += 24;
      }
      return new Clock(hh, mm, ss, ms);
   }

   void setId(long id) {
      this.id = id;
   }

   public synchronized DatagramPacket recibirGrupo() throws SocketTimeoutException {
      try {
         DatagramPacket p = new DatagramPacket(new byte[200], 200);
         mcs.receive(p);
         return p;
      } catch (IOException ex) {
         Logger.getLogger(NodoController.class.getName()).log(Level.SEVERE, null, ex);
         return null;
      }

   }

   public synchronized void enviarGrupo(DatagramPacket p) {
      try {
         p.setAddress(gpo);
         p.setPort(PUERTO_MULTICAST);
         ds.send(p);
      } catch (IOException ex) {
         Logger.getLogger(NodoController.class.getName()).log(Level.SEVERE, null, ex);
      }
   }

   public synchronized DatagramPacket recibir() throws SocketTimeoutException, IOException {
      //try {
      DatagramPacket p = new DatagramPacket(new byte[200], 200);
      ds.receive(p);
      return p;
      /*} catch (IOException ex) {
         System.err.println("IOException en recibir");
         return null;
      }*/
   }

   public synchronized void enviar(DatagramPacket p) {
      try {
         ds.send(p);
      } catch (IOException ex) {
         Logger.getLogger(NodoController.class.getName()).log(Level.SEVERE, null, ex);
      }
   }

   public boolean isMaster() {
      return masterFlag;
   }

   public void setMaster() {
      this.masterFlag = MAESTRO;
      try {
         mcs.leaveGroup(gpo);
         System.out.println("Saliendo de grupo OK");
      } catch (IOException ex) {
         Logger.getLogger(NodoController.class.getName()).log(Level.SEVERE, null, ex);
      }
   }

   public void setMasterFlag(boolean masterFlag) {
      this.masterFlag = masterFlag;
   }

   public int getMSClockValue() {
      return reloj.getMilisecondsValue();
   }

   public double getSSClockValue() {
      return reloj.getSecondsValue();
   }

   public void setMSClockValue(int ms) {
      reloj.setWithMilisecondsValue(ms);
   }

   public ArrayList<Nodo> getNodos() {
      return nodos;
   }

   public void setNodos(ArrayList<Nodo> nodos) {
      this.nodos = nodos;
   }

   void agregarNodo(DatagramPacket p, int ms) {
      //Buscar en la lista de nodos 
      // si ya existe cambia su timestamp
      // Sino lo agrega a la lista.
      Nodo aux;
      int a = nodos.indexOf(new Nodo(p.getAddress(), p.getPort()));
      if (a != -1) {
         aux = nodos.get(a);
         aux.setTimestamp(ms);
      } else {
         aux = new Nodo(p.getAddress(), p.getPort());
         aux.setTimestamp(ms);
         nodos.add(aux);
      }
      System.out.println(nodos);
   }

   public void setSoTimeout(int ms) {
      try {
         ds.setSoTimeout(ms);
      } catch (SocketException ex) {
         Logger.getLogger(NodoController.class.getName()).log(Level.SEVERE, null, ex);
      }
   }

}
