package com.revature.reimburstment.utils.database;

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
//            //D:\apache-tomcat-9.0.5\apache-tomcat-9.0.5\webapps\reimburstment\WEB-INF\classes
//            FileReader reader = new FileReader("webapps/reimburstment/WEB-INF/classes/db.properties");
//            props.load(reader);
//            reader.close();
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

        //Connection conn = DriverManager.getConnection(props.getProperty("url"), props.getProperty("username"), props.getProperty("password"));
        Connection conn = DriverManager.getConnection(url, username, password);

        if (conn == null) throw new RuntimeException("Could not establish connection with the database!");

        System.out.println("Connection successfully");
        return conn;
    }
}
