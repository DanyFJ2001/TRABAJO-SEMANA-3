package inventario; // Declara que la clase pertenece al paquete inventario

public class Producto { // Clase simple que agrupa los datos de un producto
    int id; // Codigo del producto de tipo entero
    String nombre; // Nombre del producto de tipo texto

    Producto(int id, String nombre) { // Constructor que recibe el codigo y el nombre
        this.id = id; // Guarda el codigo recibido en el atributo id
        this.nombre = nombre; // Guarda el nombre recibido en el atributo nombre
    }
}
