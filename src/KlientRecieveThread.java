import javafx.application.Platform;

import java.io.*;
import java.net.*;

public class KlientRecieveThread extends Thread{
    Socket connSocket;
    GUI gui;

    public KlientRecieveThread(Socket connSocket, GUI gui){this.connSocket=connSocket; this.gui = gui;}
    public void run(){
        try{
            BufferedReader recieveReader=new BufferedReader(new InputStreamReader(connSocket.getInputStream()));
            String player = recieveReader.readLine();

            Platform.runLater(() -> gui.addPlayer(player));
                String recieveSentence;
                while((recieveSentence = recieveReader.readLine())!=null){
                    System.out.println("Fra server: "+recieveSentence);

                }
        }catch(Exception e){
            System.out.println("Forbindelsen til serveren blev lukket");
        }
    }
}