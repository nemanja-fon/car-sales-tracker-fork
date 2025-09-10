/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.*;

/**
 *
 * @author user
 */
public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    public DatabaseConnection() {
        DatabaseProperties props = DatabaseProperties.getInstance();
        try {
            connection = DriverManager.getConnection(props.getProperty("url"), props.getProperty("user"), props.getProperty("password"));
            connection.setAutoCommit(false);
            System.out.println("Database connected successfully!");
        } catch (SQLException ex) {
            System.out.println("Connection error: "+ex.getMessage());
        }
    }
    
    public static DatabaseConnection getInstance(){
        if(instance == null){
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
    
    public void closeConnection(){
        try {
            if(connection!=null && !connection.isClosed()){
                connection.close();
                System.out.println("Connection closed successfully!");
            }
        } catch (SQLException ex) {
            System.out.println("Error with closing the database connection!\n"+ex.getMessage());
        }
    }
}
