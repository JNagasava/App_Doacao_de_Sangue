package com.example.docaodesangue.model;

import java.util.Map;

public class NotificacaoDoador {

    //Informacoes
    private String instituicao;
    private String email;
    private Map<String, String> localizacao;

    /**
     * Construtor da classe
     */
    public NotificacaoDoador(){

    }

    /**
     * Construtor da classe
     * @param instituicao Nome da instituicao
     * @param email E-mail da instituicao
     * @param localizacao Localizacao da instituicao
     */
    public NotificacaoDoador(String instituicao, String email, Map<String, String> localizacao){
        this.instituicao = instituicao;
        this.email = email;
        this.localizacao = localizacao;
    }

    //Getters
    public String getInstituicao() {
        return instituicao;
    }

    public String getEmail() {
        return email;
    }

    public Map<String, String> getLocalizacao() {
        return localizacao;
    }


    //Setters
    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLocalizacao(Map<String, String> localizacao) {
        this.localizacao = localizacao;
    }
}
