import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
        MensajeCalc<Integer> operarEnteros = new MensajeCalc<>(Operaciones.SUMA, 2, 3);
        InetSocketAddress dir = new InetSocketAddress("localhost", 8000);
        try (Socket socket = new Socket()) {
            socket.connect(dir);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            //ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream.writeObject(operarEnteros);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String linea = bufferedReader.readLine();
            System.out.println(linea);
        } catch (IOException e) {
            System.out.println();
//        } catch (ClassNotFoundException e) {
//            System.out.println("Clase no localizada");
//        }
        }
    }
}