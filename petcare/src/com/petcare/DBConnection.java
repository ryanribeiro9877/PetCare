package com.petcare;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = System.getenv("DB_URL") != null 
        ? System.getenv("DB_URL") 
        : "jdbc:postgresql://localhost:5432/Petcare";
    
    private static final String USER = System.getenv("DB_USER") != null 
        ? System.getenv("DB_USER") 
        : "postgres";
    
    private static final String PASSWORD = System.getenv("DB_PASSWORD") != null 
        ? System.getenv("DB_PASSWORD") 
        : "";

    public static Connection getConnection() throws SQLException {
        if (PASSWORD.isEmpty()) {
            throw new SQLException(
                "Senha do banco de dados nao configurada. " +
                "Configure a variavel de ambiente DB_PASSWORD antes de executar."
            );
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
