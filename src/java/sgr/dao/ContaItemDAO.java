/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sgr.service.ContaItemBean;
import sgr.util.ConnectionBuilder;

/**
 *
 * @author WASHINGTON
 */
public class ContaItemDAO {
    
    public void alterarItemStatus(ContaItemBean pContaItemBean) {
        ConnectionBuilder conexao = new ConnectionBuilder();
        Connection conn = conexao.getConnection();
        String sql = "update conta_item set status=? where codigo=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, pContaItemBean.getStatus());
            ps.setInt(2, pContaItemBean.getCodigo());
            ps.execute();
            ps.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ContaItemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
