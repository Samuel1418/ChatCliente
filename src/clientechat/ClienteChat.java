/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientechat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Samuel
 */
public class ClienteChat {

    private static DataInputStream is;
    private static DataOutputStream os;
    private Socket clienteSocket;
    static int puertoCOnexion=Integer.parseInt(JOptionPane.showInputDialog(null, "¿Que puerto desea usar?"));

    public static void main(String args[]) {

        boolean compConexion = true; 
        
        try {
            String nombre = JOptionPane.showInputDialog("Indique su nicName");
            System.out.println("Creando socket cliente");
            Socket clienteSocket = new Socket();
            System.out.println("Estableciendo la conexion");
            InetSocketAddress addr = new InetSocketAddress("localhost", puertoCOnexion);
            clienteSocket.connect(addr);
            is = new DataInputStream(clienteSocket.getInputStream());
            os = new DataOutputStream(clienteSocket.getOutputStream());
            
            os.writeUTF(nombre);
            os.flush();
            String conecta = nombre + " ha entrado al chat";
            os.writeUTF(conecta);
            os.flush();
            String mensaje = JOptionPane.showInputDialog("");
            
            while (compConexion) {
                try {
                    String mensaje2 = is.readUTF();
                    System.out.println(mensaje2);
                } catch (IOException ioe) {
                    System.out.println("No se ha podido reenviar");
                }
                compConexion = false;
            }
            
            os.writeUTF(mensaje);
            os.flush();
            conecta = nombre + " se fue para no volver";
            os.writeUTF(conecta);
            os.flush();
            clienteSocket.close();
            is.close();
            os.close();
        } catch (IOException ex) {
            Logger.getLogger(ClienteChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
