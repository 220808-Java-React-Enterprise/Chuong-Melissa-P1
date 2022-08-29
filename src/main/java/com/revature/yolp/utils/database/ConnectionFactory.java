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
//        try {
//            String dir = System.getProperty("user.dir");
//            System.out.println("\n===================================" + dir + "==================================\n");
//            ServletConfig sc = new ServletConfig() {
//                @Override
//                public String getServletName() {
//                    return null;
//                }
//
//                @Override
//                public ServletContext getServletContext() {
//                    return null;
//                }
//
//                @Override
//                public String getInitParameter(String s) {
//                    return null;
//                }
//
//                @Override
//                public Enumeration<String> getInitParameterNames() {
//                    return null;
//                }
//            } ;
//            ServletContext context = sc.getServletContext();
//            InputStream propStream = context.getResourceAsStream("/WEB-INF/classes/app.properties");
//            props.load(propStream);
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
