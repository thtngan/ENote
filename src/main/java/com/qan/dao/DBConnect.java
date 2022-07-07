/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qan.dao;
import java.sql.*;

/**
 *
 * @author Envy
 */
public class DBConnect {
    public static Connection getConnection(){
        Connection conn = null;
        String dbURL = "jdbc:sqlserver://localhost\\SQLEXPRESS:1433;encrypt=true;"
        + "trustServerCertificate=true"
        + ";databaseName=MMT";
        String user = "sa";
        String pass = "sa";
        try{
            conn = DriverManager.getConnection(dbURL, user, pass);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
    
    public static void main(String[] args) throws SQLException {
//        new MainJFrame().setVisible(true);
        Connection tmp = getConnection();
        System.out.println(tmp.toString());
        tmp.close();
    }
}
