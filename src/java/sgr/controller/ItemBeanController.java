/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgr.controller;



import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import javax.faces.context.FacesContext;
import sgr.bean.ItemBean;
import sgr.dao.ItemBeanDAO;
import sgr.service.ItemBeanService;

@ManagedBean
@ViewScoped
public class ItemBeanController {

    @ManagedProperty(value = "#{funcionarioController}")
    protected FuncionarioController funcionarioLogado;
    Integer tela = 0;
    ItemBean itemBean = new ItemBean();
    List<ItemBean> listaItem = new ArrayList<ItemBean>();
    List<ItemBean> tiposDePrato = new ArrayList<ItemBean>();
    String filtro = "";
    boolean obrigatorio = true;
    

   
    
    public ItemBeanController() {
         
    }
    
    public void salvar() {
        obrigatorio = true;
        ItemBeanService itemBeanService = new ItemBeanService();

        if (itemBeanService.salvar(itemBean) != null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "O item foi cadastrado com sucesso"));
            itemBean = new ItemBean();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Ocorreu um erro ao tentar inserir o item."));
        }

    }

    public void listarItens() {
        ItemBeanService itemBeanService = new ItemBeanService();
        listaItem = itemBeanService.listarItem(filtro);
    }

    public void mudarTela(Integer pTela) {
         tela = pTela;
         
    }

    public void novoItem() {
        ItemBeanDAO itemBeanDAO = new ItemBeanDAO();
        tiposDePrato = itemBeanDAO.listarItemTipoDistinct();
        itemBean = new ItemBean();
        tela = 1;
    }

    public void selecionarItem(ItemBean pItemBean) {
        System.out.println("entrou no metodo");
        itemBean = pItemBean;
        ItemBeanDAO itemBeanDAO = new ItemBeanDAO();
        tiposDePrato = itemBeanDAO.listarItemTipoDistinct();
        tela = 1;
    }

    public void deletar() {
        ItemBeanService itemBeanService = new ItemBeanService();
        if (itemBeanService.deletar(itemBean) == true) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Item excluído com sucesso."));
            itemBean = new ItemBean();
            listarItens();
        
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "", "Ocorreu um erro ao tentar exclui o item selecionado"));
        }
    }

    public FuncionarioController getFuncionarioLogado() {
        return funcionarioLogado;
    }

    public void setFuncionarioLogado(FuncionarioController funcionarioLogado) {
        this.funcionarioLogado = funcionarioLogado;
    }

    public ItemBean getItemBean() {
        return itemBean;
    }

    public void setItemBean(ItemBean itemBean) {
        this.itemBean = itemBean;
    }

    public List<ItemBean> getListaItem() {
        return listaItem;
    }

    public void setListaItem(List<ItemBean> listaItem) {
        this.listaItem = listaItem;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public Integer getTela() {
        return tela;
    }

    public void setTela(Integer tela) {
        this.tela = tela;
    }

    public List<ItemBean> getTiposDePrato() {
        return tiposDePrato;
    }

    public void setTiposDePrato(List<ItemBean> tiposDePrato) {
        this.tiposDePrato = tiposDePrato;
    }

    public boolean isObrigatorio() {
        return obrigatorio;
    }

    public void setObrigatorio(boolean obrigatorio) {
        this.obrigatorio = obrigatorio;
    }
    
}
