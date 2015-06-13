package sgr.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sgr.bean.FuncionarioBean;
import sgr.dao.FuncionarioDAO;
import sgr.sql.QueryBuilder;
import sgr.sql.QueryGender;
import sgr.sql.QueryOperation;
import sgr.sql.QueryType;


public class FuncionarioService {
    
    public void salvar(FuncionarioBean pFuncionarioBean) {
        
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        if(pFuncionarioBean.getCodigo() == 0) {
        try {
            System.out.println("salvando funcionario...");
            funcionarioDAO.inserirFuncionario(pFuncionarioBean);
        } catch (SQLException ex) {
            System.out.println("erro ao tentar salvar funcionario" + ex.getMessage());
            System.out.println("erro ao tentar salvar funcionario" + ex.getSQLState());
            Logger.getLogger(FuncionarioService.class.getName()).log(Level.SEVERE, null, ex);
        } 
        } else {
            try {
                System.out.println("ALTERANDO");
                funcionarioDAO.alterarFuncionario(pFuncionarioBean);
            } catch (SQLException ex) {
                Logger.getLogger(FuncionarioService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public void deletarFuncionario(FuncionarioBean pFuncionarioBean) {
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        try {
            funcionarioDAO.deletarFuncionario(pFuncionarioBean);
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<FuncionarioBean> listarFuncionario(String pNome) {
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        List<FuncionarioBean> listFuncionario = new ArrayList<FuncionarioBean>();
        QueryBuilder query = new QueryBuilder();
        query.addQuery(QueryOperation.empty, "funcionario.nome", QueryGender.has, pNome, QueryType.text);
       // query.addQuery(QueryOperation.and, "funcionario.funcao", QueryGender.different, "Gerente", QueryType.text);
        
        try {
            listFuncionario = funcionarioDAO.listarFuncionario(query);
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FuncionarioService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listFuncionario;
    }
    
  public List<FuncionarioBean> logar(String pLogin,String pSenha) {
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        List<FuncionarioBean> listFuncionario = new ArrayList<FuncionarioBean>();
        QueryBuilder query = new QueryBuilder();
        query.addQuery(QueryOperation.empty, "funcionario.nome_usuario", QueryGender.equal, pLogin, QueryType.text);
        query.addQuery(QueryOperation.and, "funcionario.senha", QueryGender.equal, pSenha, QueryType.text);
        //query.addQuery(QueryOperation.and, "funcionario.nivel", QueryGender.equal, String.valueOf(pNivel), QueryType.number);
        
        try {
            listFuncionario = funcionarioDAO.listarFuncionario(query);
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FuncionarioService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listFuncionario;
    }   
    }
   
    
  
