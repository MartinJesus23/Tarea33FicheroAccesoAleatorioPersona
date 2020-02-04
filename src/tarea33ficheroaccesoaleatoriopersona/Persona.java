//Author: Martín Jesús Mañas Rivas - 1ºGS
package tarea33ficheroaccesoaleatoriopersona;

public class Persona {

  //------ Atributos ------//
  private String dni, nombre, apellido;
  private int edad;
  private boolean borrado = false;

  //------ Constructor ------//
  public Persona(String dni, String nombre, String apellido, int edad) {
    this.dni = dni;
    this.nombre = nombre;
    this.apellido = apellido;
    this.edad = edad;
  }

  public Persona(Persona p) {
    dni = p.dni;
    nombre = p.nombre;
    apellido = p.apellido;
    edad = p.edad;
  }

  //------ Setter y Getters ------//
  public String getDni() {
    return dni;
  }

  public void setDni(String dni) {
    this.dni = dni;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getApellido() {
    return apellido;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public int getEdad() {
    return edad;
  }

  public void setEdad(int edad) {
    this.edad = edad;
  }

  public boolean isBorrado() {
    return borrado;
  }

  public void setBorrado(boolean borrado) {
    this.borrado = borrado;
  }

  //------ Metodos ------//
  @Override
  public String toString() {
    return "DNI:" + dni + " Nombre:" + nombre + " Apellidos:" + apellido + " Edad:" + edad;
  }
}
