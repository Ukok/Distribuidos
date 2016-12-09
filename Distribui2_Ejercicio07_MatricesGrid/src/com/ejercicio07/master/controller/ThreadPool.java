package com.ejercicio07.master.controller;

import com.ejercicio07.model.Nodo;
import com.ejercicio07.model.Operacion;
import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author Mario Cantellano
 */
public class ThreadPool {

    private BlockingQueue<Object> taskQueue;
    private List<Thread> pool;

    public ThreadPool(int noOfOperations, ArrayList<Nodo> nodos, ArrayList<Operacion> operacionesRealizadas) {
        taskQueue = new ArrayBlockingQueue(noOfOperations, true);
        agregarHilos(nodos, operacionesRealizadas);
    }

    public ThreadPool(BlockingQueue taskQueue, ArrayList<Nodo> nodos, ArrayList<Operacion> operacionesRealizadas) {
        this.taskQueue = taskQueue;
        agregarHilos(nodos, operacionesRealizadas);
    }

    /**
     * Inicia cada hilo en el pool.
     */
    public synchronized void start() {
        for (Thread thread : pool) {
            thread.start();
        }
    }

    /**
     * Agrega una operacion a la lista de operaciones.
     *
     * @param op Operacion a realizar.
     * @return true si se realizó correctamente, false en otro caso.
     */
    public boolean execute(Operacion op) {
        try {
            System.out.println(op);
            taskQueue.put(op);
            return true;
        } catch (InterruptedException ex) {
            System.err.println("Error al intentar almacenar una nueva operación.");
        }
        return false;
    }

    /**
     * Muestra los estados de los hilos en el pool.
     *
     * @return true si algún hilo esta disponible, false en otro caso.
     */
    public synchronized boolean isAviable() {
        boolean is = false;
        for (Thread exec : pool) {
            System.out.println("Thread name: " + exec.getName() + " ExecutorState: " + exec.getState());
            if (exec.getState() == State.WAITING) {
                is = true;
            }
        }
        return is;
    }

    /**
     * Interrumpe todos los hilos en el pool.
     */
    public synchronized void shutdown() {
        for (Thread thread : pool) {
            thread.interrupt();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ThreadPool.toString() = ");
        for (Thread thread : pool) {
            sb.append(thread.toString());
        }
        return sb.toString();
    }

    private void agregarHilos(ArrayList<Nodo> nodos, ArrayList<Operacion> operacionesRealizadas) {
        pool = new ArrayList();
        for (int i = 0; i < nodos.size(); i++) {
            pool.add(new Executor(taskQueue, nodos.get(i), operacionesRealizadas));
        }
    }

    public BlockingQueue<Object> getTaskQueue() {
        return taskQueue;
    }

    public void setTaskQueue(BlockingQueue<Object> taskQueue) {
        this.taskQueue = taskQueue;
    }

}
