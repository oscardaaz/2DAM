package demos

fun main() {
    //No hace falta new
    val libro = Book("asdasdd", 451)
    val libro2 = Book("asdasdd", 4561)

    println(libro)
    println(libro2)
//Llamar al set de titulo (setTitulo)
    libro.titulo = "asd"
    //libro.isbn llamaría a setIsbn
    libro2.titulo = "asd"
    println(libro)
    println(libro2)
    if (libro.equals(libro2)) println("error")
    else print("error2")
//libro = libro2
}

//TODO Opcional la palabra constructor en el
//TODO Constructor primario
//Class Book constructor(
//El compilador crea automaticamente:
// getters, setters, equals(), hascode(), toString(), copy()
data class Book(
    //val tiene get y var set y get
    var titulo: String,
    var isbn: Long
)//TODO