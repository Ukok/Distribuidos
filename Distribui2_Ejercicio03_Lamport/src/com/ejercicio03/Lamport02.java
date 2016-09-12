package com.ejercicio03;

import com.ejercicio03.controller.NodoLamport;
import com.ejercicio03.model.ClockLamport;
import com.ejercicio03.model.Mensaje;
import com.ejercicio03.model.NodoDestino;
import java.util.ArrayList;

/**
 *
 * @author mario
 */
public class Lamport02 {

    public static void main(String[] args) {
        NodoDestino nd1 = new NodoDestino("127.0.0.1", 2331);
        NodoDestino nd2 = new NodoDestino("127.0.0.1", 2332);
        NodoDestino nd3 = new NodoDestino("127.0.0.1", 2333);
        ClockLamport cl1 = new ClockLamport(0, 6);
        ClockLamport cl2 = new ClockLamport(0, 8);
        ClockLamport cl3 = new ClockLamport(0, 10);
        ArrayList<NodoDestino> a = new ArrayList<NodoDestino>();
        ArrayList<NodoDestino> b = new ArrayList<NodoDestino>();
        a.add(nd2);
        b.add(nd3);
        
        
        NodoLamport nl1 = new NodoLamport(2331, a, cl1);        
        NodoLamport nl2 = new NodoLamport(2332, b, cl2);
        NodoLamport nl3 = new NodoLamport(2333, new ArrayList<>(), cl3);
        nl1.setName("NL1");
        nl2.setName("NL2");
        nl3.setName("NL3");

        nl1.enviar(new Mensaje(10));
        nl1.start();
        nl2.start();
        nl3.start();                
        
    }
}
