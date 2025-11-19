/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package practica1;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author macpro2
 */
public class Practica1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        JFrame frm = new JFrame("Ventana 1");
        
        frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        frm.setSize(800, 600);
        
        BorderLayout bl = new BorderLayout();
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            }
        );
        
        frm.setLayout(bl);
        frm.add(btnCerrar,BorderLayout.PAGE_END);
        
        frm.setVisible(true);
           
    } 
}