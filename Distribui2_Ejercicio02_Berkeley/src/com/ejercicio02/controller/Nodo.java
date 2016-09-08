package com.ejercicio02.controller;

import com.ejercicio01.model.Clock;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mario
 */
public class Nodo {

    private MulticastSocket mcs;
    private InetAddress gpo;
    private boolean masterFlag;
    private Clock reloj;

    public Nodo(Clock reloj) {
        try {
            mcs = new MulticastSocket(2222);
            gpo = InetAddress.getByName("228.5.6.7");
            masterFlag = false;
            this.reloj = reloj;
        } catch (IOException ex) {
            Logger.getLogger(Nodo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Clock estimarDesfase(Clock reloj) {
        int hh, mm, ss, ms = 0;

        ms = this.reloj.get_ms() - reloj.get_ms();
        ss = this.reloj.get_ss() - reloj.get_ss();
        mm = this.reloj.get_mm() - reloj.get_mm();
        hh = this.reloj.get_hh() - reloj.get_hh();

        if (ms < 0) {
            ss -= 1;
            ms += 1000;
        }
        if (ss < 0) {
            mm -= 1;
            ss += 60;
        }
        if (mm < 0) {
            hh -= 1;
            mm += 60;
        }
        if (hh < 0) {
            hh += 24;
        }
        return new Clock(hh, mm, ss, ms);
    }

    private void master() {
        //Comportamiento de ser maestro.
    }

    private void slave() {
        //Comportamiento de ser esclavo.
    }
}
