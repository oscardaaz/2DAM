package AdivinarNumero;

public class Main {
    public static void main(String[] args) {
        Arbitro arbitro = new Arbitro();
        for (int i = 1; i <= 3; i++) new Jugador(i, arbitro).start();
        System.out.println("\nEl numero a acertar es: " + arbitro.numeroSecreto + "\n");
    }
}