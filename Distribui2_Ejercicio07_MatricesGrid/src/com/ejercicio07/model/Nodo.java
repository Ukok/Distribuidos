package com.ejercicio07.model;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Objects;

/**
 * Almacena direccion y puerto de un nodo remoto.
 *
 * @author mario
 */
public class Nodo implements Serializable {

    private InetAddress address;
    private int port;

    /**
     * Obtiene direccion de nodo.
     *
     * @return InetAddress de la direccion del nodo.
     */
    public InetAddress getAddress() {
        return address;
    }

    /**
     * Establece direccion de nodo.
     *
     * @param address Objeto InetAddres.
     */
    public void setAddress(InetAddress address) {
        this.address = address;
    }

    /**
     * Obtiene puerto de nodo.
     *
     * @return puerto del nodo.
     */
    public int getPort() {
        return port;
    }

    /**
     * Establece puerto de nodo.
     *
     * @param port Puerto int.
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Constructor
     *
     * @param dir Direccion InetAddress.
     * @param port Puerto int.
     */
    public Nodo(InetAddress dir, int port) {
        this.address = dir;
        this.port = port;
    }

    /**
     * Regresa una representacion String de los datos de nodo.
     *
     * @return Representacion en String de los datos de Nodo.
     */
    @Override
    public String toString() {
        return "Nodo{" + "address=" + address + ", port=" + port + '}';
    }

    /**
     * Compara este objeto con otro.
     *
     * @param obj Objecto para compararse.
     * @return true si tiene los mismos atributos(addres y puerto).
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Nodo other = (Nodo) obj;
        if (this.port != other.port) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        return true;
    }

}
