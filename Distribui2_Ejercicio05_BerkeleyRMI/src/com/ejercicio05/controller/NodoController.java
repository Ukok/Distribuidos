package com.ejercicio05.controller;

import com.ejercicio01.model.Clock;
import com.ejercicio05.model.Constantes;
import com.ejercicio05.model.IMaster;
import com.ejercicio05.model.Nodo;
import com.ejercicio05.server.controller.Master;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mario
 */
public class NodoController implements Constantes {

    private boolean masterFlag;
    private final Clock reloj;
    private ArrayList<Nodo> nodos;

    private Master maestro;
    private final String serverAddress;

    /**
     * Constructor.
     *
     * @param reloj Clock.
     * @param serverAddress Direccion del nodo maestro.
     */
    public NodoController(Clock reloj, String serverAddress) {
        this.masterFlag = SLAVE;
        this.reloj = reloj;
        this.serverAddress = serverAddress;
        nodos = new ArrayList<>();
    }

    /**
     * Verdadero si el nodo es maestro, falso si es esclavo.
     *
     * @return true si es maestro,false si es esclavo.
     */
    public boolean isMaster() {
        return masterFlag;
    }

    /**
     * Establece la bandera en MASTER y registra el objeto remoto.
     */
    public void setMaster() {

        try {
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager());
            }
            maestro = new Master(nodos);
            IMaster m = (IMaster) UnicastRemoteObject.exportObject(maestro, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(OBJ_MASTER, m);
            this.masterFlag = MASTER;
        } catch (RemoteException ex) {
            Logger.getLogger(NodoController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Establece la bandera en SLAVE y registra el objeto remoto.
     */
    public final void setSlave() {
        this.masterFlag = SLAVE;
    }

    /**
     * Obtiene el reloj.
     *
     * @return Clock de nodo.
     */
    public Clock getReloj() {
        return reloj;
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

    public void setClockPulseValue(int ms) {
        //ms es negativo
        //int aux = ms / (Constantes.TIEMPO_ESPERA_SINCRONIZACION / 1000);
        double ciclo = 10.0 + (((double) -ms) * TIEMPO_DESFASE_MAXIMO) / DESFASE_MAXIMO;
        reloj.setCiclo(ciclo);
    }

    public void security() {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public String getLocalAddress() {
        try {
            Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
            for (NetworkInterface netint : Collections.list(nets)) {
                if (netint.getDisplayName().contains("wlan") || netint.getDisplayName().contains("eth")) {
                    Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
                    for (InetAddress inetAddress : Collections.list(inetAddresses)) {
                        if (inetAddress.getHostAddress().matches("[[0-9]{1,3}\\.]{3,}[0-9]{1,3}")) {
                            System.out.println("Slave dir: " + inetAddress.getHostAddress());
                            return inetAddress.getHostAddress();
                        }
                    }
                }
            }

        } catch (SocketException ex) {
            Logger.getLogger(NodoController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return null;
    }
}
