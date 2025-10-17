package demos

fun main() {

    println()
    println("Hola Mundo")

    variables()
    otra()
}


fun arrays() {
    val coches = arrayOf("Volvo", "Tesla", "Mercedes")
    //val coches2 = arrayOf<String>("Volvo","Tesla","Mercedes")
    val numeros = arrayOf(1, 2, 3)
    val numeros2 = intArrayOf(1, 2, 3)
    val otro = booleanArrayOf(true, false)
    val otro2 = charArrayOf('c')
    //............
    //Podemos tener valores de distintos tipos
    val otro3 = arrayOf(1, 2, 3, "cadena", true)

    //Con el constructor de Array
    val arr1 = IntArray(size = 5);
    val arr2 = IntArray(size = 5) { 0 }
    val arr3 = Array(size = 5) { 0 }
    val arr4 = Array(size = 5) { it + 1 } //1, 2, 3, 4, 5
    val arr5 = Array(size = 5) { it -> it + 1 } //1, 2, 3, 4, 5
    val arr6 = Array(size = 5) { a -> a + 1 } //1, 2, 3, 4, 5

    //Modificar indices

    arr5[0] = 25;
    //arr5.set(1,25)    //Preferida la sintaxis con corchetes []

    println(arr5[0])
    //println(arr5.get(0))  //Preferida la sintaxis con corchetes []

    println(arr5.size) //Mostrar el tamaño del array, en java el arr5.length();


}

fun inicializarValores(a: Int) {
    val a = a + 1
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

fun otra() {
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




