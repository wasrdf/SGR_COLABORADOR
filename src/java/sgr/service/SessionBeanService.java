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
    
}
