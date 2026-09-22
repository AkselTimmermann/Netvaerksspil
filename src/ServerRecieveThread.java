import java.io.*;
import java.net.*;
import java.util.*;

public class ServerRecieveThread extends Thread{
    Socket connSocket;
    String playerName;
    private List<ClientHandler> clientHandlers;
    public ServerRecieveThread(Socket connSocket,List<ClientHandler> clientHandlers){this.connSocket=connSocket;this.clientHandlers=clientHandlers;}
    public void run(){
        ClientHandler clientHandler=null;
        try{
            BufferedReader recieveReader=new BufferedReader(new InputStreamReader(connSocket.getInputStream()));
            playerName=recieveReader.readLine();
            if(playerName==null){return;}
            System.out.println("Modtaget navn: "+playerName);
            Player player=new Player(playerName,clientHandlers.size(),clientHandlers.size(),"right");
            clientHandler=new ClientHandler(connSocket,player);
            clientHandlers.add(clientHandler);

            String playerInfo = player.name + ":" + player.getXpos() + ":" + player.getYpos() + ":" + player.getDirection();
            for (ClientHandler cl1: clientHandlers) {
                cl1.sendMessage(playerInfo);
            }

            String recieveSentence;
            while((recieveSentence=recieveReader.readLine())!=null){
                System.out.println("Modtaget fra "+playerName+": "+recieveSentence);
                synchronized(clientHandlers){
                    for(ClientHandler cl:clientHandlers){
                        try{
                            cl.sendMessage(recieveSentence);
                        }catch(Exception e){
                            System.out.println("Kunne ikke sende besked til "+cl.getPlayer().name);
                        }
                    }
                }
            }
        }catch(Exception e){
            System.out.println("Forbindelsen til "+playerName+" blev afbrudt");
        }finally{
            if(clientHandler!=null){clientHandlers.remove(clientHandler);}
            try{
                connSocket.close();
            }catch(Exception e){e.printStackTrace();}
            System.out.println(playerName+" forlod serveren");
        }
    }
}