/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import domain.Company;
import domain.Customer;
import domain.DefaultDomainObject;
import domain.Individual;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user
 */
public class DatabaseBroker {

    public DatabaseBroker() {
    }
    
    public List<DefaultDomainObject> getAll(DefaultDomainObject ddo) throws SQLException{
        String query = ddo.getGetAllQuery();
        Connection connection = DatabaseConnection.getInstance().getConnection();
        Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery(query);
        return ddo.returnList(rs);
    }
    
    public List<DefaultDomainObject> getAllOrdered(DefaultDomainObject ddo) throws SQLException{
        String query = ddo.getGetAllOrderedQuery();
        Connection connection = DatabaseConnection.getInstance().getConnection();
        Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery(query);
        return ddo.returnList(rs);
    }
    
    public List<DefaultDomainObject> getByCondition(DefaultDomainObject ddo) throws SQLException{
        String query = ddo.getGetByConditionQuery();
        Connection connection = DatabaseConnection.getInstance().getConnection();
        Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery(query);
        return ddo.returnList(rs);
    }
    
    public DefaultDomainObject getOneByCondition(DefaultDomainObject ddo) throws SQLException{
        String query = ddo.getGetByConditionQuery();
        Connection connection = DatabaseConnection.getInstance().getConnection();
        Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery(query);
        return ddo.returnList(rs).getFirst();
    }
    
    public void insertRow(DefaultDomainObject ddo) throws SQLException{
        String query = ddo.getInsertQuery();
        Connection connection = DatabaseConnection.getInstance().getConnection();
        Statement s = connection.createStatement();
        s.executeUpdate(query);
    }
    
    public Long insertRowAndGetId(DefaultDomainObject ddo) throws SQLException{
        String query = ddo.getInsertQuery();
        Connection connection = DatabaseConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        int affectedRows = ps.executeUpdate();
        Long id = null;
        if (affectedRows > 0) {
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = generatedKeys.getLong(1);
                } else {
                    System.out.println("No ID generated.");
                }
            }
        } else {
            System.out.println("Insert failed, no rows affected.");
        }
        return id;
    }
    
    public void deleteRow(DefaultDomainObject ddo) throws SQLException {
        String query = ddo.getDeleteQuery();
        Connection connection = DatabaseConnection.getInstance().getConnection();
        Statement s = connection.createStatement();
        s.executeUpdate(query);
    }
    
    public void updateRow(DefaultDomainObject ddo) throws SQLException {
        String query = ddo.getUpdateQuery();
        Connection connection = DatabaseConnection.getInstance().getConnection();
        Statement s = connection.createStatement();
        s.executeUpdate(query);
    }
    
    
    public void closeCon(){
        DatabaseConnection.getInstance().closeConnection();
    }

    public void commit() throws SQLException {
        DatabaseConnection.getInstance().getConnection().commit();
    }

    public void rollback() throws SQLException {
        DatabaseConnection.getInstance().getConnection().rollback();
    }

}
