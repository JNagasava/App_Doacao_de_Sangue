package com.example.docaodesangue.model;

import java.util.Map;

/**
 * Classe com as informacoes da Instituicao
 */
public class InstituicoesDoacao {

    //Informacoes
    private String nome;
    private String estado;
    private String cidade;
    private String endereco;
    private String telefone;
    private Map<String, String> localizacao;

    /**
     * Construtor da classe(sem parametro)
     */
    public InstituicoesDoacao(){

    }

    /**
     * Construtor da classe
     * @param nome nome da Instituicao
     * @param estado estado da Instituicao
     * @param cidade cidade da Instituicao
     * @param endereco endereco da Instituicao
     * @param telefone telefone da Instituicao
     * @param localizacao localizacao da instituicao -> coordenadas x,y
     */
    public InstituicoesDoacao(String nome, String estado, String cidade, String endereco, String telefone, Map<String, String> localizacao){
        this.nome = nome;
        this.estado = estado;
        this.cidade = cidade;
        this.endereco = endereco;
        this.telefone = telefone;
        this.localizacao = localizacao;
    }

    //GETTERS E SETTERS DOS ATRIBUTOS DESTA CLASSE

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Map<String, String> getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Map<String, String> localizacao) {
        this.localizacao = localizacao;
    }
}
