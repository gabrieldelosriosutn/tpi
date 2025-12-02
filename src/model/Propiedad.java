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
/*CREATE TABLE propiedad (
	id INT AUTO_INCREMENT PRIMARY KEY,
	esta_activo BOOLEAN NOt NULL DEFAULT TRUE,
	fecha_de_alta CHAR(10) NOT NULL DEFAULT DATE(NOW()),
	ubicacion VARCHAR(255),
	superficie DOUBLE,
	anios_de_antiguedad int,
	duenio int,
	FOREIGN KEY (duenio) REFERENCES cliente(id) 
);
*/
public class Propiedad extends Base{
    private String ubicacion;
    private Double superfice;
    private int aniosDeAntiguedad;
    //podria ser el dni del dueño si se complica
    private Cliente duenio;
    
    public Propiedad(Long id, boolean estaActivo, LocalDate fechaDeAlta, Cliente duenio, String ubicacion) {
        super(id, estaActivo, fechaDeAlta);
        this.duenio = duenio;
        this.ubicacion = ubicacion;
    }
    
    public Propiedad(Long id, boolean estaActivo, LocalDate fechaDeAlta, Cliente duenio, String ubicacion, Double superficie) {
        this(id, estaActivo, fechaDeAlta, duenio, ubicacion);
        this.superfice = superficie;
    }

    public Propiedad(Long id, boolean estaActivo, LocalDate fechaDeAlta,Cliente duenio, String ubicacion, Double superficie, int aniosDeAntiguedad) {
        this(id, estaActivo, fechaDeAlta, duenio, ubicacion, superficie);
        this.aniosDeAntiguedad = aniosDeAntiguedad;
    }
    
    public Propiedad(Cliente duenio, String ubicacion, Double superficie, int aniosDeAntiguedad){
        super();
        this.duenio = duenio;
        this.ubicacion = ubicacion;
        this.superfice = superficie;
        this.aniosDeAntiguedad = aniosDeAntiguedad;        
    }
        
    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Double getSuperfice() {
        return superfice;
    }

    public void setSuperfice(Double superfice) {
        this.superfice = superfice;
    }

    public int getAniosDeAntiguedad() {
        return aniosDeAntiguedad;
    }

    public void setAniosDeAntiguedad(int aniosDeAntiguedad) {
        this.aniosDeAntiguedad = aniosDeAntiguedad;
    }

    public Cliente getDuenio() {
        return duenio;
    }

    public Long getDuenioId() {
        return (this.getDuenio() != null) ? this.getDuenio().getId() : null;
    }
    
    public void setDuenio(Cliente duenio) {
        this.duenio = duenio;
    }

    @Override
    public String toString() {
        return super.toString() + ", " + "ubicacion=" + ubicacion + ", superfice=" + superfice + ", aniosDeAntiguedad=" + aniosDeAntiguedad + ", duenio=" + (duenio == null ? "sin dueño}" : "\n" + duenio.toString());
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Propiedad propiedad = (Propiedad) o;
        return this.getId() == propiedad.getId();
    }
    
}
