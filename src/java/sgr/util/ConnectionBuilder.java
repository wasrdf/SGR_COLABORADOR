/*
 SGR ALPHA - UTILITY PACKAGE
 File: CONNECTIONBUILDER.JAVA | Last Major Update: 30.04.2015
 Developer: Kevin Raian, Washington Reis
 IDINALOG REBORN © 2015
 */
package sgr.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionBuilder {


    public static Connection con = null;
    public Connection getConnection() {
        System.out.println("Conectando ao banco...");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/idina743_sgr", "root", "26583205");
            System.out.println("Conectado.");
        } catch (ClassNotFoundException ex) {
            System.out.println("Classe não encontrada, adicione o driver nas bibliotecas.");
            Logger.getLogger(ConnectionBuilder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
        
        return con;
    }  

    public static void main(String[] args) {
        ConnectionBuilder conexao = new ConnectionBuilder();
        conexao.getConnection();
    }

}
