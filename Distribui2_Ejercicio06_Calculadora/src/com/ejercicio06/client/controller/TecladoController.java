package com.ejercicio06.client.controller;

import com.ejercicio06.client.view.Teclado;
import com.ejercicio06.model.Constantes;
import com.ejercicio06.model.Mensaje;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author mario
 */
public class TecladoController implements Constantes {

    DatagramSocket socket;
    Teclado teclado;
    Mensaje m;
    InetAddress dir;

    public TecladoController(Teclado teclado, String dir) {
        this.teclado = teclado;
        this.m = new Mensaje();

        try {
            this.socket = new DatagramSocket();
        } catch (SocketException ex) {
            System.err.println(ex.getLocalizedMessage());
        }

        try {
            this.dir = InetAddress.getByName(dir);
        } catch (UnknownHostException u) {
            System.err.println("Direccion no valida");
        }

        teclado.btnPunto.addActionListener(this::btnPuntoActionPerformed);
        teclado.btnParentesisIzq.addActionListener(this::btnParentesisIzqActionPerformed);
        teclado.btnParentesisDer.addActionListener(this::btnParentesisDerActionPerformed);
        teclado.btnDel.addActionListener(this::btnDelActionPerformed);
        teclado.btnAC.addActionListener(this::btnACActionPerformed);
        teclado.btnDivision.addActionListener(this::btnDivisionActionPerformed);
        teclado.btnMultiplicacion.addActionListener(this::btnMultiplicacionActionPerformed);
        teclado.btnSuma.addActionListener(this::btnSumaActionPerformed);
        teclado.btnResta.addActionListener(this::btnRestaActionPerformed);
        teclado.btnIgual.addActionListener(this::btnIgualActionPerformed);
        teclado.btn0.addActionListener(this::btn0ActionPerformed);
        teclado.btn1.addActionListener(this::btn1ActionPerformed);
        teclado.btn2.addActionListener(this::btn2ActionPerformed);
        teclado.btn3.addActionListener(this::btn3ActionPerformed);
        teclado.btn4.addActionListener(this::btn4ActionPerformed);
        teclado.btn5.addActionListener(this::btn5ActionPerformed);
        teclado.btn6.addActionListener(this::btn6ActionPerformed);
        teclado.btn7.addActionListener(this::btn7ActionPerformed);
        teclado.btn8.addActionListener(this::btn8ActionPerformed);
        teclado.btn9.addActionListener(this::btn9ActionPerformed);
    }

    /**
     * ActionPerformed para el botón Punto.
     *
     * @param e ActionEvent del botón.
     */
    public void btnPuntoActionPerformed(ActionEvent e) {
        System.out.println(".");
        m.setCodigoOperacion(PUNTO);
        enviar();
    }

    /**
     * ActionPerformed para el botón (.
     *
     * @param e ActionEvent del botón.
     */
    public void btnParentesisIzqActionPerformed(ActionEvent e) {
        System.out.println("(");
        m.setCodigoOperacion(PARENTESIS_IZQ);
        enviar();
    }

    /**
     * ActionPerformed para el botón ).
     *
     * @param e ActionEvent del botón.
     */
    public void btnParentesisDerActionPerformed(ActionEvent e) {
        System.out.println(")");
        m.setCodigoOperacion(PARENTESIS_DER);
        enviar();
    }

    /**
     * ActionPerformed para el botón DEL.
     *
     * @param e ActionEvent del botón.
     */
    public void btnDelActionPerformed(ActionEvent e) {
        System.out.println("DEL");
        m.setCodigoOperacion(DEL);
        enviar();
    }

    /**
     * ActionPerformed para el botón AC.
     *
     * @param e ActionEvent del botón.
     */
    public void btnACActionPerformed(ActionEvent e) {
        System.out.println("AC");
        m.setCodigoOperacion(AC);
        enviar();
    }
    
    public void btnIgualActionPerformed(ActionEvent e){
        System.out.println("=");
        m.setCodigoOperacion(IGUAL);
        enviar();
    }

    /**
     * ActionPerformed para el botón /.
     *
     * @param e ActionEvent del botón.
     */
    public void btnDivisionActionPerformed(ActionEvent e) {
        System.out.println("/");
        m.setCodigoOperacion(DIVISION);
        enviar();
    }

    /**
     * ActionPerformed para el botón +.
     *
     * @param e ActionEvent del botón.
     */
    public void btnSumaActionPerformed(ActionEvent e) {
        System.out.println("+");
        m.setCodigoOperacion(SUMA);
        enviar();
    }

    /**
     * ActionPerformed para el botón -.
     *
     * @param e ActionEvent del botón.
     */
    public void btnRestaActionPerformed(ActionEvent e) {
        System.out.println("-");
        m.setCodigoOperacion(RESTA);
        enviar();
    }

    /**
     * ActionPerformed para el botón X.
     *
     * @param e ActionEvent del botón.
     */
    public void btnMultiplicacionActionPerformed(ActionEvent e) {
        System.out.println("X");
        m.setCodigoOperacion(MULTIPLICACION);
        enviar();
    }

    /**
     * ActionPerformed para el botón 0.
     *
     * @param e ActionEvent del botón.
     */
    public void btn0ActionPerformed(ActionEvent e) {
        System.out.println("0");
        m.setCodigoOperacion(CERO);
        enviar();
    }

    /**
     * ActionPerformed para el botón 1.
     *
     * @param e ActionEvent del botón.
     */
    public void btn1ActionPerformed(ActionEvent e) {
        System.out.println("1");
        m.setCodigoOperacion(UNO);
        enviar();
    }

    /**
     * ActionPerformed para el botón 2.
     *
     * @param e ActionEvent del botón.
     */
    public void btn2ActionPerformed(ActionEvent e) {
        System.out.println("2");
        m.setCodigoOperacion(DOS);
        enviar();
    }

    /**
     * ActionPerformed para el botón 3.
     *
     * @param e ActionEvent del botón.
     */
    public void btn3ActionPerformed(ActionEvent e) {
        System.out.println("3");
        m.setCodigoOperacion(TRES);
        enviar();
    }

    /**
     * ActionPerformed para el botón 4.
     *
     * @param e ActionEvent del botón.
     */
    public void btn4ActionPerformed(ActionEvent e) {
        System.out.println("4");
        m.setCodigoOperacion(CUATRO);
        enviar();
    }

    /**
     * ActionPerformed para el botón 5.
     *
     * @param e ActionEvent del botón.
     */
    public void btn5ActionPerformed(ActionEvent e) {
        System.out.println("5");
        m.setCodigoOperacion(CINCO);
        enviar();
    }

    /**
     * ActionPerformed para el botón 6.
     *
     * @param e ActionEvent del botón.
     */
    public void btn6ActionPerformed(ActionEvent e) {
        System.out.println("6");
        m.setCodigoOperacion(SEIS);
        enviar();
    }

    /**
     * ActionPerformed para el botón 7.
     *
     * @param e ActionEvent del botón.
     */
    public void btn7ActionPerformed(ActionEvent e) {
        System.out.println("7");
        m.setCodigoOperacion(SIETE);
        enviar();
    }

    /**
     * ActionPerformed para el botón 8.
     *
     * @param e ActionEvent del botón.
     */
    public void btn8ActionPerformed(ActionEvent e) {
        System.out.println("8");
        m.setCodigoOperacion(OCHO);
        enviar();
    }

    /**
     * ActionPerformed para el botón 9.
     *
     * @param e ActionEvent del botón.
     */
    public void btn9ActionPerformed(ActionEvent e) {
        System.out.println("9");
        m.setCodigoOperacion(NUEVE);
        enviar();
    }

    /**
     * Envia mensaje con a la dirección y puerto.
     */
    private void enviar() {
        DatagramPacket p;
        p = new DatagramPacket(m.toByteArrray(), m.toByteArrray().length);
        p.setPort(PORT);
        p.setAddress(dir);

        try {
            socket.send(p);
        } catch (IOException ex) {
            ex.getLocalizedMessage();
        }

    }

}
