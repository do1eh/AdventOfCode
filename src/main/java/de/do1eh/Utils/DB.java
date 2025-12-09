package de.do1eh.Utils;

import java.sql.*;

public class DB {

    Connection conn;

    public Connection getConnection() {

        String JDBC_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
        String USER = "sa";
        String PASSWORD = "";
        try {
            Class.forName("org.h2.Driver");
            if (null == this.conn || this.conn.isClosed()) {
                this.conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
                System.out.println("Verbindung zur H2-Datenbank erfolgreich.");
            }
            return conn;
        } catch (SQLException e) {
            throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            //}
        }


    }
}