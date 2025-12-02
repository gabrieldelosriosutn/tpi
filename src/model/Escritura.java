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
/*CREATE TABLE escritura (
	id INT AUTO_INCREMENT PRIMARY KEY,
	esta_activo BOOLEAN NOt NULL DEFAULT TRUE,
	fecha_de_alta CHAR(10) NOT NULL DEFAULT DATE(NOW()),
	propiedad int NOT NULL,
	propietario int NOT NULL,
	FOREIGN KEY (propiedad) REFERENCES propiedad(id) ON DELETE CASCADE,
	FOREIGN KEY (propietario) REFERENCES cliente(id) ON DELETE CASCADE
);*/
public class Escritura extends Base{
    Propiedad propiedad;
    Cliente propietario;
    
    public Escritura(long id, boolean estaActivo, LocalDate fechaDeAlta, Propiedad propiedad, Cliente propietario) {
        super(id, estaActivo, fechaDeAlta);
        this.propiedad = propiedad;
        this.propietario = propietario;
    }
    
    public Escritura(Propiedad propiedad, Cliente propietario) {
        this.propiedad = propiedad;
        this.propietario = propietario;
    }
    
    public Propiedad getPropiedad() {
        return propiedad;
    }

    public void setPropiedad(Propiedad propiedad) {
        this.propiedad = propiedad;
    }

    public Cliente getPropietario() {
        return propietario;
    }

    public void setPropietario(Cliente propietario) {
        this.propietario = propietario;
    }

    @Override
    public String toString() {
        return super.toString() + "propiedad=" + propiedad.toString() + "}}";
    }
    
    
}
