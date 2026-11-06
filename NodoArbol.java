package inventario; // Declara que la clase pertenece al paquete inventario

public class NodoArbol { // Nodo del Arbol Binario de Busqueda ordenado por total semanal
    int codigo; // Codigo del producto almacenado en el nodo
    double total; // Total semanal acumulado, es la clave que ordena el arbol
    NodoArbol izquierdo; // Referencia al hijo izquierdo (totales menores)
    NodoArbol derecho; // Referencia al hijo derecho (totales mayores o iguales)

    NodoArbol(int codigo, double total) { // Constructor que recibe el codigo y el total
        this.codigo = codigo; // Guarda el codigo del producto
        this.total = total; // Guarda el total semanal del producto
        this.izquierdo = null; // El nodo nuevo nace sin hijo izquierdo
        this.derecho = null; // El nodo nuevo nace sin hijo derecho (es una hoja)
    }
}
