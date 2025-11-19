/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alumno
 */
public class Servidor4 {

    ServerSocket server;
    ArrayList<Conexion4> conexiones = new ArrayList<Conexion4>();
    int i = 0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Servidor4 server = new Servidor4();
        server.run();
    }

    public void run() {
        try {
            server = new ServerSocket(4444);
            System.out.println("Iniciando server en " + server.getLocalSocketAddress());
            while (true) {
                i = i + 1;
                Conexion4 cnx = new Conexion4(server.accept(), i, this);
                cnx.start();
                conexiones.add(cnx);
            }
        } catch (IOException ex) {
            Logger.getLogger(Servidor4.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void difundir(int id, String respuesta) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));

        conexiones.forEach((cnx) -> {
            if (id != cnx.getID()) {
                cnx.enviar(dtf.format(now)+" - "+ id + " - " + respuesta);
            }
        });
    }

}

class Conexion4 extends Thread {

    int id = 0;

    Socket socket;
    BufferedReader in;
    PrintWriter out;
    String respuesta;

    Servidor4 padre;

    Conexion4(Socket sockt, int _id, Servidor4 _padre) {
        socket = sockt;
        id = _id;
        padre = _padre;
    }

    @Override
    public void run() {
        boolean band = true;
        try {
            System.out.println("Aceptando conexion desde " + socket.getInetAddress());

            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println("Bienvenido conexion " + id);

            while (band) {
                respuesta = in.readLine();

                if (respuesta.equals("ADIOS.")) {
                    break;
                };

                padre.difundir(id, respuesta);
            }

            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int getID() {
        return id;
    }

    void enviar(String respuesta) {
        out.println(respuesta);
    }

}
