package com.ejercicio02.model;

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
    private int timestamp;

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
     * Obtiene marca de tiempo
     *
     * @return tiempo de nodo en milisegundos.
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Establece marca de tiempo.
     *
     * @param timestamp
     */
    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
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

    @Override
    public String toString() {
        return "Nodo{" + "address=" + address + ", port=" + port + ", timestamp=" + timestamp + '}';
    }


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
