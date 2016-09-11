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

            PrincipalController pc = new PrincipalController();

            ClockController cc1 = new ClockController();
            ClockController cc2 = new ClockController();
            ClockController cc3 = new ClockController();
            ClockController cc4 = new ClockController();
            ClockController cc5 = new ClockController();

            pc.agregarClockPrincipal(cc1.getPanelClock());
            pc.agregarClockSecundario(cc2.getPanelClock());
            pc.agregarClockSecundario(cc3.getPanelClock());
            pc.agregarClockSecundario(cc4.getPanelClock());
            pc.agregarClockSecundario(cc5.getPanelClock());

            pc.mostrarVentana();

            System.gc();
        });
        /*        Clock c1 = new Clock(22, 42, 20, 10);
        int ms = c1.getMilisecondsValue();
        double ss = c1.getSecondsValue();
        System.out.println("ms=" + ms + " , ss = " + ss);
        
        c1.setWithMilisecondsValue(ms);
        System.out.println(c1);
        
        c1.setWithSecondsValue(ss);
        System.out.println(c1);*/
    }
}
