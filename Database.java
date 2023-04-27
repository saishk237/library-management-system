package com.pbl;

import java.sql.*;

public class Database {

    //jdbc:postgresql://localhost:5432/pbl
    private final String url = "jdbc:postgresql://localhost:5432/pbl";
    private final String user = "postgres";
    private final String password = "prasad";

    public static Connection DBConnection;
    /**
     * Connect to the PostgreSQL database
     *
     * @return a Connection object
     */
    public Connection connect() {
        DBConnection = null;
        try {
            DBConnection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");

            Statement ps=DBConnection.createStatement();
            ResultSet rs=ps.executeQuery("SELECT * FROM librarian");

            if(!rs.next()){
                String sql = "INSERT INTO librarian VALUES (1,'test', 'test', 'test')";
                ps.executeUpdate(sql);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return DBConnection;
    }
}
