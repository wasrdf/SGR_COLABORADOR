package sgr.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import sgr.bean.ClientBean;
import sgr.bean.FuncionarioBean;
import sgr.bean.MovimentoBean;
import sgr.bean.SessionBean;
import sgr.bean.TableBean;
import sgr.dao.ContaItemDAO;
import sgr.service.ClientService;
import sgr.service.ContaItemBean;
import sgr.service.FuncionarioService;
import sgr.service.MovimentoService;

import sgr.service.SessionBeanService;
import sgr.service.TableBeanService;

@SessionScoped
@ManagedBean(name = "funcionarioController")
public class FuncionarioController {

    FuncionarioService funcionarioService = new FuncionarioService();
    List<FuncionarioBean> listFuncionario;
    FuncionarioBean funcionario;
    ClientBean clienteBean = new ClientBean();
    private String funcionarioLogin = "";
    private String funcionarioSenha = "";
    private HttpSession session;
    Integer tela = 0;
    String senha = "";
    String filtroDeFuncionario = "";
    String filtroPedido = "";
    FuncionarioBean funcionarioNovo = new FuncionarioBean();
    String CurrentHour = "";
    String CurrentDate = "";
    Date dataAtual = new Date();
    SessionBean sessionBean = new SessionBean();
    MovimentoBean movimentoBean = new MovimentoBean();
    List<MovimentoBean> listaMovimento = new ArrayList<MovimentoBean>();
    ContaItemBean contaItemBean = new ContaItemBean();
    List<MovimentoBean> listaPedidosCozinha = new ArrayList<MovimentoBean>();
    List<MovimentoBean> listaPedidosFechados = new ArrayList<MovimentoBean>();
    List<MovimentoBean> listaItensCancelados = new ArrayList<MovimentoBean>();
    List<TableBean> listaMesasAberto = new ArrayList<TableBean>();
    TableBean tableBean = new TableBean();
    double total = 0;
    DecimalFormat df = new DecimalFormat("#,###.00");
    String valorTotal = "";

    String statusSelecionado = "";

    public FuncionarioController() {
        Date dataAtual = Calendar.getInstance().getTime();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat dfHour = new SimpleDateFormat("HH:mm");
        CurrentDate = df.format(dataAtual);
        CurrentHour = dfHour.format(dataAtual);
        listarItensCancelamento();
        listarItensProntosESolicitados();
        
    }
    
    
    
    public void listarItensCancelamento() {
      MovimentoService movimentoService = new MovimentoService();
      listaItensCancelados = movimentoService.listarItensCancelados("");
    }
    
    //aqui o caixa ou o gerente altera o status do item de Cancelamento para CANCELADO
    public void cancelarItem(MovimentoBean pMovimentoBean) {
        ContaItemDAO contaItemDAO = new ContaItemDAO();
        contaItemBean.setCodigo(pMovimentoBean.getContaItemCodigo());
        contaItemBean.setStatus("CANCELADO");
        contaItemDAO.alterarItemStatus(contaItemBean);
        listarItensCancelamento();
    
        
    }

    public void alterarStatusItem(MovimentoBean pMovimentoBean) {
        ContaItemDAO contaItemDAO = new ContaItemDAO();
        contaItemBean.setCodigo(pMovimentoBean.getContaItemCodigo());
        contaItemBean.setStatus("Pronto");
        System.out.println("STATUS DO ITEM ALTERADO: " + contaItemBean.getStatus());
        contaItemDAO.alterarItemStatus(contaItemBean);
        listarItensProntosESolicitados();
    }

      public void desfazer(MovimentoBean pMovimentoBean) {
        ContaItemDAO contaItemDAO = new ContaItemDAO();
        contaItemBean.setCodigo(pMovimentoBean.getContaItemCodigo());
        contaItemBean.setStatus("Solicitado");
        
        contaItemDAO.alterarItemStatus(contaItemBean);
        listarItensProntosESolicitados();
    }

    
    public void alterarStatusItemEntregue(MovimentoBean pMovimentoBean) {
        ContaItemDAO contaItemDAO = new ContaItemDAO();
        contaItemBean.setCodigo(pMovimentoBean.getContaItemCodigo());
        if (pMovimentoBean.getItemStatus().equals("Solicitado")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Você não pode entregar este item pois o mesmo ainda não está pronto.", ""));
        } else {
            contaItemBean.setStatus("Entregue");
            System.out.println("STATUS DO ITEM ALTERADO: " + contaItemBean.getStatus());
            contaItemDAO.alterarItemStatus(contaItemBean);
           listarItensProntosESolicitados();
        }
    }

    public void salvarClient() {

        ClientService clientService = new ClientService();
        if (!clienteBean.getSenha().equals(senha)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "As senhas devem ser iguais.", ""));
            return;
        } else {

            if (clientService.inserirCliente(clienteBean) != null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente cadastrado com sucesso.", ""));
                clienteBean = new ClientBean();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nome de usuário invalido.", ""));
            }
        }

    }

    //metodo cadastrar cliente() 
    public void logar() throws IOException {

        FacesContext ctx = FacesContext.getCurrentInstance();

        listFuncionario = funcionarioService.logar(funcionarioLogin, funcionarioSenha);
        System.out.println(listFuncionario.size());
        funcionario = listFuncionario.get(0);

        System.out.println("usuario do banco" + funcionario.getNome_usuario());
        System.out.println("senha do banco" + funcionario.getSenha());
        System.out.println("Nivel do funcionario" + funcionario.getFuncao());

        session = (HttpSession) ctx.getExternalContext().getSession(false);
        session.setAttribute("currentUser", funcionario);

        //carrega lista de pedidos em aberto
        MovimentoService movimentoService = new MovimentoService();
        listaMovimento = movimentoService.listarTodosMovimento("");

        //listar mesas em aberto
        TableBeanService tableBeanService = new TableBeanService();
        listaMesasAberto = tableBeanService.listarMesasAbertas();

        if (funcionario.getFuncao().equals("Gerente")) {
            System.out.println("Gerente logando");
            FacesContext.getCurrentInstance().getExternalContext().
                    redirect(ctx.getExternalContext().getRequestContextPath() + "/admin.jsf");
            listFuncionario = funcionarioService.listarFuncionario(filtroDeFuncionario);
        } else {

            if (funcionario.getFuncao().equals("Garçom")) {
                System.out.println("Garçom logando");
                FacesContext.getCurrentInstance().getExternalContext().
                        redirect(ctx.getExternalContext().getRequestContextPath() + "/waiter.jsf");

            } else {

                if (funcionario.getFuncao().equals("Caixa")) {

                    System.out.println("Caixa logando");
                    FacesContext.getCurrentInstance().getExternalContext().
                            redirect(ctx.getExternalContext().getRequestContextPath() + "/caixa.jsf");

                } else {
                    if (funcionario.getFuncao().equals("Cozinha")) {
                        System.out.println("Cozinha logando");
                        FacesContext.getCurrentInstance().getExternalContext().
                                redirect(ctx.getExternalContext().getRequestContextPath() + "/cozinha.jsf");

                    }
                }
            }
        }

    }

    
    
    public void recarregarMesas() {
        TableBeanService tableBeanService = new TableBeanService();
        listaMesasAberto = tableBeanService.listarMesasAbertas();
    }

    public void carregarPedidos() {
        //carrega lista de pedidos em aberto
        MovimentoService movimentoService = new MovimentoService();
        listaMovimento = movimentoService.listarTodosMovimento(filtroPedido);

    }

    public void visualizarPedidos(TableBean pTableBean) {
        listaMovimento = new ArrayList<MovimentoBean>();
        total = 0;
        valorTotal = "";

        MovimentoService movimentoService = new MovimentoService();
        System.out.println("Número da Mesa selecionada: " + pTableBean.getNumero());
        listaMovimento = movimentoService.listarMovimentosPorMesa(pTableBean.getNumero());
        System.out.println("Total da lista: " + listaMovimento.size());
        for (int i = 0; i < listaMovimento.size(); i++) {
            total = total + (listaMovimento.get(i).getQuantidade() * listaMovimento.get(i).getPreco());

        }
        //valor total
        valorTotal = String.valueOf(df.format(total));
        tableBean = pTableBean;
        tela = 1;
    }

    public void listarItensProntosESolicitados() {
        
        MovimentoService movimentoService = new MovimentoService();
        listaMovimento = movimentoService.listarItensSolicitados(filtroPedido);
    }

    public void encerrarConta() {
        SessionBeanService sessionBeanService = new SessionBeanService();
        MovimentoService movimentoService = new MovimentoService();
        boolean status = false;
        listaMovimento = movimentoService.listarMovimentosPorMesa(tableBean.getNumero());
        for (int i = 0; i < listaMovimento.size(); i++) {
            if (listaMovimento.get(i).getItemStatus().equals("Solicitado")) {
                status = false;

            } else {
                status = true;

            }

        }
        if (status == true) {
            
            sessionBean.setCodigo(listaMovimento.get(0).getContaCodigo());
            sessionBean.setStatus(false);
            System.out.println("Numero da conta:" + listaMovimento.get(0).getContaCodigo());
            sessionBeanService.encerrarConta(sessionBean);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "A conta foi encerrada com sucesso.", ""));

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Você não pode encerrar essa conta, pois existem itens que ainda não foram entregues.", ""));
        }
    }

    public void mudarTela(Integer pTela) {
        funcionarioNovo = new FuncionarioBean();
        tela = pTela;
    }

    public void navegarPara(String pPagina) throws IOException {
        FacesContext ctx = FacesContext.getCurrentInstance();

        FacesContext.getCurrentInstance().getExternalContext().redirect(ctx.getExternalContext().getRequestContextPath() + pPagina);

    }

    public List<MovimentoBean> getListaItensCancelados() {
        return listaItensCancelados;
    }

    public void setListaItensCancelados(List<MovimentoBean> listaItensCancelados) {
        this.listaItensCancelados = listaItensCancelados;
    }
    
    public void listarFuncionario() {

        FuncionarioService funcionarioService = new FuncionarioService();
        listFuncionario = funcionarioService.listarFuncionario(filtroDeFuncionario);
        System.out.println("valor da tela = " + tela);
    }

    public void funcionarioSelecionado(FuncionarioBean pFuncionario) {

        funcionarioNovo = pFuncionario;
        tela = 1;
    }

    public void doLogout() {

        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession s = (HttpSession) fc.getExternalContext().getSession(false);
        s.invalidate();
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(ctx.getExternalContext().getRequestContextPath() + "/index.jsf");
        } catch (IOException ex) {
            Logger.getLogger(FuncionarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void salvar() {

        System.out.println("salvando funcionario...");
        System.out.println("CPF digitado: " + funcionarioNovo.getCpf());

        if (!(senha.equals(funcionarioNovo.getSenha()))) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atenção!", "As senhas devem ser iguais!"));
            return;
        } else {

            funcionarioService.salvar(funcionarioNovo);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Funcionario salvo."));
            funcionarioNovo = new FuncionarioBean();

        }
    }

    public void pedidoSelecionado(MovimentoBean pMovimento) {
        movimentoBean = pMovimento;
        tela = 1;
    }

    public void deletarFuncionario() {

        funcionarioService.deletarFuncionario(funcionarioNovo);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Funcionario deletado com sucesso."));
        funcionarioNovo = new FuncionarioBean();
    }

    //GET E SET
    public FuncionarioService getFuncionarioService() {
        return funcionarioService;
    }

    public void setFuncionarioService(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    public List<FuncionarioBean> getListFuncionario() {
        return listFuncionario;
    }

    public void setListFuncionario(List<FuncionarioBean> listFuncionario) {
        this.listFuncionario = listFuncionario;
    }

    public FuncionarioBean getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(FuncionarioBean funcionario) {
        this.funcionario = funcionario;
    }

    public ClientBean getClienteBean() {
        return clienteBean;
    }

    public void setClienteBean(ClientBean clienteBean) {
        this.clienteBean = clienteBean;
    }

    public String getFuncionarioLogin() {
        return funcionarioLogin;
    }

    public void setFuncionarioLogin(String funcionarioLogin) {
        this.funcionarioLogin = funcionarioLogin;
    }

    public String getFuncionarioSenha() {
        return funcionarioSenha;
    }

    public void setFuncionarioSenha(String funcionarioSenha) {
        this.funcionarioSenha = funcionarioSenha;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public Integer getTela() {
        return tela;
    }

    public void setTela(Integer tela) {
        this.tela = tela;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getFiltroDeFuncionario() {
        return filtroDeFuncionario;
    }

    public void setFiltroDeFuncionario(String filtroDeFuncionario) {
        this.filtroDeFuncionario = filtroDeFuncionario;
    }

    public String getFiltroPedido() {
        return filtroPedido;
    }

    public void setFiltroPedido(String filtroPedido) {
        this.filtroPedido = filtroPedido;
    }

    public FuncionarioBean getFuncionarioNovo() {
        return funcionarioNovo;
    }

    public void setFuncionarioNovo(FuncionarioBean funcionarioNovo) {
        this.funcionarioNovo = funcionarioNovo;
    }

    public String getCurrentHour() {
        return CurrentHour;
    }

    public void setCurrentHour(String CurrentHour) {
        this.CurrentHour = CurrentHour;
    }

    public String getCurrentDate() {
        return CurrentDate;
    }

    public void setCurrentDate(String CurrentDate) {
        this.CurrentDate = CurrentDate;
    }

    public Date getDataAtual() {
        return dataAtual;
    }

    public void setDataAtual(Date dataAtual) {
        this.dataAtual = dataAtual;
    }

    public SessionBean getSessionBean() {
        return sessionBean;
    }

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public MovimentoBean getMovimentoBean() {
        return movimentoBean;
    }

    public void setMovimentoBean(MovimentoBean movimentoBean) {
        this.movimentoBean = movimentoBean;
    }

    public List<MovimentoBean> getListaMovimento() {
        return listaMovimento;
    }

    public void setListaMovimento(List<MovimentoBean> listaMovimento) {
        this.listaMovimento = listaMovimento;
    }

    public ContaItemBean getContaItemBean() {
        return contaItemBean;
    }

    public void setContaItemBean(ContaItemBean contaItemBean) {
        this.contaItemBean = contaItemBean;
    }

    public List<MovimentoBean> getListaPedidosCozinha() {
        return listaPedidosCozinha;
    }

    public void setListaPedidosCozinha(List<MovimentoBean> listaPedidosCozinha) {
        this.listaPedidosCozinha = listaPedidosCozinha;
    }

    public List<MovimentoBean> getListaPedidosFechados() {
        return listaPedidosFechados;
    }

    public void setListaPedidosFechados(List<MovimentoBean> listaPedidosFechados) {
        this.listaPedidosFechados = listaPedidosFechados;
    }

    public List<TableBean> getListaMesasAberto() {
        return listaMesasAberto;
    }

    public void setListaMesasAberto(List<TableBean> listaMesasAberto) {
        this.listaMesasAberto = listaMesasAberto;
    }

    public TableBean getTableBean() {
        return tableBean;
    }

    public void setTableBean(TableBean tableBean) {
        this.tableBean = tableBean;
    }

    public String getStatusSelecionado() {
        return statusSelecionado;
    }

    public void setStatusSelecionado(String statusSelecionado) {
        this.statusSelecionado = statusSelecionado;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public DecimalFormat getDf() {
        return df;
    }

    public void setDf(DecimalFormat df) {
        this.df = df;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
    }

}
