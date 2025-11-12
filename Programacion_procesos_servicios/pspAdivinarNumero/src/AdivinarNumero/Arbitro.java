package AdivinarNumero;

public class Arbitro {
    private int numeroSecreto = 1 + (int) (10 * Math.random());
    private int turno = 1;
    private boolean fin = false;

    public synchronized boolean jugar(int jugador, int intento) {
        // Si no es tu turno, espera
        while (jugador != turno && !fin) {
            try {
                wait();
            } catch (InterruptedException e) {
                return false;
            }
        }

        if (fin) return false;

        System.out.println("Jugador" + jugador + " dice: " + intento);

        if (intento == numeroSecreto) {
            fin = true;
            System.out.println("Jugador " + jugador + " GANA!");
            notifyAll();
            return true;
        }

        turno = (turno % 3) + 1; // Si hay 3 jugadores
        System.out.println("Le toca a Jug" + turno);
        notifyAll();
        return false;
    }

    public boolean seAcabo() {
        return fin;
    }
}