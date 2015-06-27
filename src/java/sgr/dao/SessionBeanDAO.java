/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sgr.bean.SessionBean;
import sgr.sql.QueryBuilder;
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
    
    public double atualizarTotalConta(SessionBean pSession)  {
        
         ConnectionBuilder conexao = new ConnectionBuilder();
        Connection conn = conexao.getConnection();
        String sql = "update conta set total = ? where codigo =?";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(sql);
        System.out.println(sql);
        ps.setDouble(1, pSession.getTotal());
        ps.setInt(2, pSession.getCodigo());
        ps.execute();
        ps.close();
        conn.close();
        return pSession.getTotal();
        } catch (SQLException ex) {
            Logger.getLogger(SessionBeanDAO.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        
    }
    
    public SessionBean carregarTotal(QueryBuilder query) throws SQLException {
           ConnectionBuilder conexao = new ConnectionBuilder();
        Connection conn = conexao.getConnection();
         SessionBean sessionBean = new SessionBean();
        String sql = "select total from conta " + query.buildQuery();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
       
        sessionBean.setTotal(rs.getDouble("total"));
        }
        rs.close();
        ps.close();
        conn.close();
       
        return sessionBean;
       
    }
    
    
}
