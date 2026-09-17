import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    Socket connSocket;
    Player player;

    public ClientHandler(Socket connSocket, Player player) {
        this.connSocket = connSocket;
        this.player = player;
    }

    public void sendMessage(String message) throws IOException {
        DataOutputStream sendStream = new DataOutputStream(connSocket.getOutputStream());
        sendStream.writeBytes(message + '\n');
    }

    public Socket getConnSocket() {
        return connSocket;
    }

    public Player getPlayer() {
        return player;
    }
}
