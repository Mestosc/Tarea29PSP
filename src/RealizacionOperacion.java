import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class RealizacionOperacion extends Thread {
    Socket socket;
    public RealizacionOperacion(Socket socket) {
        this.socket = socket;
    }
    public static Double minimoComunMultiplo(Double num1, Double num2) {
        double mcd = maximoComunDivisor(num1,num2);
        return Math.abs(num1*num2) / mcd;
    }
    public static Double maximoComunDivisor(Double num1, Double num2) {
        while (num2 != 0) {
            double temp = num2;
            num2 = num1 % num2;
            num1 = temp;
        }
        return num1;
    }
    @Override
    public void run() {
        try (ObjectInputStream ob = new ObjectInputStream(socket.getInputStream());
             PrintWriter writer = new PrintWriter(socket.getOutputStream())) {
            Object obj = ob.readObject();
            if (obj instanceof MensajeCalc<?> mensajeCalc) {
                Operaciones operacion = mensajeCalc.getOperacion();
                Number numero1 = mensajeCalc.getOperando1();
                Number number2 = mensajeCalc.getOperando2();
                Double resultado = switch (operacion) {
                    case SUMA -> numero1.doubleValue()+number2.doubleValue();
                    case RESTA -> numero1.doubleValue()-number2.doubleValue();
                    case MULTIPLICACION -> numero1.doubleValue()*number2.doubleValue();
                    case DIVISION -> numero1.doubleValue()/number2.doubleValue();
                    case MCD -> maximoComunDivisor(numero1.doubleValue(),number2.doubleValue());
                    case MCM -> minimoComunMultiplo(numero1.doubleValue(),number2.doubleValue());
                };
                writer.println(operacion+": "+numero1+" y "+number2+" = "+resultado);
                }
        } catch (IOException e) {
            System.out.println("Problema a nivel del socket");
        } catch (ClassNotFoundException e) {
            System.out.println("No existe la clase asegurate de tenerla");
        }
    }
}
