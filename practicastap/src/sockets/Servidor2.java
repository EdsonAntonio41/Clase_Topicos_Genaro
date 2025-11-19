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
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author macpro2
 */
public class Servidor2 {

    ServerSocket servidor;

    void run() {

        try {
            servidor = new ServerSocket(3000);

            System.out.println("ServerSocket iniciado en puerto 3000");
            
            while(true){
                Socket s = servidor.accept();
                Conexion2 c = new Conexion2(s);
                c.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Servidor2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        Servidor2 srv = new Servidor2();
        srv.run();
    }
}

class Conexion2 extends Thread {

    Socket socket;
    DataInputStream in;
    DataOutputStream out;

    Conexion2(Socket s) {
        socket = s;
    }

    @Override
    public void run() {
        String buffer="";
        String resp = "";        
        Scanner scanner = new Scanner(System.in);
        
        try {
            System.out.println("Se acepta conexion desde: " + socket.getInetAddress());

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            out.writeUTF("Hola");
            
            while(!buffer.startsWith("adios")){
                buffer=in.readUTF();
                System.out.println(buffer);
                resp = scanner.nextLine();
                out.writeUTF(resp);
            }
            
            socket.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
