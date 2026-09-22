import java.io.*;
import java.net.*;

public class Klient{
    Socket clientSocket;
    DataOutputStream sendStream;

    public Klient(String serverIP,int serverPort,String playerName)throws IOException{
        clientSocket=new Socket(serverIP,serverPort);
        sendStream=new DataOutputStream(clientSocket.getOutputStream());
        sendStream.writeBytes(playerName+'\n');
        sendStream.flush();

        KlientRecieveThread klientRecieveThread=new KlientRecieveThread(clientSocket);
        klientRecieveThread.start();
    }

    public void sendMessage(String message)throws IOException{
        sendStream.writeBytes(message+'\n');
        sendStream.flush();
    }

    public void close()throws IOException{
        clientSocket.close();
    }
}