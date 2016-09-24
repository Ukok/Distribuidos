/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejercicio04;

import com.ejercicio01.controller.ClockController;
import com.ejercicio01.controller.PrincipalController;
import com.ejercicio04.client.controller.ClienteUTC;
import com.ejercicio04.model.Constantes;
import com.ejercicio04.server.controller.ServidorUTC;

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

         ServidorUTC s = new ServidorUTC(cc1.getReloj(), Constantes.PUERTO);
         ClienteUTC c1 = new ClienteUTC(cc2.getReloj(), Constantes.DIRECCION, Constantes.PUERTO);
         ClienteUTC c2 = new ClienteUTC(cc3.getReloj(), Constantes.DIRECCION, Constantes.PUERTO);
         ClienteUTC c3 = new ClienteUTC(cc4.getReloj(), Constantes.DIRECCION, Constantes.PUERTO);
         ClienteUTC c4 = new ClienteUTC(cc5.getReloj(), Constantes.DIRECCION, Constantes.PUERTO);

         s.start();
         c1.start();
         c2.start();
         c3.start();
         c4.start();

         System.gc();
      });
   }
}
