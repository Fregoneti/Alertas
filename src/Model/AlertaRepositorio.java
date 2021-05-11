package Model;

import java.util.Set;
import java.util.TreeSet;

public class AlertaRepositorio {
    private static AlertaRepositorio instance = null;
    public Set<Alerta> alerta;

    private AlertaRepositorio() {
        alerta = new TreeSet<>();
    }

    public static AlertaRepositorio getInstance() {
        if (instance == null) {
            instance = new AlertaRepositorio();
        }
        return instance;
    }
}
