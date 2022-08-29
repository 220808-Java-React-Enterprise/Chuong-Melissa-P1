package com.revature.yolp.utils.database;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/* Singleton design pattern */
public class ConnectionFactory {
    private static ConnectionFactory connectionFactory;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private final Properties props = new Properties();

    private ConnectionFactory() {
//        try {
//            String dir = System.getProperty("user.dir");
//            System.out.println("\n===================================" + dir + "==================================\n");
//            props.load(new FileReader("src/main/resources/db.properties"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static ConnectionFactory getInstance() {
        if (connectionFactory == null) connectionFactory = new ConnectionFactory();
        return connectionFactory;
    }

    public Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://revature.cbfjslydmnoj.us-west-1.rds.amazonaws.com:5432/postgres?currentSchema=reimbursement";
        String username = "postgres";
        String password = "revature";

        System.out.println("url:" + url);
        System.out.println("username:" + username);
        System.out.println("password:" + password);

        Connection conn = DriverManager
                .getConnection(url, username, password);

        if (conn == null) throw new RuntimeException("Could not establish connection with the database!");

        System.out.println("Connection successfully");
        return conn;
    }
}
