/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgr.service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sgr.bean.MovimentoBean;
import sgr.dao.MovimentoDAO;
import sgr.sql.QueryBuilder;
import sgr.sql.QueryGender;
import sgr.sql.QueryOperation;
import sgr.sql.QueryType;

/**
 *
 * @author WASHINGTON
 */
public class MovimentoService {

    public List<MovimentoBean> listarTodosMovimento(String pNome) {

        List<MovimentoBean> movimentos = new ArrayList<MovimentoBean>();
        QueryBuilder query = new QueryBuilder();

        query.addQuery(QueryOperation.empty, "vw_movimento.cliente", QueryGender.has, pNome, QueryType.text);
        query.addQuery(QueryOperation.and, "vw_movimento.conta_status", QueryGender.equal, String.valueOf(1), QueryType.number);
        query.addQuery(QueryOperation.and, "vw_movimento.item_status", QueryGender.different, "Cancelado", QueryType.text);
        query.addQuery(QueryOperation.and, "vw_movimento.item_status", QueryGender.different, "Cancelamento", QueryType.text);

        MovimentoDAO movimentoDAO = new MovimentoDAO();

        try {
            System.out.println("[movimentoservice] listando movimentos....");
            movimentos = movimentoDAO.listaMovimentos(query);

        } catch (SQLException ex) {
            System.out.println("erro ao tentar listar movimentos: " + ex.getSQLState());
            Logger.getLogger(MovimentoService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return movimentos;
    }

    public List<MovimentoBean> listarMovimentosPorMesa(int pMesaNumero) {

        List<MovimentoBean> movimentos = new ArrayList<MovimentoBean>();
        QueryBuilder query = new QueryBuilder();

        query.addQuery(QueryOperation.empty, "vw_movimento.mesa_numero", QueryGender.equal, String.valueOf(pMesaNumero), QueryType.number);
        query.addQuery(QueryOperation.and, "vw_movimento.conta_status", QueryGender.equal, String.valueOf(1), QueryType.number);
        query.addQuery(QueryOperation.and, "vw_movimento.item_status", QueryGender.different, "Cancelado", QueryType.text);
        query.addQuery(QueryOperation.and, "vw_movimento.item_status", QueryGender.different, "Cancelamento", QueryType.text);

        MovimentoDAO movimentoDAO = new MovimentoDAO();

        try {
            System.out.println("[movimentoservice] listando movimentos....");
            movimentos = movimentoDAO.listaMovimentos(query);

        } catch (SQLException ex) {
            System.out.println("erro ao tentar listar movimentos: " + ex.getSQLState());
            Logger.getLogger(MovimentoService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return movimentos;
    }

     public List<MovimentoBean> listarItensSolicitadosEmPreparo(String pNome) {

        List<MovimentoBean> movimentos = new ArrayList<MovimentoBean>();
        QueryBuilder query = new QueryBuilder();

        query.addQuery(QueryOperation.empty, "vw_movimento.cliente", QueryGender.has, pNome, QueryType.text);
        query.addQuery(QueryOperation.and, "vw_movimento.item_status", QueryGender.equal, "Solicitado", QueryType.text);
        //query.addQuery(QueryOperation.or, "vw_movimento.item_status", QueryGender.equal, "Pronto", QueryType.text);
        query.addQuery(QueryOperation.or, "vw_movimento.item_status", QueryGender.equal, "Em Preparo", QueryType.text);
      
        query.addQuery(QueryOperation.and, "vw_movimento.item_status", QueryGender.different, "Cancelamento", QueryType.text);
        query.addQuery(QueryOperation.and, "vw_movimento.item_status", QueryGender.different, "Cancelado", QueryType.text);
       
        MovimentoDAO movimentoDAO = new MovimentoDAO();

        try {
            System.out.println("[movimentoservice] listando movimentos....");
            movimentos = movimentoDAO.listaMovimentos(query);

        } catch (SQLException ex) {
            System.out.println("erro ao tentar listar movimentos: " + ex.getSQLState());
            Logger.getLogger(MovimentoService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return movimentos;
    }

    
    
    
    
    public List<MovimentoBean> listarItensSolicitados(String pNome) {

        List<MovimentoBean> movimentos = new ArrayList<MovimentoBean>();
        QueryBuilder query = new QueryBuilder();

        query.addQuery(QueryOperation.empty, "vw_movimento.cliente", QueryGender.has, pNome, QueryType.text);
        query.addQuery(QueryOperation.and, "vw_movimento.item_status", QueryGender.equal, "Solicitado", QueryType.text);
        query.addQuery(QueryOperation.or, "vw_movimento.item_status", QueryGender.equal, "Pronto", QueryType.text);
        query.addQuery(QueryOperation.or, "vw_movimento.item_status", QueryGender.equal, "Em Preparo", QueryType.text);
      
        query.addQuery(QueryOperation.and, "vw_movimento.item_status", QueryGender.different, "Cancelamento", QueryType.text);
        query.addQuery(QueryOperation.and, "vw_movimento.item_status", QueryGender.different, "Cancelado", QueryType.text);
       
        MovimentoDAO movimentoDAO = new MovimentoDAO();

        try {
            System.out.println("[movimentoservice] listando movimentos....");
            movimentos = movimentoDAO.listaMovimentos(query);

        } catch (SQLException ex) {
            System.out.println("erro ao tentar listar movimentos: " + ex.getSQLState());
            Logger.getLogger(MovimentoService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return movimentos;
    }

      public List<MovimentoBean> listarItensCancelados(String pNome) {

        List<MovimentoBean> movimentos = new ArrayList<MovimentoBean>();
        QueryBuilder query = new QueryBuilder();

        query.addQuery(QueryOperation.empty, "vw_movimento.cliente", QueryGender.has, pNome, QueryType.text);
        query.addQuery(QueryOperation.and, "vw_movimento.item_status", QueryGender.equal, "Cancelamento", QueryType.text);
     
        MovimentoDAO movimentoDAO = new MovimentoDAO();

        try {
            System.out.println("[movimentoservice] listando movimentos....");
            movimentos = movimentoDAO.listaMovimentos(query);

        } catch (SQLException ex) {
            System.out.println("erro ao tentar listar movimentos: " + ex.getSQLState());
            Logger.getLogger(MovimentoService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return movimentos;
    }

     public List<MovimentoBean> gerarRelatorios(Date pData) {

        List<MovimentoBean> movimentos = new ArrayList<MovimentoBean>();
        QueryBuilder query = new QueryBuilder();

        query.addQuery(QueryOperation.empty, "vw_movimento.data", QueryGender.has, String.valueOf(pData), QueryType.text);
        query.addQuery(QueryOperation.and, "vw_movimento.conta_status", QueryGender.equal, String.valueOf(0), QueryType.number);
        query.addQuery(QueryOperation.and, "vw_movimento.item_status", QueryGender.equal, "Entregue", QueryType.text);

        MovimentoDAO movimentoDAO = new MovimentoDAO();

        try {
            System.out.println("[movimentoservice] listando movimentos....");
            movimentos = movimentoDAO.listaMovimentos(query);

        } catch (SQLException ex) {
            System.out.println("erro ao tentar listar movimentos: " + ex.getSQLState());
            Logger.getLogger(MovimentoService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return movimentos;
    }

   
    public List<MovimentoBean> listarItensProntos(String pNome) {

        List<MovimentoBean> movimentos = new ArrayList<MovimentoBean>();
        QueryBuilder query = new QueryBuilder();

        query.addQuery(QueryOperation.empty, "vw_movimento.cliente", QueryGender.has, pNome, QueryType.text);
        query.addQuery(QueryOperation.and, "vw_movimento.item_status", QueryGender.equal, "Pronto", QueryType.text);
        
        MovimentoDAO movimentoDAO = new MovimentoDAO();

        try {
            System.out.println("[movimentoservice] listando movimentos....");
            movimentos = movimentoDAO.listaMovimentos(query);

        } catch (SQLException ex) {
            System.out.println("erro ao tentar listar movimentos: " + ex.getSQLState());
            Logger.getLogger(MovimentoService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return movimentos;
    }
  
    
}
