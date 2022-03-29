/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author seter
 */
public class ReciboEnvioDatagrama {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Creo el socket datagram...");
        try {
            InetAddress addr = InetAddress.getByName("localhost");  //127.0.0.1
            DatagramSocket ds = new DatagramSocket(5656, addr);
            while (true) {
                byte[] mensaje = new byte[50];
                DatagramPacket dp = new DatagramPacket(mensaje, 50);
                ds.receive(dp);     //Aquí se queda bloqueado hasta que llega un mensaje

                ////Todo esto lo gestiona un hilo ////
                String cadenaRecibida = new String(mensaje);
                System.out.println("Mensaje recibido: " + cadenaRecibida);
                Thread.sleep(5000);

                //ds.close();
                //System.out.println("Conexión terminada.");
                System.out.println("Creo el socket datagram envio...");
                DatagramSocket dsEnvio = new DatagramSocket();
                InetAddress addrEnvio = InetAddress.getByName("localhost");  //localhost = 127.0.0.1
                dp = new DatagramPacket(mensaje, mensaje.length, addr, 5656);
                dsEnvio.send(dp);
                System.out.println("Mensaje enviado...");
                dsEnvio.close();
                ////Fin////
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    
      class GestionaRecepcion extends Thread {

    public void run() {

    }
}
}



