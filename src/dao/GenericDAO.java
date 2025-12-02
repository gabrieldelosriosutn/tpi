/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author delosrios
 */
public abstract class GenericDAO<T> {
    protected final Connection conn;
    public final String tableName;
    
    public GenericDAO(String tableName, Connection conn ) {
        this.conn = conn;
        this.tableName = tableName.toLowerCase();
    }
    
    public abstract T mapper(ResultSet rs);
    
    public abstract void  save(T entity) throws SQLException; 
    
    public T findById(int id) throws SQLException {
        String sqlQuery = "SELECT * FROM " + tableName + " WHERE id = ? ;";
        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()) {
            return mapper(rs);
        }
        return null;
    }
    
    public List<T> findAll() throws SQLException {
        List<T> list = new ArrayList<>();
        String sqlQuery = "SELECT * FROM " + tableName + ";";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sqlQuery); 
        while (rs.next()) {
            list.add(mapper(rs));
        }
        return list;
    }
    
    public abstract void update(T entity) throws SQLException;
    
    public boolean delete(int id) throws SQLException {
        String sqlQuery = "DELETE FROM " + tableName + " WHERE id = ? ;";
        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        stmt.setInt(1, id);
        return (stmt.executeUpdate() > 0);
    }
    //void saveTx(T entity, java.sql.Connection conn) throws Exception;
}