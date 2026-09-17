import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReceiverThread extends Thread{
    Socket connectionSocket;

    public ReceiverThread(Socket connectionSocket) {
        this.connectionSocket = connectionSocket;
    }

    public void run() {
        String receivedSentence = "";

        try {
            while (true) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                receivedSentence = reader.readLine();
                System.out.println(receivedSentence);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
