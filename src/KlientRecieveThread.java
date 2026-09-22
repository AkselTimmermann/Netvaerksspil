import java.io.*;
import java.net.*;

public class KlientRecieveThread extends Thread{
    Socket connSocket;
    public KlientRecieveThread(Socket connSocket){this.connSocket=connSocket;}
    public void run(){
        try{
            BufferedReader recieveReader=new BufferedReader(new InputStreamReader(connSocket.getInputStream()));
            String recieveSentence;
            while((recieveSentence=recieveReader.readLine())!=null){
                System.out.println("Fra server: "+recieveSentence);
            }
        }catch(Exception e){
            System.out.println("Forbindelsen til serveren blev lukket");
        }
    }
}