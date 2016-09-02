package com.ejercicio01.controller;

import com.ejercicio01.view.AcercaDe;
import com.ejercicio01.view.Principal;
import com.ejercicio01.view.panel.PanelClock;
import java.awt.event.ActionEvent;

/**
 *
 * @author mario
 */
public class PrincipalController {

    Principal ventanaPrincipal;
    AcercaDe acercaDe;

    public PrincipalController() {
        ventanaPrincipal = new Principal();
        ventanaPrincipal.menuitemSalir.addActionListener(this::menuitemSalirActionPerformed);
        ventanaPrincipal.menuitemAcercaDe.addActionListener(this::menuitemAcercaDeActionPerformed);
        ventanaPrincipal.setVisible(false);
        acercaDe = new AcercaDe();
        acercaDe.setVisible(false);
        
    }
    
    public void mostrarVentana(){
        ventanaPrincipal.setLocationRelativeTo(null);
        ventanaPrincipal.setVisible(true);
    }
    
    public void ocultarVentana(){
        ventanaPrincipal.setVisible(false);
    }
    
    public void agregarClockPrincipal(PanelClock comp){
        ventanaPrincipal.add(comp, java.awt.BorderLayout.NORTH);
    }
    
    public void agregarClockSecundario(PanelClock comp){
        comp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ventanaPrincipal.panelCenter.add(comp);
    }

    public void menuitemSalirActionPerformed(ActionEvent e) {
        System.exit(0);
    }

    public void menuitemAcercaDeActionPerformed(ActionEvent evt) {
        acercaDe.setVisible(true);
        acercaDe.setLocationRelativeTo(null);
    }
       

}
