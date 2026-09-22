import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class KlientRecieveThread extends Thread {
    Socket connSocket;
    GUI gui;

    public KlientRecieveThread(Socket connSocket, GUI gui) {
        this.connSocket = connSocket;
        this.gui = gui;
    }

    public void run() {
        try {
            BufferedReader recieveReader = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));
            String player = recieveReader.readLine();

            Platform.runLater(() -> gui.addPlayer(player));

            while (true) {
                String recieveSentence = recieveReader.readLine();
                System.out.println("Fra server: " + recieveSentence);

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
