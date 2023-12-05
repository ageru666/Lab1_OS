package src;

import java.io.*;
import java.net.Socket;
import java.net.ConnectException;
public class ComputationClient {
    public static void sendNumber(String host, int port, double number, String functionId) {
        try (Socket socket = new Socket(host, port);
             ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream())) {

            output.writeObject(new ComputationData(number, functionId));
            output.flush();

        } catch (ConnectException e) {
            System.out.println("Не вдалося підключитися до сервера: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Помилка I/O: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
