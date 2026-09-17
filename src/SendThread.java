import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class SendThread extends Thread{
    String sentence;
    Socket connSocket;

    public SendThread(Socket connSocket) {
        this.connSocket = connSocket;
    }

    public void run() {
        try {
            DataOutputStream sendStream = new DataOutputStream(connSocket.getOutputStream());
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                System.out.println("Send en besked: ");
                sentence = inFromUser.readLine();
                sendStream.writeBytes(sentence + '\n');
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
