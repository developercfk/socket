package servidor;

// Java implementation of  Server side 
// It contains two classes : Server and ClientHandler 
// Save file as Server.java 
  
import java.io.*; 
import java.text.*; 
import java.util.*; 
import java.net.*; 
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
  
// Server class 
public class Server{ 
    
    
    public static void main(String[] args) throws IOException{ 
        // server is listening on port 5056 
        ServerSocket servidor = new ServerSocket(5056); 
        Semaphore semaforo = new Semaphore(2); 
        // running infinite loop for getting 
        // s request 
        while (true){ 
            Socket s = null; 
              
            try { 
                // socket object to receive incoming s requests 
                s = servidor.accept(); 
                
                // Se instancia una clase para atender al cliente y se lanza en
               // un hilo aparte.
        
                  
                System.out.println("A new client is connected : " + s); 
                  
                // obtaining input and out streams 
                DataInputStream dis = new DataInputStream(s.getInputStream()); 
                DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
                  
                System.out.println("Assigning new thread for this client"); 
  
                // create a new thread object 
               
           
                Thread t1 = new ClientHandler(s, dis, dos,semaforo ); 
              
  
                // Invoking the start() method 
                t1.start();
            
                  
            } 
            catch (Exception e){ 
                s.close(); 
                e.printStackTrace(); 
            } 
        } 
    } 
} 
  
// ClientHandler class 
class ClientHandler extends Thread  
{ 
    DateFormat fordate = new SimpleDateFormat("yyyy/MM/dd"); 
    DateFormat fortime = new SimpleDateFormat("hh:mm:ss"); 
    final DataInputStream dis; 
    final DataOutputStream dos; 
    final Socket client; 
    Semaphore semaforo;
   
 
      
  
    // Constructor 
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos,Semaphore semaforo)  {
        this.client = s; 
        this.dis = dis; 
        this.dos = dos; 
        this.semaforo = semaforo;
        
      
    } 
      
      
    
  
    @Override
    public void run(){ 
     
            
            String received;
            String toreturn;
            
            
    try{ 
            
           
           
          semaforo.acquire();
            
            while (true){
               
                 // Ask user what he wants
                 dos.writeUTF("What do you want?[Date | Time | Name |Random xy]..\n"+
                    "Type Exit to terminate connection.");
                    // receive the answer from s
                    received = dis.readUTF();
                  
                    
                if(received.equals("Exit"))
                    {
                        System.out.println("Client " + this.client + " sends exit...");
                        System.out.println("Closing this connection.");
                        this.client.close();
                        semaforo.release();
                        System.out.println("Connection closed");
                        break;
                    }
                    
                    
                    // creating Date object
                    Date date = new Date();
                    
                    // write on output stream based on the
                    // answer from the s
                    switch (received) {
                        
                        case "Date" :
                            toreturn = fordate.format(date);
                            dos.writeUTF(toreturn);
                            break;
                            
                        case "Time" :
                            toreturn = fortime.format(date);
                            dos.writeUTF(toreturn);
                            break;
                        case "Name" :
                            toreturn = "Willy Christian";
                            dos.writeUTF(toreturn);
                            break;
                            
                        case "Random xy" :
                             Random random = new Random();
                             String str1;//str para guardar las consultas
                             String str2;//str para guardar las consultas
                             
                            toreturn = "Enter el primero numero : ";
                            dos.writeUTF(toreturn);//envia la primera consulta
                            received = dis.readUTF();//recive de la repuesta 1
                            str1 = received;//guarda de la repuesta 1
                            
                            
                            toreturn = "Enter el segundo numero : "; 
                            dos.writeUTF(toreturn);//envia la segunda consulta
                            received = dis.readUTF();//recive la repuesta 2
                            str2 = received;//guarda de la repuesta 2
                            
                            int num1 = Integer.parseInt(str1); //convierte al entero
                            int num2 = Integer.parseInt(str2); //convierte a entero
                            
                            //calculos
                            int resultado;
                            if(num1 > num2){
                                
                                
                                resultado = num2+random.nextInt(num1-num2);
                            }else{
                                
                                resultado = num1+random.nextInt(num2-num1);
                            }
                            
                            toreturn = "Numero aleatorio is : " + resultado;
                            dos.writeUTF(toreturn);
                            break;
                     
                            
                        default:
                            dos.writeUTF("Invalid input");
                            break;
                    }
                    
                    
                    
               
            }
           
           
            
            try
            {
                // closing resources
                this.dis.close();
                this.dos.close();
                
            }catch(IOException e){
                e.printStackTrace();
            }
        }catch(IOException ex){ 
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex); 
        } catch (InterruptedException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        } 
    } 
} 
