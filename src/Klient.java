import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Klient {
    static String playerName;

    public static void main(String[] args) throws IOException {
        Socket clientSocket = new Socket("10.10.131.228", 6789);

        System.out.println("Indtast navn på spiller: ");
        DataOutputStream sendStream = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        playerName = inFromUser.readLine();
        sendStream.writeBytes(playerName + '\n');



        SendThread sendThread = new SendThread(clientSocket);
        KlientRecieveThread klientRecieveThread = new KlientRecieveThread(clientSocket);

        sendThread.start();
        klientRecieveThread.start();
    }
}
