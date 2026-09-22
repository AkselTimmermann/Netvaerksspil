import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Klient {
    private String playerName;
    private GUI gui;
    private Socket clientSocket = new Socket("10.10.131.228", 6789);


    public Klient(String playerName, GUI gui) throws IOException {
        this.playerName = playerName;
        this.gui = gui;
    }

    public void connect() throws IOException {

        DataOutputStream sendStream = new DataOutputStream(clientSocket.getOutputStream());
        sendStream.writeBytes(playerName + '\n');

        KlientRecieveThread klientRecieveThread = new KlientRecieveThread(clientSocket, gui);
        klientRecieveThread.start();
    }

    public void sendMessage(String message) throws IOException {
        DataOutputStream sendMessage = new DataOutputStream(clientSocket.getOutputStream());
        sendMessage.writeBytes(message + '\n');
    }
}
