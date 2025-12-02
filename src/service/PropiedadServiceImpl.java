/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.PropiedadDAO;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import model.Cliente;

import model.Propiedad;
import utils.TextosCliente;
import utils.TextosPropiedad;
import utils.TextosComun;
import utils.Validadores;

/**
 *
 * @author delosrios
 */
public class PropiedadServiceImpl implements GenericService, Activable {
    
    private Scanner scanner;
    private PropiedadDAO dao;
    private static final int MAX_LEN_UBICACION = 255;
    public PropiedadServiceImpl(PropiedadDAO dao, Scanner scanner) {
        this.dao = dao;
        this.scanner = scanner;
    }
    
    @Override
    public void activar() {
        System.out.print(TextosPropiedad.TEXTO_INGRESE_EL_ID + " " + TextosComun.TEXTO_QUE_DESEA + " activar: ");
        String input = scanner.nextLine();
        if(Validadores.validarInt(input)) {
            int id = Integer.parseInt(input);
            if(modificarActivo(id, true)) {
                System.out.println(TextosComun.TEXTO_ACTIVACION_SATISFACTORIA);
            }
        } else {
            System.out.println(TextosComun.TEXTO_INGRESE_VALOR_VALIDO);
        }
    }
    
    @Override
    public void desactivar() {
        System.out.print(TextosPropiedad.TEXTO_INGRESE_EL_ID + " " + TextosComun.TEXTO_QUE_DESEA + " desactivar: ");
        String input = scanner.nextLine();
        if(Validadores.validarInt(input)) {
            int id = Integer.parseInt(input);
            if(modificarActivo(id, false)) {
                System.out.println(TextosComun.TEXTO_DESACTIVACION_SATISFACTORIA);
            }
        } else {
            System.out.println(TextosComun.TEXTO_INGRESE_VALOR_VALIDO);
        }
    }
    
    @Override
    public void actualizar() throws Exception {
        System.out.print(TextosPropiedad.TEXTO_INGRESE_EL_ID + " " + TextosComun.TEXTO_QUE_DESEA + " modificar: ");
        String input = scanner.nextLine();
        if(Validadores.validarInt(input)){
            int id = Integer.parseInt(input);
            Propiedad propiedad = dao.findById(id);
            if(propiedad != null) {
                String ubicacion = Validadores.promptConConfirmacionYValidacion(scanner, 
                    TextosComun.TEXTO_DESEA_EDITAR_LA + " ubicación ", 
                    propiedad.getUbicacion(),
                    TextosPropiedad.TEXTO_INGRESE_UBICACION,
                    MAX_LEN_UBICACION
                );
                propiedad.setUbicacion(ubicacion);
                Double superfice = Validadores.promptConConfirmacionYValidacion(scanner,
                    TextosComun.TEXTO_DESEA_EDITAR_LA + " superficie ",
                    propiedad.getSuperfice(),
                    TextosPropiedad.TEXTO_INGRESE_SUPERFICIE
                );
                propiedad.setSuperfice(superfice);
                int aniosDeAntiguedad = Validadores.promptConConfirmacionYValidacion(scanner, 
                    TextosComun.TEXTO_DESEA_EDITAR_LA + " antiguedad ", 
                    propiedad.getAniosDeAntiguedad(),
                    TextosPropiedad.TEXTO_INGRESE_ANIOS_ANTIGUEDAD
                );
                propiedad.setAniosDeAntiguedad(aniosDeAntiguedad);
                double idDuenio = Validadores.promptConConfirmacionYValidacion(scanner,
                    TextosComun.TEXTO_DESEA_EDITAR_EL + " dueño ",
                    propiedad.getDuenioId(),
                    TextosPropiedad.TEXTO_INGRESE_EL_ID_DUENIO_DE_LA_PROPIEDAD + ": "
                );
                Cliente duenio = dao.clienteDAO.findById((int)idDuenio);
                if(duenio != null) {
                    propiedad.setDuenio(duenio);
                } else {
                    System.out.println(TextosCliente.TEXTO_NO_CLIENTE_ID + " " + ((int)idDuenio));
                    System.out.println(TextosPropiedad.TEXTO_NO_ASIGNARA_DUENIO);
                }
                dao.update(propiedad);
                System.out.println(TextosPropiedad.TEXTO_LA_PROPIEDAD_SE_HA + " actualizado " + TextosComun.TEXTO_SATISFACTORIO);
            } else {
                System.out.println(TextosCliente.TEXTO_NO_CLIENTE_ID + " " + id);
            }
        } else {
            System.out.println(TextosComun.TEXTO_INGRESE_VALOR_VALIDO);
        }        
    }

    @Override
    public void eliminar() throws SQLException {
        System.out.print(TextosPropiedad.TEXTO_INGRESE_EL_ID + " a eliminar: ");
        String input = scanner.nextLine();
        if(Validadores.validarInt(input)) {
            int id = Integer.parseInt(input);
            if(dao.delete(id)) {
                System.out.println(TextosPropiedad.TEXTO_LA_PROPIEDAD_SE_HA + " eliminado " + TextosComun.TEXTO_SATISFACTORIO);
            } else {
                System.out.println(TextosPropiedad.TEXTO_NO_PROPIEDAD_ID + " " + id);
            }
        } else {
            System.out.println(TextosComun.TEXTO_INGRESE_VALOR_VALIDO);
        }
    }

    @Override
    public void getAll() throws SQLException {
        List<Propiedad> propiedades = dao.findAll();
        if(!propiedades.isEmpty()){
            for(Propiedad propiedad : propiedades) {
                System.out.println(propiedad);
            }
        } else {
            System.out.println(TextosPropiedad.TEXTO_NO_PROPIEDADES);
        }

    }

    @Override
    public void getById() throws SQLException {
        System.out.print(TextosPropiedad.TEXTO_INGRESE_EL_ID_DE_LA_PROPIEDAD + " " + TextosComun.TEXTO_DESEA_BUSCAR);
        int id;
        id = Integer.parseInt(scanner.nextLine());
        Propiedad propiedad = dao.findById(id);
        if(propiedad != null) {
            System.out.println(propiedad);
        } else {
                System.out.println(TextosPropiedad.TEXTO_NO_PROPIEDAD_ID + " " + id);
        } 
    }

    @Override
    public void insertar() throws Exception {
        String ubicacion = Validadores.promptConValidacion(scanner, TextosPropiedad.TEXTO_INGRESE_UBICACION, MAX_LEN_UBICACION);
        System.out.print(TextosPropiedad.TEXTO_INGRESE_SUPERFICIE);
        String input = scanner.nextLine();
        while(!Validadores.validarDouble(input)) {
            System.out.println(TextosComun.TEXTO_INGRESE_VALOR_VALIDO);
        }
        double superficie = Double.parseDouble(input);
        System.out.print(TextosPropiedad.TEXTO_INGRESE_ANIOS_ANTIGUEDAD);
        input = scanner.nextLine();
        while(!Validadores.validarInt(input)) {
            System.out.println(TextosComun.TEXTO_INGRESE_VALOR_VALIDO);
        }
        int aniosDeAntiguedad = Integer.parseInt(input);
        
        
        String idDuenio = Validadores.promptConConfirmacionYValidacion(scanner,  
                TextosPropiedad.TEXTO_DESEA_INGRESAR_DUENIO + " ", 
                null, 
                TextosComun.TEXTO_INGRESE_ID + " del dueño " + TextosPropiedad.TEXTO_DE_LA_PROPIEDAD +": ");
        while(idDuenio != null && !Validadores.validarInt(idDuenio)) {
            System.out.println(TextosComun.TEXTO_INGRESE_VALOR_VALIDO);
            idDuenio = Validadores.promptConConfirmacionYValidacion(scanner,  
                TextosPropiedad.TEXTO_DESEA_INGRESAR_DUENIO + " ", 
                null, 
            TextosComun.TEXTO_INGRESE_ID + " del dueño " + TextosPropiedad.TEXTO_DE_LA_PROPIEDAD +": ");
        }
        
        Cliente duenio = null;
        if(idDuenio != null) {
           duenio = dao.clienteDAO.findById(Integer.parseInt(idDuenio));
           if(duenio == null){
               System.out.println(TextosCliente.TEXTO_NO_CLIENTE_ID + idDuenio);
               System.out.println(TextosPropiedad.TEXTO_NO_ASIGNARA_DUENIO);
           }
        }
        
        Propiedad propiedad = new Propiedad(duenio, ubicacion, superficie, aniosDeAntiguedad);
        dao.save(propiedad);
        System.out.println(TextosPropiedad.TEXTO_PROPIEDAD_GUARDADO_SATISFACTORIAMENTE);
    }
    
    private boolean modificarActivo(int id, boolean activo) {
        try {
            Propiedad propiedad = dao.findById(id);
            if(propiedad != null) {
                return dao.setActive(id, activo);
            } else {
                System.out.println(TextosPropiedad.TEXTO_NO_PROPIEDAD_ID + " " + id);
            }
        } catch(SQLException e) {
            System.out.println("Error al intentar actualizar el registro en la base de datos");
        }
        return false;
    }
}
