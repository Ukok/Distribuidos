package com.ejercicio07.master.controller;

import com.ejercicio07.model.Nodo;
import com.ejercicio07.model.Operacion;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author Mario Cantellano
 */
public class Executor extends Thread {

    private final BlockingQueue taskQueue;
    private final Nodo nodo;
    private final ArrayList<Operacion> operaciones;
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public Executor(BlockingQueue taskQueue, Nodo nodo, ArrayList<Operacion> operaciones) {
        this.taskQueue = taskQueue;
        this.nodo = nodo;
        this.operaciones = operaciones;
    }

    @Override
    public void run() {
        try {
            Operacion op;
            //Crea socket e inicializa los flujos de entrada/salida.
            socket = new Socket(nodo.getAddress(), nodo.getPort());
            socket.setSoTimeout(2000);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());

            while (true) {
                try {
                    // Intenta sacar una operacion de la cola si está vacia Estado del hilo es WAITING.
                    op = (Operacion) taskQueue.take();
                    try {
                        // Envia operacion.
                        oos.writeObject(op);
                        oos.flush();
                        // Recibe operacion con resultado.
                        op = (Operacion) ois.readObject();
                        // Agrega operacion con resultado en lista_resultados.
                        agregarOperacion(op);
                    } catch (SocketTimeoutException ex) {
                        taskQueue.add(op);
                    } catch (IOException | ClassNotFoundException ex) {
                        taskQueue.add(op);
                    }
                } catch (InterruptedException ex) {
                    System.err.println("InterrumpedException: Al intentar sacar Operación");
                }
            }

        } catch (IOException ex) {
            System.err.println("IOException: " + ex.getLocalizedMessage());
        }

    }

    private synchronized void agregarOperacion(Operacion op) {
        operaciones.add(op);
    }

}
