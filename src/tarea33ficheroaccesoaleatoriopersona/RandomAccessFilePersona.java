//Author: Martín Jesús Mañas Rivas - 1ºGS
package tarea33ficheroaccesoaleatoriopersona;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFilePersona {

  //------ Atributos ------//
  private RandomAccessFile raf;
  private final char relleno = '#';

  //------ Constructor ------//
  public RandomAccessFilePersona(File f, String modo) throws FileNotFoundException {
    raf = new RandomAccessFile(f, modo);
  }

  //------ Setter y Getters ------//
  public RandomAccessFile getRaf() {
    return raf;
  }

  public void setRaf(RandomAccessFile raf) {
    this.raf = raf;
  }

  //------ Metodos ------//
  public String rellenarString(String s, int longitud) {
    for (int i = s.length(); i < longitud; i++) {
      s += relleno;
    }
    return s;
  }

  public String filtrarString(String s) {
    return s.substring(0, s.indexOf(relleno));
  }

  public void escribirString(String s) throws IOException {
    for (int i = 0; i < s.length(); i++) {
      raf.writeChar(s.charAt(i));
    }
  }

  public String leerString(int longitud) throws IOException {
    String s = "";
    for (int i = 0; i < longitud; i++) {
      s += raf.readChar();
    }
    return s;
  }

  public void añadirPersona(Persona p) throws IOException {
    raf.seek(raf.length()); //Posiciona el puntero al final
    escribirString(p.getDni());
    escribirString(rellenarString(p.getNombre(), 20));
    escribirString(rellenarString(p.getApellido(), 30));
    raf.writeInt(p.getEdad());
    raf.writeBoolean(p.isBorrado());
  }

  public void leerPersonas() throws IOException {
    raf.seek(0); // Posicionamos el fichero en la posición inicial
    if (raf.length() != 0) { //Si no esta vacio.
      while (raf.getFilePointer() < raf.length()) {
        raf.seek(raf.getFilePointer() + 122); //Se posiciona en el boolean
        Boolean borrado = raf.readBoolean(); //Saca el boolean de persona
        raf.seek(raf.getFilePointer() - 123); //Vuelve a la posición anterior
        if (!borrado) {
          String dni = leerString(9);
          String nombre = filtrarString(leerString(20));
          String apellido = filtrarString(leerString(30));
          int edad = raf.readInt();
          System.out.println("-" + new Persona(dni, nombre, apellido, edad));
          raf.skipBytes(1); //Saltamos el byte del booleano
        } else {
          raf.seek(raf.getFilePointer() + 123); //Posicionamos en la siguiente persona
        }
      }
    } else {
      System.out.println("El fichero esta vacio");
    }
  }

  public void leerPersonaConPosion(int pos) throws IOException {
    pos = 123 * (pos - 1); //numero de bytes segun la posicion dada
    if (raf.length() != 0) {
      if (raf.length() > pos) {
        raf.seek(pos);
        raf.seek(raf.getFilePointer() + 122); //Se posiciona en el boolean
        Boolean borrado = raf.readBoolean(); //Saca el boolean de persona
        raf.seek(raf.getFilePointer() - 123); //Vuelve a la posición anterior
        if (!borrado) {
          String dni = leerString(9);
          String nombre = filtrarString(leerString(20));
          String apellido = filtrarString(leerString(30));
          int edad = raf.readInt();
          System.out.println("-" + new Persona(dni, nombre, apellido, edad));
          raf.skipBytes(1); //Saltamos el byte del booleano
        } else {
          System.out.println("La persona seleccionada ha sido eliminada");
        }
      } else {
        System.out.println("No exite la persona en esa posición");
      }
    } else {
      System.out.println("El fichero esta vacio");
    }
  }

  public void leerPersonaConDNI(String dniBusqueda) throws IOException {
    boolean encontrado = false;
    raf.seek(0); // Posicionamos el fichero en la posición inicial
    if (raf.length() != 0) { //Si no esta vacio.
      while (raf.getFilePointer() < raf.length() && !encontrado) {
        raf.seek(raf.getFilePointer() + 122); //Se posiciona en el boolean
        Boolean borrado = raf.readBoolean(); //Saca el boolean de persona
        raf.seek(raf.getFilePointer() - 123); //Vuelve a la posición anterior
        String dni = leerString(9);
        raf.seek(raf.getFilePointer() - 18);
        if (!borrado && dni.equals(dniBusqueda)) {
          raf.skipBytes(18); //Saltamos los bytes del dni
          String nombre = filtrarString(leerString(20));
          String apellido = filtrarString(leerString(30));
          int edad = raf.readInt();
          System.out.println("-" + new Persona(dni, nombre, apellido, edad));
          raf.skipBytes(1); //Saltamos el byte del booleano
          encontrado = true;
        } else {
          raf.seek(raf.getFilePointer() + 123); //Posicionamos en la siguiente persona
        }
      }
      if (!encontrado) {
        System.out.println("No se ha encontrado la Persona con DNI:" + dniBusqueda);
      }
    } else {
      System.out.println("El fichero esta vacio");
    }
  }

  public void eliminarPersonaConDNI(String dniBusqueda) throws IOException {
    boolean encontrado = false;
    raf.seek(0); // Posicionamos el fichero en la posición inicial
    if (raf.length() != 0) { //Si no esta vacio.
      while (raf.getFilePointer() < raf.length() && !encontrado) {
        raf.seek(raf.getFilePointer() + 122); //Se posiciona en el boolean
        Boolean borrado = raf.readBoolean(); //Saca el boolean de persona
        raf.seek(raf.getFilePointer() - 123); //Vuelve a la posición anterior
        String dni = leerString(9);
        raf.seek(raf.getFilePointer() - 18);
        if (!borrado && dni.equals(dniBusqueda)) {
          raf.skipBytes(122); //Saltamos los bytes del dni
          raf.writeBoolean(true);
          encontrado = true;
        } else {
          raf.seek(raf.getFilePointer() + 123); //Posicionamos en la siguiente persona
        }
      }
      if (!encontrado) {
        System.out.println("No se ha podido eliminar la Persona con DNI:" + dniBusqueda);
      } else {
        System.out.println("Persona borrada correctamente");
      }
    } else {
      System.out.println("El fichero esta vacio");
    }
  }

  public void empaquetarFichero() throws IOException {
    long truncatePoint = raf.length();
    boolean encontrado = true, borrado;
    if (raf.length() != 0) { //si no esta vacio..
      for (int i = 0; i < raf.length() && encontrado; i += 123) {
        encontrado = false;
        raf.seek(i + 122); //Se posiciona en el boolean
        borrado = raf.readBoolean(); //Saca el boolean de persona
        raf.seek(i); //Vuelve a la posición anterior
        if (borrado) {
          for (int j = i + 123; j < raf.length() && !encontrado; j += 123) {
            raf.seek(j + 122); //Se posiciona en el boolean
            borrado = raf.readBoolean(); //Saca el boolean de persona
            raf.seek(j); //Vuelve a la posición anterior
            if (!borrado) {
              String dni = leerString(9);
              String nombre = filtrarString(leerString(20));
              String apellido = filtrarString(leerString(30));
              int edad = raf.readInt();
              raf.writeBoolean(true);
              raf.seek(i); //Nos movemos al borrado y escribimos la persona
              escribirString(dni);
              escribirString(rellenarString(nombre, 20));
              escribirString(rellenarString(apellido, 30));
              raf.writeInt(edad);
              raf.writeBoolean(false);
              encontrado = true;
            }
          }
          if (!encontrado) {
            truncatePoint = i;
          }
        } else {
          encontrado = true;
        }
      }
    }
    raf.setLength(truncatePoint);
  }

}
