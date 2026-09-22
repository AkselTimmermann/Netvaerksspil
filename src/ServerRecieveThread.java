import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class ServerRecieveThread extends Thread {
    Socket connSocket;
    String playerName;
    private ArrayList<ClientHandler> clientHandlers;


    public ServerRecieveThread(Socket connSocket, ArrayList<ClientHandler> clientHandlers) {
        this.connSocket = connSocket;
        this.clientHandlers = clientHandlers;
    }

    public void run() {
        try {
            BufferedReader recieveReader = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));

            playerName = recieveReader.readLine();
            System.out.println("Modtaget navn: " + playerName);
            Player player = new Player(playerName, clientHandlers.size(), clientHandlers.size(), "right");
            ClientHandler clientHandler = new ClientHandler(connSocket, player);
            clientHandlers.add(clientHandler);

            String playerInfo = player.name + ":" + player.getXpos() + ":" + player.getYpos() + ":" + player.getDirection();

            for (ClientHandler cl1: clientHandlers) {
                cl1.sendMessage(playerInfo);
            }

            while (true) {
                String recieveSentence = recieveReader.readLine();
                System.out.println("Modtaget fra " + playerName + ": " + recieveSentence);
                for (ClientHandler cl2 : clientHandlers) {
                    cl2.sendMessage(recieveSentence);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
