package com.ejercicio05.server.controller;

import com.ejercicio05.model.Constantes;
import static com.ejercicio05.model.Constantes.OBJ_SLAVE;
import com.ejercicio05.model.IMaster;
import com.ejercicio05.model.Nodo;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author mario
 */
public class Master implements IMaster, Constantes {

    ArrayList<Nodo> list;

    public Master(ArrayList<Nodo> list) {
        super();
        this.list = list;
    }

    @Override
    public void register(String addr) throws RemoteException {
        Nodo aux = new Nodo(addr, OBJ_SLAVE);
        int a = list.indexOf(aux);
        if (a == -1) {
            list.add(aux);
        }
    }
}
