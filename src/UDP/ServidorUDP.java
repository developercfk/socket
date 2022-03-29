
package UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author chris
 */
public class ServidorUDP {
    
    
    public static void main(String[] args) {
        
        System.out.println("--[  SERVIDOR  ]--");
        try {
            
            DatagramSocket socketUDP = new DatagramSocket(5656);
            
        
            
            byte[] bufer = new byte[100];
            
            while (true) {                
            
            DatagramPacket peticion = new DatagramPacket(bufer, bufer.length);
            
            
            socketUDP.receive(peticion);
            
            System.out.println(" Mensaje recibido del cliente :  "+ new String(peticion.getData()));
            
            InetAddress host = peticion.getAddress();
            
            
            
            int puertoCliente = peticion.getPort();
            
            System.out.println("host : " + host +" Puerto : " + puertoCliente);
            String str = "Hola soy el servidor , su mensaje se ha bien recibido";
            
            bufer = str.getBytes();
            
            DatagramPacket reinviar = new DatagramPacket(bufer, bufer.length,host,puertoCliente);
            
            Thread.sleep(2000);
            
            
            socketUDP.send(reinviar);
            
            }
            
           
        } catch (Exception e) {
            
            
        }
    }
}
