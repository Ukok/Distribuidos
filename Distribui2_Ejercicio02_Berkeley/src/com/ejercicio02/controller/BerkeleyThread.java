package com.ejercicio02.controller;

import com.ejercicio02.model.Constantes;
import com.ejercicio02.model.MensajeBerkeley;
import java.net.DatagramPacket;

/**
 * BerkeleyThread se encarga de llevar a cabo el algoritmo de Berkeley
 *
 * @author mario
 */
public class BerkeleyThread extends Thread {

    private NodoController nodo;

    private int sumatoriaDeRelojes;
    private int relojesSumados;

    private int tiempo;

    public BerkeleyThread(NodoController nodo) {
        this.nodo = nodo;
        this.nodo.setId(this.getId());
    }

    public NodoController getNodo() {
        return nodo;
    }

    public void setNodo(NodoController nodo) {
        this.nodo = nodo;
    }

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
     * Comportamiento cuando el nodo es Maestro.
     */
    private void master() {
        // this.sleep(7000);// duerme(7 segundos)Está en funcion del total de nodos por sincronizar al parecer.
        // Enviar SolicitudDeRelojes a esclavos MC.
        nodo.enviarGrupo(solicitudDesfase());
        // Recibe relojes de cada nodo en la lista nodos.(Sumando los valores del mensaje)

        // Obtiene el promedio de los valores recibidos.
        // Reliza el ajuste para cada nodo. ( Lista ajustes )
        sumatoriaDeRelojes = 0;
        relojesSumados = 0;
    }

    /**
     * Comportamiento cuando el nodo es Esclavo.
     */
    private void slave() {
        // Recibe valor 
        // Realiza la diferencia.
        // Envia diferencia al maestro.
        // Recibe ajuste.
    }

    private DatagramPacket solicitudDesfase() {
        MensajeBerkeley mensaje = new MensajeBerkeley(Constantes.SOLICITUD_DESFASE);
        mensaje.setMilisegundos(nodo.getMSClockValue());
        tiempo = mensaje.getMilisegundos();
        DatagramPacket p = new DatagramPacket(mensaje.toByteArrray(), mensaje.toByteArrray().length);
        return p;
    }

    private void recibirDesfases() { // Detalle en esta funcion. 
        int aux = nodo.getMSClockValue();
        int tiempoLimite = aux + Constantes.TIEMPO_ESPERA;

        while (true) {

            //if (aux <= tiempoLimite) {
            DatagramPacket p = nodo.recibir();
            MensajeBerkeley mb = MensajeBerkeley.fromByteArray(p.getData());

            if (mb.getCodigoOperacion() == Constantes.DESFASE) {
                if (estaEnRango(mb.getMilisegundos())) {
                    sumatoriaDeRelojes += mb.getMilisegundos();
                    relojesSumados++;
                }
                nodo.agregarNodo(p, mb.getMilisegundos());
            }

            //     aux = nodo.getMSClockValue();
            // } else {
            //    break;
            //}
        }

    }

    private boolean estaEnRango(int milisegundos) {
        if (milisegundos > (tiempo + Constantes.RANGO_MAX)
                || milisegundos < (tiempo + Constantes.RANGO_MIN)) {
            return false;
        }
        return true;
    }

    private void recibirMensajes() {
        while (true) {
            DatagramPacket p = nodo.recibir();
            MensajeBerkeley mb = MensajeBerkeley.fromByteArray(p.getData());
            switch (mb.getCodigoOperacion()) {
                case Constantes.DESFASE: //Master recibe de esclavo.
                    if (estaEnRango(mb.getMilisegundos())) {
                        sumatoriaDeRelojes += mb.getMilisegundos();
                        relojesSumados++;
                    }
                    nodo.agregarNodo(p, mb.getMilisegundos());
                    break;
                case Constantes.SOLICITUD_DESFASE: // Esclavo recibe de Master.
                    // Realiza la direfencia de tiempo y responde con Cod. DESFASE.
                    // desfase = Valor actual de reloj - Valor de reloj del Nodo Maestro.
                    int desfase = nodo.getMSClockValue() - mb.getMilisegundos();
                    mb.setMilisegundos(desfase);
                    mb.setCodigoOperacion(Constantes.DESFASE);
                    DatagramPacket dp = new DatagramPacket(mb.toByteArrray(), mb.toByteArrray().length);
                    dp.setAddress(p.getAddress());
                    dp.setPort(p.getPort());
                    nodo.enviar(dp);
                    break;
                case Constantes.AJUSTE: // Esclavo recibe de Master.
                    //Ajusta el reloj.
                    nodo.setMSClockValue(nodo.getMSClockValue() + mb.getMilisegundos());
                    break;
            }
        }
    }
}
