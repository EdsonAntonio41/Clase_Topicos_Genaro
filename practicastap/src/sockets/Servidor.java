/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author macpro2
 */
public class Servidor {

    ServerSocket servidor;

    void run() {

        try {
            servidor = new ServerSocket(3000);

            System.out.println("ServerSocket iniciado en puerto 3000");
            
            while(true){
                Socket s = servidor.accept();
                Conexion c = new Conexion(s);
                c.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        Servidor srv = new Servidor();
        srv.run();
    }
}

class Conexion extends Thread {

    Socket conexion;
    DataInputStream in;
    DataOutputStream out;

    Conexion(Socket s) {
        conexion = s;
    }

    @Override
    public void run() {

        try {
            System.out.println("Se acepta conexion desde: " + conexion.getInetAddress());

            in = new DataInputStream(conexion.getInputStream());
            out = new DataOutputStream(conexion.getOutputStream());

            out.writeUTF("Hola");

            String respuesta = in.readUTF();
            System.out.println(respuesta);

            out.writeUTF("Adios");
            System.out.println("Se cierra la conexion");

            conexion.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
