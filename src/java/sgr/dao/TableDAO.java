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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sgr.bean.TableBean;
import sgr.sql.QueryBuilder;
import sgr.util.ConnectionBuilder;

/**
 *
 * @author WASHINGTON
 */
public class TableDAO {

    public List<TableBean> listarMesasEmAberto(QueryBuilder sClausula) {
        ConnectionBuilder conexao = new ConnectionBuilder();
        Connection conn = conexao.getConnection();
        List<TableBean> mesas = new ArrayList<TableBean>();
        String sql = "select * from mesa" + sClausula.buildQuery();
        System.out.println(sql);
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TableBean tableBean = new TableBean();
                tableBean.setNumero(rs.getInt("numero"));
                tableBean.setFrag(rs.getString("flag"));
                tableBean.setStatus(rs.getBoolean("status"));
                mesas.add(tableBean);

            }
            rs.close();
            ps.close();
            conn.close();
            return mesas;
        } catch (SQLException ex) {
            Logger.getLogger(TableDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro:" + ex.getSQLState());
            return null;
        }
    }

    public boolean fecharMesa(TableBean pTableBean) {
        ConnectionBuilder conexao = new ConnectionBuilder();
        Connection conn = conexao.getConnection();

        String sql = "update mesa set status = ? where numero = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setBoolean(1, pTableBean.isStatus());
            ps.setInt(2, pTableBean.getNumero());
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(TableDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }
}