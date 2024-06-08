public class Nodo {
    String id;
    String nombre;
    int edad; // Este atributo se usará solo para los usuarios

    public Nodo(String id, String nombre, int edad) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
    }

    public Nodo(String id, String nombre) {
        this(id, nombre, -1); // Se asigna -1 para los libros ya que no se usa la edad
    }
}
