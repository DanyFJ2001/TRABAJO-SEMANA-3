package inventario; // Declara que la clase pertenece al paquete inventario

import java.util.Locale; // Importa Locale para usar el punto como separador decimal
import java.util.Scanner; // Importa Scanner para leer datos desde el teclado

public class SistemaInventario { // Clase princpal del Sistema de Inventario y Auditoria
    static final int DIAS = 5; // Constante con los dias registrados (Lunes a Viernes)
    static final double UMBRAL_MINIMO = 500; // Umbral minimo de unidades para generar una alerta

    public static void main(String[] args) { // Metodo principal donde inicia la ejecucion
        Locale.setDefault(Locale.US); // Usa punto decimal al leer y mostrar numeros
        Scanner sc = new Scanner(System.in); // Crea el lector de los datos ingresados por teclado
        String[] nombresDias = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes"}; // Nombres de los 5 dias

        System.out.println("=== SISTEMA INTEGRADO DE INVENTARIO Y AUDITORIA ==="); // Muestra el titulo
        System.out.print("Cantidad de productos (N): "); // Solicita la cantidad de productos
        int n = sc.nextInt(); // Lee y guarda N

        while (n <= 0) { // Repite la lectura si N no es un numero positivo
            System.out.print("Ingrese una cantidad mayor que cero: "); // Informa el valor esperado
            n = sc.nextInt(); // Lee nuevamente N
        }

        Producto[] productos = new Producto[n]; // Arreglo estatico de N objetos Producto
        double[][] ventas = new double[n][DIAS]; // Matriz homogenea de N x 5 con las ventas diarias

        for (int i = 0; i < n; i++) { // Recorre cada producto que se va a registrar
            System.out.println("\nPRODUCTO " + (i + 1)); // Muestra el numero del producto
            System.out.print("Codigo: "); // Solicita el codigo
            int id = sc.nextInt(); // Lee el codigo como entero
            sc.nextLine(); // Limpia el salto de linea que dejo nextInt
            System.out.print("Nombre: "); // Solicita el nombre
            String nombre = sc.nextLine(); // Lee el nombre completo, incluso con espacios
            productos[i] = new Producto(id, nombre); // Crea el objeto y lo guarda en el arreglo

            for (int j = 0; j < DIAS; j++) { // Recorre los 5 dias de la semana
                System.out.print("Ventas del " + nombresDias[j] + ": "); // Solicita la venta del dia
                ventas[i][j] = sc.nextDouble(); // Guarda la venta en la fila i, columna j
                while (ventas[i][j] < 0) { // Repite la lectura si la venta es negativa
                    System.out.print("Ingrese un valor mayor o igual a cero: "); // Informa el valor esperado
                    ventas[i][j] = sc.nextDouble(); // Lee nuevamente la venta
                }
            }
        }

        double[] totales = calcularTotales(ventas); // Calcula el acumulado semanal de cada producto
        mostrarTotales(productos, ventas, totales); // Muestra la matriz y los totales

        NodoLista cabeza = null; // Puntero de entrada (head): la lista inicia vacia
        for (int i = 0; i < n; i++) { // Recorre todos los productos
            if (totales[i] < UMBRAL_MINIMO) { // Verifica si el total esta bajo el umbral
                cabeza = insertarAlerta(cabeza, productos[i], totales[i]); // Inserta el producto en la lista
            }
        }
        mostrarAlertas(cabeza); // Recorre y muestra la lista de alertas

        NodoArbol raiz = null; // Referencia a la raiz: el arbol inicia vacio
        for (int i = 0; i < n; i++) { // Recorre todos los productos
            raiz = insertarArbol(raiz, productos[i].id, totales[i]); // Inserta el producto en el arbol
        }

        System.out.println("\n=== REPORTE CONSOLIDADO (MENOR A MAYOR VENTA) ==="); // Muestra el titulo del reporte
        System.out.printf("%-10s %10s%n", "Codigo", "Total"); // Muestra los encabezados del reporte
        recorrerInorden(raiz); // Recorre el arbol en Inorden e imprime cada nodo
        sc.close(); // Cierra el lector de teclado
    }

    static double[] calcularTotales(double[][] ventas) { // Calcula el total de cada fila de la matriz
        double[] totales = new double[ventas.length]; // Crea un arreglo con un total por producto
        for (int i = 0; i < ventas.length; i++) { // Bucle externo: recorre las filas (productos)
            for (int j = 0; j < ventas[i].length; j++) { // Bucle interno: recorre las columnas (dias)
                totales[i] += ventas[i][j]; // Acumula la venta del dia en el total del producto
            }
        }
        return totales; // Devuelve el arreglo con los totales
    }

    static void mostrarTotales(Producto[] productos, double[][] ventas, double[] totales) { // Imprime la matriz
        System.out.println("\n=== VENTAS SEMANALES Y TOTAL ACUMULADO ==="); // Muestra el titulo
        System.out.printf("%-6s %-20s %8s %8s %8s %8s %8s %10s%n", "Cod", "Nombre",
                "Lun", "Mar", "Mie", "Jue", "Vie", "Total"); // Muestra los encabezados de la tabla
        for (int i = 0; i < productos.length; i++) { // Recorre las filas de la matriz
            System.out.printf("%-6d %-20s", productos[i].id, productos[i].nombre); // Imprime codigo y nombre
            for (int j = 0; j < ventas[i].length; j++) { // Recorre las columnas de la fila
                System.out.printf(" %8.2f", ventas[i][j]); // Imprime la venta del dia
            }
            System.out.printf(" %10.2f%n", totales[i]); // Imprime el total y salta de linea
        }
    }

    static NodoLista insertarAlerta(NodoLista cabeza, Producto dato, double total) { // Inserta al inicio
        NodoLista nuevo = new NodoLista(dato, total); // Paso 1: crea el nodo en memoria dinamica
        nuevo.siguiente = cabeza; // Paso 2: el nuevo nodo apunta al que era el primero
        return nuevo; // Paso 3: el nuevo nodo pasa a ser la cabeza de la lista
    }

    static void mostrarAlertas(NodoLista cabeza) { // Recorre la lista de alertas
        System.out.println("\n=== ALERTAS DE REPOSICION (TOTAL < " + UMBRAL_MINIMO + ") ==="); // Muestra el titulo
        if (cabeza == null) { // Verifica si la lista esta vacia
            System.out.println("No hay productos que requieran reabastecimiento."); // Informa que no hay alertas
        }
        NodoLista actual = cabeza; // Puntero auxiliar que inicia en la cabeza
        while (actual != null) { // Avanza mientras exista un nodo por visitar
            System.out.printf("Codigo: %-6d Producto: %-20s Total: %.2f%n",
                    actual.dato.id, actual.dato.nombre, actual.total); // Imprime los datos del nodo actual
            actual = actual.siguiente; // Mueve el auxiliar al siguiente nodo
        }
    }

    static NodoArbol insertarArbol(NodoArbol raiz, int codigo, double total) { // Inserta de forma recursiva
        if (raiz == null) { // Caso base: se encontro una posicion vacia
            return new NodoArbol(codigo, total); // Crea el nodo y lo devuelve como nueva hoja
        }
        if (total < raiz.total) { // Si el total es menor al de la raiz actual
            raiz.izquierdo = insertarArbol(raiz.izquierdo, codigo, total); // Inserta en el subarbol izquierdo
        } else { // Si el total es mayor o igual al de la raiz actual
            raiz.derecho = insertarArbol(raiz.derecho, codigo, total); // Inserta en el subarbol derecho
        }
        return raiz; // Devuelve la raiz del subarbol ya actualizado
    }

    static void recorrerInorden(NodoArbol raiz) { // Recorrido Inorden: Izquierda, Raiz, Derecha
        if (raiz != null) { // Condicion de parada: solo continua si el nodo existe
            recorrerInorden(raiz.izquierdo); // Visita primero el subarbol izquierdo
            System.out.printf("%-10d %10.2f%n", raiz.codigo, raiz.total); // Imprime la raiz del subarbol
            recorrerInorden(raiz.derecho); // Visita al final el subarbol derecho
        }
    }
}
