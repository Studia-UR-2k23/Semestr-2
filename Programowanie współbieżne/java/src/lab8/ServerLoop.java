package lab8;

import sun.misc.Signal;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerLoop {
    Socket connection;
    ServerSocket serverSocket;
    public ServerLoop() throws IOException {
        serverSocket = new ServerSocket(6000);
        connection = null;
    }
    public void close() throws IOException {
        if (connection != null) {
            this.connection.close();
        }
        if (serverSocket != null) {
            serverSocket.close();
        }
    }
    public static void main(String[] args) throws IOException {
        ServerLoop sl = new ServerLoop();
        System.out.println("Started server at: " + sl.serverSocket.getLocalSocketAddress());
        System.out.println(sl.serverSocket.getInetAddress());
        AtomicBoolean serverStopped = new AtomicBoolean(false);
        AtomicBoolean connectionNonEnded = new AtomicBoolean(true);
        Signal.handle(new Signal("INT"),
                signal ->
                {
                    System.out.println("Interrupted by Ctrl+C");
                    connectionNonEnded.set(false);
                    serverStopped.set(true);
                    try {
                        if (sl.connection != null) {
                            sl.connection.shutdownInput();
                            sl.connection.shutdownOutput();
                            sl.connection.close();
                        }
                        sl.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("stopped!");
                });
        while (!serverStopped.get()) {
            try {
                sl.connection = sl.serverSocket.accept();
                System.out.println("Accepted connection from: " + sl.connection.getLocalSocketAddress());
                connectionNonEnded.set(true);
            } catch (IOException e) {
                System.out.println("Server was stopped by user!");
            }
            DataOutputStream dataOutputStream = new DataOutputStream(sl.connection.getOutputStream());
            dataOutputStream.writeBytes("Accepted connection" + System.lineSeparator());
            dataOutputStream.flush();
            BufferedReader br = new BufferedReader(new InputStreamReader(sl.connection.getInputStream()));
            while (connectionNonEnded.get()) {
                if (serverStopped.get()) {
                    System.out.println("Server stopped");
                    break;
                }
                if (sl.connection.isClosed() || sl.connection.isInputShutdown()) {
                    System.out.println("Client closed connection!");
                    break;
                }
                String request;
                try {
                    request = br.readLine();
                } catch (SocketException e) {
                    System.out.println("Client closed connection!");
                    break;
                }
                if (request == null) {
                    break;
                }
                switch (request) {
                    case "all files": {
                        StringBuilder stringBuilder = new StringBuilder();
                        String[] txts = new File(".").list((file, s) -> s.endsWith(".txt"));
                        if (txts != null && txts.length > 0) {
                            System.out.println("There are txts");
                            for (String f : txts) {
                                stringBuilder.append(f);
                                stringBuilder.append(", ");
                            }
                            System.out.println(stringBuilder.toString());
                            dataOutputStream.writeBytes(stringBuilder.toString() + System.lineSeparator());
                            dataOutputStream.flush();
                        } else {
                            dataOutputStream.writeBytes("No files found!" + System.lineSeparator());
                            dataOutputStream.flush();
                        }
                    }
                    break;
                    case "new file": {
                        try {
                            String fileName = br.readLine();
                            String fileContent = br.readLine();
                            FileWriter fw = new FileWriter(new File(fileName));
                            fw.write(fileContent);
                            fw.close();
                            dataOutputStream.writeBytes("successfully transfered file" +
                                    System.lineSeparator());
                            dataOutputStream.flush();
                        } catch (SocketException e) {
                            System.out.println("Client closed connection!");
                        }
                    }
                    break;
                    case "end": {
                        dataOutputStream.close();
                        br.close();
                        sl.connection.close();
                        connectionNonEnded.set(false);
                    }
                    break;
                    default: {
                        System.out.println("Bad request " + request);
                        for (char c : request.toCharArray())
                            System.out.println((int) c);
                        dataOutputStream.writeBytes("bad request!" + System.lineSeparator());
                        dataOutputStream.flush();
                    }
                }
            }
        }
    }
}
