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
import sgr.bean.ItemBean;
import sgr.sql.QueryBuilder;
import sgr.util.ConnectionBuilder;

/**
 *
 * @author WASHINGTON
 */
public class ItemBeanDAO {

    public ItemBean inserirItem(ItemBean pItemBean) throws SQLException {
        ConnectionBuilder conexao = new ConnectionBuilder();
        Connection conn = conexao.getConnection();

        String sql = "INSERT INTO ITEM (Nome,Composicao,Tipo,Preco) VALUES (?,?,?,?)";
        System.out.println(sql);

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, pItemBean.getNome());
        ps.setString(2, pItemBean.getComposicao());
        ps.setString(3, pItemBean.getTipo());
        ps.setDouble(4, pItemBean.getPreço());

        ps.execute();
        ps.close();
        conn.close();
        return pItemBean;
    }

    public ItemBean atualizarItem(ItemBean pItemBean) throws SQLException {
        ConnectionBuilder conexao = new ConnectionBuilder();
        Connection conn = conexao.getConnection();

        String sql = "UPDATE ITEM SET NOME=?, COMPOSICAO=?,TIPO=?, PRECO=? WHERE CODIGO=?";
        System.out.println(sql);

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, pItemBean.getNome());
        ps.setString(2, pItemBean.getComposicao());
        ps.setString(3, pItemBean.getTipo());
        ps.setDouble(4, pItemBean.getPreço());
        ps.setInt(5, pItemBean.getCodigo());
        ps.execute();
        ps.close();
        conn.close();
        return pItemBean;
    }

    public boolean deletar(ItemBean pItemBean) throws SQLException {
        ConnectionBuilder conexao = new ConnectionBuilder();
        Connection conn = conexao.getConnection();

        String sql = "delete from item where codigo=?";
        try {

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, pItemBean.getCodigo());
            ps.execute();
            ps.close();
            conn.close();
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public List<ItemBean> listarItem(QueryBuilder sClausula) throws SQLException {
        List<ItemBean> lista = new ArrayList<ItemBean>();
        ConnectionBuilder conexao = new ConnectionBuilder();
        Connection conn = conexao.getConnection();
        String sql = "select * from item" + sClausula.buildQuery();

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            ItemBean itemBean = new ItemBean();
            itemBean.setNome(rs.getString("nome"));
            itemBean.setComposicao(rs.getString("composicao"));
            itemBean.setTipo(rs.getString("tipo"));
            itemBean.setPreço(rs.getDouble("preco"));
            itemBean.setCodigo(rs.getInt("codigo"));
            lista.add(itemBean);
        }
        rs.close();
        ps.close();
        conn.close();
        return lista;
    }

    public List<ItemBean> listarItemTipoDistinct() {
        List<ItemBean> lista = new ArrayList<ItemBean>();
        ConnectionBuilder conexao = new ConnectionBuilder();
        Connection conn = conexao.getConnection();
        String sql = "select distinct(tipo) from item";

        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ItemBean itemBean = new ItemBean();
                itemBean.setTipo(rs.getString("tipo"));
                lista.add(itemBean);

            }

            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ItemBeanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;
    }

}
