package calculadora;

import java.util.*;
import java.text.DecimalFormat;

/**
 * Motor de cálculo profesional con soporte para:
 * - Operaciones básicas y científicas
 * - Conversión de bases numéricas
 * - Historial de operaciones
 * - Memoria de valores
 * - Funciones trigonométricas y logarítmicas
 */
public class Calculadora {

    private double resultado = 0;
    private double memoria = 0;
    private String operador = "";
    private boolean nuevoNumero = true;
    private boolean modoRadianes = true;
    private List<String> historial = new ArrayList<>();
    private DecimalFormat formatter = new DecimalFormat("#.##########");

    // Stack para evaluación de expresiones
    private Stack<Double> valores = new Stack<>();
    private Stack<String> operadores = new Stack<>();

    public Calculadora() {
        formatter.setGroupingUsed(false);
    }

    // ============ OPERACIONES BÁSICAS ============

    public double calcular(double valor) {
        double res = resultado;

        switch(operador) {
            case "+":
                res = resultado + valor;
                agregarHistorial(resultado + " + " + valor + " = " + res);
                break;
            case "-":
                res = resultado - valor;
                agregarHistorial(resultado + " - " + valor + " = " + res);
                break;
            case "*":
                res = resultado * valor;
                agregarHistorial(resultado + " × " + valor + " = " + res);
                break;
            case "/":
                if(valor == 0) {
                    agregarHistorial(resultado + " ÷ " + valor + " = Error");
                    return Double.NaN;
                }
                res = resultado / valor;
                agregarHistorial(resultado + " ÷ " + valor + " = " + res);
                break;
            case "^":
                res = Math.pow(resultado, valor);
                agregarHistorial(resultado + " ^ " + valor + " = " + res);
                break;
            case "mod":
                res = resultado % valor;
                agregarHistorial(resultado + " mod " + valor + " = " + res);
                break;
            case "":
                res = valor;
                break;
        }

        resultado = res;
        return resultado;
    }

    // ============ FUNCIONES CIENTÍFICAS ============

    public double seno(double valor) {
        double rad = modoRadianes ? valor : Math.toRadians(valor);
        double res = Math.sin(rad);
        agregarHistorial("sin(" + valor + ") = " + res);
        return res;
    }

    public double coseno(double valor) {
        double rad = modoRadianes ? valor : Math.toRadians(valor);
        double res = Math.cos(rad);
        agregarHistorial("cos(" + valor + ") = " + res);
        return res;
    }

    public double tangente(double valor) {
        double rad = modoRadianes ? valor : Math.toRadians(valor);
        double res = Math.tan(rad);
        agregarHistorial("tan(" + valor + ") = " + res);
        return res;
    }

    public double logaritmo(double valor) {
        if(valor <= 0) return Double.NaN;
        double res = Math.log10(valor);
        agregarHistorial("log(" + valor + ") = " + res);
        return res;
    }

    public double logaritmoNatural(double valor) {
        if(valor <= 0) return Double.NaN;
        double res = Math.log(valor);
        agregarHistorial("ln(" + valor + ") = " + res);
        return res;
    }

    public double raizCuadrada(double valor) {
        if(valor < 0) return Double.NaN;
        double res = Math.sqrt(valor);
        agregarHistorial("√(" + valor + ") = " + res);
        return res;
    }

    public double potencia(double base, double exponente) {
        double res = Math.pow(base, exponente);
        agregarHistorial(base + " ^ " + exponente + " = " + res);
        return res;
    }

    public double factorial(double valor) {
        if(valor < 0 || valor != (int)valor) return Double.NaN;
        int n = (int)valor;
        if(n > 170) return Double.POSITIVE_INFINITY; // Límite para evitar overflow

        double res = 1;
        for(int i = 2; i <= n; i++) {
            res *= i;
        }
        agregarHistorial(n + "! = " + res);
        return res;
    }

    public double reciproco(double valor) {
        if(valor == 0) return Double.NaN;
        double res = 1.0 / valor;
        agregarHistorial("1/" + valor + " = " + res);
        return res;
    }

    // ============ CONVERSIONES DE BASE ============

    public String toBinary(long n) {
        return Long.toBinaryString(n);
    }

    public String toOctal(long n) {
        return Long.toOctalString(n);
    }

    public String toDecimal(long n) {
        return String.valueOf(n);
    }

    public String toHex(long n) {
        return Long.toHexString(n).toUpperCase();
    }

    public long fromBinary(String bin) {
        try {
            return Long.parseLong(bin, 2);
        } catch(NumberFormatException e) {
            return 0;
        }
    }

    public long fromOctal(String oct) {
        try {
            return Long.parseLong(oct, 8);
        } catch(NumberFormatException e) {
            return 0;
        }
    }

    public long fromHex(String hex) {
        try {
            return Long.parseLong(hex, 16);
        } catch(NumberFormatException e) {
            return 0;
        }
    }

    // ============ OPERACIONES LÓGICAS (para programadores) ============

    public long and(long a, long b) {
        return a & b;
    }

    public long or(long a, long b) {
        return a | b;
    }

    public long xor(long a, long b) {
        return a ^ b;
    }

    public long not(long a) {
        return ~a;
    }

    public long leftShift(long a, int bits) {
        return a << bits;
    }

    public long rightShift(long a, int bits) {
        return a >> bits;
    }

    // ============ MEMORIA ============

    public void memoriaGuardar(double valor) {
        memoria = valor;
        agregarHistorial("M← " + valor);
    }

    public void memoriaSumar(double valor) {
        memoria += valor;
        agregarHistorial("M+ " + valor);
    }

    public void memoriaRestar(double valor) {
        memoria -= valor;
        agregarHistorial("M- " + valor);
    }

    public double memoriaRecuperar() {
        agregarHistorial("MR → " + memoria);
        return memoria;
    }

    public void memoriaLimpiar() {
        memoria = 0;
        agregarHistorial("MC");
    }

    public double getMemoria() {
        return memoria;
    }

    // ============ HISTORIAL ============

    private void agregarHistorial(String operacion) {
        historial.add(operacion);
        if(historial.size() > 100) {
            historial.remove(0);
        }
    }

    public List<String> getHistorial() {
        return new ArrayList<>(historial);
    }

    public void limpiarHistorial() {
        historial.clear();
    }

    // ============ UTILIDADES ============

    public void reiniciar() {
        resultado = 0;
        operador = "";
        nuevoNumero = true;
    }

    public void reiniciarCompleto() {
        reiniciar();
        memoria = 0;
        historial.clear();
    }

    public String formatear(double valor) {
        if(Double.isNaN(valor)) return "Error";
        if(Double.isInfinite(valor)) return "∞";
        return formatter.format(valor);
    }

    // ============ GETTERS Y SETTERS ============

    public void setOperador(String op) {
        operador = op;
    }

    public String getOperador() {
        return operador;
    }

    public void setResultado(double r) {
        resultado = r;
    }

    public double getResultado() {
        return resultado;
    }

    public boolean isNuevoNumero() {
        return nuevoNumero;
    }

    public void setNuevoNumero(boolean nuevo) {
        nuevoNumero = nuevo;
    }

    public boolean isModoRadianes() {
        return modoRadianes;
    }

    public void setModoRadianes(boolean radianes) {
        modoRadianes = radianes;
    }

    // ============ CONSTANTES ============

    public double getPi() {
        return Math.PI;
    }

    public double getE() {
        return Math.E;
    }
}