import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class RealizacionOperacion extends Thread {
    Socket socket;

    public RealizacionOperacion(Socket socket) {
        this.socket = socket;
    }

    private static Number minimoComunMultiplo(Number num1, Number num2) {
        return switch (num1) {
            case Integer i when num2 instanceof Integer -> {
                int mcd = maximoComunDivisor(i,num2).intValue();
                yield Math.abs(i * (Integer) num2) / mcd;
            }
            case Long l when num2 instanceof Long -> {
                long mcd = maximoComunDivisor(l, num2).longValue();
                yield Math.abs(l * (Long) num2) / mcd;
            }
            default -> {
                double mcd = (double) maximoComunDivisor(num1.doubleValue(), num2.doubleValue());
                yield Math.abs(num1.doubleValue() * num2.doubleValue()) / mcd;
            }
        };
    }
    private static Number maximoComunDivisor(Number num1, Number num2) { // Aqui aproveche un poquito la ayuda de la IA para hacer algunas cosas algo más dificiles
        return switch (num1) {
            case Integer i when num2 instanceof Integer -> {
                int a = i;
                int b = (Integer) num2;
                while (b != 0) {
                int temp = b;
                b = a % b;
                a = temp;
                }
                yield a;
            }
            case Long l when num2 instanceof Long -> {
                long a = l;
                long b = (Long) num2;
                while (b != 0) {
                    long temp = b;
                    b = a % b;
                    a = temp;
                }
                yield a;
            }
            case Float l when num2 instanceof Float -> {
                float a = l;
                float b = (Float) num2;
                while (b != 0) {
                    float temp = b;
                    b = a % b;
                    a = temp;
                }
                yield a;
            }
            default -> {
                double a = num1.doubleValue();
                double b = num2.doubleValue();
                while (b != 0) {
                    double temp = b;
                    b = a % b;
                    a = temp;
                }
                yield a;
            }
        };
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
                Number resultado = switch (operacion) {
                    case SUMA -> sumar(numero1,number2);
                    case RESTA -> restar(numero1,number2);
                    case MULTIPLICACION -> multiplicacion(numero1,number2);
                    case DIVISION -> division(numero1,number2);
                    case MCD -> maximoComunDivisor(numero1,number2);
                    case MCM -> minimoComunMultiplo(numero1,number2);
                };
                writer.println(operacion+": "+numero1+" y "+number2+" = "+resultado);
                }
        } catch (IOException e) {
            System.out.println("Problema a nivel del socket");
        } catch (ClassNotFoundException e) {
            System.out.println("No existe la clase asegurate de tenerla");
        }
    }
    private static Number sumar(Number a, Number b) {
        return switch (a) {
            case Integer i when b instanceof Integer -> a.intValue() + b.intValue();
            case Long l when b instanceof Long -> a.longValue() + b.longValue();
            case Float v when b instanceof Float -> a.floatValue() + b.floatValue();
            default -> a.doubleValue() + b.doubleValue();
        };
    }
    private static Number restar(Number a, Number b) {
        return switch (a) {
            case Integer i when b instanceof Integer -> a.intValue() - b.intValue();
            case Long l when b instanceof Long -> a.longValue() - b.longValue();
            case Float v when b instanceof Float -> a.floatValue() - b.floatValue();
            default -> a.doubleValue() - b.doubleValue();
        };
    }
    private static Number multiplicacion(Number a, Number b) {
        return switch (a) {
            case Integer i when b instanceof Integer -> a.intValue() * b.intValue();
            case Long l when b instanceof Long -> a.longValue() * b.longValue();
            case Float v when b instanceof Float -> a.floatValue() * b.floatValue();
            default -> a.doubleValue() * b.doubleValue();
        };
    }
    private static Number division(Number a, Number b) {
        return switch (a) {
            case Integer i when b instanceof Integer -> a.intValue() / b.intValue();
            case Long l when b instanceof Long -> a.longValue() / b.longValue();
            case Float v when b instanceof Float -> a.floatValue() / b.floatValue();
            default -> a.doubleValue() * b.doubleValue();
        };
    }
}
