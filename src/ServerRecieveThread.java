import java.io.*;
import java.net.*;
import java.util.*;

public class ServerRecieveThread extends Thread {
    Socket connSocket;
    String playerName;
    private List<ClientHandler> clientHandlers;
    private int[][] pladser = {{1, 1},
            {1, 18},
            {18, 1},
            {18, 18}};

    public ServerRecieveThread(Socket connSocket, List<ClientHandler> clientHandlers) {
        this.connSocket = connSocket;
        this.clientHandlers = clientHandlers;
    }

    public void run() {
        ClientHandler clientHandler = null;
        try {
            BufferedReader recieveReader = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));
            playerName = recieveReader.readLine();
            if (playerName == null) {
                return;
            }

            Player player = null;
            synchronized (clientHandlers) {
                for (int i = 0; i < pladser.length; i++) {
                    int x = pladser[i][0];
                    int y = pladser[i][1];

                    boolean optaget = false;

                    for (ClientHandler cl : clientHandlers) {
                        Player p = cl.getPlayer();

                        if (p.getXpos() == x && p.getYpos() == y) {
                            optaget = true;
                            break;
                        }
                    }
                    if (!optaget) {
                        System.out.println("Modtaget navn: " + playerName);
                        player = new Player(playerName, x, y, "right");
                        clientHandler = new ClientHandler(connSocket, player);
                        break;
                    }
                }
                if (player == null) {
                    return;
                }


                for (ClientHandler clientHandler1 : clientHandlers) {
                    Player p = clientHandler1.getPlayer();
                    String info = "PLAYER:" + p.name + ":" + p.getXpos() + ":" + p.getYpos() + ":" + p.getDirection();
                    clientHandler.sendMessage(info);
                }
                clientHandlers.add(clientHandler);


                String playerInfo = "PLAYER:" + player.name + ":" + player.getXpos() + ":" + player.getYpos() + ":" + player.getDirection();
                for (ClientHandler cl1 : clientHandlers) {
                    cl1.sendMessage(playerInfo);
                }
            }


            String recieveSentence;
            while ((recieveSentence = recieveReader.readLine()) != null) {

                if (recieveSentence.startsWith("MOVE:")) {
                    String direction = recieveSentence.substring(5);

                    List<ClientHandler> modtagere;
                    String moveInfo = null;

                    synchronized (clientHandlers) {

                        int x = player.getXpos();
                        int y = player.getYpos();

                        switch (direction) {
                            case "up":
                                y--;
                                break;
                            case "down":
                                y++;
                                break;
                            case "left":
                                x--;
                                break;
                            case "right":
                                x++;
                                break;
                            default:
                                continue;
                        }

                        boolean pladsOptaget = false;


                        for (ClientHandler cl : clientHandlers) {
                            Player andenPlayer = cl.getPlayer();

                            if (andenPlayer != player
                                    && andenPlayer.getXpos() == x
                                    && andenPlayer.getYpos() == y) {

                                pladsOptaget = true;
                                break;
                            }
                        }

                        if (!pladsOptaget) {
                            player.setXpos(x);
                            player.setYpos(y);
                            player.setDirection(direction);

                            moveInfo = "MOVE:" + player.name + ":" + player.getXpos() + ":" + player.getYpos() + ":" + player.getDirection();


                        }
                        modtagere = new ArrayList<>(clientHandlers);

                    }

                    if (moveInfo != null) {
                        for (ClientHandler cl : modtagere) {
                            try {
                                cl.sendMessage(moveInfo);
                            } catch (IOException e) {
                                System.out.println("Kunne ikke sende besked til " + cl.getPlayer().name);
                            }
                        }
                    }
                }

            }

        } catch (Exception e) {
            System.out.println("Forbindelsen til " + playerName + " blev afbrudt");
        } finally {
            if (clientHandler != null) {
                clientHandlers.remove(clientHandler);
            }
            try {
                connSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(playerName + " forlod serveren");
        }
    }
}