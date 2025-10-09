import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class JuegoBolaAvanzado extends JPanel implements ActionListener {

    private static final int ANCHO = 800, ALTO = 600;
    private Timer timer;

    private Barra barra;
    private Bola bola;
    private java.util.List<Bloque> bloques;

    private int puntuacion = 0;
    private int nivel = 1;
    private int maxPuntuacion = 0;
    private final String RECORD_FILE = "records.txt";

    public JuegoBolaAvanzado() {
        setPreferredSize(new Dimension(ANCHO, ALTO));
        setBackground(Color.BLACK);
        setFocusable(true);

        cargarRecord();

        barra = new Barra(ANCHO / 2 - 50, ALTO - 60, 100, 15, ANCHO);
        bola = Bola.crearBolaTipoRandom(ANCHO / 2, ALTO / 2);

        crearBloques();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                barra.keyPressed(e);
            }
            @Override
            public void keyReleased(KeyEvent e) {
                barra.keyReleased(e);
            }
        });

        timer = new Timer(1000 / 60, this);
        timer.start();
    }

    private void crearBloques() {
        bloques = new ArrayList<>();
        int filas = 5;
        int columnas = 10;
        int anchoBloque = 70;
        int altoBloque = 25;
        int margenX = 40;
        int margenY = 50;
        Random rnd = new Random();

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                int durabilidad = rnd.nextInt(3) + 1;
                int x = margenX + j * (anchoBloque + 10);
                int y = margenY + i * (altoBloque + 5);
                bloques.add(new Bloque(x, y, anchoBloque, altoBloque, durabilidad));
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        actualizar();
        repaint();
    }

    private void actualizar() {
        bola.mover();

        // Rebotar en paredes
        if (bola.getX() <= bola.getRadio() || bola.getX() >= ANCHO - bola.getRadio()) {
            bola.rebotarX();
        }
        if (bola.getY() <= bola.getRadio()) {
            bola.rebotarY();
        }

        // Rebotar en barra
        if (bola.getRect().intersects(barra.getRect())) {
            bola.rebotarY();
            puntuacion += 10;
        }

        // Rebotar y destruir bloques
        Iterator<Bloque> it = bloques.iterator();
        while (it.hasNext()) {
            Bloque b = it.next();
            if (b.isVivo() && bola.getRect().intersects(b.getRect())) {
                b.golpear();
                bola.rebotarY();
                puntuacion += 20 * b.getDurabilidadInicial();
                if (!b.isVivo() && puntuacion % 200 == 0) {
                    nivel++;
                    bola.aumentarVelocidad(1);
                }
                break; // Evitar detectar varios bloques al mismo tiempo
            }
        }

        // Mover barra
        barra.mover();

        // Game Over
        if (bola.getY() > ALTO) {
            guardarRecord();
            JOptionPane.showMessageDialog(this, "¡Game Over!\nPuntuación: " + puntuacion);
            System.exit(0);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (Bloque b : bloques) {
            b.dibujar(g2d);
        }
        bola.dibujar(g2d);
        barra.dibujar(g2d);

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("Puntuación: " + puntuacion, 10, 20);
        g2d.drawString("Récord: " + maxPuntuacion, 10, 40);
        g2d.drawString("Nivel: " + nivel, 10, 60);
    }

    private void cargarRecord() {
        try (BufferedReader br = new BufferedReader(new FileReader(RECORD_FILE))) {
            String line = br.readLine();
            if (line != null) {
                maxPuntuacion = Integer.parseInt(line.trim());
            }
        } catch (IOException e) {
            maxPuntuacion = 0;
        }
    }

    private void guardarRecord() {
        if (puntuacion > maxPuntuacion) {
            maxPuntuacion = puntuacion;
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(RECORD_FILE))) {
                bw.write(String.valueOf(maxPuntuacion));
            } catch (IOException e) {
                System.err.println("Error guardando récord");
            }
        }
    }

    // Clases internas

    static class Bola {
        private int x, y;
        private int dx, dy;
        private final int radio;
        private final Color color;

        private Bola(int x, int y, int dx, int dy, int radio, Color color) {
            this.x = x;
            this.y = y;
            this.dx = dx;
            this.dy = dy;
            this.radio = radio;
            this.color = color;
        }

        public static Bola crearBolaTipoRandom(int x, int y) {
            Random rnd = new Random();
            int tipo = rnd.nextInt(3);
            switch (tipo) {
                case 0:
                    return new Bola(x, y, 3, -3, 10, Color.YELLOW);
                case 1:
                    return new Bola(x, y, 4, -4, 12, Color.CYAN);
                default:
                    return new Bola(x, y, 2, -2, 8, Color.MAGENTA);
            }
        }

        public void mover() {
            x += dx;
            y += dy;
        }

        public void rebotarX() {
            dx = -dx;
        }

        public void rebotarY() {
            dy = -dy;
        }

        public void aumentarVelocidad(int incremento) {
            dx += dx > 0 ? incremento : -incremento;
            dy += dy > 0 ? incremento : -incremento;
        }

        public Rectangle getRect() {
            return new Rectangle(x - radio, y - radio, radio * 2, radio * 2);
        }

        public void dibujar(Graphics2D g) {
            g.setColor(color);
            g.fillOval(x - radio, y - radio, radio * 2, radio * 2);
        }

        public int getX() { return x; }
        public int getY() { return y; }
        public int getRadio() { return radio; }
    }

    static class Bloque {
        private final int x, y, ancho, alto;
        private int durabilidad;
        private final int durabilidadInicial;

        public Bloque(int x, int y, int ancho, int alto, int durabilidad) {
            this.x = x;
            this.y = y;
            this.ancho = ancho;
            this.alto = alto;
            this.durabilidad = durabilidad;
            this.durabilidadInicial = durabilidad;
        }

        public void golpear() {
            durabilidad--;
        }

        public boolean isVivo() {
            return durabilidad > 0;
        }

        public Rectangle getRect() {
            return new Rectangle(x, y, ancho, alto);
        }

        public int getDurabilidadInicial() {
            return durabilidadInicial;
        }

        public void dibujar(Graphics2D g) {
            if (!isVivo()) return;

            switch (durabilidad) {
                case 3: g.setColor(new Color(139, 0, 0)); break;
                case 2: g.setColor(new Color(255, 140, 0)); break;
                default: g.setColor(new Color(255, 215, 0)); break;
            }

            g.fillRect(x, y, ancho, alto);
            g.setColor(Color.BLACK);
            g.drawRect(x, y, ancho, alto);
        }
    }

    static class Barra {
        private int x, y;
        private final int ancho, alto;
        private final int limiteAncho;
        private boolean izquierda, derecha;

        public Barra(int x, int y, int ancho, int alto, int limiteAncho) {
            this.x = x;
            this.y = y;
            this.ancho = ancho;
            this.alto = alto;
            this.limiteAncho = limiteAncho;
        }

        public void mover() {
            if (izquierda && x > 0) x -= 7;
            if (derecha && x < limiteAncho - ancho) x += 7;
        }

        public Rectangle getRect() {
            return new Rectangle(x, y, ancho, alto);
        }

        public void dibujar(Graphics2D g) {
            g.setColor(Color.GREEN);
            g.fillRect(x, y, ancho, alto);
        }

        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) izquierda = true;
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) derecha = true;
        }

        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) izquierda = false;
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) derecha = false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Juego Avanzado de la Bola que Destruye Cuadrados");
            JuegoBolaAvanzado juego = new JuegoBolaAvanzado();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(juego);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setVisible(true);
        });
    }
}
