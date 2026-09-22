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

            String message;

            while ((message = recieveReader.readLine()) != null) {
                if (message.startsWith("PLAYER:")) {
                    String playerInfo = message.substring(7);
                     Platform.runLater(() -> gui.addPlayer(playerInfo));
                }
                if (message.startsWith("MOVE:")) {
                    String playerInfo = message.substring(5);
                    Platform.runLater(() ->gui.updatePlayer(playerInfo));
                }
            }
        }catch(Exception e){
            System.out.println("Forbindelsen til serveren blev lukket");
        }
    }
}