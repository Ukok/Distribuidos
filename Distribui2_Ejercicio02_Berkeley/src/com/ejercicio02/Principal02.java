/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejercicio02;

import com.ejercicio01.Reloj;
import com.ejercicio01.controller.ClockController;
import com.ejercicio01.controller.PrincipalController;
import com.ejercicio01.model.Clock;
import com.ejercicio02.controller.BerkeleyThread;
import com.ejercicio02.controller.NodoController;
import com.ejercicio02.model.Constantes;

/**
 *
 * @author mario
 */
public class Principal02 {

   public static void main(String[] args) {
      java.awt.EventQueue.invokeLater(() -> {
         PrincipalController pc = new PrincipalController();
         double ciclo = 10.0 + (100.0 * Constantes.TIEMPO_DESFASE_MAXIMO) / Constantes.DESFASE_MAXIMO;
         System.out.println("ciclo" + ciclo);
         ClockController cc = new ClockController();
         Clock c = cc.getReloj();

         c.setCiclo(ciclo);

         pc.agregarClockPrincipal(cc.getPanelClock());

         pc.mostrarVentana();

         /*NodoController nc = new NodoController(cc.getReloj(), 5555);
      BerkeleyThread bt = new BerkeleyThread(nc);
      nc.setMaster();

      ClockController cc1 = new ClockController();
      NodoController nc1 = new NodoController(cc1.getReloj(), 5556);
      BerkeleyThread bt1 = new BerkeleyThread(nc1);

      ClockController cc2 = new ClockController();
      NodoController nc2 = new NodoController(cc2.getReloj(), 5557);
      BerkeleyThread bt2 = new BerkeleyThread(nc1);*/
 /*
      bt.start();
      bt1.start();
      bt2.start();
          */
         //Clock c = new Clock(23, 59, 59, 9990);
         //System.out.println("Eq = " + c.getMilisecondsValue());
      });
   }
}
