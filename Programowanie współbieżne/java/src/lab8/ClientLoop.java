package lab8;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

public class ClientLoop {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 6000);
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        BufferedReader socketBufferedReader = new BufferedReader(new
                InputStreamReader(socket.getInputStream()));
        System.out.println(socketBufferedReader.readLine());
        while (true) {
            try {
                output.writeBytes("all files" + System.lineSeparator());
                output.flush();
                String response = socketBufferedReader.readLine();
                System.out.println("Sever responsed: " + response);

                output.writeBytes("new file" + System.lineSeparator());
                output.flush();
                output.writeBytes("nowy.txt" + System.lineSeparator());
                output.flush();
                output.writeBytes("This is a content of a file, which will be send to a server!" +
                        System.lineSeparator());
                output.flush();
                response = socketBufferedReader.readLine();
                System.out.println("Server responded: " + response);
            } catch (SocketException e) {
                System.out.println("Serwer closed a connection!");
                break;
            }
        }
    }
}
