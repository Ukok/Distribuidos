/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejercicio02;

import com.ejercicio01.controller.ClockController;
import com.ejercicio01.controller.PrincipalController;
import com.ejercicio02.controller.BerkeleyThread;
import com.ejercicio02.controller.NodoController;

/**
 *
 * @author mario
 */
public class Principal01 {

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

         NodoController nc1 = new NodoController(cc1.getReloj(), 5555);

         nc1.setMaster();
         BerkeleyThread bt1 = new BerkeleyThread(nc1);

         bt1.setName("Master");

         NodoController nc2 = new NodoController(cc2.getReloj(), 5556);
         BerkeleyThread bt2 = new BerkeleyThread(nc2);
         bt2.setName("Slave1");

         NodoController nc3 = new NodoController(cc3.getReloj(), 5557);
         BerkeleyThread bt3 = new BerkeleyThread(nc3);

         NodoController nc4 = new NodoController(cc4.getReloj(), 5558);
         BerkeleyThread bt4 = new BerkeleyThread(nc4);

         NodoController nc5 = new NodoController(cc5.getReloj(), 5559);
         BerkeleyThread bt5 = new BerkeleyThread(nc5);

         bt1.start();
         bt2.start();
         bt3.start();
         bt4.start();
         bt5.start();

      });
   }

}
