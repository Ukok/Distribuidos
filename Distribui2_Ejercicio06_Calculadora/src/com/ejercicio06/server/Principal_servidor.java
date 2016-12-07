package com.ejercicio06.server;

import com.ejercicio06.server.controller.DisplayController;
import com.ejercicio06.server.view.Display;
import javax.swing.JFrame;

/**
 *
 * @author mario
 */
public class Principal_servidor {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            JFrame ventana = new JFrame("Calculadora (servidor)");
            Display r = new Display();
            DisplayController dc = new DisplayController(r);

            r.lblDisplay.setText("");
            r.lblDisplay.setFont(new java.awt.Font("Comic Sans MS", 1, 35));

            ventana.setMinimumSize(new java.awt.Dimension(600, 200));
            ventana.add(r);
            ventana.setLocationRelativeTo(null);
            ventana.setVisible(true);
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            dc.start();
        });
    }
}
