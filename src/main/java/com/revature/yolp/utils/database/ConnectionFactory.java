package com.revature.yolp.utils.database;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
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
        try {
            //D:\apache-tomcat-9.0.5\apache-tomcat-9.0.5\webapps\reimburstment\WEB-INF\classes
            FileReader reader = new FileReader("webapps/reimburstment/WEB-INF/classes/db.properties");
            props.load(reader);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ConnectionFactory getInstance() {
        if (connectionFactory == null) connectionFactory = new ConnectionFactory();
        return connectionFactory;
    }

    public Connection getConnection() throws SQLException {

        Connection conn = DriverManager.getConnection(props.getProperty("url"), props.getProperty("username"), props.getProperty("password"));

        if (conn == null) throw new RuntimeException("Could not establish connection with the database!");

        System.out.println("Connection successfully");
        return conn;
    }
}
