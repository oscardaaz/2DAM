package ejercicios

import com.sun.jdi.IntegerValue
import java.util.Locale
import java.util.Locale.getDefault


fun main() {

    //ejercicio1()
    //ejercicio2()
    //ejercicio3()
    //ejercicio4()
    //ejercicio5()
    //ejercicio6()
    //ejercicio7()
    //ejercicio8()
    //ejercicio9()
    //ejercicio10()
    ejercicio11()
}

fun ejercicio1() {

    val a = 6
    val b = 2
    println("Suma:  ${a + b}")
    println("Resta:  ${a - b}")
    println("Multiplicacion:  ${a * b}")
    println("Division:  ${a / b}")
    println("Modulo:  ${a % b}")


}

fun ejercicio2() {

    val nombre = "Oscar"
    println("Bienvenido, $nombre")

}

fun ejercicio3() {

    print("Introduce tu nombre: ")
    val nombre = readln() + "\n"
    println("Bienvenido, $nombre")

}

fun ejercicio4() {

    print("\nIntroduce un numero a: ")
    val a = readln().toInt()
    print("Introduce un numero b: ")
    val b = readln().toInt()
    val suma = a + b;
    if (a > b) println("El numero mayor es el a: $a")
    else if (a == b) println("Los numeros $a y $b son iguales")
    else println("El numero mayor es el b: $b")

}

fun ejercicio5() {

    print("\nIntroduce un numero entero: ")
    val numero = readln().toInt()
    if (numero % 2 == 0) println("Tu numero es divisible entre dos (Es par)")
    else println("Tu numero no es divisible entre 2 (No es par)")

}

fun ejercicio6() {

    do {
        val pregunta = """
            ¿Cual es la capital de Castilla y Leon?
                a -> Madrid
                b -> Zaragoza
                c -> Valladolid
                d -> Desconocida
            """.trimIndent()
        println(pregunta)
        print("Elige tu respuesta: ")
        val respuesta = readln().lowercase()
        if (respuesta != "d") println("\nIncorrecta, intentelo de nuevo\n")
        else println("\nRespuesta correcta")
    } while (respuesta != "d")


}

fun ejercicio7() {
    println("\nNumeros del 1 al 100 con bucle for: \n")
    for (numero in 1..100)
        println("   Número: $numero")
}

fun ejercicio8() {
    var numero = 1;
    println("\nNumeros del 1 al 100 con bucle while: \n")
    while (numero <= 100) {
        println(numero)
        numero++
    }

    /*println("\nNumeros del 1 al 100 con bucle do-while: \n")
    do {
        println(numero++)
        // O tambien
        //println(numero)
        //numero++

    }while (numero <= 100)*/

}

fun ejercicio9() {

    for (numero in 100 downTo 1)
        if (numero % 2 == 0 && numero % 3 == 0)
        println(numero)
}

fun ejercicio10(){

    do {
        print("\nIntroduce un numero: ")
        val numero = readln().toInt()
        if (numero >= 0) println("Tu numero es mayor o igual que 0 (cero), Enhorabuena!! ")
        else println("Tu numero es menor de 0. sigue intentandolo: ")
    }while (numero < 0)
}

fun ejercicio11(){

    val contrasena = "prueba"
    for (i in 2 downTo 0) {
        print("Introduce la contraseña: ")
        val respuesta = readln()
            if (respuesta.equals(contrasena)){ println("Enhorabuena, acertaste") ;break}
            else println("Has fallado. Te quedan $i intentos")
        if (i == 0) println("Numero maximo de intentos superado")
    }




}