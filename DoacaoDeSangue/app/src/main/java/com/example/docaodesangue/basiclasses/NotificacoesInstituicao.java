package com.example.docaodesangue.basiclasses;

import java.util.List;
import java.util.Map;

public class NotificacoesInstituicao {

    //Informacoes
    private int raio;
    private List<String> tipoSangue;
    private String data;
    private String email;
    private String nome;
    private Map<String, String> localizacao;

    /**
     * Cria a classe de notificacoes da instituicao
     */
    public NotificacoesInstituicao(){

    }

    /**
     *
     * @param raio distancia da notificacao
     * @param tipoSangue lista com os sangues em falta
     * @param data data da notificacao
     * @param email e-mail da instituicao
     * @param nome nome da instituicao
     * @param localizacao localizacao (x,y) da instituicao
     */
    public NotificacoesInstituicao(int raio, List<String> tipoSangue, String data, String email, String nome, Map<String, String> localizacao) {
        this.raio = raio;
        this.tipoSangue = tipoSangue;
        this.data = data;
        this.email = email;
        this.nome = nome;
        this.localizacao = localizacao;
    }

    //Getters e Setters

    public int getRaio() {
        return raio;
    }

    public void setRaio(int raio) {
        this.raio = raio;
    }

    public List<String> getCheck() {
        return tipoSangue;
    }

    public void setCheck(List<String> tipoSangue) {
        this.tipoSangue = tipoSangue;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Map<String, String> getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Map<String, String> localizacao) {
        this.localizacao = localizacao;
    }
}
