import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class ServerRecieveThread extends Thread {
    Socket connSocket;
    String playerName;
    private ArrayList<Player> players;


    public ServerRecieveThread(Socket connSocket, ArrayList<Player> players) {
        this.connSocket = connSocket;
        this.players = players;
    }

    public void run() {
        try {
            BufferedReader recieveReader = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));
            DataOutputStream ekkoMessage = new DataOutputStream(connSocket.getOutputStream());
            System.out.println("Modtaget navn: ");
            playerName = recieveReader.readLine();
            Player player = new Player(playerName, players.size(), players.size(), "right");
            players.add(player);

            while (true) {
                String recieveSentence = recieveReader.readLine();
                System.out.println("Modtaget fra " + player + ": " + recieveSentence);
                ekkoMessage.writeBytes("tilbage fra servern: " + recieveSentence + '\n');
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
