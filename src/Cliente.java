import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
        InetSocketAddress dir = new InetSocketAddress("localhost", 8000);
        try (Socket socket = new Socket()) {
            socket.connect(dir);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {
                MensajeCalc<?> mensajeCalc = new MensajeCalc<>(Operaciones.RESTA,23.2,2.12);
                objectOutputStream.writeObject(mensajeCalc);
                System.out.println(bufferedReader.readLine());
            }
        } catch (IOException e) {
            System.out.println();
        }
    }
}
