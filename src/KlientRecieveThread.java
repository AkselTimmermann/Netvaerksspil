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


                String recieveSentence;
                while((recieveSentence = recieveReader.readLine())!=null){
                    if (recieveSentence.startsWith("Modtaget")) {
                        String finalRecieveSentence = recieveSentence;
                        Platform.runLater(() -> gui.addPlayer(finalRecieveSentence));
                    } else {
                        System.out.println("Fra server: "+recieveSentence);
                    }
                }
        }catch(Exception e){
            System.out.println("Forbindelsen til serveren blev lukket");
        }
    }
}