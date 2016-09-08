package com.ejercicio01.controller;

import com.ejercicio01.model.Clock;
import javax.swing.JLabel;

/**
 * Simula el funcionamiento de un reloj digital de 24 hrs.
 *
 * @author mario
 */
public class ClockThread extends Thread {

    private Clock reloj;
    private JLabel label;

    /**
     * Constructor de ClockThread.
     *
     * @param reloj Objeto Clock.
     * @param label Objeto JLabel.
     */
    public ClockThread(Clock reloj, JLabel label) {
        this.reloj = reloj;
        this.label = label;
    }

    /**
     * Obtener label.
     *
     * @return JLabel label.
     */
    public JLabel getLabel() {
        return label;
    }

    /**
     * Establecer JLabel.
     *
     * @param label
     */
    public void setLabel(JLabel label) {
        this.label = label;
    }

    /**
     * Obtener reloj.
     *
     * @return Clock reloj.
     */
    public Clock getReloj() {
        return reloj;
    }

    /**
     * Establecer reloj.
     *
     * @param reloj
     */
    public void setReloj(Clock reloj) {
        this.reloj = reloj;
    }

    @Override
    public void run() {
        while (true) {
            try {
                //sleep(1);
                sleep(10);
                reloj.clickClack();
                label.setText(reloj.getStringValue());
            } catch (InterruptedException ex) {
                System.err.println("Error: sleep");
            }
        }
    }

}
