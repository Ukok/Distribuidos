package com.ejercicio06;

import com.ejercicio06.client.controller.TecladoController;
import com.ejercicio06.client.view.Teclado;
import com.ejercicio06.server.controller.DisplayController;
import com.ejercicio06.server.view.Display;
import javax.swing.JFrame;

/**
 *
 * @author mario
 */
public class Principal {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            if (args.length == 0) {
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

            } else {
                String dir = args[0];
                JFrame ventana = new JFrame("Calculadora (cliente)");
                Teclado t = new Teclado();
                TecladoController tc = new TecladoController(t, dir);
                ventana.setMinimumSize(new java.awt.Dimension(350, 350));
                ventana.add(t);
                ventana.setLocationRelativeTo(null);
                ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                ventana.setVisible(true);

            }

        });
    }
}
