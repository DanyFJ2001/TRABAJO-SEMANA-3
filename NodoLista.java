package inventario; // Declara que la clase pertenece al paquete inventario

public class NodoLista { // Nodo de la Lista Enlazada Simple de Alertas de Reposicion
    Producto dato; // Campo dato: informacion del producto que requiere reposicion
    double total; // Total semanal acumulado del producto
    NodoLista siguiente; // Puntero al siguiente nodo de la lista

    NodoLista(Producto dato, double total) { // Constructor que recibe el producto y su total
        this.dato = dato; // Guarda el producto dentro del nodo
        this.total = total; // Guarda el total semanal dentro del nodo
        this.siguiente = null; // Al crearse, el nodo todavia no apunta a ningun otro nodo
    }
}
