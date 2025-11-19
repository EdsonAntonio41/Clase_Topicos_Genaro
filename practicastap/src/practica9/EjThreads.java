/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica9;

import java.util.Random;

/**
 *
 * @author macpro2
 */
public class EjThreads {

    Random random = new Random();
    int x = 0;
    Object lock;

    public static void main(String args[]) {
        EjThreads m = new EjThreads();
        m.run();
    }

    void run() {
        lock = new Object();
        Thread h1 = new Thread(new HiloHijoR("hiloR1", 0));
        Thread h2 = new Thread(new HiloHijoR("hiloR2", 0));
        HiloHijoT h3 = new HiloHijoT("hiloT1", 0, this);
        h1.start();
        h2.start();
        h3.start();
        try {

            h1.join();
            h2.join();
            h3.join();

            System.out.println("Terminó el main!");
        } catch (InterruptedException ex) {
            System.getLogger(EjThreads.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    class HiloHijoR implements Runnable {

        String nombre;
        int delay;

        public HiloHijoR(String _nombre, int _delay) {
            nombre = _nombre;
            //delay = _delay;
            int randomNumber = random.nextInt(5 - 1 + 1) + 1;
            delay = randomNumber * 300;
            System.out.println("Hilo: " + _nombre + " delay=" + delay);
        }

        public void run() {
            for (int i = 0; i < 100; i++) {
                incrementar(i);
            }
            System.out.println("Terminó el hilo hijo! " + nombre);
        }

        private void incrementar(int i) {
            synchronized (lock) {
                int n = x;

                x = ++n;
                System.out.println(nombre + ": durmiendo " + (delay / 300) + " seg!, i=" + i + " x = " + x);
            }
            /*
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                System.getLogger(EjThreads.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
             */
        }
    }

}

class HiloHijoT extends Thread {

    String nombre;
    int delay;
    EjThreads m;

    public HiloHijoT(String _nombre, int _delay, EjThreads _m) {
        nombre = _nombre;
        delay = _delay;
        m = _m;
        int randomNumber = m.random.nextInt(5 - 1 + 1) + 1;
        delay = randomNumber * 300;
        System.out.println("Hilo: " + _nombre + " delay=" + delay);
    }

    public void run() {
        for (int i = 0; i < 100; i++) {
            incrementar(i);
        }
        System.out.println("Terminó el hilo hijo!: " + nombre);
    }

    private void incrementar(int i) {
        /*
        try {
            Thread.sleep(delay);
        } catch (InterruptedException ex) {
            System.getLogger(HiloHijoT.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
         */
        synchronized (m.lock) {
            int n = m.x;
            m.x = ++n;
            System.out.println(nombre + ": durmiendo " + delay / 300 + " seg!, i=" + i + " x = " + m.x);
        }

    }
}
