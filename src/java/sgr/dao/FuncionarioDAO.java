package sgr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sgr.bean.FuncionarioBean;
import sgr.sql.QueryBuilder;
import sgr.util.ConnectionBuilder;

/**
 *
 * @author washington
 */
public class FuncionarioDAO {
    
     public List<FuncionarioBean> logar (QueryBuilder sClausula) throws SQLException, ClassNotFoundException {

        ConnectionBuilder conexao = new ConnectionBuilder();
        Connection conn = conexao.getConnection();
        List<FuncionarioBean> listaFuncionario = new ArrayList<FuncionarioBean>();
        String sql = "SELECT * FROM FUNCIONARIO " + sClausula.buildQuery();
        System.out.println(sql);

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            FuncionarioBean funcionarioBean = new FuncionarioBean();
            funcionarioBean.setCodigo(rs.getInt("codigo"));
            funcionarioBean.setNome(rs.getString("nome"));
            funcionarioBean.setDataNasc(rs.getDate("data_nasc"));
            funcionarioBean.setTelefone(rs.getString("tel_movel"));
            funcionarioBean.setFuncao(rs.getString("funcao"));
            funcionarioBean.setCpf(rs.getString("cpf"));
            funcionarioBean.setRg(rs.getString("rg"));
            funcionarioBean.setNome_usuario(rs.getString("nome_usuario"));
            funcionarioBean.setSenha(rs.getString("senha"));
           
            listaFuncionario.add(funcionarioBean);
            
          
        }

        rs.close();
        ps.close();
        conn.close();
        return listaFuncionario;

    }
 
         public List<FuncionarioBean> listarFuncionario (QueryBuilder sClausula) throws SQLException, ClassNotFoundException {

        ConnectionBuilder conexao = new ConnectionBuilder();
        Connection conn = conexao.getConnection();
        List<FuncionarioBean> listaFuncionario = new ArrayList<FuncionarioBean>();
        String sql = "SELECT * FROM FUNCIONARIO " + sClausula.buildQuery();
        System.out.println(sql);

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            FuncionarioBean funcionarioBean = new FuncionarioBean();
            funcionarioBean.setCodigo(rs.getInt("codigo"));
            funcionarioBean.setNome(rs.getString("nome"));
            funcionarioBean.setDataNasc(rs.getDate("data_nasc"));
            funcionarioBean.setTelefone(rs.getString("tel_movel"));
            funcionarioBean.setFuncao(rs.getString("funcao"));
            funcionarioBean.setCpf(rs.getString("cpf"));
            funcionarioBean.setRg(rs.getString("rg"));
            funcionarioBean.setNome_usuario(rs.getString("nome_usuario"));
            funcionarioBean.setSenha(rs.getString("senha"));
           
            listaFuncionario.add(funcionarioBean);
            
          
        }

        rs.close();
        ps.close();
        conn.close();
        return listaFuncionario;

    }
     
 
     
     
     public void  inserirFuncionario(FuncionarioBean pFuncionarioBean) throws SQLException {
        ConnectionBuilder conexao = new ConnectionBuilder();
        Connection conn = conexao.getConnection();
        
        String sql = "INSERT INTO FUNCIONARIO(Nome,data_nasc,Tel_movel,Funcao,CPF,RG,Nome_Usuario,Senha,status)VALUES(?,?,?,?,?,?,?,?,?)"; 
        System.out.println(sql);

        PreparedStatement ps = conn.prepareStatement(sql);
        
        ps.setString(1, pFuncionarioBean.getNome());
         
        ps.setDate(2, new java.sql.Date(pFuncionarioBean.getDataNasc().getTime()));
         System.out.println("data de nascimento: " + pFuncionarioBean.getDataNasc());  
        
        ps.setString(3, pFuncionarioBean.getTelefone());
        ps.setString(4, pFuncionarioBean.getFuncao());
        ps.setString(5, pFuncionarioBean.getCpf());
        ps.setString(6, pFuncionarioBean.getRg());
        ps.setString(7, pFuncionarioBean.getNome_usuario());
        ps.setString(8, pFuncionarioBean.getSenha());
        ps.setString(9, pFuncionarioBean.getSituacao());
        
        ps.execute();
        ps.close();
        conn.close();
        

     }
    
     public void alterarFuncionario(FuncionarioBean pFuncionario) throws SQLException {
         ConnectionBuilder conexao = new ConnectionBuilder();
         Connection conn = conexao.getConnection();
         
         String sql = "UPDATE FUNCIONARIO SET NOME=?,DATA_NASC=?,CPF=?,RG=?,TEL_MOVEL=?,"
                 + "FUNCAO=?,NOME_USUARIO=?,SENHA=?,STATUS=? WHERE CODIGO=?";
         PreparedStatement ps = conn.prepareStatement(sql);
         ps.setString(1, pFuncionario.getNome());
         ps.setDate(2, new java.sql.Date(pFuncionario.getDataNasc().getTime()));
         ps.setString(3, pFuncionario.getCpf());
         ps.setString(4, pFuncionario.getRg());
         ps.setString(5, pFuncionario.getTelefone());
         ps.setString(6, pFuncionario.getFuncao());
         ps.setString(7, pFuncionario.getNome_usuario());
         ps.setString(8, pFuncionario.getSenha());
         ps.setString(9, pFuncionario.getSituacao());
         ps.setInt(10, pFuncionario.getCodigo());
         ps.execute();
         ps.close();
         conn.close();
     }
     
     public void deletarFuncionario(FuncionarioBean pFuncionarioBean) throws SQLException {
       ConnectionBuilder conexao = new ConnectionBuilder();
        Connection conn = conexao.getConnection();
        
        String sql = "delete from funcionario where codigo=?"; 
        System.out.println(sql);

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, pFuncionarioBean.getCodigo());
        ps.execute();
        ps.close();
        conn.close();
     }
}
