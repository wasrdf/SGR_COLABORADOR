/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgr.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sgr.bean.ItemBean;
import sgr.dao.ItemBeanDAO;
import sgr.sql.QueryBuilder;
import sgr.sql.QueryGender;
import sgr.sql.QueryOperation;
import sgr.sql.QueryType;

/**
 *
 * @author WASHINGTON
 */
public class ItemBeanService {

    public ItemBean salvar(ItemBean pItemBean) {
        ItemBeanDAO itemBeanDAO = new ItemBeanDAO();

        if (pItemBean.getCodigo() == 0) {

            try {
              return  itemBeanDAO.inserirItem(pItemBean);
            } catch (SQLException ex) {
                Logger.getLogger(ItemBeanService.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }

        } else {

            try {
               return itemBeanDAO.atualizarItem(pItemBean);
            } catch (SQLException ex) {
                Logger.getLogger(ItemBeanService.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }

    }

    public boolean deletar(ItemBean pItemBean) {
        ItemBeanDAO itemBeanDAO = new ItemBeanDAO();
        try {
            return itemBeanDAO.deletar(pItemBean);
        } catch (Exception e) {
            return false;
        }

    }

    public List<ItemBean> listarItem(String pNome) {
        List<ItemBean> lista = new ArrayList<ItemBean>();
        QueryBuilder condicao = new QueryBuilder();
        ItemBeanDAO itemBeanDAO = new ItemBeanDAO();
        condicao.addQuery(QueryOperation.empty, "item.nome", QueryGender.has, pNome, QueryType.text);
        try {
            lista = itemBeanDAO.listarItem(condicao);
        } catch (SQLException ex) {
            Logger.getLogger(ItemBeanService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;

    }
}
