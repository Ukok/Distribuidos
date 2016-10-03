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
public class Principal {

   public static void main(String[] args) {

      java.awt.EventQueue.invokeLater(() -> {
         int master = 0;
         if (args.length == 0) {
            System.out.println("Falta argumento");
         } else {
            master = Integer.parseInt(args[0]);

            PrincipalController pc = new PrincipalController();
            ClockController cc1 = new ClockController();
            pc.agregarClockSecundario(cc1.getPanelClock());
            pc.mostrarVentana();
            NodoController nc1 = new NodoController(cc1.getReloj());
            BerkeleyThread bt1 = new BerkeleyThread(nc1);

            if (master == 1) {
               nc1.setMaster();
               bt1.setName("Master");
            } else {
               bt1.setName("Slave");
            }

            bt1.start();
         }
      });
   }
}
