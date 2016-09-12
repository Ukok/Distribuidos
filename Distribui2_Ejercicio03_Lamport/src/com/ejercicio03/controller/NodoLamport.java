package com.ejercicio03.controller;

import com.ejercicio03.model.ClockLamport;
import com.ejercicio03.model.Mensaje;
import com.ejercicio03.model.NodoDestino;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mario
 */
public class NodoLamport extends Thread {

    private DatagramSocket socket;
    private ClockLamportController cc;
    private ArrayList<NodoDestino> nd;

    public NodoLamport(int port, ArrayList<NodoDestino> nd, ClockLamport cl) {
        try {
            socket = new DatagramSocket(port);
            cc = new ClockLamportController(cl);
            this.nd = nd;
            cc.start();
        } catch (SocketException ex) {
            Logger.getLogger(NodoLamport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void enviar(String direccion, int puerto, Mensaje mensaje) {
        try {
            InetAddress address = InetAddress.getByName(direccion);
            cc.pause();
            mensaje.setTiempo(cc.getClockValue());
            cc.restart();
            byte[] buf = mensaje.toByteArray();
            DatagramPacket dp = new DatagramPacket(buf, buf.length, address, puerto);
            socket.send(dp);
        } catch (UnknownHostException ex) {
            Logger.getLogger(NodoLamport.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NodoLamport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void enviar(Mensaje mensaje) {
        if (!nd.isEmpty()) {
            try {
                NodoDestino local = null;
                cc.pause();
                mensaje.setTiempo(cc.getClockValue());
                cc.restart();
                byte[] buf = mensaje.toByteArray();
                local = nd.get(0);

                DatagramPacket dp
                        = new DatagramPacket(buf, buf.length, local.getInetAddress(), local.getPort());
                socket.send(dp);
                nd.remove(0);

            } catch (UnknownHostException ex) {
                Logger.getLogger(NodoLamport.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(NodoLamport.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Mensaje recibir() {
        try {
            DatagramPacket dp = new DatagramPacket(new byte[100], 100);
            socket.receive(dp);
            Mensaje mensaje = Mensaje.fromByteArray(dp.getData());
            System.out.println("[" + this.getName() + "]Mensaje recibido de "
                    + dp.getAddress().getHostAddress()
                    + ":" + dp.getPort()
                    + " valor = " + mensaje.getValue()
            );
            nd.add(new NodoDestino(dp.getAddress().getHostAddress(), dp.getPort()));
            return mensaje;
        } catch (IOException ex) {
            Logger.getLogger(NodoLamport.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void sincronizar(Mensaje mensaje) {
        System.out.println("Tiempo actual (" + this.getName() + "): " + cc.getClockValue());
        cc.pause();
        System.out.println("Sincronizando... max(" + cc.getClockValue() + "," + mensaje.getTiempo() + ")");
        if (mensaje.getTiempo() > cc.getClockValue()) {
            cc.setClockValue(Integer.max(mensaje.getTiempo(), cc.getClockValue()) + 1);
        }
        cc.restart();
        System.out.println("Tiempo actual (" + this.getName() + "): " + cc.getClockValue());
    }

    @Override
    public void run() {
        while (true) {
            try {
                Mensaje mensaje = recibir();
                sincronizar(mensaje);
                Thread.sleep(3000);
                enviar(new Mensaje(valorMensajeNuevo(mensaje)));
            } catch (InterruptedException ex) {
                Logger.getLogger(NodoLamport.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("---");
        }
    }

    private int valorMensajeNuevo(Mensaje mensaje) {
        return mensaje.getValue() + 1;
    }

}
