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
public abstract class Base {
    private Long id;
    private Boolean estaActivo;
    private LocalDate fechaDeAlta = null;

    public Base(long id, boolean estaActivo, LocalDate fechaDeAlta) {
        this.id = id;
        this.estaActivo = estaActivo;
        this.fechaDeAlta = fechaDeAlta;
    }
    
    public Base() {}
    
    public long getId() {
        return id;
    }
    
    public LocalDate getFechaDeAlta() {
        return fechaDeAlta;
    }

    public Boolean getEstaActivo() {
        return estaActivo;
    }

    public void setEstaActivo(Boolean estaActivo) {
        this.estaActivo = estaActivo;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" + "id=" + id + ", estaActivo=" + estaActivo + ", fechaDeAlta=" + fechaDeAlta + ' ';
    }
    
}
