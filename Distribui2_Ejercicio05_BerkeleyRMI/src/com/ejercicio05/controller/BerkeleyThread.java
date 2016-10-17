package com.ejercicio05.controller;

import com.ejercicio05.model.Constantes;
import com.ejercicio05.model.IMaster;
import com.ejercicio05.model.ISlave;
import com.ejercicio05.model.Nodo;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mario
 */
public class BerkeleyThread implements Runnable, Constantes {

    private NodoController nodo;

    private int sumatoriaDeRelojes;
    private int relojesSumados;
    private double promedio;

    @Override
    public void run() {
        while (true) {
            if (nodo.isMaster()) {
                master();
            } else {
                slave();
            }
        }
    }

    /**
     * Constructor de BerkeleyThread.
     *
     * @param nodo NodoController necesario para la ejecución berkeley.
     */
    public BerkeleyThread(NodoController nodo) {
        this.nodo = nodo;
        relojesSumados = 1;
    }

    /**
     * Comportamiento cuando el nodo es Maestro.
     */
    private void master() {

        try {
            Thread.sleep(TIEMPO_ESPERA_SINCRONIZACION);
            System.out.println("\n");
            // Recibe relojes de cada nodo en la lista nodos.(Sumando los valores)
            calcularDesfases();
            // Obtiene el promedio de los valores recibidos
            calculaPromedio();
            // Realiza y envia el ajuste para cada nodo. ( Lista ajustes )
            enviarAjustes();
            // Ajusta reloj local.
            ajustar_clock();
            reset();
        } catch (InterruptedException ex) {
            Logger.getLogger(BerkeleyThread.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Obtiene los desfases de cada nodo en la lista.
     */
    private void calcularDesfases() {
        ArrayList<Nodo> list = nodo.getNodos();
        int desfase;
        int tiempoLocal = nodo.getMSClockValue();
        nodo.security();
        for (Iterator<Nodo> iterator = list.iterator(); iterator.hasNext();) {
            try {
                Nodo next = iterator.next();
                Registry registry = LocateRegistry.getRegistry(next.getAddr());
                ISlave s = (ISlave) registry.lookup(OBJ_SLAVE);
                desfase = s.getClockOffsetValue(tiempoLocal);
                next.setTimestamp(desfase);
                if (estaEnRango(desfase)) {
                    sumatoriaDeRelojes += desfase;
                    relojesSumados++;
                }
            } catch (NotBoundException ex) {
                Logger.getLogger(BerkeleyThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (AccessException ex) {
                Logger.getLogger(BerkeleyThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException ex) {
                Logger.getLogger(BerkeleyThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Calcula el promedio de los desfases.
     */
    private void calculaPromedio() {
        promedio = Math.round((double) sumatoriaDeRelojes / (double) relojesSumados);
        System.out.println(
                " Sumatoria : " + sumatoriaDeRelojes
                + " nRelojes:" + relojesSumados
                + " Promedio:" + promedio);
    }

    /**
     * Ajusta el reloj de cada nodo en la lista.
     */
    private void enviarAjustes() {
        ArrayList<Nodo> list = nodo.getNodos();
        int desfase;
        int tiempoLocal = nodo.getMSClockValue();
        nodo.security();
        for (Iterator<Nodo> iterator = list.iterator(); iterator.hasNext();) {
            try {
                Nodo next = iterator.next();
                Registry registry = LocateRegistry.getRegistry(next.getAddr());
                ISlave s = (ISlave) registry.lookup(OBJ_SLAVE);
                s.adjustClockValue((int) ((next.getTimestamp() * -1) + promedio));
            } catch (RemoteException ex) {
                Logger.getLogger(BerkeleyThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NotBoundException ex) {
                Logger.getLogger(BerkeleyThread.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    /**
     * Ajusta el reloj local con el valor de ms.
     *
     */
    public void ajustar_clock() {
        System.out.println("Ajustando reloj en " + promedio + " ms");
        if (promedio >= 0) {
            nodo.setMSClockValue((int) (nodo.getMSClockValue() + promedio));
        }
    }

    /**
     * Restablece los valores para una futura ejecución del algoritmo.
     */
    private void reset() {
        sumatoriaDeRelojes = 0;
        relojesSumados = 1;
        promedio = 0.0;
    }

    /**
     * Evalua si un valor en milisegundos de reloj esta en un rango especificado
     * por RANGOMAX y RANGOMIN.
     *
     * @param milisegundos valor en milisegundos.
     * @return true si está en rango, false sino lo está.
     */
    public boolean estaEnRango(int milisegundos) {
        return (milisegundos < (RANGO_MAX)
                && milisegundos > (RANGO_MIN));
    }

    /**
     * Comportamiento para nodo esclavo.
     */
    private void slave() {
        try {
            nodo.security();
            Registry registry = LocateRegistry.getRegistry(nodo.getServerAddress());
            IMaster m = (IMaster) registry.lookup(Constantes.OBJ_MASTER);
            String myAddress = nodo.getLocalAddress();
            if (myAddress != null) {
                m.register(nodo.getLocalAddress());
            } else {
                System.err.println("No hay conexión a la red.");
            }
            Thread.sleep(TIEMPO_ESPERA_REGISTRO);
        } catch (RemoteException ex) {
            Logger.getLogger(BerkeleyThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(BerkeleyThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(BerkeleyThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
