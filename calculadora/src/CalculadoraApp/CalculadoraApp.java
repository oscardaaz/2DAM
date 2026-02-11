package CalculadoraApp;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.help.HelpBroker;
import javax.help.HelpSet;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


/**
 * Calculadora con dos modos:
 *
 * 1) BASIC (Calculadora):
 *    - Decimales
 *    - Operaciones: +, -, *, /
 *    - +/- (cambio de signo)
 *    - Clear (borrado total)
 *    - Control del teclado (fila superior y numpad)
 *    - Barra de expresión (ej: "5 + 5", y al "=" -> "5 + 5 =")
 *    - IMPORTANTE: Backspace/Delete del teclado borra carácter a carácter (como una calculadora normal).
 *
 * 2) ADVANCED (Conversor de bases + Operaciones):
 *    - Base 2/8/10/16
 *    - Botones 0-9 y A-F (activados/desactivados según base)
 *    - Campo único de entrada
 *    - 4 salidas con conversiones BIN/OCT/DEC/HEX
 *    - Operaciones básicas (+, -, *, /) que funcionan en la base seleccionada
 *
 * Diseño:
 * - En modo BASIC el display NO es editable: evitamos que Swing escriba por sí mismo.
 * - Teclado: usamos KeyBindings globales para números/operadores, y un KeyEventDispatcher
 *   para asegurar que BACKSPACE/DELETE funcionen incluso si el foco está en un botón o menú.
 */
public class CalculadoraApp extends JFrame {

    // =========================
    // Tipos / Modo
    // =========================
    private enum Mode { BASIC, ADVANCED }
    private Mode currentMode = Mode.BASIC;

    // =========================
    // Estado BASIC (motor de cálculo)
    // =========================
    private BigDecimal acumulado = null;
    private String operacionPendiente = null;
    private boolean limpiarEnSiguiente = false;
    private String ultimaExpresion = "";

    // =========================
    // Estado ADVANCED
    // =========================
    private int baseActual = 10;

    // Motor de cálculo para modo avanzado
    private BigDecimal avanzadoAcumulado = null;
    private String avanzadoOperacionPendiente = null;
    private boolean avanzadoLimpiarEnSiguiente = false;
    private String avanzadoUltimaExpresion = "";

    // Evita que el DocumentFilter interprete setText() programáticos como tecleo del usuario
    private boolean avanzadoProgrammaticUpdate = false;

    // =========================
    // UI general (cards)
    // =========================
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel pnlCards = new JPanel(cardLayout);

    // =========================
    // UI BASIC
    // =========================
    private final JTextField txtBasicoDisplay = new JTextField();
    private final JLabel lblBasicExpr = new JLabel(" ");

    // Dispatcher (para no instalarlo dos veces)
    private boolean basicDeleteDispatcherInstalled = false;

    // =========================
    // UI ADVANCED
    // =========================
    private final JTextField txtAvanzadoEntrada = new JTextField();
    private final JTextField txtOutBin = new JTextField();
    private final JTextField txtOutOct = new JTextField();
    private final JTextField txtOutDec = new JTextField();
    private final JTextField txtOutHex = new JTextField();
    private final JLabel lblAvanzadoBase = new JLabel("Base seleccionada: 10");
    private final JLabel lblAvanzadoExpr = new JLabel(" "); // Barra de expresión

    private final ButtonGroup buttonGroupBases = new ButtonGroup();
    private final JRadioButton rdbBin = new JRadioButton("BIN");
    private final JRadioButton rdbOct = new JRadioButton("OCT");
    private final JRadioButton rdbDec = new JRadioButton("DEC");
    private final JRadioButton rdbHex = new JRadioButton("HEX");

    private final JButton btnAvClear = new JButton("Reiniciar");
    private final JButton[] btnDig = new JButton[16];

    private Dimension sizeBasico;
    private Dimension sizeAvanzado;

    // JavaHelp
    private HelpBroker helpBroker;

    // =========================
    // Constructor
    // =========================
    public CalculadoraApp() {
        super("Calculadora");

        aplicarLookAndFeel();
        construirUI();
        initHelp();
        configurarBasico();
        configurarAvanzado();

        calcularTamanosPorModo();
        setMode(Mode.BASIC);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // =========================================================
    //  JavaHelp
    // =========================================================
    private Path getAppBaseDir() {
        try {
            URL location = getClass().getProtectionDomain().getCodeSource().getLocation();
            Path p = Paths.get(location.toURI()).toAbsolutePath().normalize();
            String s = p.toString().toLowerCase();
            if (Files.isRegularFile(p) || s.endsWith(".jar") || s.endsWith(".exe")) {
                return p.getParent();
            }
            return p;
        } catch (Exception e) {
            return Paths.get(System.getProperty("user.dir")).toAbsolutePath().normalize();
        }
    }

    private URL locateHelpSetURL() throws Exception {
        Path baseFromCodeSource = getAppBaseDir();
        Path userDir = Paths.get(System.getProperty("user.dir")).toAbsolutePath().normalize();
        Set<Path> candidates = new LinkedHashSet<>();
        List<Path> bases = List.of(baseFromCodeSource, userDir);

        for (Path base : bases) {
            candidates.add(base.resolve("help").resolve("help_set.hs"));
            candidates.add(base.resolve("recursos").resolve("help").resolve("help_set.hs"));
            candidates.add(base.resolve("..").resolve("recursos").resolve("help").resolve("help_set.hs"));
            candidates.add(base.resolve("..").resolve("help").resolve("help_set.hs"));
            if (base.getFileName() != null && base.getFileName().toString().equalsIgnoreCase("recursos")) {
                candidates.add(base.resolve("help").resolve("help_set.hs"));
            }
        }

        for (Path p : candidates) {
            p = p.toAbsolutePath().normalize();
            if (Files.exists(p)) {
                return p.toUri().toURL();
            }
        }

        StringBuilder sb = new StringBuilder("No se encontró help_set.hs. Se intentó en:\n");
        for (Path p : candidates) sb.append(" - ").append(p.toAbsolutePath().normalize()).append("\n");
        throw new IllegalStateException(sb.toString());
    }

    private void initHelp() {
        try {
            URL hsURL = locateHelpSetURL();
            HelpSet hs = new HelpSet(null, hsURL);
            helpBroker = hs.createHelpBroker();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo cargar la ayuda.\n" + ex.getMessage(),
                    "Ayuda", JOptionPane.ERROR_MESSAGE);
            helpBroker = null;
        }
    }

    private void showHelp() {
        if (helpBroker == null) return;
        try { helpBroker.setCurrentID("bienvenida"); } catch (Exception ignored) {}
        helpBroker.setDisplayed(true);
    }

    // =========================================================
    //  UI: Look & Feel / Menu / Panels
    // =========================================================
    private void aplicarLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {}
    }

    private void construirUI() {
        setJMenuBar(crearMenuBar());

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(root);

        pnlCards.add(crearPanelBasico(), Mode.BASIC.name());
        pnlCards.add(crearPanelAvanzado(), Mode.ADVANCED.name());
        root.add(pnlCards, BorderLayout.CENTER);
    }

    private JMenuBar crearMenuBar() {
        JMenuBar bar = new JMenuBar();

        JMenu menuModo = new JMenu("Modo");
        JRadioButtonMenuItem miBasico = new JRadioButtonMenuItem("Básico");
        JRadioButtonMenuItem miAvanzado = new JRadioButtonMenuItem("Avanzado");

        ButtonGroup grp = new ButtonGroup();
        grp.add(miBasico);
        grp.add(miAvanzado);

        miBasico.setSelected(true);

        miBasico.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, InputEvent.CTRL_DOWN_MASK));
        miAvanzado.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, InputEvent.CTRL_DOWN_MASK));

        miBasico.addActionListener(e -> setMode(Mode.BASIC));
        miAvanzado.addActionListener(e -> setMode(Mode.ADVANCED));

        menuModo.add(miBasico);
        menuModo.add(miAvanzado);
        bar.add(menuModo);

        JMenu menuOpciones = new JMenu("Opciones");
        JMenuItem miSalir = new JMenuItem("Salir");
        miSalir.addActionListener(e -> dispose());
        menuOpciones.add(miSalir);
        bar.add(menuOpciones);

        JMenu menuAyuda = new JMenu("Ayuda");
        JMenuItem miVerAyuda = new JMenuItem("Ver ayuda");
        miVerAyuda.addActionListener(e -> showHelp());
        menuAyuda.add(miVerAyuda);
        bar.add(menuAyuda);

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "HELP_F1");
        getRootPane().getActionMap().put("HELP_F1", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                showHelp();
            }
        });

        return bar;
    }

    // =========================================================
    //  Panel BASIC
    // =========================================================
    private JPanel crearPanelBasico() {
        JPanel p = new JPanel(new BorderLayout(8, 8));
        p.setOpaque(false);

        JPanel top = new JPanel(new BorderLayout(4, 4));
        top.setOpaque(false);

        lblBasicExpr.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblBasicExpr.setForeground(new Color(90, 90, 90));
        lblBasicExpr.setHorizontalAlignment(SwingConstants.RIGHT);

        txtBasicoDisplay.setHorizontalAlignment(JTextField.RIGHT);
        txtBasicoDisplay.setFont(new Font("SansSerif", Font.PLAIN, 24));
        txtBasicoDisplay.setPreferredSize(new Dimension(10, 48));

        top.add(lblBasicExpr, BorderLayout.NORTH);
        top.add(txtBasicoDisplay, BorderLayout.CENTER);

        p.add(top, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridBagLayout());
        grid.setOpaque(false);

        Insets in = new Insets(5, 5, 5, 5);
        Dimension btnSize = new Dimension(62, 40);
        Dimension wide = new Dimension(btnSize.width * 2 + 5, btnSize.height);

        class Add {
            void b(JButton b, int x, int y, int w, int h, Dimension pref) {
                b.setFont(new Font("SansSerif", Font.PLAIN, 16));
                b.setFocusable(false);
                b.setPreferredSize(pref);

                GridBagConstraints c = new GridBagConstraints();
                c.gridx = x;
                c.gridy = y;
                c.gridwidth = w;
                c.gridheight = h;
                c.weightx = 1.0;
                c.weighty = 1.0;
                c.fill = GridBagConstraints.BOTH;
                c.insets = in;
                grid.add(b, c);
            }
        }
        Add add = new Add();

        JButton b7 = mkBtn("7", e -> basicoAppend("7"));
        JButton b8 = mkBtn("8", e -> basicoAppend("8"));
        JButton b9 = mkBtn("9", e -> basicoAppend("9"));
        JButton bDiv = mkBtn("/", e -> basicoSetOperacion("/"));

        JButton b4 = mkBtn("4", e -> basicoAppend("4"));
        JButton b5 = mkBtn("5", e -> basicoAppend("5"));
        JButton b6 = mkBtn("6", e -> basicoAppend("6"));
        JButton bMul = mkBtn("*", e -> basicoSetOperacion("*"));

        JButton b1 = mkBtn("1", e -> basicoAppend("1"));
        JButton b2 = mkBtn("2", e -> basicoAppend("2"));
        JButton b3 = mkBtn("3", e -> basicoAppend("3"));
        JButton bRes = mkBtn("-", e -> basicoMinusInteligente());

        JButton b0 = mkBtn("0", e -> basicoAppend("0"));
        JButton bDot = mkBtn(".", e -> basicoPunto());
        JButton bSign = mkBtn("+/-", e -> basicoCambiarSigno());
        JButton bSum = mkBtn("+", e -> basicoSetOperacion("+"));

        JButton bClear = mkBtn("Clear", e -> basicoClear());
        JButton bEq = mkBtn("=", e -> basicoIgual());

        add.b(b7, 0, 0, 1, 1, btnSize);
        add.b(b8, 1, 0, 1, 1, btnSize);
        add.b(b9, 2, 0, 1, 1, btnSize);
        add.b(bDiv, 3, 0, 1, 1, btnSize);

        add.b(b4, 0, 1, 1, 1, btnSize);
        add.b(b5, 1, 1, 1, 1, btnSize);
        add.b(b6, 2, 1, 1, 1, btnSize);
        add.b(bMul, 3, 1, 1, 1, btnSize);

        add.b(b1, 0, 2, 1, 1, btnSize);
        add.b(b2, 1, 2, 1, 1, btnSize);
        add.b(b3, 2, 2, 1, 1, btnSize);
        add.b(bRes, 3, 2, 1, 1, btnSize);

        add.b(b0, 0, 3, 1, 1, btnSize);
        add.b(bDot, 1, 3, 1, 1, btnSize);
        add.b(bSign, 2, 3, 1, 1, btnSize);
        add.b(bSum, 3, 3, 1, 1, btnSize);

        add.b(bClear, 0, 4, 2, 1, wide);
        add.b(bEq, 2, 4, 2, 1, wide);

        p.add(grid, BorderLayout.CENTER);
        return p;
    }

    // =========================================================
    //  Panel ADVANCED
    // =========================================================
    private JPanel crearPanelAvanzado() {
        JPanel p = new JPanel(new BorderLayout(10, 10));
        p.setOpaque(false);

        JPanel top = new JPanel(new GridBagLayout());
        top.setOpaque(false);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.fill = GridBagConstraints.HORIZONTAL;

        // Barra de expresión (igual que en BASIC)
        lblAvanzadoExpr.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblAvanzadoExpr.setForeground(new Color(90, 90, 90));
        lblAvanzadoExpr.setHorizontalAlignment(SwingConstants.RIGHT);

        c.gridx = 0; c.gridy = 0; c.gridwidth = 6; c.weightx = 1;
        top.add(lblAvanzadoExpr, c);

        txtAvanzadoEntrada.setFont(new Font("SansSerif", Font.PLAIN, 22));
        txtAvanzadoEntrada.setPreferredSize(new Dimension(10, 48));

        c.gridx = 0; c.gridy = 1; c.gridwidth = 6; c.weightx = 1;
        top.add(txtAvanzadoEntrada, c);

        buttonGroupBases.add(rdbBin);
        buttonGroupBases.add(rdbOct);
        buttonGroupBases.add(rdbDec);
        buttonGroupBases.add(rdbHex);

        rdbBin.addActionListener(e -> setBaseActual(2));
        rdbOct.addActionListener(e -> setBaseActual(8));
        rdbDec.addActionListener(e -> setBaseActual(10));
        rdbHex.addActionListener(e -> setBaseActual(16));

        c.gridy = 2; c.gridwidth = 1; c.weightx = 0;
        c.gridx = 0; top.add(rdbBin, c);
        c.gridx = 1; top.add(rdbOct, c);
        c.gridx = 2; top.add(rdbDec, c);
        c.gridx = 3; top.add(rdbHex, c);

        c.gridx = 4; c.weightx = 1; top.add(new JLabel(""), c);
        c.gridx = 5; c.weightx = 0;

        btnAvClear.setFocusable(false);
        btnAvClear.addActionListener(e -> avanzadoClear());
        top.add(btnAvClear, c);

        c.gridx = 0; c.gridy = 3; c.gridwidth = 6; c.weightx = 1;
        top.add(lblAvanzadoBase, c);

        p.add(top, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);

        GridBagConstraints cc = new GridBagConstraints();
        cc.insets = new Insets(6, 6, 6, 6);
        cc.fill = GridBagConstraints.BOTH;

        JPanel digits = new JPanel(new GridLayout(4, 4, 8, 8));
        digits.setOpaque(false);

        Dimension digSize = new Dimension(52, 38);
        for (int i = 0; i < 16; i++) {
            String t = (i < 10) ? String.valueOf(i) : String.valueOf((char) ('A' + (i - 10)));
            JButton b = new JButton(t);
            b.setFont(new Font("SansSerif", Font.PLAIN, 15));
            b.setFocusable(false);
            b.setPreferredSize(digSize);

            String toAppend = t;
            b.addActionListener(e -> avanzadoAppend(toAppend));

            btnDig[i] = b;
            digits.add(b);
        }

        cc.gridx = 0; cc.gridy = 0; cc.weightx = 0.40; cc.weighty = 1.0;
        center.add(digits, cc);

        // Panel derecho: salidas + botones de operaciones
        JPanel rightPanel = new JPanel(new BorderLayout(8, 8));
        rightPanel.setOpaque(false);

        JPanel outs = new JPanel(new GridBagLayout());
        outs.setOpaque(false);

        prepararOut(txtOutBin);
        prepararOut(txtOutOct);
        prepararOut(txtOutDec);
        prepararOut(txtOutHex);

        outs.setPreferredSize(new Dimension(340, 10));

        addOutRow(outs, 0, "BIN:", txtOutBin);
        addOutRow(outs, 1, "OCT:", txtOutOct);
        addOutRow(outs, 2, "DEC:", txtOutDec);
        addOutRow(outs, 3, "HEX:", txtOutHex);

        rightPanel.add(outs, BorderLayout.CENTER);

        // Botones de operaciones (fila abajo)
        JPanel ops = new JPanel(new GridLayout(1, 5, 6, 6));
        ops.setOpaque(false);

        JButton bOp1 = mkBtn("+", e -> avanzadoSetOperacion("+"));
        JButton bOp2 = mkBtn("-", e -> avanzadoSetOperacion("-"));
        JButton bOp3 = mkBtn("*", e -> avanzadoSetOperacion("*"));
        JButton bOp4 = mkBtn("/", e -> avanzadoSetOperacion("/"));
        JButton bOp5 = mkBtn("=", e -> avanzadoIgual());

        bOp1.setPreferredSize(new Dimension(10, 36));
        bOp2.setPreferredSize(new Dimension(10, 36));
        bOp3.setPreferredSize(new Dimension(10, 36));
        bOp4.setPreferredSize(new Dimension(10, 36));
        bOp5.setPreferredSize(new Dimension(10, 36));

        ops.add(bOp1);
        ops.add(bOp2);
        ops.add(bOp3);
        ops.add(bOp4);
        ops.add(bOp5);

        rightPanel.add(ops, BorderLayout.SOUTH);

        cc.gridx = 1; cc.gridy = 0; cc.weightx = 0.60; cc.weighty = 1.0;
        center.add(rightPanel, cc);

        p.add(center, BorderLayout.CENTER);
        return p;
    }

    private void prepararOut(JTextField tf) {
        tf.setEditable(false);
        tf.setFont(new Font("Monospaced", Font.PLAIN, 15));
        tf.setPreferredSize(new Dimension(280, 34));
    }

    private void addOutRow(JPanel parent, int row, String label, JTextField field) {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 6, 8, 6);
        c.gridy = row;
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0; c.weightx = 0;
        parent.add(new JLabel(label), c);

        c.gridx = 1; c.weightx = 1;
        parent.add(field, c);
    }

    private JButton mkBtn(String text, ActionListener al) {
        JButton b = new JButton(text);
        b.setFocusable(false);
        b.addActionListener(al);
        return b;
    }

    // =========================================================
    //  Modo / tamaños / foco
    // =========================================================
    private void calcularTamanosPorModo() {
        setModeInternal(Mode.BASIC);
        pack();
        Dimension dB = getSize();
        sizeBasico = new Dimension(Math.max(300, dB.width), Math.max(520, dB.height));

        setModeInternal(Mode.ADVANCED);
        pack();
        Dimension dA = getSize();
        sizeAvanzado = new Dimension(Math.max(560, dA.width), Math.max(520, dA.height));
    }

    private void setMode(Mode mode) {
        this.currentMode = mode;
        setModeInternal(mode);

        Dimension min = (mode == Mode.BASIC) ? sizeBasico : sizeAvanzado;
        setMinimumSize(min);

        if (getWidth() < min.width || getHeight() < min.height) {
            setSize(Math.max(getWidth(), min.width), Math.max(getHeight(), min.height));
        }

        SwingUtilities.invokeLater(() -> {
            if (mode == Mode.BASIC) txtBasicoDisplay.requestFocusInWindow();
            else txtAvanzadoEntrada.requestFocusInWindow();
        });
    }

    private void setModeInternal(Mode mode) {
        cardLayout.show(pnlCards, mode.name());
        pnlCards.revalidate();
        pnlCards.repaint();
        pack();
    }

    // =========================================================
    //  BASIC: configuración
    // =========================================================
    private void configurarBasico() {
        txtBasicoDisplay.setEditable(false);
        txtBasicoDisplay.setFocusable(true);
        txtBasicoDisplay.setText("");
        setBasicExpr(" ");
        instalarDispatcherBorrado();
    }

    private void setBasicExpr(String s) {
        lblBasicExpr.setText(s == null || s.isBlank() ? " " : s);
    }

    private void refrescarExpresionBasica(boolean conIgual) {
        if (acumulado == null || operacionPendiente == null) {
            if (conIgual && !ultimaExpresion.isBlank()) setBasicExpr(ultimaExpresion + " =");
            else setBasicExpr(" ");
            return;
        }

        String b = limpiarEnSiguiente ? "" : txtBasicoDisplay.getText().trim();
        String expr = formato(acumulado) + " " + operacionPendiente;
        if (!b.isEmpty()) expr += " " + b;
        if (conIgual) expr += " =";
        setBasicExpr(expr);
    }

    // =========================================================
    //  BASIC: lógica
    // =========================================================
    private void basicoAppend(String s) {
        if (limpiarEnSiguiente) {
            txtBasicoDisplay.setText("");
            limpiarEnSiguiente = false;
        }
        txtBasicoDisplay.setText(txtBasicoDisplay.getText() + s);
        refrescarExpresionBasica(false);
    }

    private void basicoPunto() {
        if (limpiarEnSiguiente) {
            txtBasicoDisplay.setText("0.");
            limpiarEnSiguiente = false;
            refrescarExpresionBasica(false);
            return;
        }

        String t = txtBasicoDisplay.getText();
        if (t.isEmpty()) {
            txtBasicoDisplay.setText("0.");
            refrescarExpresionBasica(false);
            return;
        }

        if (!t.contains(".")) {
            txtBasicoDisplay.setText(t + ".");
            refrescarExpresionBasica(false);
        }
    }

    private BigDecimal basicoLeerDisplay() {
        String t = txtBasicoDisplay.getText().trim();
        if (t.isEmpty() || t.equals("-") || t.equals(".") || t.equals("-.")) {
            throw new NumberFormatException("Introduce un número válido.");
        }
        return new BigDecimal(t);
    }

    private void basicoMinusInteligente() {
        String t = txtBasicoDisplay.getText().trim();
        if (t.isEmpty() || t.equals("-") || limpiarEnSiguiente) basicoCambiarSigno();
        else basicoSetOperacion("-");
    }

    private void basicoSetOperacion(String op) {
        try {
            BigDecimal actual = basicoLeerDisplay();

            if (acumulado == null) {
                acumulado = actual;
            } else if (operacionPendiente != null && !limpiarEnSiguiente) {
                acumulado = basicoAplicar(acumulado, actual, operacionPendiente);
                txtBasicoDisplay.setText(formato(acumulado));
            }

            operacionPendiente = op;
            limpiarEnSiguiente = true;
            refrescarExpresionBasica(false);

        } catch (NumberFormatException ex) {
            mostrarError("Número inválido.\n" + ex.getMessage());
        } catch (ArithmeticException ex) {
            mostrarError("Error aritmético: " + ex.getMessage());
            basicoClear();
        }
    }

    private BigDecimal basicoAplicar(BigDecimal a, BigDecimal b, String op) {
        switch (op) {
            case "+": return a.add(b);
            case "-": return a.subtract(b);
            case "*": return a.multiply(b);
            case "/":
                if (b.compareTo(BigDecimal.ZERO) == 0) throw new ArithmeticException("División entre cero.");
                return a.divide(b, 12, RoundingMode.HALF_UP).stripTrailingZeros();
            default:  return b;
        }
    }

    private void basicoIgual() {
        try {
            if (operacionPendiente == null || acumulado == null) return;

            BigDecimal b = basicoLeerDisplay();
            BigDecimal res = basicoAplicar(acumulado, b, operacionPendiente);

            ultimaExpresion = formato(acumulado) + " " + operacionPendiente + " " + formato(b);
            txtBasicoDisplay.setText(formato(res));

            acumulado = null;
            operacionPendiente = null;
            limpiarEnSiguiente = true;
            refrescarExpresionBasica(true);

        } catch (NumberFormatException ex) {
            mostrarError("Número inválido.\n" + ex.getMessage());
        } catch (ArithmeticException ex) {
            mostrarError("Error aritmético: " + ex.getMessage());
            basicoClear();
        }
    }

    private void basicoClear() {
        txtBasicoDisplay.setText("");
        acumulado = null;
        operacionPendiente = null;
        limpiarEnSiguiente = false;
        ultimaExpresion = "";
        setBasicExpr(" ");
    }

    private void basicoBorrarUltimo() {
        if (limpiarEnSiguiente) {
            txtBasicoDisplay.setText("");
            limpiarEnSiguiente = false;
            refrescarExpresionBasica(false);
            return;
        }

        String t = txtBasicoDisplay.getText();
        if (!t.isEmpty()) txtBasicoDisplay.setText(t.substring(0, t.length() - 1));
        refrescarExpresionBasica(false);
    }

    private void basicoCambiarSigno() {
        if (limpiarEnSiguiente) {
            txtBasicoDisplay.setText("-");
            limpiarEnSiguiente = false;
            refrescarExpresionBasica(false);
            return;
        }

        String t = txtBasicoDisplay.getText().trim();
        if (t.isEmpty()) txtBasicoDisplay.setText("-");
        else if (t.startsWith("-")) txtBasicoDisplay.setText(t.substring(1));
        else txtBasicoDisplay.setText("-" + t);
        refrescarExpresionBasica(false);
    }

    private String formato(BigDecimal d) {
        if (d == null) return "";
        return d.stripTrailingZeros().toPlainString();
    }

    // =========================================================
    //  ADVANCED: configuración
    // =========================================================
    private void configurarAvanzado() {
        instalarFiltroAvanzado();
        instalarDispatcherBorrado();
        rdbDec.setSelected(true);
        setBaseActual(10);
        setAvanzadoExpr(" ");
        convertirYMostrar();
    }

    private void setBaseActual(int base) {
        this.baseActual = base;
        lblAvanzadoBase.setText("Base seleccionada: " + base);
        actualizarBotonesPorBase();

        String txt = txtAvanzadoEntrada.getText().toUpperCase();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < txt.length(); i++) {
            char ch = txt.charAt(i);
            int val = charToVal(ch);
            if (val >= 0 && val < baseActual) sb.append(ch);
        }
        txtAvanzadoEntrada.setText(sb.toString());
        convertirYMostrar();
        refrescarExpresionAvanzada(false);
    }

    private void actualizarBotonesPorBase() {
        for (int i = 0; i < 16; i++) btnDig[i].setEnabled(i < baseActual);
    }

    private void avanzadoAppend(String s) {
        if (avanzadoLimpiarEnSiguiente) {
            avanzadoProgrammaticUpdate = true;
            txtAvanzadoEntrada.setText("");
            avanzadoProgrammaticUpdate = false;
            avanzadoLimpiarEnSiguiente = false;
        }
        txtAvanzadoEntrada.setText(txtAvanzadoEntrada.getText() + s);
        convertirYMostrar();
        refrescarExpresionAvanzada(false);
    }

    private int charToVal(char ch) {
        ch = Character.toUpperCase(ch);
        if (ch >= '0' && ch <= '9') return ch - '0';
        if (ch >= 'A' && ch <= 'F') return 10 + (ch - 'A');
        return -1;
    }

    private void convertirYMostrar() {
        String input = txtAvanzadoEntrada.getText().trim().toUpperCase();
        if (input.isEmpty()) {
            txtOutBin.setText("");
            txtOutOct.setText("");
            txtOutDec.setText("");
            txtOutHex.setText("");
            return;
        }
        try {
            long valor = Long.parseLong(input, baseActual);
            txtOutBin.setText(Long.toString(valor, 2).toUpperCase());
            txtOutOct.setText(Long.toString(valor, 8).toUpperCase());
            txtOutDec.setText(Long.toString(valor, 10).toUpperCase());
            txtOutHex.setText(Long.toString(valor, 16).toUpperCase());
        } catch (NumberFormatException ex) {
            txtOutBin.setText("ERROR");
            txtOutOct.setText("ERROR");
            txtOutDec.setText("ERROR");
            txtOutHex.setText("ERROR");
        }
    }

    private void avanzadoClear() {
        avanzadoProgrammaticUpdate = true;
        txtAvanzadoEntrada.setText("");
        avanzadoProgrammaticUpdate = false;
        avanzadoAcumulado = null;
        avanzadoOperacionPendiente = null;
        avanzadoLimpiarEnSiguiente = false;
        avanzadoUltimaExpresion = "";
        setAvanzadoExpr(" ");
        convertirYMostrar();
    }

    // =========================================================
    //  ADVANCED: operaciones
    // =========================================================
    private void setAvanzadoExpr(String s) {
        lblAvanzadoExpr.setText(s == null || s.isBlank() ? " " : s);
    }

    private void refrescarExpresionAvanzada(boolean conIgual) {
        if (avanzadoAcumulado == null || avanzadoOperacionPendiente == null) {
            if (conIgual && !avanzadoUltimaExpresion.isBlank()) {
                setAvanzadoExpr(avanzadoUltimaExpresion + " =");
            } else {
                setAvanzadoExpr(" ");
            }
            return;
        }

        String b = avanzadoLimpiarEnSiguiente ? "" : txtAvanzadoEntrada.getText().trim();
        String expr = formatoEnBase(avanzadoAcumulado) + " " + avanzadoOperacionPendiente;
        if (!b.isEmpty()) expr += " " + b;
        if (conIgual) expr += " =";
        setAvanzadoExpr(expr);
    }

    private BigDecimal avanzadoLeerEntrada() {
        String t = txtAvanzadoEntrada.getText().trim().toUpperCase();
        if (t.isEmpty()) throw new NumberFormatException("Introduce un número válido.");
        try {
            long valor = Long.parseLong(t, baseActual);
            return new BigDecimal(valor);
        } catch (NumberFormatException ex) {
            throw new NumberFormatException("Número inválido en base " + baseActual);
        }
    }

    private void avanzadoSetOperacion(String op) {
        try {
            BigDecimal actual = avanzadoLeerEntrada();

            if (avanzadoAcumulado == null) {
                avanzadoAcumulado = actual;
            } else if (avanzadoOperacionPendiente != null && !avanzadoLimpiarEnSiguiente) {
                avanzadoAcumulado = basicoAplicar(avanzadoAcumulado, actual, avanzadoOperacionPendiente);
                avanzadoProgrammaticUpdate = true;
                txtAvanzadoEntrada.setText(formatoEnBase(avanzadoAcumulado));
                avanzadoProgrammaticUpdate = false;
                convertirYMostrar();
            }

            avanzadoOperacionPendiente = op;
            avanzadoLimpiarEnSiguiente = true;
            refrescarExpresionAvanzada(false);

        } catch (NumberFormatException ex) {
            mostrarError("Número inválido.\n" + ex.getMessage());
        } catch (ArithmeticException ex) {
            mostrarError("Error aritmético: " + ex.getMessage());
            avanzadoClear();
        }
    }

    private void avanzadoIgual() {
        try {
            if (avanzadoOperacionPendiente == null || avanzadoAcumulado == null) return;

            BigDecimal b = avanzadoLeerEntrada();
            BigDecimal res = basicoAplicar(avanzadoAcumulado, b, avanzadoOperacionPendiente);

            avanzadoUltimaExpresion = formatoEnBase(avanzadoAcumulado) + " " +
                    avanzadoOperacionPendiente + " " + formatoEnBase(b);

            avanzadoProgrammaticUpdate = true;
            txtAvanzadoEntrada.setText(formatoEnBase(res));
            avanzadoProgrammaticUpdate = false;
            convertirYMostrar();

            avanzadoAcumulado = null;
            avanzadoOperacionPendiente = null;
            avanzadoLimpiarEnSiguiente = true;
            refrescarExpresionAvanzada(true);

        } catch (NumberFormatException ex) {
            mostrarError("Número inválido.\n" + ex.getMessage());
        } catch (ArithmeticException ex) {
            mostrarError("Error aritmético: " + ex.getMessage());
            avanzadoClear();
        }
    }

    private String formatoEnBase(BigDecimal d) {
        if (d == null) return "";
        long val = d.longValue();
        return Long.toString(val, baseActual).toUpperCase();
    }

    private void avanzadoBorrarUltimo() {
        if (avanzadoLimpiarEnSiguiente) {
            avanzadoProgrammaticUpdate = true;
            txtAvanzadoEntrada.setText("");
            avanzadoProgrammaticUpdate = false;
            avanzadoLimpiarEnSiguiente = false;
            refrescarExpresionAvanzada(false);
            return;
        }

        String t = txtAvanzadoEntrada.getText();
        if (!t.isEmpty()) txtAvanzadoEntrada.setText(t.substring(0, t.length() - 1));
        convertirYMostrar();
        refrescarExpresionAvanzada(false);
    }

    private void instalarFiltroAvanzado() {
        ((AbstractDocument) txtAvanzadoEntrada.getDocument()).setDocumentFilter(new DocumentFilter() {

            private boolean esValido(char c) {
                int v = charToVal(c);
                return v >= 0 && v < baseActual;
            }

            private String sanitizarCompleto(String raw) {
                if (raw == null) return "";
                raw = raw.trim().toUpperCase();

                boolean negativo = raw.startsWith("-");
                String body = negativo ? raw.substring(1) : raw;

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < body.length(); i++) {
                    char c = body.charAt(i);
                    if (esValido(c)) sb.append(c);
                }

                // Permitimos "-" solo si hay algún dígito o si el usuario está empezando a teclear el signo
                if (negativo) return "-" + sb;
                return sb.toString();
            }

            private void aplicar(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {

                String actual = fb.getDocument().getText(0, fb.getDocument().getLength());

                // Si el usuario va a introducir el segundo operando tras pulsar un operador, limpiamos antes de insertar
                if (!avanzadoProgrammaticUpdate && avanzadoLimpiarEnSiguiente && text != null && !text.isEmpty()) {
                    actual = "";
                    offset = 0;
                    length = 0;
                    avanzadoLimpiarEnSiguiente = false;
                    SwingUtilities.invokeLater(() -> refrescarExpresionAvanzada(false));
                }

                String insert = (text == null) ? "" : text;

                // Componemos el nuevo texto "como si" Swing lo insertara, y después sanitizamos todo
                String candidato = actual.substring(0, Math.min(offset, actual.length()))
                        + insert
                        + actual.substring(Math.min(offset + length, actual.length()));

                String limpio = sanitizarCompleto(candidato);

                fb.replace(0, fb.getDocument().getLength(), limpio, attrs);
                SwingUtilities.invokeLater(() -> convertirYMostrar());
            }

            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                aplicar(fb, offset, 0, string, attr);
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                aplicar(fb, offset, length, text, attrs);
            }

            @Override
            public void remove(FilterBypass fb, int offset, int length)
                    throws BadLocationException {
                super.remove(fb, offset, length);
                SwingUtilities.invokeLater(() -> convertirYMostrar());
            }
        });


        txtAvanzadoEntrada.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                txtAvanzadoEntrada.setText(txtAvanzadoEntrada.getText().toUpperCase());
            }
        });
    }

    // =========================================================
    //  TECLADO BASIC: KeyBindings globales + Dispatcher borrado
    // =========================================================
    private void instalarKeyBindingsGlobales() {
        JRootPane root = getRootPane();
        InputMap im = root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = root.getActionMap();

        // Dígitos (fila superior)
        for (int i = 0; i <= 9; i++) {
            final String digit = String.valueOf(i);
            bind(im, am, KeyStroke.getKeyStroke(KeyEvent.VK_0 + i, 0), "D_TOP_" + i,
                    e -> onBasicKey(() -> basicoAppend(digit)));
        }

        // Dígitos (numpad)
        for (int i = 0; i <= 9; i++) {
            final String digit = String.valueOf(i);
            bind(im, am, KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD0 + i, 0), "D_PAD_" + i,
                    e -> onBasicKey(() -> basicoAppend(digit)));
        }

        // Decimal: '.' y ',' y numpad decimal
        bind(im, am, KeyStroke.getKeyStroke(KeyEvent.VK_PERIOD, 0), "DOT_PERIOD",
                e -> onBasicKey(this::basicoPunto));
        bind(im, am, KeyStroke.getKeyStroke(KeyEvent.VK_COMMA, 0), "DOT_COMMA",
                e -> onBasicKey(this::basicoPunto));
        bind(im, am, KeyStroke.getKeyStroke(KeyEvent.VK_DECIMAL, 0), "DOT_NUMPAD",
                e -> onBasicKey(this::basicoPunto));

        // Operadores (numpad)
        bind(im, am, KeyStroke.getKeyStroke(KeyEvent.VK_ADD, 0), "OP_ADD_PAD",
                e -> onBasicKey(() -> basicoSetOperacion("+")));
        bind(im, am, KeyStroke.getKeyStroke(KeyEvent.VK_SUBTRACT, 0), "OP_SUB_PAD",
                e -> onBasicKey(this::basicoMinusInteligente));
        bind(im, am, KeyStroke.getKeyStroke(KeyEvent.VK_MULTIPLY, 0), "OP_MUL_PAD",
                e -> onBasicKey(() -> basicoSetOperacion("*")));
        bind(im, am, KeyStroke.getKeyStroke(KeyEvent.VK_DIVIDE, 0), "OP_DIV_PAD",
                e -> onBasicKey(() -> basicoSetOperacion("/")));

        // Operadores por carácter - Compatibilidad ES/US
        // En ES: + es Shift+], * es Shift+], / es Shift+7, - es tecla directa
        // En US: + es Shift+=, * es Shift+8, / es /, - es -

        // Suma: Shift++ (US) o la tecla que sea en ES
        bind(im, am, KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, InputEvent.SHIFT_DOWN_MASK), "OP_ADD_SHIFT",
                e -> onBasicKey(() -> basicoSetOperacion("+")));
        bind(im, am, KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, InputEvent.SHIFT_DOWN_MASK), "OP_ADD_EQUALS",
                e -> onBasicKey(() -> basicoSetOperacion("+")));

        // Multiplicación: Shift+8 (US) y otros
        bind(im, am, KeyStroke.getKeyStroke(KeyEvent.VK_8, InputEvent.SHIFT_DOWN_MASK), "OP_MUL_8",
                e -> onBasicKey(() -> basicoSetOperacion("*")));
        bind(im, am, KeyStroke.getKeyStroke(KeyEvent.VK_CLOSE_BRACKET, InputEvent.SHIFT_DOWN_MASK), "OP_MUL_BRACKET",
                e -> onBasicKey(() -> basicoSetOperacion("*")));

        // División: tecla / directa (US) o Shift+7 (ES)
        bind(im, am, KeyStroke.getKeyStroke(KeyEvent.VK_SLASH, 0), "OP_DIV_SLASH",
                e -> onBasicKey(() -> basicoSetOperacion("/")));
        bind(im, am, KeyStroke.getKeyStroke(KeyEvent.VK_7, InputEvent.SHIFT_DOWN_MASK), "OP_DIV_7",
                e -> onBasicKey(() -> basicoSetOperacion("/")));

        // Resta: tecla - directa (funciona en ambos)
        bind(im, am, KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, 0), "OP_SUB_MINUS",
                e -> onBasicKey(this::basicoMinusInteligente));

        // Igual: Enter y tecla = (con y sin Shift para compatibilidad)
        bind(im, am, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "EQ_ENTER",
                e -> onBasicKey(this::basicoIgual));
        bind(im, am, KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, 0), "EQ_EQUALS",
                e -> onBasicKey(this::basicoIgual));
        bind(im, am, KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, InputEvent.SHIFT_DOWN_MASK), "EQ_EQUALS_SHIFT",
                e -> onBasicKey(this::basicoIgual));

        // Clear: Escape
        bind(im, am, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "CLR_ESC",
                e -> onBasicKey(this::basicoClear));

        // +/-: F9
        bind(im, am, KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0), "SIGN_F9",
                e -> onBasicKey(this::basicoCambiarSigno));
    }

    private void instalarKeyBindingsAvanzado() {
        JRootPane root = getRootPane();
        InputMap im = root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = root.getActionMap();

        // Operadores (numpad)
        bind(im, am, KeyStroke.getKeyStroke(KeyEvent.VK_ADD, 0), "AV_OP_ADD",
                e -> onAdvancedKey(() -> avanzadoSetOperacion("+")));
        bind(im, am, KeyStroke.getKeyStroke(KeyEvent.VK_SUBTRACT, 0), "AV_OP_SUB",
                e -> onAdvancedKey(() -> avanzadoSetOperacion("-")));
        bind(im, am, KeyStroke.getKeyStroke(KeyEvent.VK_MULTIPLY, 0), "AV_OP_MUL",
                e -> onAdvancedKey(() -> avanzadoSetOperacion("*")));
        bind(im, am, KeyStroke.getKeyStroke(KeyEvent.VK_DIVIDE, 0), "AV_OP_DIV",
                e -> onAdvancedKey(() -> avanzadoSetOperacion("/")));

        // Operadores por carácter - Compatibilidad ES/US
        // Suma
        bind(im, am, KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, InputEvent.SHIFT_DOWN_MASK), "AV_OP_ADD_SHIFT",
                e -> onAdvancedKey(() -> avanzadoSetOperacion("+")));
        bind(im, am, KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, InputEvent.SHIFT_DOWN_MASK), "AV_OP_ADD_EQUALS",
                e -> onAdvancedKey(() -> avanzadoSetOperacion("+")));

        // Multiplicación
        bind(im, am, KeyStroke.getKeyStroke(KeyEvent.VK_8, InputEvent.SHIFT_DOWN_MASK), "AV_OP_MUL_8",
                e -> onAdvancedKey(() -> avanzadoSetOperacion("*")));
        bind(im, am, KeyStroke.getKeyStroke(KeyEvent.VK_CLOSE_BRACKET, InputEvent.SHIFT_DOWN_MASK), "AV_OP_MUL_BRACKET",
                e -> onAdvancedKey(() -> avanzadoSetOperacion("*")));

        // División
        bind(im, am, KeyStroke.getKeyStroke(KeyEvent.VK_SLASH, 0), "AV_OP_DIV_SLASH",
                e -> onAdvancedKey(() -> avanzadoSetOperacion("/")));
        bind(im, am, KeyStroke.getKeyStroke(KeyEvent.VK_7, InputEvent.SHIFT_DOWN_MASK), "AV_OP_DIV_7",
                e -> onAdvancedKey(() -> avanzadoSetOperacion("/")));

        // Resta
        bind(im, am, KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, 0), "AV_OP_SUB_MINUS",
                e -> onAdvancedKey(() -> avanzadoSetOperacion("-")));

        // Igual: Enter y tecla = (con y sin Shift)
        bind(im, am, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "AV_EQ_ENTER",
                e -> onAdvancedKey(this::avanzadoIgual));
        bind(im, am, KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, 0), "AV_EQ_EQUALS",
                e -> onAdvancedKey(this::avanzadoIgual));
        bind(im, am, KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, InputEvent.SHIFT_DOWN_MASK), "AV_EQ_EQUALS_SHIFT",
                e -> onAdvancedKey(this::avanzadoIgual));

        // Clear
        bind(im, am, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "AV_CLR_ESC",
                e -> onAdvancedKey(this::avanzadoClear));
    }

    private void instalarDispatcherBorrado() {
        // Dispatcher global de teclado: funciona aunque el foco esté en botones,
        // y es independiente del layout (ES/US) porque usa el carácter tipeado cuando aplica.
        if (basicDeleteDispatcherInstalled) return;
        basicDeleteDispatcherInstalled = true;

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {

            // Solo si la ventana activa es esta
            Component focusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
            if (focusOwner == null) return false;
            Window w = SwingUtilities.getWindowAncestor(focusOwner);
            if (w != this) return false;

            // No interferir con atajos Ctrl/Alt/Meta (copiar/pegar, etc.)
            int mods = e.getModifiersEx();
            boolean hasCtrlAltMeta =
                    (mods & (InputEvent.CTRL_DOWN_MASK | InputEvent.ALT_DOWN_MASK | InputEvent.META_DOWN_MASK)) != 0;
            if (hasCtrlAltMeta) return false;

            // Referencia rápida al campo avanzado
            boolean focoEnEntradaAvanzada = (focusOwner == txtAvanzadoEntrada);

            // =============== KEY_PRESSED (teclas sin carácter: Enter, Escape, Numpad, Backspace/Delete) ===============
            if (e.getID() == KeyEvent.KEY_PRESSED) {
                int code = e.getKeyCode();

                // Enter => igual
                if (code == KeyEvent.VK_ENTER) {
                    if (currentMode == Mode.BASIC) basicoIgual();
                    else if (currentMode == Mode.ADVANCED) avanzadoIgual();
                    return true;
                }

                // Escape => clear
                if (code == KeyEvent.VK_ESCAPE) {
                    if (currentMode == Mode.BASIC) basicoClear();
                    else if (currentMode == Mode.ADVANCED) avanzadoClear();
                    return true;
                }

                // Backspace/Delete: en BASIC siempre; en ADVANCED solo si NO estás escribiendo en el input
                if (code == KeyEvent.VK_BACK_SPACE || code == KeyEvent.VK_DELETE) {
                    if (currentMode == Mode.BASIC) {
                        basicoBorrarUltimo();
                        return true;
                    }
                    if (currentMode == Mode.ADVANCED && !focoEnEntradaAvanzada) {
                        avanzadoBorrarUltimo();
                        return true;
                    }
                    return false;
                }

                // Operadores del numpad
                if (code == KeyEvent.VK_ADD) {
                    if (currentMode == Mode.BASIC) basicoSetOperacion("+");
                    else if (currentMode == Mode.ADVANCED) avanzadoSetOperacion("+");
                    return true;
                }
                if (code == KeyEvent.VK_SUBTRACT) {
                    if (currentMode == Mode.BASIC) basicoMinusInteligente();
                    else if (currentMode == Mode.ADVANCED) avanzadoSetOperacion("-");
                    return true;
                }
                if (code == KeyEvent.VK_MULTIPLY) {
                    if (currentMode == Mode.BASIC) basicoSetOperacion("*");
                    else if (currentMode == Mode.ADVANCED) avanzadoSetOperacion("*");
                    return true;
                }
                if (code == KeyEvent.VK_DIVIDE) {
                    if (currentMode == Mode.BASIC) basicoSetOperacion("/");
                    else if (currentMode == Mode.ADVANCED) avanzadoSetOperacion("/");
                    return true;
                }

                return false;
            }

            // =============== KEY_TYPED (caracteres: dígitos, + - * /, =, ., ,) ===============
            if (e.getID() == KeyEvent.KEY_TYPED) {
                char ch = e.getKeyChar();

                // En ADVANCED, si estás escribiendo en el input, dejamos pasar dígitos/letras válidas.
                // Pero interceptamos operadores para que NO se inserten como texto.
                if (currentMode == Mode.ADVANCED && focoEnEntradaAvanzada) {
                    // En el input avanzado dejamos teclear los números/letras según base.
                    // Interceptamos operadores para que NO se inserten como texto... excepto el '-' cuando se usa como signo.
                    if (ch == '+' || ch == '*' || ch == '/') {
                        avanzadoSetOperacion(String.valueOf(ch));
                        return true; // consumimos para que no se escriba el símbolo
                    }
                    if (ch == '-') {
                        String t = txtAvanzadoEntrada.getText();
                        boolean permitirComoSigno = t.isEmpty() && (avanzadoAcumulado == null || avanzadoLimpiarEnSiguiente);
                        if (permitirComoSigno) {
                            return false; // dejamos que el DocumentFilter lo gestione como signo negativo
                        }
                        avanzadoSetOperacion("-");
                        return true;
                    }
                    if (ch == '=') {
                        avanzadoIgual();
                        return true;
                    }
                    // Dejar escribir (0-9, A-F, etc.). El DocumentFilter ya filtra por base.
                    return false;
                }

                // BASIC: dígitos
                if (currentMode == Mode.BASIC) {
                    if (ch >= '0' && ch <= '9') {
                        basicoAppend(String.valueOf(ch));
                        return true;
                    }
                    if (ch == '.' || ch == ',') {
                        basicoPunto();
                        return true;
                    }
                    if (ch == '+' || ch == '*' || ch == '/') {
                        basicoSetOperacion(String.valueOf(ch));
                        return true;
                    }
                    if (ch == '-') {
                        basicoMinusInteligente();
                        return true;
                    }
                    if (ch == '=') {
                        basicoIgual();
                        return true;
                    }
                }

                // ADVANCED (foco no está en el input): permitir atajos rápidos con teclado
                if (currentMode == Mode.ADVANCED) {
                    if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
                        avanzadoSetOperacion(String.valueOf(ch));
                        return true;
                    }
                    if (ch == '=') {
                        avanzadoIgual();
                        return true;
                    }
                }

                return false;
            }

            return false;
        });
    }

    private void onBasicKey(Runnable r) {
        if (currentMode != Mode.BASIC) return;
        r.run();
    }

    private void onAdvancedKey(Runnable r) {
        if (currentMode != Mode.ADVANCED) return;
        r.run();
    }

    private void bind(InputMap im, ActionMap am, KeyStroke ks, String name, ActionListener al) {
        im.put(ks, name);
        am.put(name, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                al.actionPerformed(e);
            }
        });
    }

    // =========================================================
    //  Errores
    // =========================================================
    private void mostrarError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // =========================================================s
    //  Main
    // =========================================================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(CalculadoraApp::new);
    }
}
