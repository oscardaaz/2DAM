package calculadora;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Calculadora profesional con diseño premium
 */
public class CalculadoraGUI extends JFrame {

    private JTextField displayPrincipal;
    private JLabel displayOperacion;
    private JPanel panelCentral;
    private Calculadora calc;

    private enum Modo { ESTANDAR, CIENTIFICO, PROGRAMADOR }
    private Modo modoActual = Modo.ESTANDAR;

    private enum Tema { CYBER_DARK, OCEAN_BLUE, SUNSET, FOREST, NEON_PURPLE }
    private Tema temaActual = Tema.CYBER_DARK;
    private Map<Tema, ColorScheme> esquemas;

    private JPanel panelHistorial;
    private JTextArea areaHistorial;
    private JPanel panelConversion;
    private JTextField[] camposConversion;
    private JRadioButton[] radiosBases;
    private ButtonGroup grupoBases;

    private StringBuilder entradaActual = new StringBuilder();
    private boolean nuevoCalculo = true;
    private StringBuilder expresionCompleta = new StringBuilder();

    public CalculadoraGUI() {
        calc = new Calculadora();
        esquemas = inicializarEsquemas();

        configurarVentana();
        crearDisplay();
        crearPanelCentral();
        crearPanelHistorial();
        crearMenuSuperior();  // Mover el menú ANTES de cambiar modo

        cambiarModo(Modo.ESTANDAR);
        aplicarTema(temaActual);

        setVisible(true);
    }

    private void configurarVentana() {
        setTitle("Calculadora Premium");
        setSize(920, 720);
        setMinimumSize(new Dimension(450, 550));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0, 0));
    }

    private Map<Tema, ColorScheme> inicializarEsquemas() {
        Map<Tema, ColorScheme> map = new HashMap<>();

        // CYBER DARK - Negro con cyan eléctrico y púrpura
        map.put(Tema.CYBER_DARK, new ColorScheme(
                new Color(18, 18, 24),          // fondo principal
                new Color(30, 30, 42),          // panel display
                new Color(45, 47, 65),          // botón normal
                new Color(0, 255, 255),         // acento cyan brillante
                new Color(255, 255, 255),       // texto blanco
                new Color(255, 71, 125),        // operador rosa neón
                new Color(65, 67, 88),          // hover
                new Color(189, 147, 249),       // secundario púrpura
                new Color(138, 43, 226)         // menuBar violeta
        ));

        // OCEAN BLUE - Azules vibrantes con dorado
        map.put(Tema.OCEAN_BLUE, new ColorScheme(
                new Color(15, 32, 58),          // fondo azul oscuro
                new Color(24, 48, 82),          // panel
                new Color(35, 68, 112),         // botón
                new Color(52, 152, 219),        // acento azul brillante
                new Color(236, 245, 255),       // texto claro
                new Color(241, 196, 15),        // operador dorado
                new Color(52, 85, 125),         // hover
                new Color(46, 204, 113),        // secundario verde agua
                new Color(41, 128, 185)         // menuBar azul medio
        ));

        // SUNSET - Naranjas y morados vibrantes
        map.put(Tema.SUNSET, new ColorScheme(
                new Color(30, 20, 48),          // fondo morado profundo
                new Color(48, 35, 75),          // panel
                new Color(72, 52, 105),         // botón
                new Color(255, 140, 50),        // acento naranja brillante
                new Color(255, 250, 240),       // texto crema
                new Color(255, 85, 85),         // operador rojo coral
                new Color(95, 70, 135),         // hover
                new Color(255, 215, 0),         // secundario dorado
                new Color(156, 39, 176)         // menuBar púrpura vibrante
        ));

        // FOREST - Verdes vibrantes con aqua
        map.put(Tema.FOREST, new ColorScheme(
                new Color(20, 30, 35),          // fondo verde muy oscuro
                new Color(30, 48, 55),          // panel
                new Color(42, 68, 78),          // botón
                new Color(26, 188, 156),        // acento verde mint brillante
                new Color(240, 255, 245),       // texto verdoso claro
                new Color(255, 107, 107),       // operador coral brillante
                new Color(58, 88, 98),          // hover
                new Color(52, 231, 189),        // secundario aqua brillante
                new Color(22, 160, 133)         // menuBar verde esmeralda
        ));

        // NEON PURPLE - Púrpura y magenta ultra vibrante
        map.put(Tema.NEON_PURPLE, new ColorScheme(
                new Color(18, 15, 40),          // fondo púrpura casi negro
                new Color(32, 25, 62),          // panel
                new Color(52, 42, 88),          // botón
                new Color(255, 71, 255),        // acento magenta neón
                new Color(255, 255, 255),       // texto blanco puro
                new Color(255, 51, 153),        // operador rosa neón
                new Color(72, 60, 115),         // hover
                new Color(186, 85, 211),        // secundario violeta medio
                new Color(147, 51, 234)         // menuBar púrpura neón
        ));

        return map;
    }

    private void crearMenuSuperior() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBorderPainted(false);
        menuBar.setBorder(new EmptyBorder(10, 15, 10, 15));

        JMenu menuVer = crearMenu("🔧 Ver");
        JMenuItem miEstandar = crearMenuItem("📱 Estándar", e -> cambiarModo(Modo.ESTANDAR));
        JMenuItem miCientifico = crearMenuItem("🔬 Científica", e -> cambiarModo(Modo.CIENTIFICO));
        JMenuItem miProgramador = crearMenuItem("💻 Programador", e -> cambiarModo(Modo.PROGRAMADOR));

        menuVer.add(miEstandar);
        menuVer.add(miCientifico);
        menuVer.add(miProgramador);

        JMenu menuTema = crearMenu("🎨 Tema");
        JMenuItem miCyber = crearMenuItem("⚡ Cyber Dark", e -> aplicarTema(Tema.CYBER_DARK));
        JMenuItem miOcean = crearMenuItem("🌊 Ocean Blue", e -> aplicarTema(Tema.OCEAN_BLUE));
        JMenuItem miSunset = crearMenuItem("🌅 Sunset", e -> aplicarTema(Tema.SUNSET));
        JMenuItem miForest = crearMenuItem("🌲 Forest", e -> aplicarTema(Tema.FOREST));
        JMenuItem miNeon = crearMenuItem("💜 Neon Purple", e -> aplicarTema(Tema.NEON_PURPLE));

        menuTema.add(miCyber);
        menuTema.add(miOcean);
        menuTema.add(miSunset);
        menuTema.add(miForest);
        menuTema.add(miNeon);

        JMenu menuOpciones = crearMenu("⚙️ Opciones");
        JMenuItem miHistorial = crearMenuItem("📜 Mostrar Historial", e -> toggleHistorial());
        JMenuItem miLimpiarHist = crearMenuItem("🗑️ Limpiar Historial", e -> limpiarHistorial());
        JMenuItem miAcercaDe = crearMenuItem("ℹ️ Acerca de", e -> mostrarAcercaDe());

        menuOpciones.add(miHistorial);
        menuOpciones.add(miLimpiarHist);
        menuOpciones.addSeparator();
        menuOpciones.add(miAcercaDe);

        menuBar.add(menuVer);
        menuBar.add(Box.createHorizontalStrut(5));
        menuBar.add(menuTema);
        menuBar.add(Box.createHorizontalStrut(5));
        menuBar.add(menuOpciones);

        setJMenuBar(menuBar);
    }

    private JMenu crearMenu(String texto) {
        JMenu menu = new JMenu(texto);
        menu.setFont(new Font("Segoe UI", Font.BOLD, 14));
        menu.setOpaque(true);
        menu.setBorderPainted(false);
        return menu;
    }

    private JMenuItem crearMenuItem(String texto, ActionListener listener) {
        JMenuItem item = new JMenuItem(texto);
        item.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        item.setOpaque(true);
        item.setBorderPainted(false);
        item.setBorder(new EmptyBorder(8, 15, 8, 15));
        item.addActionListener(listener);

        // Efecto hover para los items
        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                ColorScheme cs = esquemas.get(temaActual);
                item.setBackground(cs.acento);
                item.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ColorScheme cs = esquemas.get(temaActual);
                item.setBackground(cs.panel);
                item.setForeground(cs.texto);
            }
        });

        return item;
    }

    private void crearDisplay() {
        JPanel panelDisplay = new JPanel();
        panelDisplay.setLayout(new BoxLayout(panelDisplay, BoxLayout.Y_AXIS));
        panelDisplay.setBorder(new EmptyBorder(25, 25, 15, 25));

        // Display de operación completa (arriba)
        displayOperacion = new JLabel(" ");
        displayOperacion.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        displayOperacion.setAlignmentX(Component.RIGHT_ALIGNMENT);
        displayOperacion.setBorder(new EmptyBorder(0, 0, 8, 0));
        displayOperacion.setMinimumSize(new Dimension(100, 30));
        displayOperacion.setPreferredSize(new Dimension(800, 30));

        // Display principal (grande, abajo)
        displayPrincipal = new JTextField("0") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        displayPrincipal.setFont(new Font("Segoe UI", Font.BOLD, 52));
        displayPrincipal.setHorizontalAlignment(SwingConstants.RIGHT);
        displayPrincipal.setEditable(false);
        displayPrincipal.setBorder(new EmptyBorder(15, 20, 15, 20));
        displayPrincipal.setOpaque(false);
        displayPrincipal.setMaximumSize(new Dimension(Integer.MAX_VALUE, 95));

        panelDisplay.add(displayOperacion);
        panelDisplay.add(displayPrincipal);

        add(panelDisplay, BorderLayout.NORTH);
    }

    private void crearPanelCentral() {
        panelCentral = new JPanel(new BorderLayout(12, 12));
        panelCentral.setBorder(new EmptyBorder(10, 25, 25, 25));
        add(panelCentral, BorderLayout.CENTER);
    }

    private void cambiarModo(Modo modo) {
        modoActual = modo;
        panelCentral.removeAll();

        switch(modo) {
            case ESTANDAR:
                setTitle("Calculadora Premium - Estándar");
                crearBotonesEstandar();
                break;
            case CIENTIFICO:
                setTitle("Calculadora Premium - Científica");
                crearBotonesCientifico();
                break;
            case PROGRAMADOR:
                setTitle("Calculadora Premium - Programador");
                crearBotonesProgramador();
                break;
        }

        aplicarTema(temaActual);
        panelCentral.revalidate();
        panelCentral.repaint();
    }

    private void crearBotonesEstandar() {
        JPanel panelBotones = new JPanel(new GridLayout(6, 4, 10, 10));
        panelBotones.setOpaque(false);

        String[][] botones = {
                {"MC", "MR", "M+", "M-"},
                {"C", "CE", "⌫", "÷"},
                {"7", "8", "9", "×"},
                {"4", "5", "6", "-"},
                {"1", "2", "3", "+"},
                {"±", "0", ".", "="}
        };

        for(String[] fila : botones) {
            for(String texto : fila) {
                panelBotones.add(crearBoton(texto, determinarTipoBoton(texto)));
            }
        }

        panelCentral.add(panelBotones, BorderLayout.CENTER);
    }

    private void crearBotonesCientifico() {
        JPanel panelCompleto = new JPanel(new BorderLayout(12, 12));
        panelCompleto.setOpaque(false);

        JPanel panelSuperior = new JPanel(new GridLayout(1, 6, 8, 8));
        panelSuperior.setOpaque(false);
        String[] memBotones = {"MC", "MR", "M+", "M-", "MS", "RAD"};
        for(String texto : memBotones) {
            JButton btn = crearBoton(texto, TipoBoton.MEMORIA);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
            panelSuperior.add(btn);
        }

        JPanel panelBotones = new JPanel(new GridLayout(6, 7, 8, 8));
        panelBotones.setOpaque(false);

        String[][] botones = {
                {"(", ")", "n!", "π", "e", "C", "⌫"},
                {"x²", "√", "x^y", "1/x", "|x|", "÷", "CE"},
                {"sin", "7", "8", "9", "log", "×", "mod"},
                {"cos", "4", "5", "6", "ln", "-", "EXP"},
                {"tan", "1", "2", "3", "10^x", "+", "x³"},
                {"±", "0", ".", "=", "∛", "sinh", "cosh"}
        };

        for(String[] fila : botones) {
            for(String texto : fila) {
                JButton btn = crearBoton(texto, determinarTipoBoton(texto));
                btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
                panelBotones.add(btn);
            }
        }

        panelCompleto.add(panelSuperior, BorderLayout.NORTH);
        panelCompleto.add(panelBotones, BorderLayout.CENTER);

        panelCentral.add(panelCompleto, BorderLayout.CENTER);
    }

    private void crearBotonesProgramador() {
        JPanel panelCompleto = new JPanel(new BorderLayout(12, 12));
        panelCompleto.setOpaque(false);

        JPanel panelConv = new JPanel(new GridLayout(4, 2, 8, 8));
        panelConv.setOpaque(false);
        panelConv.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(80, 80, 100), 2),
                " Bases Numéricas ",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 13)
        ));

        grupoBases = new ButtonGroup();
        radiosBases = new JRadioButton[4];
        camposConversion = new JTextField[4];
        String[] bases = {"BIN", "OCT", "DEC", "HEX"};

        for(int i = 0; i < 4; i++) {
            radiosBases[i] = new JRadioButton(bases[i], i == 2);
            radiosBases[i].setFont(new Font("Segoe UI", Font.BOLD, 12));
            radiosBases[i].setOpaque(false);
            grupoBases.add(radiosBases[i]);
            panelConv.add(radiosBases[i]);

            camposConversion[i] = new JTextField("0");
            camposConversion[i].setEditable(false);
            camposConversion[i].setFont(new Font("Consolas", Font.BOLD, 14));
            camposConversion[i].setBorder(new CompoundBorder(
                    new LineBorder(new Color(60, 60, 80), 1),
                    new EmptyBorder(5, 8, 5, 8)
            ));
            panelConv.add(camposConversion[i]);

            final int index = i;
            radiosBases[i].addActionListener(e -> cambiarBase(index));
        }

        JPanel panelBotones = new JPanel(new GridLayout(6, 6, 8, 8));
        panelBotones.setOpaque(false);

        String[][] botones = {
                {"AND", "OR", "XOR", "NOT", "<<", ">>"},
                {"A", "B", "D", "E", "F", "⌫"},
                {"7", "8", "9", "÷", "(", ")"},
                {"4", "5", "6", "×", "C", "CE"},
                {"1", "2", "3", "-", "mod", "x²"},
                {"±", "0", ".", "+", "=", "MS"}
        };

        for(String[] fila : botones) {
            for(String texto : fila) {
                JButton btn = crearBoton(texto, determinarTipoBoton(texto));
                btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
                panelBotones.add(btn);
            }
        }

        panelCompleto.add(panelConv, BorderLayout.NORTH);
        panelCompleto.add(panelBotones, BorderLayout.CENTER);

        panelCentral.add(panelCompleto, BorderLayout.CENTER);
        actualizarConversiones();
    }

    private void crearPanelHistorial() {
        panelHistorial = new JPanel(new BorderLayout(10, 10));
        panelHistorial.setBorder(new EmptyBorder(25, 0, 25, 25));
        panelHistorial.setPreferredSize(new Dimension(290, 0));
        panelHistorial.setVisible(false);

        JLabel lblTitulo = new JLabel("📜 Historial de Cálculos");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setBorder(new EmptyBorder(0, 0, 12, 0));

        areaHistorial = new JTextArea();
        areaHistorial.setEditable(false);
        areaHistorial.setFont(new Font("Consolas", Font.BOLD, 13));
        areaHistorial.setLineWrap(true);
        areaHistorial.setWrapStyleWord(true);
        areaHistorial.setMargin(new Insets(12, 12, 12, 12));

        JScrollPane scroll = new JScrollPane(areaHistorial);
        scroll.setBorder(new CompoundBorder(
                new LineBorder(new Color(100, 100, 140), 2),
                new EmptyBorder(5, 5, 5, 5)
        ));

        panelHistorial.add(lblTitulo, BorderLayout.NORTH);
        panelHistorial.add(scroll, BorderLayout.CENTER);

        add(panelHistorial, BorderLayout.EAST);
    }

    private void toggleHistorial() {
        panelHistorial.setVisible(!panelHistorial.isVisible());
        actualizarHistorial();
    }

    private void actualizarHistorial() {
        StringBuilder sb = new StringBuilder();
        java.util.List<String> hist = calc.getHistorial();

        for(int i = hist.size() - 1; i >= 0; i--) {
            sb.append(hist.get(i)).append("\n\n");
        }

        areaHistorial.setText(sb.toString());
        areaHistorial.setCaretPosition(0);
    }

    private void limpiarHistorial() {
        calc.limpiarHistorial();
        actualizarHistorial();
        JOptionPane.showMessageDialog(this,
                "✅ Historial limpiado correctamente",
                "Información",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private enum TipoBoton { NUMERO, OPERADOR, FUNCION, MEMORIA, ESPECIAL }

    private TipoBoton determinarTipoBoton(String texto) {
        if(texto.matches("[0-9A-F]")) return TipoBoton.NUMERO;
        if(texto.matches("[+\\-×÷]|mod")) return TipoBoton.OPERADOR;
        if(texto.matches("MC|MR|M\\+|M-|MS")) return TipoBoton.MEMORIA;
        if(texto.equals("=")) return TipoBoton.ESPECIAL;
        return TipoBoton.FUNCION;
    }

    private JButton crearBoton(String texto, TipoBoton tipo) {
        JButton btn = new JButton(texto) {
            private Color colorBase = Color.BLACK;
            private Color colorHover = Color.BLACK;
            private boolean isHovering = false;

            @Override
            public void setBackground(Color c) {
                super.setBackground(c);
                colorBase = c;
            }

            public void setColorHover(Color c) {
                colorHover = c;
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color color;
                if(getModel().isPressed()) {
                    color = colorBase.darker();
                } else if(isHovering) {
                    color = colorHover;
                } else {
                    color = colorBase;
                }

                g2.setColor(color);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);

                // Borde sutil
                g2.setColor(new Color(255, 255, 255, 30));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);

                g2.dispose();
                super.paintComponent(g);
            }

            {
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        isHovering = true;
                        repaint();
                    }
                    public void mouseExited(MouseEvent e) {
                        isHovering = false;
                        repaint();
                    }
                });
            }
        };

        btn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(e -> procesarBoton(texto));

        return btn;
    }

    private void procesarBoton(String texto) {
        try {
            if(texto.equals("C")) {
                limpiar();
                return;
            }
            if(texto.equals("CE")) {
                limpiarEntrada();
                return;
            }
            if(texto.equals("⌫")) {
                borrar();
                return;
            }
            if(texto.equals("±")) {
                cambiarSigno();
                return;
            }

            switch(texto) {
                case "0": case "1": case "2": case "3": case "4":
                case "5": case "6": case "7": case "8": case "9":
                case "A": case "B": case "D": case "E": case "F":
                case ".":
                    procesarNumero(texto);
                    break;

                case "+": case "-": case "×": case "÷": case "mod":
                    procesarOperador(texto);
                    break;

                case "=":
                    procesarIgual();
                    break;

                case "MC":
                    calc.memoriaLimpiar();
                    break;
                case "MR":
                    memoriaRecuperar();
                    break;
                case "M+":
                    calc.memoriaSumar(obtenerValorActual());
                    break;
                case "M-":
                    calc.memoriaRestar(obtenerValorActual());
                    break;
                case "MS":
                    calc.memoriaGuardar(obtenerValorActual());
                    break;

                case "sin":
                    aplicarFuncion(calc.seno(obtenerValorActual()));
                    break;
                case "cos":
                    aplicarFuncion(calc.coseno(obtenerValorActual()));
                    break;
                case "tan":
                    aplicarFuncion(calc.tangente(obtenerValorActual()));
                    break;
                case "log":
                    aplicarFuncion(calc.logaritmo(obtenerValorActual()));
                    break;
                case "ln":
                    aplicarFuncion(calc.logaritmoNatural(obtenerValorActual()));
                    break;
                case "√":
                    aplicarFuncion(calc.raizCuadrada(obtenerValorActual()));
                    break;
                case "x²":
                    aplicarFuncion(Math.pow(obtenerValorActual(), 2));
                    break;
                case "x³":
                    aplicarFuncion(Math.pow(obtenerValorActual(), 3));
                    break;
                case "1/x":
                    aplicarFuncion(calc.reciproco(obtenerValorActual()));
                    break;
                case "n!":
                    aplicarFuncion(calc.factorial(obtenerValorActual()));
                    break;
                case "π":
                    mostrarValor(calc.getPi());
                    break;
                case "e":
                    mostrarValor(calc.getE());
                    break;
                case "RAD":
                    toggleRadianes();
                    break;

                case "AND": case "OR": case "XOR": case "NOT":
                case "<<": case ">>":
                    procesarOperacionLogica(texto);
                    break;
            }

            if(modoActual == Modo.PROGRAMADOR) {
                actualizarConversiones();
            }
            actualizarHistorial();

        } catch(Exception ex) {
            displayPrincipal.setText("Error");
            ex.printStackTrace();
        }
    }

    private void procesarNumero(String num) {
        if(nuevoCalculo) {
            entradaActual.setLength(0);
            expresionCompleta.setLength(0);
            nuevoCalculo = false;
        }

        if(calc.isNuevoNumero()) {
            entradaActual.setLength(0);
            calc.setNuevoNumero(false);
        }

        if(num.equals(".") && entradaActual.toString().contains(".")) {
            return;
        }

        if(entradaActual.length() == 0 && num.equals(".")) {
            entradaActual.append("0");
        }

        entradaActual.append(num);
        displayPrincipal.setText(entradaActual.toString());
    }

    private void procesarOperador(String op) {
        double valor = obtenerValorActual();

        // Añadir número actual a la expresión
        if(expresionCompleta.length() == 0 || calc.isNuevoNumero()) {
            expresionCompleta.append(displayPrincipal.getText());
        }

        if(!calc.isNuevoNumero()) {
            valor = calc.calcular(valor);
            mostrarValor(valor);
        }

        // Añadir operador a la expresión
        expresionCompleta.append(" ").append(op).append(" ");
        displayOperacion.setText(expresionCompleta.toString());

        String opSimple = op.equals("×") ? "*" : op.equals("÷") ? "/" : op;
        calc.setOperador(opSimple);
        calc.setNuevoNumero(true);
    }

    private void procesarIgual() {
        double valor = obtenerValorActual();

        // Completar la expresión
        if(expresionCompleta.length() > 0) {
            expresionCompleta.append(displayPrincipal.getText()).append(" =");
            displayOperacion.setText(expresionCompleta.toString());
        }

        double resultado = calc.calcular(valor);
        mostrarValor(resultado);

        calc.setNuevoNumero(true);
        nuevoCalculo = true;

        // Limpiar expresión para próxima operación
        expresionCompleta.setLength(0);
    }

    private void procesarOperacionLogica(String op) {
        long valor1 = (long) calc.getResultado();
        long valor2 = (long) obtenerValorActual();
        long resultado = 0;

        switch(op) {
            case "AND": resultado = calc.and(valor1, valor2); break;
            case "OR": resultado = calc.or(valor1, valor2); break;
            case "XOR": resultado = calc.xor(valor1, valor2); break;
            case "NOT": resultado = calc.not(valor2); break;
            case "<<": resultado = calc.leftShift(valor2, 1); break;
            case ">>": resultado = calc.rightShift(valor2, 1); break;
        }

        mostrarValor((double) resultado);
    }

    private void aplicarFuncion(double resultado) {
        String nombreFunc = "";
        double valorOriginal = obtenerValorActual();

        // Determinar qué función se aplicó (puedes mejorar esto)
        displayOperacion.setText("Función aplicada a " + calc.formatear(valorOriginal));

        mostrarValor(resultado);
        calc.setNuevoNumero(true);
        nuevoCalculo = true;
    }

    private void memoriaRecuperar() {
        mostrarValor(calc.memoriaRecuperar());
        calc.setNuevoNumero(true);
    }

    private void cambiarSigno() {
        double valor = obtenerValorActual();
        mostrarValor(-valor);
    }

    private void limpiar() {
        calc.reiniciar();
        entradaActual.setLength(0);
        expresionCompleta.setLength(0);
        displayPrincipal.setText("0");
        displayOperacion.setText(" ");
        nuevoCalculo = true;
    }

    private void limpiarEntrada() {
        entradaActual.setLength(0);
        displayPrincipal.setText("0");
    }

    private void borrar() {
        if(entradaActual.length() > 0) {
            entradaActual.setLength(entradaActual.length() - 1);
            displayPrincipal.setText(entradaActual.length() == 0 ?
                    "0" : entradaActual.toString());
        }
    }

    private void toggleRadianes() {
        calc.setModoRadianes(!calc.isModoRadianes());
        String modo = calc.isModoRadianes() ? "RAD" : "DEG";
        JOptionPane.showMessageDialog(this,
                "Modo cambiado a: " + modo,
                "Información",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private double obtenerValorActual() {
        try {
            String texto = displayPrincipal.getText();
            if(texto.isEmpty() || texto.equals("Error")) return 0;

            if(modoActual == Modo.PROGRAMADOR) {
                for(int i = 0; i < radiosBases.length; i++) {
                    if(radiosBases[i].isSelected()) {
                        switch(i) {
                            case 0: return calc.fromBinary(texto);
                            case 1: return calc.fromOctal(texto);
                            case 2: return Double.parseDouble(texto);
                            case 3: return calc.fromHex(texto);
                        }
                    }
                }
            }

            return Double.parseDouble(texto);
        } catch(NumberFormatException e) {
            return 0;
        }
    }

    private void mostrarValor(double valor) {
        if(Double.isNaN(valor)) {
            displayPrincipal.setText("Error");
        } else {
            displayPrincipal.setText(calc.formatear(valor));
        }
        entradaActual.setLength(0);
        entradaActual.append(displayPrincipal.getText());
    }

    private void cambiarBase(int baseIndex) {
        actualizarConversiones();
    }

    private void actualizarConversiones() {
        if(camposConversion == null) return;

        long valor = (long) obtenerValorActual();

        camposConversion[0].setText(calc.toBinary(valor));
        camposConversion[1].setText(calc.toOctal(valor));
        camposConversion[2].setText(calc.toDecimal(valor));
        camposConversion[3].setText(calc.toHex(valor));
    }

    private void aplicarTema(Tema tema) {
        temaActual = tema;
        ColorScheme cs = esquemas.get(tema);

        getContentPane().setBackground(cs.fondo);

        if(displayPrincipal != null) {
            displayPrincipal.setBackground(cs.panel);
            displayPrincipal.setForeground(cs.texto);
            displayOperacion.setForeground(cs.acento);
        }

        if(panelCentral != null) {
            panelCentral.setBackground(cs.fondo);
            aplicarTemaRecursivo(panelCentral, cs);
        }

        if(panelHistorial != null) {
            panelHistorial.setBackground(cs.fondo);

            // Título del historial con color vibrante
            for(Component c : panelHistorial.getComponents()) {
                if(c instanceof JLabel) {
                    c.setForeground(cs.acento);
                }
            }

            // Panel del historial con fondo negro
            areaHistorial.setBackground(Color.BLACK);
            areaHistorial.setForeground(cs.acento);
            areaHistorial.setCaretColor(cs.acento);
        }

        // Aplicar al menú con colores vibrantes y alegres
        JMenuBar menuBar = getJMenuBar();
        if(menuBar != null) {
            menuBar.setBackground(cs.menuBar);
            menuBar.setBorderPainted(false);

            for(int i = 0; i < menuBar.getMenuCount(); i++) {
                JMenu menu = menuBar.getMenu(i);
                if(menu != null) {
                    menu.setForeground(Color.WHITE);
                    menu.setBackground(cs.menuBar);
                    menu.setOpaque(true);

                    // Popup del menú con bordes vibrantes
                    if(menu.getPopupMenu() != null) {
                        menu.getPopupMenu().setBackground(cs.panel);
                        menu.getPopupMenu().setBorder(new CompoundBorder(
                                new LineBorder(cs.acento, 3),
                                new EmptyBorder(5, 5, 5, 5)
                        ));
                    }

                    for(int j = 0; j < menu.getItemCount(); j++) {
                        JMenuItem item = menu.getItem(j);
                        if(item != null) {
                            item.setBackground(cs.panel);
                            item.setForeground(cs.texto);
                            item.setOpaque(true);
                        }
                    }
                }
            }
        }

        repaint();
    }

    private void aplicarTemaRecursivo(Container contenedor, ColorScheme cs) {
        contenedor.setBackground(cs.fondo);

        for(Component comp : contenedor.getComponents()) {
            if(comp instanceof JButton) {
                JButton btn = (JButton) comp;
                String texto = btn.getText();
                TipoBoton tipo = determinarTipoBoton(texto);

                switch(tipo) {
                    case NUMERO:
                        btn.setBackground(cs.boton);
                        btn.setForeground(cs.texto);
                        try {
                            java.lang.reflect.Method m = btn.getClass().getMethod("setColorHover", Color.class);
                            m.invoke(btn, cs.hover);
                        } catch(Exception e) {}
                        break;
                    case OPERADOR:
                        btn.setBackground(cs.operador);
                        btn.setForeground(Color.WHITE);
                        try {
                            java.lang.reflect.Method m = btn.getClass().getMethod("setColorHover", Color.class);
                            m.invoke(btn, cs.operador.brighter());
                        } catch(Exception e) {}
                        break;
                    case ESPECIAL:
                        btn.setBackground(cs.acento);
                        btn.setForeground(Color.WHITE);
                        try {
                            java.lang.reflect.Method m = btn.getClass().getMethod("setColorHover", Color.class);
                            m.invoke(btn, cs.acento.brighter());
                        } catch(Exception e) {}
                        break;
                    case FUNCION:
                        btn.setBackground(cs.boton);
                        btn.setForeground(cs.secundario);
                        try {
                            java.lang.reflect.Method m = btn.getClass().getMethod("setColorHover", Color.class);
                            m.invoke(btn, cs.hover);
                        } catch(Exception e) {}
                        break;
                    case MEMORIA:
                        btn.setBackground(new Color(
                                cs.boton.getRed() + 15,
                                cs.boton.getGreen() + 15,
                                cs.boton.getBlue() + 15
                        ));
                        btn.setForeground(cs.acento);
                        try {
                            java.lang.reflect.Method m = btn.getClass().getMethod("setColorHover", Color.class);
                            m.invoke(btn, cs.hover);
                        } catch(Exception e) {}
                        break;
                }
            } else if(comp instanceof JPanel) {
                comp.setBackground(cs.fondo);
                aplicarTemaRecursivo((Container) comp, cs);
            } else if(comp instanceof JTextField) {
                comp.setBackground(cs.panel);
                comp.setForeground(cs.texto);
            } else if(comp instanceof JRadioButton) {
                comp.setBackground(cs.fondo);
                comp.setForeground(cs.texto);
            } else if(comp instanceof JLabel) {
                comp.setForeground(cs.texto);
            }
        }
    }

    private void mostrarAcercaDe() {
        String mensaje = """
            ⚡ Calculadora Premium v2.0
            
            🎨 Características:
            • 3 modos: Estándar, Científica, Programador
            • 5 temas visuales espectaculares
            • Historial de operaciones
            • Sistema de memoria completo
            • Conversión de bases (BIN, OCT, DEC, HEX)
            • Operaciones lógicas (AND, OR, XOR, NOT)
            • Funciones trigonométricas y logarítmicas
            
            💻 Desarrollado con Java Swing
            © 2025
            """;

        JOptionPane.showMessageDialog(this,
                mensaje,
                "Acerca de Calculadora Premium",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private static class ColorScheme {
        Color fondo, panel, boton, acento, texto, operador, hover, secundario, menuBar;

        ColorScheme(Color fondo, Color panel, Color boton, Color acento,
                    Color texto, Color operador, Color hover, Color secundario, Color menuBar) {
            this.fondo = fondo;
            this.panel = panel;
            this.boton = boton;
            this.acento = acento;
            this.texto = texto;
            this.operador = operador;
            this.hover = hover;
            this.secundario = secundario;
            this.menuBar = menuBar;
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {
            e.printStackTrace();
        }

        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

        SwingUtilities.invokeLater(() -> new CalculadoraGUI());
    }
}