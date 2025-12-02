/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import model.Cliente;
import model.Escritura;
import model.Propiedad;
import utils.TextosEscritura;

/**
 *
 * @author delosrios
 */
public class EscrituraDAO extends ActivableDAO<Escritura> {

    public final ClienteDAO clienteDAO;
    public final PropiedadDAO propiedadDAO;
    
    public EscrituraDAO(Connection conn, ClienteDAO clienteDAO, PropiedadDAO propiedadDAO) {
        super(Escritura.class.getSimpleName(), conn);
        this.clienteDAO = clienteDAO;
        this.propiedadDAO = propiedadDAO;
    }

    @Override
    public Escritura mapper(ResultSet rs) {
        Escritura escritura = null;
        try {
            long id = rs.getInt("id");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            boolean estaActivo = rs.getBoolean("esta_activo");
            LocalDate fechaDeAlta = LocalDate.parse(rs.getString("fecha_de_alta"), formatter); 
            int propiedadID = rs.getInt("propiedad");
            Propiedad propiedad = null;
            if(propiedadID != 0) {
                propiedad = propiedadDAO.findById(propiedadID);
            }
            int propietarioID = rs.getInt("propietario");
            Cliente propietario = null;
            if(propietarioID != 0) {
                propietario = clienteDAO.findById(propietarioID);
            }
            escritura = new Escritura(id, estaActivo, fechaDeAlta, propiedad, propietario);
            
        } catch(SQLException e) {
            System.out.println("Error de la base de datos al ejecutar la consulta");
        }
        return escritura;
    }

    @Override
    public void save(Escritura escritura) throws SQLException {
        if(escritura.getPropiedad() != null && escritura.getPropietario() != null) {
            //Deberia ser transaction
            Propiedad propiedad = escritura.getPropiedad();
            Cliente propietario = escritura.getPropietario();
            propiedad.setDuenio(propietario);
            propiedadDAO.update(propiedad);
            
            final String sqlQuery = "INSERT INTO " + tableName + " (propiedad, propietario) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, (int)propiedad.getId());
            stmt.setInt(2, (int)propietario.getId());
            stmt.executeUpdate();
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (!generatedKeys.next()) {
                throw new SQLException(TextosEscritura.TEXTO_ERROR_INTENTAR_GUARDAR_ESCRITURA);
            }
        }
            
    }

    @Override
    public void update(Escritura escritura) throws SQLException {
        final String sqlQuery = "UPDATE " + tableName + " SET propiedad = ?, propietario = ? WHERE id = ?;";
        conn.setAutoCommit(false);
        try {
            if(escritura.getPropiedad() != null && escritura.getPropietario() != null) {
                Escritura escrituraOriginal = findById((int)escritura.getId());
                Cliente propietario = escritura.getPropietario();
                Propiedad propiedad = escritura.getPropiedad();

                if(!escrituraOriginal.getPropiedad().equals(escritura.getPropiedad())
                   && !escrituraOriginal.getPropietario().equals(escritura.getPropietario())) {
                   propiedad.setDuenio(propietario);
                   propiedadDAO.update(propiedad);
                }
                else if(!escrituraOriginal.getPropiedad().equals( escritura.getPropiedad())) {
                    Propiedad propiedadOriginal = escrituraOriginal.getPropiedad();
                    propiedad.setDuenio(propietario);
                    propiedadOriginal.setDuenio(null);
                    propiedadDAO.update(propiedadOriginal);
                    propiedadDAO.update(propiedad);
                }
                else if(!escrituraOriginal.getPropietario().equals(escritura.getPropietario())) {
                    Propiedad propiedadOriginal = escrituraOriginal.getPropiedad();
                    propiedadOriginal.setDuenio(propietario);
                    propiedadDAO.update(propiedadOriginal);
                }

                PreparedStatement stmt = conn.prepareStatement(sqlQuery);
                stmt.setInt(1, (int)escritura.getPropiedad().getId());
                stmt.setInt(2, (int)escritura.getPropietario().getId());
                stmt.setInt(3, (int)escritura.getId());
                int rowsAffected = stmt.executeUpdate();
                this.conn.commit();
                if (rowsAffected == 0) {
                    throw new SQLException("Error al intentar actualizar la escritura");
                }
            }
        }catch(SQLException e) {
            this.conn.rollback();
            throw e;
        }finally {
            conn.setAutoCommit(true);
        }
    }
    
}
