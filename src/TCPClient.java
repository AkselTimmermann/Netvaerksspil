import java.io.IOException;
import java.net.Socket;

public class TCPClient {
    public static void main (String[] args) throws Exception, IOException {

            Socket clientSocket = new Socket("10.10.131.88", 6789);
        System.out.println("Forbindelse forbundet");
            (new ReceiverThread(clientSocket)).start();
            (new SenderThread(clientSocket)).start();
    }
}
