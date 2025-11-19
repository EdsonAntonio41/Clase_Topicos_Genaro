/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author macpro2
 */
public class Cliente3 {
    Socket conexion;
    DataInputStream in;
    DataOutputStream out;
   
    void run(){
        Scanner scanner = new Scanner(System.in);
        String buffer = "";
        String resp   = "";
        
        try {
            conexion = new Socket("10.24.8.247",3000);
            in = new DataInputStream(conexion.getInputStream());
            out = new DataOutputStream(conexion.getOutputStream());
            
            
            while(!buffer.startsWith("adios")){
                buffer = in.readUTF();
                System.out.println(buffer);
                
                resp = scanner.nextLine();
                
                out.writeUTF(resp);     
            }
            
            conexion.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Cliente3.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    };
    
    public static void main(String[] args){
        Cliente3 cliente = new Cliente3();
        cliente.run();
        
    }
    
}
