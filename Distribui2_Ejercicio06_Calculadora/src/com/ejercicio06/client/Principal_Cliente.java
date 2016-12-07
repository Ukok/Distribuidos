
package com.ejercicio06.client;

import com.ejercicio06.client.controller.TecladoController;
import com.ejercicio06.client.view.Teclado;
import com.ejercicio06.model.Constantes;
import javax.swing.JFrame;

/**
 *
 * @author mario
 */
public class Principal_Cliente implements Constantes{
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
        /* if (args.length == 0) {
            System.out.println("Falta argumento");
         } else {*/
            //String dir = args[0];
            String dir = "127.0.0.1";
            JFrame ventana = new JFrame("Calculadora (cliente)");
            Teclado t = new Teclado();
            TecladoController tc = new TecladoController(t, dir);
            ventana.setMinimumSize(new java.awt.Dimension(350, 350));
            ventana.add(t);
            ventana.setLocationRelativeTo(null);
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventana.setVisible(true);
            
         //}
      });
    }
}
