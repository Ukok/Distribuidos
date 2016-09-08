package com.ejercicio01.controller;

import com.ejercicio01.model.Clock;
import com.ejercicio01.view.panel.PanelClock;
import java.awt.event.ActionEvent;

/**
 *
 * @author mario
 */
public class ClockController {

    private Clock reloj;
    private PanelClock pc;
    private ClockThread ct;

    /**
     * Constructor, inicializa sus atributos y agrega las funciones de acción
     * para los componentes swing del PanelClock, inicia la ejecucion de
     * ClockThread.
     */
    public ClockController() {
        reloj = new Clock();
        pc = new PanelClock();
        ct = new ClockThread(reloj, pc.lblClock);
        pc.btnModificar.addActionListener(this::btnModificarActionPerformed);
        pc.btnReset.addActionListener(this::btnResetActionPerformed);
        pc.btnSistema.addActionListener(this::btnSistemaActionPerformed);
        ct.start();
    }

    /**
     * ActionPerformed para el botón modificar.
     *
     * @param e ActionEvent del botón.
     */
    public void btnModificarActionPerformed(ActionEvent e) {
        int hh, mm, ss;
        hh = Integer.parseInt(pc.spHH.getValue().toString());
        mm = Integer.parseInt(pc.spMM.getValue().toString());
        ss = Integer.parseInt(pc.spSS.getValue().toString());
        reloj.updateClock(hh, mm, ss);
        update_lblClock();
    }

    /**
     * ActionPerformed para el botón reset.
     *
     * @param e ActionEvent del botón.
     */
    public void btnResetActionPerformed(ActionEvent e) {
        reloj.resetClock();

        update_lblClock();
    }

    /**
     * ActionPerformed para el botón Sistema.
     *
     * @param e ActionEvent del botón.
     */
    public void btnSistemaActionPerformed(ActionEvent e) {
        reloj.systemClock();

        update_lblClock();
    }

    /**
     * Actualiza el texto que muestra lblClock que se encuentra en PanelClock.
     */
    private void update_lblClock() {
        pc.lblClock.setText(reloj.getStringValue());
    }

    /**
     * Obtener reloj.
     *
     * @return Objecto Clock reloj.
     */
    public Clock getReloj() {
        return reloj;
    }

    /**
     * Establecer reloj
     *
     * @param reloj
     */
    public void setReloj(Clock reloj) {
        this.reloj = reloj;
    }

    /**
     * Obtener PanelClock.
     *
     * @return PanelClock pc.
     */
    public PanelClock getPanelClock() {
        return pc;
    }

    /**
     * Establecer PanelClock.
     *
     * @param pc
     */
    public void setPanelClock(PanelClock pc) {
        this.pc = pc;
    }

}
