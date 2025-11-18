package Supermercado;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Bandeja bandeja = new Bandeja(6);
        List<Thread> todosLosHilos = new ArrayList<>();

        // ✅ GUARDAR SOLO LOS ESCRITORES en lista separada
        List<Empleado> empleados = new ArrayList<>();

        // 2 escritores
        for (int i = 1; i <= 2; i++) {
            Empleado empleado = new Empleado(bandeja, 10,"Empleado-" + i);
            empleados.add(empleado);
            todosLosHilos.add(empleado);
            empleado.start();
        }

        // 3 lectores
        for (int i = 1; i <= 3; i++) {
            Cliente cliente = new Cliente(bandeja, "Cliente-" + i);
            todosLosHilos.add(cliente);
            cliente.start();
        }

        // ✅ PRIMERO: Esperar solo a que terminen los ESCRITORES
        for (Empleado empleado : empleados) {
            empleado.join();
        }

        // ✅ SEGUNDO: Avisar que la producción terminó
        System.out.println("📢 Todos los escritores han terminado. No habrá más libros.");
        bandeja.terminarProduccion();

        // ✅ TERCERO: Esperar a que terminen los LECTORES
        for (Thread hilo : todosLosHilos) {
            hilo.join();  // Esto incluye escritores y lectores
        }

        System.out.println("🏪 La tienda ha cerrado");
    }


    }

