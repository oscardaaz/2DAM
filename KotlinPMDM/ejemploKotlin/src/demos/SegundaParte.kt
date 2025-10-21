package demos

fun main() {

//    println(saludo())
//    println(saludo("Oscar Dominguez"))

    //otra(numero = 48)

    print(euro2dollar(2.00))

}

//TODO Argumentos por defecto (default arguments)
fun saludo(nombre: String = "Mundo"): String {
    return "Hola, $nombre"
}

//TODO Object = Any
// Argumentos nombrados (named arguments)
fun otra(letra: Char = 'a', numero: Int = 15): Unit {
    //Acciones
    println("$letra $numero")
}

//TODO Funciones de expresión (single-line or one-line functions)
fun euro2dollar(euro: Double):Double {
    return euro * 1.18
}

fun euro2dollar_v2(euro: Double):Double = euro * 1.18