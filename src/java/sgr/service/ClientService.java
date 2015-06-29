/*
    SGR ALPHA - SERVICE PACKAGE
    File: CLIENTSERVICE.JAVA | Last Major Update: 29.04.2015
    Developer: Kevin Raian, Washington Reis
    IDINALOG REBORN © 2015
*/

package sgr.service;

import sgr.bean.ClientBean;
import sgr.sql.QueryBuilder;
import sgr.sql.QueryGender;
import sgr.sql.QueryOperation;
import sgr.sql.QueryType;
import sgr.dao.ClientDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ClientService {

    // Login
    public List<ClientBean> doLogin(String clientUsername, String clientPassword) {
        
        List<ClientBean> currentClient = new ArrayList<ClientBean>();
        QueryBuilder query = new QueryBuilder();
        ClientDAO clientDAO = new ClientDAO();

        query.addQuery(QueryOperation.empty, "cliente.nome_usuario", QueryGender.equal, clientUsername, QueryType.text);
        query.addQuery(QueryOperation.and, "cliente.senha", QueryGender.equal, clientPassword, QueryType.text);

        try {
            try {
                System.out.println("[CLIENT SERVICE] Loading currentClient with clientDAO.loadClient...");                
                currentClient = clientDAO.loadClient(query);
                System.out.println("[CLIENT SERVICE] Query loaded to ClientDAO: '" + query + "'.");
            } catch (ClassNotFoundException ex) {
                System.out.println("[CLIENT SERVICE] ERROR: Failed to load Query on ClientDAO.");
                Logger.getLogger(ClientService.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (SQLException e) {
            System.out.println("[CLIENT SERVICE] ERROR: Failed to execute currentClient query.'" + query + "'.");
            System.out.println(e.getMessage());

        }
        
        System.out.println("[CLIENT SERVICE] currentCliente value: '" + currentClient + "'.");
        return currentClient;
    }
    
    public void atualizarClient(ClientBean pBean) {
        ClientDAO dao = new ClientDAO();
        
        
            System.out.println("atualizando cliente....");
        try {
            dao.atualizarCliente(pBean);
        } catch (SQLException ex) {
            Logger.getLogger(ClientService.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
    }
    
    public ClientBean inserirCliente(ClientBean pCliente) {
        ClientDAO clientDAO = new ClientDAO();
        
        try {
            System.out.println("Salvando cliente...");
           return clientDAO.inserirCliente(pCliente);
        } catch (SQLException ex) {
            Logger.getLogger(ClientService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<ClientBean> listarClientes(String pNome) {
        List<ClientBean> listaCliente = new ArrayList<ClientBean>();
        QueryBuilder query = new QueryBuilder();
        ClientDAO clientDAO = new ClientDAO();

        query.addQuery(QueryOperation.empty, "cliente.nome_usuario", QueryGender.has, pNome, QueryType.text);
        try {
             listaCliente = clientDAO.loadClient(query);
        } catch (SQLException ex) {
            Logger.getLogger(ClientService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return listaCliente;
    }
}
