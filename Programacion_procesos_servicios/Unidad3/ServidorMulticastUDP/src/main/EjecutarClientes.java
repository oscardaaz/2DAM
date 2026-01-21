package main;

public class EjecutarClientes {
    public static void main(String[] args) {
        System.out.println("Iniciando múltiples clientes...\n");

        // Crear un hilo para cada cliente
        Thread cliente1 = new Thread(new ClienteRunnable("Cliente 1"));
        Thread cliente2 = new Thread(new ClienteRunnable("Cliente 2"));
        Thread cliente3 = new Thread(new ClienteRunnable("Cliente 3"));

        cliente1.start();
        cliente2.start();
        cliente3.start();
    }
}

class ClienteRunnable implements Runnable {
    private String nombre;

    public ClienteRunnable(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        System.out.println(nombre + " iniciando...");
        ClienteMulticast.main(new String[]{nombre});
    }
}