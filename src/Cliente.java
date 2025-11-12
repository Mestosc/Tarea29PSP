import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
        InetSocketAddress dir = new InetSocketAddress("localhost", 8000);
        Operaciones operacion;
        try (Socket socket = new Socket()) {
            socket.connect(dir);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            //ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            MensajeCalc<Double> operar = new MensajeCalc<>(Operaciones.MULTIPLICACION,4.12,2.12);
            objectOutputStream.writeObject(operar);
            System.out.println(bufferedReader.readLine());
        } catch (IOException e) {
            System.out.println();
//        } catch (ClassNotFoundException e) {
//            System.out.println("Clase no localizada");
//        }
        }
    }
}