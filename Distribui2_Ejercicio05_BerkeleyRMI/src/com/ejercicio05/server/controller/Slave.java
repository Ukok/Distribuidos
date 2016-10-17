package com.ejercicio05.server.controller;

import com.ejercicio05.controller.NodoController;
import com.ejercicio05.model.ISlave;
import java.rmi.RemoteException;

/**
 *
 * @author mario
 */
public class Slave implements ISlave {

    NodoController nodo;

    public Slave(NodoController nodo) {
        super();
        this.nodo = nodo;
    }

    @Override
    public int getClockOffsetValue(int ms) throws RemoteException {
        return nodo.getMSClockValue() - ms;
    }

    @Override
    public void adjustClockValue(int ms) throws RemoteException {
        System.out.println("Ajustando reloj en " + ms + " ms");
        if (ms >= 0) {
            nodo.setMSClockValue(nodo.getMSClockValue() + ms);
        } else {
            nodo.setClockPulseValue(ms);
        }
    }

}
