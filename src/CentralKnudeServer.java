import java.io.*;
import java.net.*;
import java.util.*;

public class CentralKnudeServer{
    private static List<ClientHandler> players=Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args)throws IOException{
        ServerSocket welcomeSocket=new ServerSocket(6789);
        System.out.println("Serveren venter på spillere");

        while(true){
            Socket connectionSocket=welcomeSocket.accept();
            ServerRecieveThread serverRecieveThread=new ServerRecieveThread(connectionSocket,players);
            serverRecieveThread.start();
        }
    }
}