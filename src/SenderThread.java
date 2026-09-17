import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class SenderThread extends Thread{
    Socket connectionSocket;

    public SenderThread(Socket connectionSocket) {
        this.connectionSocket = connectionSocket;
    }

    DataOutputStream write;
    BufferedReader outPrint = new BufferedReader(new InputStreamReader(System.in));

    public void run() {
        try {
            write = new DataOutputStream(connectionSocket.getOutputStream());

            while (true) {
                String sentence = outPrint.readLine();
                write.writeBytes(sentence + "\n");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
