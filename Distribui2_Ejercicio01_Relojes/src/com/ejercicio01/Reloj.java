package com.ejercicio01;

import com.ejercicio01.controller.ClockController;
import com.ejercicio01.controller.PrincipalController;

/**
 *
 * @author mario
 */
public class Reloj {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
                      
            ClockController cc1 = new ClockController();
            ClockController cc2 = new ClockController();
            ClockController cc3 = new ClockController();
            ClockController cc4 = new ClockController();
            ClockController cc5 = new ClockController();

            PrincipalController pc = new PrincipalController();

            pc.agregarClockPrincipal(cc1.getPanelClock());
            pc.agregarClockSecundario(cc2.getPanelClock());
            pc.agregarClockSecundario(cc3.getPanelClock());
            pc.agregarClockSecundario(cc4.getPanelClock());
            pc.agregarClockSecundario(cc5.getPanelClock());
            
            pc.mostrarVentana();

            cc1.start();
            cc2.start();
            cc3.start();
            cc4.start();
            cc5.start();

        });
    }
}
