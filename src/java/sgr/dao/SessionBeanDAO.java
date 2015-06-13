/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import sgr.bean.SessionBean;
import sgr.util.ConnectionBuilder;

/**
 *
 * @author WASHINGTON
 */
public class SessionBeanDAO {
    
    public void encerrarConta(SessionBean pSession) throws SQLException {
        
         ConnectionBuilder conexao = new ConnectionBuilder();
        Connection conn = conexao.getConnection();
        String sql = "update conta set status = ? where codigo =?";
        PreparedStatement ps = conn.prepareStatement(sql);
        System.out.println(sql);
        ps.setBoolean(1, pSession.isStatus());
        ps.setInt(2, pSession.getCodigo());
        ps.execute();
        ps.close();
        conn.close();
        
    }
    
}
