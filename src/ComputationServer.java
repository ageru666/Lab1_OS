package src;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
public class ComputationServer {
    private static final SharedResourceWithLock sharedResource = new SharedResourceWithLock();
    private static final long TIME_LIMIT_NANOSECONDS = TimeUnit.SECONDS.toNanos(100); // 10 секунд в наносекундах
    private static boolean fReceived = false;
    private static boolean gReceived = false;
    private static final Logger logger = Logger.getLogger(ComputationServer.class.getName());

    public static void main(String[] args) {
        int port = 1234;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("Server is listening on port " + port);

            while (!(fReceived && gReceived)) {
                long startTime = System.nanoTime();

                ComputationData data = acceptNumber(serverSocket);
                if (data == null) continue;

                if ("f".equals(data.functionId()) && !fReceived) {
                    fReceived = true;
                    sharedResource.addF(data.value());
                } else if ("g".equals(data.functionId()) && !gReceived) {
                    gReceived = true;
                    sharedResource.addG(data.value());
                }

                long duration = System.nanoTime() - startTime;
                if (duration > TIME_LIMIT_NANOSECONDS) {
                    logger.warning("Warning: Processing time exceeded the limit!");
                }

                logger.info("Current sum of results: " + sharedResource.getSum());
            }

            logger.info("Both f(x) and g(x) received. No longer accepting new connections.");
        } catch (IOException e) {
            logger.warning("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static ComputationData acceptNumber(ServerSocket serverSocket) {
        try (Socket socket = serverSocket.accept();
             ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {
            return (ComputationData) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.warning("Error receiving data: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
