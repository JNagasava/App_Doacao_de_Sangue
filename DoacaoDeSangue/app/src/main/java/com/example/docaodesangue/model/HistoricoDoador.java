package com.example.docaodesangue.model;

/**
 * Classe com informacoes do historico do doador
 */
public class HistoricoDoador {

    //Informacoes
    private String nomeInstituicao;
    private String data;
    private String doador;

    /**
     * Construtor da classe
     */
    public HistoricoDoador(){

    }

    /**
     * Construtor da classe
     * @param nomeInstituicao nome da Instituicao
     * @param data data em que foi doado sangue
     * @param doador nome do doador
     */
    public HistoricoDoador(String nomeInstituicao, String data, String doador) {
        this.nomeInstituicao = nomeInstituicao;
        this.data = data;
        this.doador = doador;
    }

    //GETTERS E SETTERS DOS ATRIBUTOS DA CLASSE HISTORICO DOADOR

    public String getNomeInstituicao() {
        return nomeInstituicao;
    }

    public void setNomeInstituicao(String nomeInstituicao) {
        this.nomeInstituicao = nomeInstituicao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDoador() {
        return doador;
    }

    public void setDoador(String doador) {
        this.doador = doador;
    }
}
