/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import java.sql.SQLException;

import dao.EscrituraDAO;
import java.util.List;
import java.util.Scanner;
import model.Cliente;
import model.Escritura;
import model.Propiedad;
import utils.TextosCliente;
import utils.TextosComun;
import utils.TextosEscritura;
import utils.TextosPropiedad;
import utils.Validadores;
 
/**
 *
 * @author delosrios
 */
public class EscrituraServiceImpl implements GenericService, Activable {
    private final EscrituraDAO dao;
    private final Scanner scanner;
    public EscrituraServiceImpl(EscrituraDAO dao, Scanner scanner) {
        this.dao = dao;
        this.scanner = scanner;
    }
    
    @Override
    public void activar() {
        System.out.print(TextosEscritura.TEXTO_INGRESE_ID_ESCRITURA_ACTIVAR);
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
        System.out.print(TextosEscritura.TEXTO_INGRESE_ID_ESCRITURA_DESACTIVAR);
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
    public void insertar() throws SQLException {
        System.out.print( TextosEscritura.TEXTO_INGRESE_ID_PROPIETARIO + ": ");
        String inputPropietarioID = scanner.nextLine();
        while(!Validadores.validarInt(inputPropietarioID)) {
            System.out.println(TextosComun.TEXTO_INGRESE_VALOR_VALIDO);
            System.out.println("");
            System.out.print(TextosEscritura.TEXTO_INGRESE_ID_PROPIETARIO + ": ");
            inputPropietarioID = scanner.nextLine();
        }
        int propietarioID = Integer.parseInt(inputPropietarioID);
        System.out.print(TextosEscritura.TEXTO_INGRESE_ID_PROPIEDAD + ": ");
        String inputPropiedadID = scanner.nextLine();
        while(!Validadores.validarInt(inputPropiedadID)) {
            System.out.println(TextosComun.TEXTO_INGRESE_VALOR_VALIDO);
            System.out.println("");
            System.out.print(TextosEscritura.TEXTO_INGRESE_ID_PROPIEDAD + ": ");
            inputPropiedadID = scanner.nextLine();
        }
        int propiedadID = Integer.parseInt(inputPropiedadID);
        Cliente propietario = this.dao.clienteDAO.findById(propietarioID);
        Propiedad propiedad = this.dao.propiedadDAO.findById(propiedadID);
        if(propiedadYPropietarioValidos(propiedadID, propiedad, propietarioID, propietario)) {
            Escritura escritura = new Escritura(propiedad, propietario);
            dao.save(escritura);
            System.out.println(TextosEscritura.TEXTO_ESCRITURA_GENERADA_SATISFACTORIO);
        }
    }
    
    @Override
    public void actualizar() throws SQLException {
        System.out.print(TextosEscritura.TEXTO_INGRESE_ID_ESCRITURA + " " 
                + TextosComun.TEXTO_QUE_DESEA + " modificar: ");
        String inputEscrituraID = scanner.nextLine();
        while(!Validadores.validarInt(inputEscrituraID)) {
            System.out.println(TextosComun.TEXTO_INGRESE_VALOR_VALIDO);
            System.out.println("");
            System.out.print(TextosEscritura.TEXTO_INGRESE_ID_ESCRITURA + " " 
                    + TextosComun.TEXTO_QUE_DESEA + " modificar: ");
            inputEscrituraID = scanner.nextLine();
        }
        int escrituraID = Integer.parseInt(inputEscrituraID);
        Escritura escritura = dao.findById(escrituraID);
        if(escritura != null) {
            int propiedadID = Validadores.promptConConfirmacionYValidacion(scanner, 
                    TextosComun.TEXTO_DESEA_EDITAR_LA + " propiedad asignada ", 
                    (int)escritura.getPropiedad().getId(), 
                    TextosEscritura.TEXTO_INGRESE_ID_PROPIEDAD + ": ");
            
            int propietarioID = Validadores.promptConConfirmacionYValidacion(scanner, 
                    TextosComun.TEXTO_DESEA_EDITAR_EL + " propietario asignado ", 
                    (int)escritura.getPropietario().getId(), 
                    TextosEscritura.TEXTO_INGRESE_ID_PROPIETARIO + ": ");
            Propiedad propiedad = dao.propiedadDAO.findById(propiedadID);
            Cliente propietario = dao.clienteDAO.findById(propietarioID);
            if(propiedadYPropietarioValidos(propiedadID, propiedad, propietarioID, propietario)) {
                escritura.setPropiedad(propiedad);
                escritura.setPropietario(propietario);
                dao.update(escritura);
                System.out.println(TextosEscritura.TEXTO_ESCRITURA_MODIFICADA_SATISFACTORIO);
            }
        } else {
            System.out.println(TextosEscritura.TEXTO_NO_ESCRITURA_ID + " " + escrituraID);
        }
    }

    @Override
    public void eliminar() throws SQLException {
        System.out.print(TextosEscritura.TEXTO_INGRESE_ID_ESCRITURA + " " 
                + TextosComun.TEXTO_A_ELIMINAR + ": ");
        String inputEscrituraID = scanner.nextLine();
        while(!Validadores.validarInt(inputEscrituraID)) {
            System.out.println(TextosComun.TEXTO_INGRESE_VALOR_VALIDO);
            System.out.println("");
            System.out.print(TextosEscritura.TEXTO_INGRESE_ID_ESCRITURA + " " 
                    + TextosComun.TEXTO_A_ELIMINAR + ": ");
            inputEscrituraID = scanner.nextLine();
        }
        int escrituraID = Integer.parseInt(inputEscrituraID);
        if(dao.delete(escrituraID)) {
            System.out.println(TextosEscritura.TEXTO_ESCRITURA_ELIMINADO_SATISFACTORIO);
        } else {
            System.out.println(TextosEscritura.TEXTO_NO_ESCRITURA_ID + " " + escrituraID);
        }
    }

    @Override
    public void getAll() throws SQLException {
        List<Escritura> escrituras = dao.findAll();
        if(!escrituras.isEmpty()) {
            for(Escritura escritura : escrituras) {
                System.out.println(escritura);
            } 
        } else {
            System.out.println(TextosEscritura.TEXTO_NO_ESCRITURAS);
        } 
    }

    @Override
    public void getById() throws SQLException {
        System.out.print(TextosEscritura.TEXTO_INGRESE_ID_ESCRITURA + " " + TextosComun.TEXTO_DESEA_BUSCAR );
        String inputEscrituraID = scanner.nextLine();
        while(!Validadores.validarInt(inputEscrituraID)) {
            System.out.println(TextosComun.TEXTO_INGRESE_VALOR_VALIDO);
            System.out.println("");
            System.out.print(TextosEscritura.TEXTO_INGRESE_ID_ESCRITURA + " " + TextosComun.TEXTO_DESEA_BUSCAR);
            inputEscrituraID = scanner.nextLine();
        }
        int escrituraID = Integer.parseInt(inputEscrituraID);
        Escritura escritura = dao.findById(escrituraID);
        if(escritura != null) {
            System.out.println(escritura);
        } else {
                System.out.println(TextosEscritura.TEXTO_NO_ESCRITURA_ID + " " + escrituraID);
        }   
    }

    private boolean propiedadYPropietarioValidos(int propiedadID, Propiedad propiedad, int propietarioID, Cliente propietario) {
        boolean validos = true;
        if(propietario == null) {
            System.out.println(TextosCliente.TEXTO_NO_CLIENTE_ID + " " + propietarioID);
            validos = false;
        }
        if(propiedad == null) {
            System.out.println(TextosPropiedad.TEXTO_NO_PROPIEDAD_ID + " " + propiedadID);
            validos = false;
        }
        if(!validos) {
            System.out.println(TextosComun.TEXTO_OP_CANCELADA);
        }
        return validos;
    }
    
    private boolean modificarActivo(int id, boolean activo) {
        try {
            Escritura escritura = dao.findById(id);
            if(escritura != null) {
                return dao.setActive(id, activo);
            } else {
                System.out.println(TextosEscritura.TEXTO_NO_ESCRITURA_ID + " " + id);
            }
        } catch(SQLException e) {
            System.out.println("Error al intentar actualizar el registro en la base de datos");
        }
        return false;
    }
    
}
