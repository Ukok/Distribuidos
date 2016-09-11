/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
                clock.clickClack();
                sleep(1000);
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
    
    void setClockValue(int i) {
        this.clock.setTiempo(i);
    }
}
