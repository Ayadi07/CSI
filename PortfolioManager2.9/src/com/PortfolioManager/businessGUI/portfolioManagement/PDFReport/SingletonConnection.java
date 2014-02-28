
package com.PortfolioManager.businessGUI.portfolioManagement.PDFReport;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Ahmed
 */

public class SingletonConnection {

    private String url = "jdbc:mysql://localhost:3306/portfolio";
    private String login = "root";
    private String pwd = "root";
    private static Connection singleConnection;

    private SingletonConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
        } catch (ClassNotFoundException ex) {
            System.err.println("Driver failed");
            ex.printStackTrace();
        }
        try {
            singleConnection = DriverManager.getConnection(url, login, pwd);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public static Connection getConnexion() {
        if (singleConnection == null) {
            new SingletonConnection();
        }
        return singleConnection;
    }
}
