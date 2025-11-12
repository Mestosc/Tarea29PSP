import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        InetSocketAddress dir = new InetSocketAddress("localhost",8000);
        try (ServerSocket serverSocket = new ServerSocket()) {
            while (true) {
                serverSocket.bind(dir);
                Socket socket = serverSocket.accept();
                RealizacionOperacion operacion = new RealizacionOperacion(socket);
                operacion.start();
            }
        } catch (IOException e) {
            System.out.println("Problemas a nivel de socket");
        }
    }
}
