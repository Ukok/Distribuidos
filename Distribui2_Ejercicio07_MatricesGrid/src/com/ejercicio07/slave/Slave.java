package com.ejercicio07.slave;

import com.ejercicio07.model.Constantes;
import static com.ejercicio07.model.Constantes.DIRECCION_DE_GRUPO;
import com.ejercicio07.model.Datos;
import com.ejercicio07.model.Nodo;
import com.ejercicio07.model.Operacion;
import com.ejercicio07.model.Utileria;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mario
 */
public class Slave extends Thread {

    ServerSocket serverSocket;
    MulticastSocket mcs;
    Datos datos;
    Nodo nodo;

    ObjectOutputStream oos;
    ObjectInputStream ois;

    public Slave(int pto) {
        InetAddress gpo = null;
        try {
            gpo = InetAddress.getByName(DIRECCION_DE_GRUPO);
            mcs = new MulticastSocket(Constantes.PUERTO_MULTICAST);
            mcs.joinGroup(gpo);
            System.out.println("Unido a grupo:" + gpo);

        } catch (UnknownHostException u) {
            System.err.println("Direccion no valida");

        } catch (IOException ex) {
            Logger.getLogger(Slave.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            serverSocket = new ServerSocket(pto);
        } catch (IOException ex) {
            Logger.getLogger(Slave.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {

        while (true) {
            try {
                this.recibirDatosGrupo();
                this.enviarNodo_Master();
                System.out.print("Esperando peticion...");
                Socket s = serverSocket.accept();
                System.out.println("[OK]");
                s.setSoTimeout(5000);
                oos = new ObjectOutputStream(s.getOutputStream());
                ois = new ObjectInputStream(s.getInputStream());

                while (this.atender(s)) {
                }
            } catch (IOException ex) {
                Logger.getLogger(Slave.class.getName()).log(Level.SEVERE, null, ex);
                break;
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Slave.class.getName()).log(Level.SEVERE, null, ex);
                break;
            }
        }

    }

    private void recibirDatosGrupo() {
        try {
            System.out.print("Recibiendo datos...");
            DatagramPacket p = new DatagramPacket(new byte[1024], 1024);
            mcs.receive(p);
            datos = (Datos) Utileria.fromByteArray(p.getData());
            nodo = new Nodo(p.getAddress(), p.getPort());
            System.out.println("[OK]");
        } catch (IOException ex) {
            System.out.println("\n");
            Logger.getLogger(Slave.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    private void enviarNodo_Master() {
        try {
            Nodo aux = new Nodo(InetAddress.getByName(getLocalAddress()), serverSocket.getLocalPort());
            byte[] buf = Utileria.toByteArrray(aux);
            DatagramPacket p = new DatagramPacket(buf, buf.length, nodo.getAddress(), nodo.getPort());
            mcs.send(p);

        } catch (IOException ex) {
            Logger.getLogger(Slave.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean atender(Socket s) throws IOException, ClassNotFoundException {

        try {
            Operacion op = (Operacion) ois.readObject();
//            System.out.print("Atendiendo...");
            realizarOperacion(op);
            oos.writeObject(op);
            oos.flush();
            System.out.println("["+ this.getName() +"] "+op + " [OK]");
            return true;
        } catch (SocketTimeoutException ex) {
            System.out.println("Regresando a esperar datos...");
            return false;
        }
    }

    private void realizarOperacion(Operacion op) {
        double[] row = datos.getA().row(op.getFila());
        double[] col = datos.getB().col(op.getColumna());

        double result = 0.0;
        double d, e;
        for (int i = 0; i < row.length; ++i) {

            result += (row[i] * col[i]);

        }
        op.setResultado(result);
    }

    public String getLocalAddress() {
        try {
            Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
            for (NetworkInterface netint : Collections.list(nets)) {
                if (netint.getDisplayName().contains("wlan") || netint.getDisplayName().contains("eth")) {
                    Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
                    for (InetAddress inetAddress : Collections.list(inetAddresses)) {
                        if (inetAddress.getHostAddress().matches("[[0-9]{1,3}\\.]{3,}[0-9]{1,3}")) {
                            //System.out.println("Slave dir: " + inetAddress.getHostAddress());
                            return inetAddress.getHostAddress();
                        }
                    }
                }
            }

        } catch (SocketException ex) {
            Logger.getLogger(Slave.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return null;
    }

    public static void main(String[] args) {
        Slave s3 = new Slave(1193);
        Slave s2 = new Slave(1192);
        Slave s1 = new Slave(1191);
        s3.start();
        s2.start();
        s1.start();
    }
}
