package com.ejercicio03.controller;

import com.ejercicio03.model.Mensaje;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mario
 */
public class NodoLamport {

    private DatagramSocket socket;
    private ClockLamportController cc;

    public NodoLamport(int port) {
        try {
            socket = new DatagramSocket(port);
            cc = new ClockLamportController();

        } catch (SocketException ex) {
            Logger.getLogger(NodoLamport.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void enviar(String direccion, int puerto, Mensaje mensaje) {
        try {
            mensaje.setTiempo(cc.getClockValue());
            InetAddress address = InetAddress.getByName(direccion);
            byte[] buf = mensaje.toByteArray();
            DatagramPacket dp = new DatagramPacket(buf, buf.length);
            dp.setAddress(address);
            dp.setPort(puerto);
            socket.send(dp);
        } catch (UnknownHostException ex) {
            Logger.getLogger(NodoLamport.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NodoLamport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Mensaje recibir() {
        try {
            DatagramPacket dp = new DatagramPacket(new byte[100], 100);
            socket.receive(dp);
            Mensaje mensaje = Mensaje.fromByteArray(dp.getData());
            System.out.print("Recibido dato de "
                    + dp.getAddress().getHostName() + ": "
                    + mensaje.getValue());
            return mensaje;
        } catch (IOException ex) {
            Logger.getLogger(NodoLamport.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
}
