/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgr.service;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sgr.bean.SessionBean;
import sgr.dao.SessionBeanDAO;
import sgr.sql.QueryBuilder;
import sgr.sql.QueryGender;
import sgr.sql.QueryOperation;
import sgr.sql.QueryType;

/**
 *
 * @author WASHINGTON
 */
public class SessionBeanService {
    
    public void encerrarConta(SessionBean pSession) {
        SessionBeanDAO dao = new SessionBeanDAO();
        try {
            dao.encerrarConta(pSession);
        } catch (SQLException ex) {
            Logger.getLogger(SessionBeanService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public SessionBean carregarTotal(int pCliente) {
        SessionBean sessionBean = new SessionBean();
        SessionBeanDAO sessioBeanDAO = new SessionBeanDAO();
        QueryBuilder condicao = new QueryBuilder();
        condicao.addQuery(QueryOperation.empty, "conta.cliente_codigo", QueryGender.equal, String.valueOf(pCliente), QueryType.number);
        //contas em aberto...    
        condicao.addQuery(QueryOperation.and, "conta.status", QueryGender.equal, String.valueOf(1), QueryType.number);
        try {
            return sessionBean=sessioBeanDAO.carregarTotal(condicao);
        } catch (SQLException ex) {
       
            Logger.getLogger(SessionBeanService.class.getName()).log(Level.SEVERE, null, ex);
          return null;
        }
    }
    
}
