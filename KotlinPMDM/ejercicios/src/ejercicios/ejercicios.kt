package ejercicios

import java.sql.SQLOutput
import kotlin.math.exp
import kotlin.math.pow
import kotlin.random.Random

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
    //ejercicio11()
    //ejercicio12()
    //ejercicio13()
    //ejercicio14()
    //ejercicio15()
    //ejercicio16()
    //ejercicio17()
    //ejercicio18()
    //ejercicio19()
    //ejercicio20()
    //ejercicio21()
    ejercicio22()
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
    //val suma = a + b;
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
    var numero = 1
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

fun ejercicio10() {

    do {
        print("\nIntroduce un numero: ")
        val numero = readln().toInt()
        if (numero >= 0) println("Tu numero es mayor o igual que 0 (cero), Enhorabuena!! ")
        else println("Tu numero es menor de 0. sigue intentandolo: ")
    } while (numero < 0)
}

fun ejercicio11() {

    val contrasena = "prueba"
    for (i in 2 downTo 0) {
        print("Introduce la contraseña: ")
        val respuesta = readln()
        if (respuesta.equals(contrasena)) {
            println("Enhorabuena, acertaste"); break
        } else println("Has fallado. Te quedan $i intentos")
        if (i == 0) println("Numero maximo de intentos superado")
    }
}

fun ejercicio12() {

    print("\nIntroduce un mes (con numero del 1 al 12): ")
    val mes = readln().toInt()
    val respuesta = when (mes) {
        1 -> "El mes de enero tiene 31 dias"
        2 -> "El mes de febrero tiene 28 o 29 dias si es bisiesto"
        3 -> "El mes de marzo tiene 31 dias"
        4 -> "El mes de abril tiene 30 dias"
        5 -> "El mes de mayo tiene 31 dias"
        6 -> "El mes de junio tiene 30 dias"
        7 -> "El mes de julio tiene 31 dias"
        8 -> "El mes de agosto tiene 31 dias"
        9 -> "El mes de septiembre tiene 30 dias"
        10 -> "El mes de octubre tiene 31 dias"
        11 -> "El mes de noviembre tiene 30 dias"
        12 -> "El mes de diciembre tiene 31 dias"
        else -> "Mes invalido\n"
    }
    println("\n$respuesta")
}

fun ejercicio13() {
    print("\nIntroduce un dia de la semana: ")
    val dia = readln().trim().lowercase()

    if (dia.equals("sabado") || dia.equals("domingo"))
        println("Es fin de semana disfrutalo")
    else println("Es un dia laborable, a currar")
}

fun ejercicio14() {
    print("\nCuantas ventas ha realizado el comercial en los ultimos 15 dias: ")
    val ventas = readln().trim().toInt()
    //var contador = 1
    var total = 0.0
    /*do {
        println("Introduce el precio de la venta $contador")
        val cantidad = readln().toDouble()
        total += cantidad
        contador++
    }while(contador <= ventas)*/

    for (i in 1..ventas) {
        println("Introduce el precio de la venta $i ")
        val cantidad = readln().toDouble()
        total += cantidad
    }
    val redondeado = String.format("El precio total asciende a %.2f €", total)
    println(redondeado)
}

fun ejercicio15() {

    var contador = 1
    var total = 0.0
    do {
        print("Introduce el precio de la venta $contador (Para salir introduce '*' ): ")
        val auxiliar = readln()
        if (auxiliar.trim() == "*") {
            println("Saliendo..."); break
        }
        val cantidad = auxiliar.toDouble()
        total += cantidad
        contador++
    } while (auxiliar != "*".trim())
    val redondeado = String.format("\nEl precio total asciende a %.2f €", total)
    println(redondeado)
}

fun ejercicio16() {

    print("\nIntroduce un numero entero: ")
    val a = readln().toInt()
    print("Introduce otro numero entero: ")
    val b = readln().toInt()
    var contador = 1
    println("\n10 numeros aleatorios entre $a y $b :")
    do {
        val aleatorio: Int
        if (a <= b) {
            aleatorio = Random.nextInt(a, b + 1) //Incluye inicio excluye final
            //val aleatorio2 = (a..b).random() //Incluye ambos rangos
            //println("Numero $contador : $aleatorio")
        } else {
            aleatorio = Random.nextInt(b, a + 1)
            //println("Numero $contador : $aleatorio")
        }
        //println("Numero $contador : $aleatorio")
        println(String.format("Numero %-3d: %-3d", contador, aleatorio))
        //val mensaje = String.format("Numero %-3d %-3d%n",contador,aleatorio)
        //print(mensaje)
        contador++
    } while (contador <= 10)
}

fun ejercicio17() {
    println("Introduce cualquier cadena de caracteres: ")
    val texto = readln()

    var vocales = 0
    var consonantes = 0
    var numeros = 0
    var espacios = 0
    var i = 0

    for (caracter in texto) {

        if (caracter in "aeiouáéíóúüAEIOUÁÉÍÓÚÜ") {
            vocales++
        } else if (caracter.isLetter()) {
            consonantes++
        } else if (caracter.isDigit()) {
            numeros++
        } else if (caracter.isWhitespace()) {
            espacios++
        }
        i++
    }

    println("Vocales: $vocales")
    println("Consonantes: $consonantes")
    println("Números: $numeros")
    println("Espacios: $espacios")
}


fun ejercicio18() {

}

fun ejercicio19() {

    print("\nIntroduce el primer numero: ")
    var a = readln().toDouble()
    print("Introduce el segundo numero: ")
    var b = readln().toDouble()

    do {
        println("\n-- Elige una opcion --")
        println("1: Suma los operandos")
        println("2: Resta los operandos")
        println("3: Multiplica los operandos")
        println("4: Divide los operandos")
        println("5: Potencia (1º operando como base y 2º como exponente)")
        println("6: módulo , resto de la división entre operando1 y operando2.")
        println("7: pedir 2 operandos nuevos")
        println("8: Salir")
        print("Elige una opcion: ")
        var opcion = readln()
        when (opcion) {

            "1" -> println("\nLa suma de $a + $b es: ${(a + b)}")
            "2" -> println("\nLa resta de $a - $b es: ${a - b}")
            "3" -> println("\nLa multiplicacion de $a * $b es: ${a * b}")
            "4" -> println("\nLa division de $a / $b es: ${a / b}")
            "5" -> println("\nLa potencia de ${a.toInt()} elevado a ${b.toInt()} es: ${a.pow(b)}")
            "6" -> println("\nEl modulo(resto) de $a / $b es: ${a % b}")
            "7" -> {
                println("\nIntroduce otro primer numero: ")
                a = readln().toDouble()
                println("Introduce otro segundo numero: ")
                b = readln().toDouble()
            }
            "8" -> println("\nSaliendo...")
            else -> println("Opcion no valida")

        }
    } while (opcion != "8")

}

fun ejercicio20(){
    print("Introduce un numero entero mayor que 1: ")
    val n = readln().toInt()
    print("La suma desde 1 hasta $n es: ${n*(n+1)/2}")
}

fun ejercicio21(){

    print("Introduce una cadena de texto: ")
    var cadena = readln()
    cadena = cadena.replace(" ", "")
    print(cadena)
}

fun ejercicio22(){
    var cadenaTotal = ""
    do {
        println("Introduce un texto, cadena vacía para terminar")
        val cadena = readln().trim()
        cadenaTotal += "\t$cadena\n"
        //cadenaTotal += "\n\t"
    }while (cadena != "")
    println("Cadena resultante:\n--------------------------------------------")
    println(cadenaTotal)
}