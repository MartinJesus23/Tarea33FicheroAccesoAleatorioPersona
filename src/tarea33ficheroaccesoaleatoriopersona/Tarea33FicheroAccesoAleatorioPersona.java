//Author: Martín Jesús Mañas Rivas - 1ºGS
package tarea33ficheroaccesoaleatoriopersona;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Tarea33FicheroAccesoAleatorioPersona {

  public static Scanner teclado = new Scanner(System.in);
  public static File fichero;
  public static RandomAccessFilePersona rafp;

  public static void main(String[] args) {
    int opcion;
    try {
      fichero = new File("C:\\temp\\Personas.dat");
      confirmarFichero(fichero);
    } catch (IOException ioe) {
      System.out.println("Error: No se puede acceder al fichero por defecto.");
    }

    do {
      opcion = -1;
      mostrarMenu();
      try {
        opcion = teclado.nextInt();
        teclado.nextLine(); //Vaciar buffer teclado
        switch (opcion) {
          case 0:
            if (!confirmar("¿Seguro que desea salir?(s/n):")) {
              opcion = -1;
            }
            break;
          case 1:
            añadirPersona();
            break;
          case 2:
            leerPersonaConPosicion();
            break;
          case 3:
            leerPersonaConDni();
            break;
          case 4:
            eliminarPersonaConDni();
            break;
          case 5:
            rafp = new RandomAccessFilePersona(fichero, "rw");
            rafp.empaquetarFichero();
            rafp.getRaf().close();
            break;
          case 6:
            if (confirmar("¿Seguro que desea borrar el fichero?(s/n):")) {
              fichero.delete();
              System.out.println("Fichero borrado correctamente");
              confirmarFichero(fichero); //Volvemos a crear el fichero
            }
            break;
          case 7:
            rafp = new RandomAccessFilePersona(fichero, "r");
            rafp.leerPersonas();
            rafp.getRaf().close();
            break;
          case 8:
            cambiarRuta();
            break;
        }

      } catch (Exception e) {
        System.out.println(e);

        teclado.nextLine(); //Vaciar buffer teclado
      }
    } while (opcion != 0);
  }

  public static void mostrarMenu() {
    System.out.println("\n0.Salir del programa\t\t 1.Añadir Persona\t\t 2.Mostrar Persona según posición");
    System.out.println("3.Mostrar persona por NIF\t 4.Borrar persona por NIF\t 5.Empaquetar fichero");
    System.out.println("6.Borrar fichero completo\t 7.Listar fichero completo\t 8.Cambiar ruta fichero");
    System.out.print("Introduzca una opcion(0-8):");
  }

  public static boolean confirmar(String mensaje) {
    System.out.print(mensaje);
    char res = teclado.next().toLowerCase().charAt(0);
    return (res == 's' || res == 'y');
  }

  public static File cambiarRuta() throws IOException {
    System.out.println("Ruta actual:" + fichero.getAbsolutePath());
    System.out.print("Introduzca la ruta completa:");
    File f = new File(teclado.nextLine());
    confirmarFichero(f);
    System.out.println("Ruta cambiada correctamente");
    return f;
  }

  public static void confirmarFichero(File f) throws IOException {
    if (!f.exists()) {
      new File(f.getParent()).mkdirs(); //Crea las carpetas necesarias
      f.createNewFile();
    }
  }

  public static void añadirPersona() {
    String dni, nombre, apellido;
    int edad;
    boolean creada = false;

    do {
      try {
        System.out.print("Introduzca el DNI:");
        dni = teclado.next();

        System.out.print("Introduzca el Nombre:");
        teclado.nextLine(); //limpiar buffer
        nombre = teclado.nextLine();

        System.out.print("Introduzca los apellidos:");
        apellido = teclado.nextLine();

        System.out.print("Introduzca la edad:");
        edad = teclado.nextInt();
        teclado.nextLine(); //vaciar buffer

        Persona p = new Persona(dni, nombre, apellido, edad);
        rafp = new RandomAccessFilePersona(fichero, "rw");
        rafp.añadirPersona(p);
        creada = true;
        rafp.getRaf().close();

      } catch (Exception e) {
        teclado.nextLine();
        System.out.println(e);
      }
    } while (!creada);
    System.out.println("Persona añadida correctamente");
  }

  public static void leerPersonaConPosicion() {
    try {
      rafp = new RandomAccessFilePersona(fichero, "r");
      System.out.print("Introduzca la posicion:");
      int pos = teclado.nextInt();
      rafp.leerPersonaConPosion(pos);
      rafp.getRaf().close();
    } catch (Exception e) {
      System.out.println("Error en la entrada de datos");
    }
  }

  public static void leerPersonaConDni() {
    try {
      rafp = new RandomAccessFilePersona(fichero, "r");
      System.out.print("Introduzca el DNI:");
      String dni = teclado.next();
      rafp.leerPersonaConDNI(dni);
      rafp.getRaf().close();
    } catch (Exception e) {
      System.out.println("Error en la entrada de datos");
    }
  }

  public static void eliminarPersonaConDni() {
    try {
      System.out.print("Introduzca el DNI:");
      String dni = teclado.next();
      if (confirmar("¿Seguro que desea borrar la persona?(s/n):")) {
        rafp = new RandomAccessFilePersona(fichero, "rw");
        rafp.eliminarPersonaConDNI(dni);
        rafp.getRaf().close();
      }
    } catch (Exception e) {
      System.out.println("Error en la entrada de datos");
    }
  }

}
