/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgr.bean;

/**
 *
 * @author WASHINGTON
 */
public class TableBean {
    
    private int numero;
    private int capacidade;
    private String identificador;
    private boolean status;
    private int funcionarioCodigo;
    private String funcionarioCpf;
    private String frag;
    private String flagGarcom;

    public String getFlagGarcom() {
        return flagGarcom;
    }

    public void setFlagGarcom(String flagGarcom) {
        this.flagGarcom = flagGarcom;
    }
    
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getFuncionarioCodigo() {
        return funcionarioCodigo;
    }

    public void setFuncionarioCodigo(int funcionarioCodigo) {
        this.funcionarioCodigo = funcionarioCodigo;
    }

    public String getFuncionarioCpf() {
        return funcionarioCpf;
    }

    public void setFuncionarioCpf(String funcionarioCpf) {
        this.funcionarioCpf = funcionarioCpf;
    }

    public String getFrag() {
        return frag;
    }

    public void setFrag(String frag) {
        this.frag = frag;
    }
    
    
    
}
