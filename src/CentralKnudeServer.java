import java.net.ServerSocket;
import java.net.Socket;

public class CentralKnudeServer {
    public static void main(String[] args) throws  Exception {
        ServerSocket serverSocket = new ServerSocket(6789);
        System.out.println("Venter på client");

        Socket connectionSocket = serverSocket.accept();
        System.out.println("Forbundet");
            (new ReceiverThread(connectionSocket)).start();
            (new SenderThread(connectionSocket)).start();
    }
}
