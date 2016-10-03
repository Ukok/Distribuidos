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

   /**
    * Constructor.
    *
    * @param reloj Clock.
    * @param puerto Entero para especificar el puerto del DatagramSocket.
    */
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
      }
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
   /**
    * Constructor.
    *
    * @param reloj Clock.
    */
   public NodoController(Clock reloj) {

      try {
         mcs = new MulticastSocket(PUERTO_MULTICAST);
      } catch (IOException ex) {
         Logger.getLogger(NodoController.class.getName()).log(Level.SEVERE, null, ex);
      }
      try {
         ds = new DatagramSocket();
      } catch (SocketException ex) {
         Logger.getLogger(NodoController.class.getName()).log(Level.SEVERE, null, ex);
      }
      try {
         gpo = InetAddress.getByName(DIRECCION_DE_GRUPO);
      } catch (UnknownHostException u) {
         System.err.println("Direccion no valida");
      }
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

   /**
    * Establece el id de BerkeleyThread
    *
    * @param id de BerkeleyThread.
    */
   void setId(long id) {
      this.id = id;
   }

   /**
    * Recibe DatagramPacket del grupo con MulticastSocket.
    *
    * @return DatagramPacket recibido.
    * @throws SocketTimeoutException Cuando el socket tiene un timeout definido
    * y este espira.
    */
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

   /**
    * Envia un DatagramPacket al grupo Multicast.
    *
    * @param p DatagramPacket por salir.
    */
   public synchronized void enviarGrupo(DatagramPacket p) {
      try {
         p.setAddress(gpo);
         p.setPort(PUERTO_MULTICAST);
         ds.send(p);
      } catch (IOException ex) {
         Logger.getLogger(NodoController.class.getName()).log(Level.SEVERE, null, ex);
      }
   }

   /**
    * Recibe un DatagramPacket por el DatagramSocket.
    *
    * @return DatagramPacket Recibido.
    * @throws SocketTimeoutException Cuando el socket tiene un timeout definido
    * y este espira.
    * @throws IOException Cuando existe un problema de entrada o salida con el
    * socket.
    */
   public synchronized DatagramPacket recibir() throws SocketTimeoutException, IOException {
      DatagramPacket p = new DatagramPacket(new byte[200], 200);
      ds.receive(p);
      return p;
   }

   /**
    * Envia un DatagramPacket por el DatagramSocket.
    *
    * @param p DatagramPacket.
    */
   public synchronized void enviar(DatagramPacket p) {
      try {
         ds.send(p);
      } catch (IOException ex) {
         Logger.getLogger(NodoController.class.getName()).log(Level.SEVERE, null, ex);
      }
   }

   /**
    * Regresa true si el nodo es maestro, false si es esclavo.
    *
    * @return true si el nodo es maestro, false si es esclavo.
    */
   public boolean isMaster() {
      return masterFlag;
   }

   /**
    * Saca el MulticastSocket del grupo que por defecto se une y pone la bandera
    * que indica que el nodo es Maestro.
    */
   public void setMaster() {
      this.masterFlag = MAESTRO;
      try {
         mcs.leaveGroup(gpo);
         //System.out.println("Saliendo de grupo OK");
      } catch (IOException ex) {
         Logger.getLogger(NodoController.class.getName()).log(Level.SEVERE, null, ex);
      }
   }

   /**
    * Establece la bandera que indica si el nodo es Maestro = true o
    * Esclavo=false.
    *
    * @param masterFlag true si es nodo master, false si es esclavo
    */
   public void setMasterFlag(boolean masterFlag) {
      this.masterFlag = masterFlag;
   }

   /**
    * Regresa el valor de reloj en milisegundos.
    *
    * @return valor de reloj en milisegundos.
    */
   public int getMSClockValue() {
      return reloj.getMilisecondsValue();
   }

   /**
    * Establece valor del reloj con milisegundos.
    *
    * @param ms Valor de reloj en milisegundos.
    */
   public void setMSClockValue(int ms) {
      reloj.setCiclo(10);
      reloj.setWithMilisecondsValue(ms);
   }

   /**
    * Regresa el atributo lista de nodos.
    *
    * @return ArrayList Lista de nodos conocidos.
    */
   public ArrayList<Nodo> getNodos() {
      return nodos;
   }

   /**
    * Establece la lista de nodos conocidos.
    *
    * @param nodos ArrayList Lista de nodos conocidos.
    */
   public void setNodos(ArrayList<Nodo> nodos) {
      this.nodos = nodos;
   }

   /**
    * Busca en la lista de nodos, si ya estiste cambia su marca de tiempo, sino
    * agrega el nodo a la lista
    *
    * @param p DatagramPacket con la informacion de dirección y puerto.
    * @param ms Marca de tiempo en milisegundos.
    */
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
   }

   /**
    * Establece un tiempo de espera del DatagramSocket
    *
    * @param ms tiempo de espera en milisegundos.
    */
   public void setSoTimeout(int ms) {
      try {
         ds.setSoTimeout(ms);
      } catch (SocketException ex) {
         Logger.getLogger(NodoController.class.getName()).log(Level.SEVERE, null, ex);
      }
   }

   void setClockPulseValue(int ms) {
      //ms es negativo
      //int aux = ms / (Constantes.TIEMPO_ESPERA_SINCRONIZACION / 1000);
      double ciclo = 10.0 + (((double) -ms) * Constantes.TIEMPO_DESFASE_MAXIMO) / Constantes.DESFASE_MAXIMO;
      reloj.setCiclo(ciclo);
   }

}
