package cliente;

// Java implementation for a client 
// Save file as Client.java 
  
import java.io.*; 
import java.net.*; 
import java.util.Scanner; 
  
// Client class 
public class Client3{
    
    
    
    
    public static void main(String[] args){
        
   
    Socket s;
    DataInputStream dis;
    DataOutputStream dos ;
           try
        { 
            Scanner scn = new Scanner(System.in); 
              
            // getting localhost ip 
            InetAddress ip = InetAddress.getByName("localhost"); 
      
            // establish the connection with server port 5056 
            s = new Socket(ip, 5056); 
      
            // obtaining input and out streams 
            dis = new DataInputStream(s.getInputStream()); 
            dos = new DataOutputStream(s.getOutputStream()); 
      
            // the following loop performs the exchange of 
            // information between client and client handler 
            while (true)  
            { 
                System.out.println(dis.readUTF()); 
                String tosend = scn.nextLine(); 
                dos.writeUTF(tosend); 
                  
                // If client sends exit,close this connection  
                // and then break from the while loop 
                if(tosend.equals("Exit")) 
                { 
                    System.out.println("Closing this connection : " + s); 
                    s.close(); 
                    System.out.println("Connection closed"); 
                    break; 
                } 
                  
                // printing date or time as requested by client 
                String received = dis.readUTF(); 
                System.out.println(received); 
                
                tosend = scn.nextLine();
                dos.writeUTF(tosend);
                
                received = dis.readUTF();
                System.out.println(received);
                
                tosend = scn.nextLine();
                dos.writeUTF(tosend);
                
                received = dis.readUTF();
                System.out.println(received);
            } 
              
            // closing resources 
            scn.close(); 
            dis.close(); 
            dos.close(); 
        }catch(Exception e){ 
            e.printStackTrace(); 
        } 
    }
    
  
    
   

   
    
    
  
} 