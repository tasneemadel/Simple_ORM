/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sw_maintenance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseEntity {

    private String databaseName, userName, password;
    private Connection connection;
    private String url;
    private Statement statment;
    private DBSet Dbset;

    public DatabaseEntity() {
    }

    public DatabaseEntity(String databaseName, String userName, String password) {
        this.databaseName = databaseName;
        this.userName = userName;
        this.password = password;
        System.out.println(this.databaseName + " " + this.userName + " " + this.password);
    }

    public DBSet getDBInstance(Class<?> className) {

        DBSet db = new DBSet(this, className);
        this.Dbset = db;
        // this.Dbset.DBInitation(className);
        return db;

    }

    public void openConnection() {
        System.out.println("Test for entering the function ");
        try {

            //Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            url = "jdbc:mysql://localhost:3306/";
            //Open a connection
            connection = DriverManager.getConnection(
                    url, userName, password);
            System.out.println("Connected");
            //Execute a query
            statment = connection.createStatement();
            String sql = "CREATE DATABASE " + databaseName;
            System.out.println("Dataabse name " + databaseName);
            statment.executeUpdate(sql);
            url = "jdbc:mysql://localhost:3306/" + databaseName;
            connection = DriverManager.getConnection(
                    url, userName, password);

        } catch (SQLException e) {
            System.out.println("Error in opening " + e);

            try {
                System.out.println("Database is already existed");
                url = "jdbc:mysql://localhost:3306/" + databaseName.toLowerCase();
                //Open a connection
                connection = DriverManager.getConnection(url, userName, password);
                //Execute a query
                
                statment = connection.createStatement();
                System.out.println("Execute a query");
            } catch (SQLException ex) {
                System.out.println("Error  " + ex);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Error  " + e);
        }
    }

    public void closeConnection() {
        try {
            connection.close();
            statment.close();
            System.out.println("Connection is closed ");
        } catch (SQLException e) {
        }
    }

    public void excuteSql(String query) throws SQLException {
        System.out.println("Query: " + query);

        try {
            int i = statment.executeUpdate(query);

            System.out.println("result of query " + i);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // New added by Team one
    public ResultSet excuteQUERY(String query) throws SQLException {
        System.out.println("Execute Query " + query);
        System.out.println(" " + statment.executeQuery(query));
        return statment.executeQuery(query);
    }
}
