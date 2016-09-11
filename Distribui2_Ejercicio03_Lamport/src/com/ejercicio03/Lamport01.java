package com.ejercicio03;

import com.ejercicio03.controller.ClockLamportController;
import com.ejercicio03.model.ClockLamport;

/**
 *
 * @author mario
 */
public class Lamport01 {

    public static void main(String[] args) throws InterruptedException {
        ClockLamportController[] control = new ClockLamportController[3];
        for (int i = 0; i < 3; i++) {
            control[i] = new ClockLamportController(new ClockLamport(0, i + 1));
            control[i].start();
        }

        while (true) {
            for (int i = 0; i < 3; i++) {
                System.out.println(control[i]);
            }
            Thread.sleep(1000);
            System.out.println("---");
        }
    }
}
