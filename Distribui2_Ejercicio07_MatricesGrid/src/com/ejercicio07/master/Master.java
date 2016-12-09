package com.ejercicio07.master;

import com.ejercicio07.master.controller.ThreadPool;
import com.ejercicio07.model.Constantes;
import com.ejercicio07.model.Datos;
import com.ejercicio07.model.Matriz;
import com.ejercicio07.model.Nodo;
import com.ejercicio07.model.Operacion;
import com.ejercicio07.model.Utileria;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mario
 */
public class Master extends Thread implements Constantes {

    ArrayList<Nodo> nodos;
    DatagramSocket ds;
    InetAddress gpo;
    Datos datos;

    ThreadPool tp;
    ArrayList<Operacion> operacionesRealizadas;

    int n_operaciones;
    private Matriz resultado;

    public Master(Matriz a, Matriz b) {

        this.nodos = new ArrayList<>();
        this.datos = new Datos(a, b);

        try {
            gpo = InetAddress.getByName(DIRECCION_DE_GRUPO);
        } catch (UnknownHostException u) {
            System.err.println("Direccion no válida");
        }

        try {
            ds = new DatagramSocket();
        } catch (SocketException ex) {
            Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
        }
        operacionesRealizadas = new ArrayList<>();

    }

    @Override
    public void run() {
        // Enviar Datos.
        if (datos.getB().getM() == datos.getA().getN()) {
            while (true) {
                System.out.println("==============================================");
                System.out.print("Enviando datos...");
                this.enviarDatosGrupo();
                System.out.println("[OK]");
                // Recibir nodos.
                System.out.print("Recibiendo nodos...");
                if (this.recibirNodos()) {
                    System.out.println("[OK]");
                    // Crear operaciones en taskQueue
                    System.out.print("Generando peticiones...");
                    this.generarOperaciones();
                    System.out.println("[OK]");
                    // Crear e Iniciar tp.
                    System.out.print("Iniciando pool...");
                    tp.start();
                    System.out.println("[OK]");
                    // Esperar a que se ejecuten las operaciones.
                    System.out.print("Esperando...");
                    this.esperar();
                    System.out.println("[OK]");
                    //tp.shutdown();
                    // Resultado obtenido.
                    System.out.print("Resultado...");
                    this.resultado();
                    System.out.println("[OK]");
                    System.out.println(resultado);

                    try {
                        sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    operacionesRealizadas.clear();
                    nodos.clear();

                } else {
                    try {
                        sleep(3000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }else{
            System.err.println("Matrices no compatibles.");
        }
    }

    /**
     * Envia un DatagramPacket al grupo Multicast.
     *
     */
    public synchronized void enviarDatosGrupo() {
        byte[] buf = Utileria.toByteArrray(datos);
        DatagramPacket p = new DatagramPacket(buf, buf.length);
        try {
            p.setAddress(gpo);
            p.setPort(PUERTO_MULTICAST);
            ds.send(p);
        } catch (IOException ex) {
            Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Recibe un DatagramPacket por el DatagramSocket.
     *
     * @throws SocketTimeoutException Cuando el socket tiene un timeout definido
     * y este espira.
     * @throws IOException Cuando existe un problema de entrada o salida con el
     * socket.
     */
    public void recibirNodo() throws SocketTimeoutException, IOException {

        DatagramPacket p = new DatagramPacket(new byte[512], 512);
        ds.receive(p);
        Nodo aux = (Nodo) Utileria.fromByteArray(p.getData());

        System.out.println(aux);

        nodos.add(aux);
    }

    public boolean recibirNodos() {
        try {
            this.ds.setSoTimeout(Constantes.TIEMPO_ESPERA_SOCKET);
            System.out.println("");
            while (true) {
                try {
                    recibirNodo();
                } catch (SocketTimeoutException ex) {
                    if (nodos.size() > 0) {
                        //System.out.println("Nodos registrados...");
                        return true;
                    } else {
                        System.out.println("No hay nodos");
                        return false;
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SocketException ex) {
            Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Establece un tiempo de espera del DatagramSocket
     *
     * @param ms tiempo de espera en milisegundos.
     */
    public void setSoTimeout(int ms) {
        try {
            ds.setSoTimeout(ms);
        } catch (SocketException ex) {
            Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void generarOperaciones() {
        int N, M;
        M = datos.getA().getM();
        N = datos.getB().getN();
        n_operaciones = M * N;
        tp = new ThreadPool(n_operaciones, nodos, operacionesRealizadas);
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                tp.execute(new Operacion(i, j));
            }
        }
    }

    public void esperar() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (true) {
            if (tp.getTaskQueue().isEmpty()) {
                break;
            } else {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void resultado() {
        Matriz aux = new Matriz(datos.getA().getM(), datos.getB().getN());
        for (Iterator<Operacion> iterator = operacionesRealizadas.iterator(); iterator.hasNext();) {
            Operacion next = iterator.next();
            aux.set_at(next.getFila(), next.getColumna(), next.getResultado());
        }
        this.resultado = aux;
    }

    public static void main(String[] args) {
        Master m;
        //Matriz A
        double[][] a = {{1, 2, 3, 4, 5, 6}, {1, 2, 3, 4, 5, 6}};
        //Matriz B
        double[][] b = {{1, 2, 3}, {3, 4,5}, {5, 6,7}, {7, 8,9}, {9, 10,11}, { 10,11, 12}};
        m = new Master(new Matriz(a), new Matriz(b));
        m.start();

    }
}
