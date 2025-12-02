/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.List;

/**
 *
 * @author delosrios
 */
public interface GenericService {
    void insertar() throws Exception;
    void actualizar() throws Exception;
    void eliminar() throws Exception;
    void getById() throws Exception;
    void getAll() throws Exception;    
}
