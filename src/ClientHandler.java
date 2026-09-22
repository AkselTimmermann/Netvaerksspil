import java.io.*;
import java.net.*;

public class ClientHandler{
    Socket connSocket;
    Player player;
    DataOutputStream sendStream;

    public ClientHandler(Socket connSocket,Player player)throws IOException{
        this.connSocket=connSocket;
        this.player=player;
        this.sendStream=new DataOutputStream(connSocket.getOutputStream());
    }

    public synchronized void sendMessage(String message)throws IOException{
        sendStream.writeBytes(message+'\n');
        sendStream.flush();
    }

    public Socket getConnSocket(){
        return connSocket;
    }

    public Player getPlayer(){
        return player;
    }
}