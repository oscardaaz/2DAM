package CalculadoraApp;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;

public class CalculadoraApp extends JFrame {

    // Estado calculadora básica
    private Double acumulado = null;
    private String operacionPendiente = null; // "+", "-", "*", "/"
    private boolean limpiarEnSiguiente = false;

    // Estado conversor
    private int baseActual = 10;

    // UI general
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel pnlCards = new JPanel(cardLayout);
    private final JComboBox<String> cmbModo =
            new JComboBox<>(new String[]{"Básica", "Avanzada"});

    // UI básica
    private final JTextField txtBasicoDisplay = new JTextField();

    // UI avanzada
    private final JTextField txtAvanzadoEntrada = new JTextField();
    private final JTextField txtOutBin = new JTextField();
    private final JTextField txtOutOct = new JTextField();
    private final JTextField txtOutDec = new JTextField();
    private final JTextField txtOutHex = new JTextField();
    private final JLabel lblAvanzadoBase = new JLabel("Base seleccionada: 10");

    private final ButtonGroup buttonGroupBases = new ButtonGroup();
    private final JRadioButton rdbBin = new JRadioButton("BIN");
    private final JRadioButton rdbOct = new JRadioButton("OCT");
    private final JRadioButton rdbDec = new JRadioButton("DEC");
    private final JRadioButton rdbHex = new JRadioButton("HEX");
    private final JButton btnAvClear = new JButton("Clear");

    private final JButton[] btnDig = new JButton[16]; // 0-9, A-F

    // Tamaños recomendados por modo
    private Dimension sizeBasico;
    private Dimension sizeAvanzado;

    public CalculadoraApp() {
        super("Calculadora Básica y Avanzada");

        aplicarLookAndFeel();
        construirUI();
        instalarTecladoBasico();
        instalarFiltroAvanzado();

        // Arranca en básico
        cambiarModoInterno("BASICO");

        // En conversor: base 10 por defecto
        rdbDec.setSelected(true);
        setBaseActual(10);
        actualizarBotonesPorBase();
        convertirYMostrar();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(true);

        calcularTamanosPorModo();

        // Ventana inicial (básica): más compacta
        setMinimumSize(sizeBasico);
        setSize(sizeBasico);
        setLocationRelativeTo(null);
    }

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
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(root);

        cmbModo.setPreferredSize(new Dimension(10, 34));
        cmbModo.addActionListener(e -> {
            if (cmbModo.getSelectedIndex() == 0) cambiarModo("BASICO");
            else cambiarModo("AVANZADO");
        });

        root.add(cmbModo, BorderLayout.NORTH);

        pnlCards.add(crearPanelBasico(), "BASICO");
        pnlCards.add(crearPanelAvanzado(), "AVANZADO");
        root.add(pnlCards, BorderLayout.CENTER);
    }

    // -------------------------
    // Panel Básico
    // -------------------------
    private JPanel crearPanelBasico() {
        JPanel p = new JPanel(new BorderLayout(8, 8));
        p.setOpaque(false);

        // Más pequeño que antes
        txtBasicoDisplay.setHorizontalAlignment(JTextField.RIGHT);
        txtBasicoDisplay.setFont(new Font("SansSerif", Font.PLAIN, 24));
        txtBasicoDisplay.setPreferredSize(new Dimension(10, 48));
        p.add(txtBasicoDisplay, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridBagLayout());
        grid.setOpaque(false);

        Insets in = new Insets(5, 5, 5, 5);

        // Botones más pequeños
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
        JButton bRes = mkBtn("-", e -> basicoSetOperacion("-"));

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

    // -------------------------
    // Panel Avanzado
    // -------------------------
    private JPanel crearPanelAvanzado() {
        JPanel p = new JPanel(new BorderLayout(10, 10));
        p.setOpaque(false);

        JPanel top = new JPanel(new GridBagLayout());
        top.setOpaque(false);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.fill = GridBagConstraints.HORIZONTAL;

        txtAvanzadoEntrada.setFont(new Font("SansSerif", Font.PLAIN, 22));
        txtAvanzadoEntrada.setPreferredSize(new Dimension(10, 48));

        c.gridx = 0; c.gridy = 0; c.gridwidth = 6; c.weightx = 1;
        top.add(txtAvanzadoEntrada, c);

        buttonGroupBases.add(rdbBin);
        buttonGroupBases.add(rdbOct);
        buttonGroupBases.add(rdbDec);
        buttonGroupBases.add(rdbHex);

        rdbBin.addActionListener(e -> setBaseActual(2));
        rdbOct.addActionListener(e -> setBaseActual(8));
        rdbDec.addActionListener(e -> setBaseActual(10));
        rdbHex.addActionListener(e -> setBaseActual(16));

        c.gridy = 1; c.gridwidth = 1; c.weightx = 0;
        c.gridx = 0; top.add(rdbBin, c);
        c.gridx = 1; top.add(rdbOct, c);
        c.gridx = 2; top.add(rdbDec, c);
        c.gridx = 3; top.add(rdbHex, c);

        c.gridx = 4; c.weightx = 1; top.add(new JLabel(""), c);
        c.gridx = 5; c.weightx = 0;
        btnAvClear.setFocusable(false);
        btnAvClear.addActionListener(e -> avanzadoClear());
        top.add(btnAvClear, c);

        c.gridx = 0; c.gridy = 2; c.gridwidth = 6; c.weightx = 1;
        top.add(lblAvanzadoBase, c);

        p.add(top, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);

        GridBagConstraints cc = new GridBagConstraints();
        cc.insets = new Insets(6, 6, 6, 6);
        cc.fill = GridBagConstraints.BOTH;

        // ---- DÍGITOS: un poco más pequeños y ocupan MENOS ancho ----
        JPanel digits = new JPanel(new GridLayout(4, 4, 8, 8));
        digits.setOpaque(false);

        Dimension digSize = new Dimension(52, 38); // más pequeños
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

        // Antes: 0.55 vs 0.45 (ganaban los dígitos)
        // Ahora: 0.40 vs 0.60 (gana la columna de resultados)
        cc.gridx = 0; cc.gridy = 0; cc.weightx = 0.40; cc.weighty = 1.0;
        center.add(digits, cc);

        // ---- RESULTADOS: más anchos, para que "respiren" ----
        JPanel outs = new JPanel(new GridBagLayout());
        outs.setOpaque(false);

        prepararOut(txtOutBin);
        prepararOut(txtOutOct);
        prepararOut(txtOutDec);
        prepararOut(txtOutHex);

        // Darle más presencia a la columna de resultados
        outs.setPreferredSize(new Dimension(340, 10));

        addOutRow(outs, 0, "BIN:", txtOutBin);
        addOutRow(outs, 1, "OCT:", txtOutOct);
        addOutRow(outs, 2, "DEC:", txtOutDec);
        addOutRow(outs, 3, "HEX:", txtOutHex);

        cc.gridx = 1; cc.gridy = 0; cc.weightx = 0.60; cc.weighty = 1.0;
        center.add(outs, cc);

        p.add(center, BorderLayout.CENTER);
        return p;
    }

    private void prepararOut(JTextField tf) {
        tf.setEditable(false);
        tf.setFont(new Font("Monospaced", Font.PLAIN, 15));
        tf.setPreferredSize(new Dimension(280, 34)); // más ancho
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

    // Tamaños mínimos por modo para que nunca se corte
    private void calcularTamanosPorModo() {
        cambiarModoInterno("BASICO");
        pack();
        Dimension dB = getSize();
        sizeBasico = new Dimension(Math.max(300, dB.width), Math.max(520, dB.height));

        cambiarModoInterno("AVANZADO");
        pack();
        Dimension dA = getSize();
        sizeAvanzado = new Dimension(Math.max(560, dA.width), Math.max(520, dA.height));

        cambiarModoInterno("BASICO");
        pack();
    }

    private void cambiarModo(String modo) {
        cambiarModoInterno(modo);

        Dimension min = "BASICO".equals(modo) ? sizeBasico : sizeAvanzado;
        setMinimumSize(min);

        if (getWidth() < min.width || getHeight() < min.height) {
            setSize(Math.max(getWidth(), min.width), Math.max(getHeight(), min.height));
        }
        setLocationRelativeTo(null);
    }

    private void cambiarModoInterno(String modo) {
        cardLayout.show(pnlCards, modo);
        pnlCards.revalidate();
        pnlCards.repaint();
    }

    // =========================
    // Lógica básica
    // =========================
    private void basicoAppend(String s) {
        if (limpiarEnSiguiente) {
            txtBasicoDisplay.setText("");
            limpiarEnSiguiente = false;
        }
        txtBasicoDisplay.setText(txtBasicoDisplay.getText() + s);
    }

    private void basicoPunto() {
        String t = txtBasicoDisplay.getText();
        if (limpiarEnSiguiente) {
            txtBasicoDisplay.setText("0.");
            limpiarEnSiguiente = false;
            return;
        }
        if (!t.contains(".")) basicoAppend(".");
    }

    private Double basicoLeerDisplay() {
        String t = txtBasicoDisplay.getText().trim();
        if (t.isEmpty() || t.equals("-") || t.equals(".")) {
            throw new NumberFormatException("Entrada vacía o incompleta.");
        }
        return Double.parseDouble(t);
    }

    private void basicoSetOperacion(String op) {
        try {
            Double actual = basicoLeerDisplay();
            if (acumulado == null) {
                acumulado = actual;
            } else if (operacionPendiente != null && !limpiarEnSiguiente) {
                acumulado = basicoAplicar(acumulado, actual, operacionPendiente);
                txtBasicoDisplay.setText(formato(acumulado));
            }
            operacionPendiente = op;
            limpiarEnSiguiente = true;
        } catch (NumberFormatException ex) {
            mostrarError("Número inválido.\n" + ex.getMessage());
        } catch (ArithmeticException ex) {
            mostrarError("Error aritmético: " + ex.getMessage());
            basicoClear();
        }
    }

    private Double basicoAplicar(Double a, Double b, String op) {
        switch (op) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/":
                if (b == 0) throw new ArithmeticException("División entre cero.");
                return a / b;
            default:  return b;
        }
    }

    private void basicoIgual() {
        try {
            if (operacionPendiente == null || acumulado == null) return;
            Double actual = basicoLeerDisplay();
            Double res = basicoAplicar(acumulado, actual, operacionPendiente);
            txtBasicoDisplay.setText(formato(res));
            acumulado = null;
            operacionPendiente = null;
            limpiarEnSiguiente = true;
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
    }

    private void basicoCambiarSigno() {
        String t = txtBasicoDisplay.getText().trim();
        if (t.isEmpty()) return;
        txtBasicoDisplay.setText(t.startsWith("-") ? t.substring(1) : "-" + t);
    }

    private String formato(Double d) {
        if (d == null) return "";
        if (Math.abs(d - Math.rint(d)) < 1e-10) return String.valueOf(d.longValue());
        return String.valueOf(d);
    }

    // =========================
    // Lógica avanzada
    // =========================
    private void setBaseActual(int base) {
        this.baseActual = base;
        lblAvanzadoBase.setText("Base seleccionada: " + base);
        actualizarBotonesPorBase();

        String txt = txtAvanzadoEntrada.getText().toUpperCase();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < txt.length(); i++) {
            char ch = txt.charAt(i);
            int val = -1;
            if (ch >= '0' && ch <= '9') val = ch - '0';
            else if (ch >= 'A' && ch <= 'F') val = 10 + (ch - 'A');
            if (val >= 0 && val < baseActual) sb.append(ch);
        }
        txtAvanzadoEntrada.setText(sb.toString());
        convertirYMostrar();
    }

    private void actualizarBotonesPorBase() {
        for (int i = 0; i < 16; i++) {
            btnDig[i].setEnabled(i < baseActual);
        }
    }

    private void avanzadoAppend(String s) {
        txtAvanzadoEntrada.setText(txtAvanzadoEntrada.getText() + s);
        convertirYMostrar();
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
        txtAvanzadoEntrada.setText("");
        convertirYMostrar();
    }

    // =========================
    // Validaciones / teclado
    // =========================
    private void instalarTecladoBasico() {
        txtBasicoDisplay.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (Character.isDigit(c) || c == '.' || c == '-' || c == '+' || c == '*' || c == '/'
                        || c == '\b' || c == '\n') {
                    return;
                }
                e.consume();
                Toolkit.getDefaultToolkit().beep();
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    basicoIgual();
                    e.consume();
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    basicoClear();
                    e.consume();
                }
            }
        });
    }

    private void instalarFiltroAvanzado() {
        ((AbstractDocument) txtAvanzadoEntrada.getDocument()).setDocumentFilter(new DocumentFilter() {

            private boolean esValido(char c, int base) {
                c = Character.toUpperCase(c);
                if (c >= '0' && c <= '9') return (c - '0') < base;
                if (c >= 'A' && c <= 'F') return (10 + (c - 'A')) < base;
                return false;
            }

            private String filtrar(String text) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < text.length(); i++) {
                    char c = Character.toUpperCase(text.charAt(i));
                    if (esValido(c, baseActual)) sb.append(c);
                }
                return sb.toString();
            }

            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                if (string == null) return;
                super.insertString(fb, offset, filtrar(string), attr);
                SwingUtilities.invokeLater(() -> convertirYMostrar());
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                if (text == null) return;
                super.replace(fb, offset, length, filtrar(text), attrs);
                SwingUtilities.invokeLater(() -> convertirYMostrar());
            }

            @Override
            public void remove(FilterBypass fb, int offset, int length)
                    throws BadLocationException {
                super.remove(fb, offset, length);
                SwingUtilities.invokeLater(() -> convertirYMostrar());
            }
        });

        txtAvanzadoEntrada.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = Character.toUpperCase(e.getKeyChar());
                if (c == KeyEvent.CHAR_UNDEFINED) return;

                boolean ok = false;
                if (c >= '0' && c <= '9') ok = (c - '0') < baseActual;
                else if (c >= 'A' && c <= 'F') ok = (10 + (c - 'A')) < baseActual;
                else if (c == '\b') ok = true;

                if (!ok) {
                    e.consume();
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        });
    }

    private void mostrarError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalculadoraApp().setVisible(true));
    }
}
