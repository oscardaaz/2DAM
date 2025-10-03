package demos

fun main() {

    println()
    println("Hola Mundo")

    variables()
    otra()
}



fun variables() {

    //Java
    //final int X;
    //final int Y = 1;

    //Kotlin

    /*val x: Int
    val y = 1.00
    val z: Int
        //Hay que inicializarla o ponerle un tipo, no puedes hacer como en la variable Z
    z = 1

    int w;
    int z = 2;
    z = 3;
    w = 1;*/

    //Variables en Kotlin son inmutables, no se pueden modificar
    /*val w = 2
    val z = 2
    w = 3;
    //TODO revisar

    val s = "pepe"
    val d = 3.1416

    val prueba2: String = null // No se puede dejar null, tienes que asignarle "?"
    val prueba: String? = null

    //Nombres de variables no validos (Suelen ser como en java)
    val 99problems
    val _99problems: String
    //TODO etc.*/
}
    fun otra(){
        println("Escribe algo")
        val nombre2 = readln()

        println("Hola\n")
        println("Hola $nombre2\n")
        println("La cadena $nombre2 tiene ${nombre2.length}\t")

        val cadena = """
            Hola aqui puedo poner lo que quiera
            y respeta los saltos de linea
            y esas cosas varias 
            """.trimIndent()
        println(cadena)
    }




