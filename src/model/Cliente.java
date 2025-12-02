/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.time.LocalDate;
/**
 *
 * @author delosrios
 */
/**
 * CREATE TABLE cliente ( id INT AUTO_INCREMENT PRIMARY KEY, esta_activo BOOLEAN
 * NOt NULL DEFAULT TRUE, fecha_de_alta CHAR(10) NOT NULL DEFAULT DATE(NOW()),
 * nombre VARCHAR(60), apellido VARCHAR(60), dni VARCHAR(8) );
 * @author delosrios
 */
public class Cliente extends Base {
    private String nombre;
    private String apellido;
    private String dni;

    
    public Cliente(long id, boolean estaActivo, LocalDate fechaDeAlta, String nombre, String apellido, String dni) {
        super(id, estaActivo, fechaDeAlta);
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
    }
    
    public Cliente(String nombre, String apellido, String dni) {
        super();
        this.nombre = nombre;
        this.apellido = apellido;
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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    @Override
    public String toString() {
        return super.toString() + "" + "nombre=" + nombre + ", apellido=" + apellido + ", dni=" + dni + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return this.getId() == cliente.getId();
    }
    
}
