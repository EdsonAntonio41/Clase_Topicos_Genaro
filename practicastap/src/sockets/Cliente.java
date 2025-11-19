/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author macpro2
 */
public class Cliente {
    Socket conexion;
    DataInputStream in;
    DataOutputStream out;
   
    void run(){
        
        try {
            conexion = new Socket("10.24.8.247",3000);
            in = new DataInputStream(conexion.getInputStream());
            out = new DataOutputStream(conexion.getOutputStream());
            
            String respuesta = in.readUTF();
            System.out.println(respuesta);
            
            out.writeUTF("Buenos días");
            
            respuesta = in.readUTF();
            
            System.out.println(respuesta);
            
            conexion.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    };
    
    public static void main(String[] args){
        Cliente cliente = new Cliente();
        cliente.run();
        
    }
    
}
