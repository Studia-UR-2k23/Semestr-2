package last_kolos.Zadanie2;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
public class HelloClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket("127.0.0.121", 1064);
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        BufferedReader socketBufferedReader = new BufferedReader(new
                InputStreamReader(socket.getInputStream()));
        output.writeBytes("Hello Karol " + "\n");
        output.flush();
        String response = socketBufferedReader.readLine();
        System.out.println("Sever responsed: " + response);
        output.writeBytes("Thank you 106421!\n");
        output.flush();
        output.close();
        socketBufferedReader.close();
        socket.close();
    }
}