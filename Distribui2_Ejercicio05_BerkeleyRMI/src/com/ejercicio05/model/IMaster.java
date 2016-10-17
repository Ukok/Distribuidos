package com.ejercicio05.model;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author mario
 */
public interface IMaster extends Remote {

    public void register(String addr) throws RemoteException;

}
