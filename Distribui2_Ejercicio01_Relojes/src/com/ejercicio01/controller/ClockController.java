package com.ejercicio01.controller;

import com.ejercicio01.model.Clock;
import com.ejercicio01.view.panel.PanelClock;
import java.awt.event.ActionEvent;

/**
 *
 * @author mario
 */
public class ClockController extends Thread {

    private Clock reloj;
    private PanelClock pc;

    public ClockController() {
        reloj = new Clock();
        pc = new PanelClock();
        pc.btnModificar.addActionListener(this::btnModificarActionPerformed);
        pc.btnReset.addActionListener(this::btnResetActionPerformed);
        pc.btnSistema.addActionListener(this::btnSistemaActionPerformed);
    }

    @Override
    public void run() {
        while (true) {
            try {
                // sleep(1);
                sleep(1000);
                clickClack();
                update_lblClock();
            } catch (InterruptedException ex) {
                System.err.println("Error: sleep");
            }
        }
    }

    public void clickClack() {
        //    if (reloj.ms_sum(1) == 1000) {
        //        reloj.set_ms(0);
        if (reloj.ss_sum(1) == 60) {
            reloj.set_ss(0);
            if (reloj.mm_sum(1) == 60) {
                reloj.set_mm(0);
                if (reloj.hh_sum(1) == 24) {
                    reloj.set_hh(0);
                }
            }
        }
        //  }
    }

    public void btnModificarActionPerformed(ActionEvent e) {
        int hh, mm, ss;
        hh = Integer.parseInt(pc.spHH.getValue().toString());
        mm = Integer.parseInt(pc.spMM.getValue().toString());
        ss = Integer.parseInt(pc.spSS.getValue().toString());
        reloj.updateClock(hh, mm, ss);

        update_lblClock();
    }

    public void btnResetActionPerformed(ActionEvent e) {
        reloj.resetClock();

        update_lblClock();
    }

    public void btnSistemaActionPerformed(ActionEvent e) {
        reloj.systemClock();

        update_lblClock();
    }

    private void update_lblClock() {
        pc.lblClock.setText(reloj.getStringValue());
    }

    public Clock getReloj() {
        return reloj;
    }

    public void setReloj(Clock reloj) {
        this.reloj = reloj;
    }

    public PanelClock getPanelClock() {
        return pc;
    }

    public void setPanelClock(PanelClock pc) {
        this.pc = pc;
    }

}
