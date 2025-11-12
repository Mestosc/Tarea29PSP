package comun;

import java.io.Serializable;

public class MensajeCalc<T extends Number> implements Serializable {
    private Operaciones operacion;
    private T operando1;
    private T operando2;

    public MensajeCalc(Operaciones operacion,T operando1, T operando2) {
        this.operacion = operacion;
        this.operando1 = operando1;
        this.operando2 = operando2;
    }

    public Operaciones getOperacion() {
        return operacion;
    }

    public void setOperacion(Operaciones operacion) {
        this.operacion = operacion;
    }

    public T getOperando1() {
        return operando1;
    }

    public void setOperando1(T operando1) {
        this.operando1 = operando1;
    }

    public T getOperando2() {
        return operando2;
    }

    public void setOperando2(T operando2) {
        this.operando2 = operando2;
    }
}
