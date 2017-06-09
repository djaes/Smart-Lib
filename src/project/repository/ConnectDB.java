package project.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by angel on 02/06/2017.
 */
public class ConnectDB {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public ConnectDB() {

        System.out.println("-------- MySQL JDBC Connection Testing ------------");

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            //e.printStackTrace();
            return;
        }

        System.out.println("MySQL JDBC Driver Registered!");


        try {

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartlib","smartlib", "smartlib");


        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            //e.printStackTrace();

        }

        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }
    }

}
