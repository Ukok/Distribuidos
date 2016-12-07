package com.ejercicio06.server.controller;

import com.ejercicio06.model.Constantes;
import com.ejercicio06.model.Mensaje;
import com.ejercicio06.server.view.Display;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 *
 * @author mario
 */
public class DisplayController extends Thread implements Constantes {

    Display dis;
    DatagramSocket socket;
    String operaciones;
    Mensaje m;

    public DisplayController(Display dis) {
        this.dis = dis;
        try {
            this.socket = new DatagramSocket(PORT);
        } catch (SocketException ex) {
            System.err.println(ex.getLocalizedMessage());
        }
        this.operaciones = new String();
    }

    @Override
    public void run() {
        while(true){
            recibir();
            ejecutarCodigoOperacion();
        }
    }

    public void recibir() {
        try {
            DatagramPacket p = new DatagramPacket(new byte[200], 200);
            socket.receive(p);
            m = Mensaje.fromByteArray(p.getData());
        } catch (IOException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }
    
    public void ejecutarCodigoOperacion(){
        switch(m.getCodigoOperacion()){
            
            case PUNTO:
                operaciones = operaciones + ".";
                break;
            case UNO:
                operaciones = operaciones + "1";
                break;
            case DOS:
                operaciones = operaciones + "2";
                break;
            case TRES:
                operaciones = operaciones + "3";
                break;
            case CUATRO:
                operaciones = operaciones + "4";
                break;
            case CINCO:
                operaciones = operaciones + "5";
                break;
            case SEIS:
                operaciones = operaciones + "6";
                break;
            case SIETE:
                operaciones = operaciones + "7";
                break;
            case OCHO:
                operaciones = operaciones + "8";
                break;
            case NUEVE:
                operaciones = operaciones + "9";
                break;
            case CERO:
                operaciones = operaciones + "0";
                break;
            case DEL:
                if(!operaciones.isEmpty())
                    operaciones = operaciones.substring(0, operaciones.length()-1);
                break;
            case AC:
                operaciones = "";
                break;
            case SUMA:
                operaciones = operaciones + "+";
                break;
            case RESTA:
                operaciones = operaciones + "-";
                break;
            case MULTIPLICACION:
                operaciones = operaciones + "*";
                break;
            case DIVISION:
                operaciones = operaciones + "/";
                break;
            case IGUAL:
                resultado();
                break;   
            case PARENTESIS_IZQ:
                operaciones = operaciones + "(";
                break;
            case PARENTESIS_DER:
                operaciones = operaciones + ")";
                break;
                
        }
        dis.lblDisplay.setText(operaciones);
    }
    
    private void resultado(){
        System.out.println("Calculando....");
    // AQUI REALIZA OPERACIONES
        
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");

        try {
            Object operation = engine.eval(operaciones);
            System.out.println("Evaluado operacion: " + operation);
            operaciones = "" + String.valueOf(operation);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    
    }

}
