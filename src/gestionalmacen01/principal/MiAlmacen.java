package gestionalmacen01.principal;

import java.util.List;
import java.util.Scanner;
import gestionalmacen01.modelo.ModeloAbs;
import gestionalmacen01.modelo.ModeloArrayList;
import gestionalmacen01.modelo.Producto;

public class MiAlmacen {
    static private ModeloAbs almacen;
    static private Scanner sc;

    public static void main(String[] args) {
        almacen = new ModeloArrayList();
        sc = new Scanner(System.in);

        cargarDatosPrueba();

        int opcion = 0;
        do {
            mostrarMenu();
            opcion = leerOpcion(1, 9);
            switch (opcion) {
                case 1: crear(); break;
                case 2: consultar(); break;
                case 3: borrar(); break;
                case 4: modificarPrecio(); break;
                case 5: comprar(); break;
                case 6: vender(); break;
                case 7: listar(); break;
                case 8: listarPocoStock(); break;
            }
            if (opcion != 9) {
                System.out.println("\n---------------------------- ");
                System.out.print("Pulse enter para continuar");
                sc.nextLine();
            }
        } while (opcion != 9);
        System.out.println("\n Fin del programa.");
        sc.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n\n    MENU");
        System.out.println("1. Nuevo producto ");
        System.out.println("2. Consulta producto ");
        System.out.println("3. Borrar producto ");
        System.out.println("4. Modificar precio ");
        System.out.println("5. Compra de productos ");
        System.out.println("6. Venta de productos ");
        System.out.println("7. Listado completo de productos ");
        System.out.println("8. Listado de productos con stock inferior al mínimo");
        System.out.println("9. Terminar ");
        System.out.print("Elige una opción (1-9): ");
    }

    private static int leerOpcion(int primero, int ultimo) {
        int valor = leerEntero();
        while (valor < primero || valor > ultimo) {
            System.out.print("Opción no válida. Intente de nuevo (" + primero + "-" + ultimo + "): ");
            valor = leerEntero();
        }
        return valor;
    }

    static private int leerEntero() {
        boolean error = false;
        int valor = 0;
        do {
            error = false;
            try {
                valor = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número entero válido.");
                error = true;
            }
        } while (error);
        return valor;
    }

    static private float leerFloat() {
        boolean error = false;
        float valor = 0;
        do {
            error = false;
            try {
                valor = Float.parseFloat(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número decimal válido.");
                error = true;
            }
        } while (error);
        return valor;
    }

    private static void consultar() {
        System.out.println("<CONSULTA>");
        System.out.print("Introduzca código: ");
        int codigo = leerEntero();
        Producto p = almacen.buscarProducto(codigo);
        if (p == null) {
            System.out.println("El producto no se encuentra en almacén.");
        } else {
            System.out.println("PRODUCTO: " + p);
        }
    }

    private static void borrar() {
        System.out.println("<ELIMINAR>");
        System.out.print("Introduzca código del producto a borrar: ");
        int codigo = leerEntero();
        if (almacen.borrarProducto(codigo)) {
            System.out.println("Producto eliminado correctamente.");
        } else {
            System.out.println("Error: No existe ningún producto con ese código.");
        }
    }

    private static void modificarPrecio() {
        System.out.println("<MODIFICAR PRECIO>");
        System.out.print("Introduzca código: ");
        int codigo = leerEntero();
        Producto p = almacen.buscarProducto(codigo);
        if (p != null) {
            System.out.print("Nuevo precio para " + p.getNombre() + ": ");
            float nuevoPrecio = leerFloat();
            p.setPrecio(nuevoPrecio);
            almacen.modificarProducto(p);
            System.out.println("Precio actualizado.");
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    private static void comprar() {
        System.out.println("<COMPRAR>");
        System.out.print("Introduzca código: ");
        int codigo = leerEntero();
        Producto p = almacen.buscarProducto(codigo);
        if (p != null) {
            System.out.print("Cantidad a incrementar: ");
            int cantidad = leerEntero();
            if (cantidad > 0) {
                p.setStock(p.getStock() + cantidad);
                almacen.modificarProducto(p);
                System.out.println("Stock actualizado correctamente.");
            } else {
                System.out.println("La cantidad debe ser positiva.");
            }
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    private static void vender() {
        System.out.println("<VENDER>");
        System.out.print("Introduzca código: ");
        int codigo = leerEntero();
        Producto p = almacen.buscarProducto(codigo);
        if (p != null) {
            System.out.print("Cantidad a vender: ");
            int cantidad = leerEntero();
            if (cantidad > 0 && cantidad <= p.getStock()) {
                p.setStock(p.getStock() - cantidad);
                almacen.modificarProducto(p);
                System.out.println("Venta realizada.");
            } else {
                System.out.println("Cantidad no válida o stock insuficiente.");
            }
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    private static void listar() {
        System.out.println("<LISTADO COMPLETO>");
        almacen.imprimirProductosTodos();
    }

    private static void listarPocoStock() {
        System.out.println("<LISTAR STOCK BAJO MÍNIMOS>");
        List<Producto> lista = almacen.obtenerProductosStockMin();
        if (lista.isEmpty()) {
            System.out.println("No hay productos con stock bajo.");
        } else {
            for (Producto p : lista) {
                System.out.println(p);
            }
        }
    }

    private static void crear() {
        System.out.println("<NUEVO PRODUCTO>");
        System.out.print("Código: ");
        int cod = leerEntero();
        if (almacen.buscarProducto(cod) != null) {
            System.out.println("Error: El código ya existe.");
            return;
        }
        System.out.print("Nombre: ");
        String nom = sc.nextLine();
        System.out.print("Stock inicial: ");
        int stock = leerEntero();
        System.out.print("Stock mínimo: ");
        int min = leerEntero();
        System.out.print("Precio: ");
        float precio = leerFloat();

        Producto p = new Producto(cod, nom);
        p.setStock(stock);
        p.setStock_min(min);
        p.setPrecio(precio);

        if (almacen.insertarProducto(p)) {
            System.out.println("Producto creado con éxito.");
        }
    }

    private static void cargarDatosPrueba() {
        Producto p1 = new Producto(101, "Monitor 24 Pulgadas");
        p1.setStock(15); p1.setStock_min(5); p1.setPrecio(159.99f);
        Producto p2 = new Producto(102, "Teclado Mecánico");
        p2.setStock(4); p2.setStock_min(10); p2.setPrecio(45.50f);
        Producto p3 = new Producto(103, "Ratón Inalámbrico");
        p3.setStock(20); p3.setStock_min(8); p3.setPrecio(22.00f);
        Producto p4 = new Producto(104, "Disco SSD 1TB");
        p4.setStock(2); p4.setStock_min(5); p4.setPrecio(89.90f);

        almacen.insertarProducto(p1);
        almacen.insertarProducto(p2);
        almacen.insertarProducto(p3);
        almacen.insertarProducto(p4);
    }
}