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
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author macpro2
 */
public class Servidor3 {

    ServerSocket servidor;
    int countId = 0;
    ArrayList<Conexion3> conexiones = new ArrayList<>();

    void run() {

        try {
            servidor = new ServerSocket(3000);

            System.out.println("ServerSocket iniciado en puerto 3000");

            while (true) {
                Socket s = servidor.accept();
                int id = ++countId;
                Conexion3 c = new Conexion3(this, id, s);
                conexiones.add(c);

                c.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Servidor3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        Servidor3 srv = new Servidor3();
        srv.run();
    }

    public void replica(int origen, String mensaje) {
         DataOutputStream out;
         
         for(int i=0; i<conexiones.size();i++){
             if(conexiones.get(i).id != origen){
                 try {
                     out = new DataOutputStream(conexiones.get(i).socket.getOutputStream());
                     out.writeUTF(mensaje);
                 } catch (IOException ex) {
                     Logger.getLogger(Servidor3.class.getName()).log(Level.SEVERE, null, ex);
                 }
             }
         }
    }

   
}

class Conexion3 extends Thread {

        Socket socket;
        DataInputStream in;
        DataOutputStream out;
        Servidor3 padre;
        int id;

        Conexion3(Servidor3 _padre, int _id, Socket s) {
            padre = _padre;
            id = _id;
            socket = s;
        }

        @Override
        public void run() {
            String buffer = "";
            String resp = "";
            int id = 0;
            Scanner scanner = new Scanner(System.in);

            try {
                System.out.println("Se acepta conexion desde: " + socket.getInetAddress());

                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());

                while (!buffer.startsWith(".adios")) {
                    buffer = in.readUTF();
                    System.out.println(buffer);
                    padre.replica(this.id, buffer);
                }
                socket.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            
        }
    }