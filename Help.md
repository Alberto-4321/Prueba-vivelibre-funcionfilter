# Instrucciones

Se pide realizar una función que, a partir de una lista de libros y una cadena de
caracteres, realice lo siguiente:

- Escriba por pantalla los libros que no tengan fecha de publicación
- Devuelva los libros que contengan la cadena de caracteres en el nombre, en el
  resumen y en la biografia del autor del libro. En caso de encontrar más de un
  libro en la lista devolver aquel más recientemente publicado. Además se deberá
  devolver el libro con un campo date adicional que contenga el timestamp con el
  siguiente formato de fecha: mm-dd-yyyy.
- Por último, la lista deberá quedar ordenada de la siguiente manera: Primero
  agrupada por fecha de publicación y luego ordenada por la biografia de autor
  más corta.

La cabecera de la función es la siguiente:

    private Optional<BookDate> filter(String filter, List<Book> books)

Se proporciona una lista de libros de ejemplo: “books.json”