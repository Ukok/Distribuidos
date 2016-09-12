package com.ejercicio03.model;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author mario
 */
public class NodoDestino {

    private String address;
    private int port;
    private boolean destiny;

    public NodoDestino(String address, int port) {
        this.address = address;
        this.port = port;
        this.destiny = true;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isDestiny() {
        return destiny;
    }

    public void setDestiny(boolean flag) {
        this.destiny = flag;
    }

    @Override
    public String toString() {
        return "NodoDestino{" + "address=" + address + ", port=" + port
                + ", flag=" + destiny + '}';
    }

    public InetAddress getInetAddress() throws UnknownHostException {
        return InetAddress.getByName(address);
    }

}
