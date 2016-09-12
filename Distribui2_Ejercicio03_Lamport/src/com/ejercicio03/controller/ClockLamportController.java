package com.ejercicio03.controller;

import com.ejercicio03.model.ClockLamport;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mario
 */
public class ClockLamportController extends Thread {

    private ClockLamport clock;
    private boolean flagPause;

    public ClockLamportController() {
        clock = new ClockLamport();
    }

    public ClockLamportController(ClockLamport clock) {
        this.clock = clock;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (!flagPause) {
                    clock.clickClack();
                    sleep(1000);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(ClockLamport.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public String toString() {
        return "ClockLamportController{" + "clock=" + clock + '}';
    }

    public int getClockValue() {
        return clock.getTiempo();
    }

    public void setClockValue(int i) {
        this.clock.setTiempo(i);
    }

    public void pause() {
        this.flagPause = true;
    }

    public void restart() {
        this.flagPause = false;
    }
}
