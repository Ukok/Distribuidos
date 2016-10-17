package com.ejercicio05.model;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author mario
 */
public interface ISlave extends Remote {

    public int getClockOffsetValue(int ms) throws RemoteException;

    public void adjustClockValue(int ms) throws RemoteException;

}