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

                /* if (recieveSentence.startsWith("POINT:")) {

                    String[] pointArray = recieveSentence.split(":");
                    String name = pointArray[1];
                    String points = pointArray[2];
                    synchronized (clientHandlers) {
                        for (ClientHandler cl2 : clientHandlers) {
                            cl2.sendMessage("POINT:" + name + ":" + points);
                        }
                    }
                } */

                if (recieveSentence.startsWith("MOVE:")) {

                    String direction = recieveSentence.substring(5);

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


                        // Væg = -1
                        if (GameBoard.isWall(x, y)) {

                            player.addPoints(-1);

                            String pointInfo = "POINT:" + player.name + ":" + player.point;

                            for (ClientHandler cl : clientHandlers) {
                                try {
                                    cl.sendMessage(pointInfo);
                                } catch (IOException e) {
                                    System.out.println("Kunne ikke sende besked til " + cl.getPlayer().name);
                                }
                            }
                            continue;
                        }

                        // Finder evt. spiller vi er ved at støde ind i
                        Player andenPlayer = null;
                        for (ClientHandler cl : clientHandlers) {

                            Player p = cl.getPlayer();

                            if (p != player && p.getXpos() == x && p.getYpos() == y) {

                                andenPlayer = p;
                                break;
                            }
                        }

                        // Anden spiller = -10/+10
                        if (andenPlayer != null) {
                            player.addPoints(10);
                            andenPlayer.addPoints(-10);

                            String angribendeSpiller = "POINT:" + player.name + ":" + player.point;

                            String ramtSpiller = "POINT:" + andenPlayer.name + ":" + andenPlayer.point;

                            for (ClientHandler cl : clientHandlers) {
                                try {
                                    cl.sendMessage(angribendeSpiller);
                                    cl.sendMessage(ramtSpiller);
                                } catch (IOException e) {
                                    System.out.println("Kunne ikke sende til " + cl.getPlayer().name);
                                }
                            }
                            continue;
                        }

                        // Feltet er frit = +1 og opdater position
                        player.setXpos(x);
                        player.setYpos(y);
                        player.setDirection(direction);

                        player.addPoints(1);

                        String pointInfo = "POINT:" + player.name + ":" + player.point;

                        String moveInfo = "MOVE:" + player.name + ":" + player.getXpos() + ":" + player.getYpos() + ":" + player.getDirection();

                        for (ClientHandler cl : clientHandlers) {
                            try {
                                cl.sendMessage(pointInfo);
                                cl.sendMessage(moveInfo);
                            } catch (IOException e) {
                                System.out.println("Kunne ikke sende til " + cl.getPlayer().name);
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