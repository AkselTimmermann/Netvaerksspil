import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class CentralKnudeServer {
    private static ArrayList<Player> players = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket welcomeSocket = new ServerSocket(6789);
        System.out.println("Serveren venter på spillere");

        while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            ServerRecieveThread serverRecieveThread = new ServerRecieveThread(connectionSocket, players);

            serverRecieveThread.start();
        }
    }
}
