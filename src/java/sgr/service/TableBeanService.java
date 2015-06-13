/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgr.service;

import java.util.ArrayList;
import java.util.List;
import sgr.bean.TableBean;
import sgr.dao.TableDAO;
import sgr.sql.QueryBuilder;
import sgr.sql.QueryGender;
import sgr.sql.QueryOperation;
import sgr.sql.QueryType;

/**
 *
 * @author WASHINGTON
 */
public class TableBeanService {
    
    public List<TableBean> listarMesasAbertas() {
       TableDAO tableDAO = new TableDAO();
       List<TableBean> lista = new ArrayList<TableBean>();    
       QueryBuilder query = new QueryBuilder();
       query.addQuery(QueryOperation.empty, "mesa.status", QueryGender.equal, String.valueOf(1), QueryType.number);
       return lista = tableDAO.listarMesasEmAberto(query);
        
    }
    
    public boolean fecharMesa(TableBean pTableBean) {
        TableDAO tableDAO = new TableDAO();
        return tableDAO.fecharMesa(pTableBean);
        
    }
}
