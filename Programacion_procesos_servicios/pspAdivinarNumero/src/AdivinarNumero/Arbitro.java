package AdivinarNumero;

public class Arbitro {
    public int numeroSecreto = 1 + (int) (10 * Math.random());
    private int turno = 1;
    private boolean fin = false;

    public synchronized void jugar(int jugador, int intento) {
        // Si no es tu turno, espera
        while (jugador != turno && !fin) {
            try {
                wait();
            } catch (InterruptedException e) {
                return;
            }
        }

        if (fin) return;

        System.out.println("Jugador" + jugador + " dice: " + intento + "\n");

        if (intento == numeroSecreto) {
            fin = true;
            System.out.println("El jugador " + jugador + " ha ganado!");
            notifyAll();
            return;
        }

        turno = (turno % 3) + 1; // Si hay 3 jugadores
        System.out.println("Le toca a Jug" + turno);
        notifyAll();
    }

    public boolean seAcabo() {
        return fin;
    }
}