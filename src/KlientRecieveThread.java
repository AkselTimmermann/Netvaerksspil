import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class KlientRecieveThread extends Thread {
    Socket connSocket;

    public KlientRecieveThread(Socket connSocket) {
        this.connSocket = connSocket;
    }

    public void run() {
        try {
            BufferedReader recieveReader = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));
            while (true) {
                String recieveSentence = recieveReader.readLine();
                System.out.println("Fra server: " + recieveSentence);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
