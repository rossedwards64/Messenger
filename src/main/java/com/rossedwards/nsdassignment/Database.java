package com.rossedwards.nsdassignment;

import java.sql.*;

public class Database {

    public static Connection connect() {
        Connection conn = null;

        try {
            String fileName = "identifier.sqlite";
            String url = "jdbc:sqlite:" + fileName;
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void logIn(String username) {
        Connection conn = connect();
        String searchSQL = "SELECT * FROM Users WHERE Username = ? OR (Username IS NULL)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(searchSQL);
            pstmt.setString(1, username);
            ResultSet result = pstmt.executeQuery();

            if (result.next()) {
                System.out.println("Logging in " + username);
                conn.close();
            } else {
                System.out.println("Registering " + username);
                String insertSQL = "INSERT INTO Users(Username) VALUES(?)";
                pstmt = conn.prepareStatement(insertSQL);
                pstmt.setString(1, username);
                pstmt.executeUpdate();
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
