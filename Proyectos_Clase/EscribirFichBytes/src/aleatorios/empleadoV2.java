package aleatorios;

import java.util.Locale;

public class empleadoV2 {

    private int id;
    private String apellido;
    private int dept;
    private double salario;

    public empleadoV2(int id, String apellido, int dept, double salario) {
        this.id = id;
        this.apellido = apellido;
        this.dept = dept;
        this.salario = salario;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "Id: %-3d, Apellido: %-9s, Dept: %-3d, Salario: %-12.2f%n"
                , id, apellido, dept, salario);
    }
}
