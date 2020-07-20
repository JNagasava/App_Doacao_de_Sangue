package com.example.docaodesangue.basiclasses;

import com.example.docaodesangue.config.ConfiguracaoFirebase;
import com.example.docaodesangue.helper.SharedPreferencesCustom;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class UserInstituicao implements Serializable {

    //Informacoes
    private String idInstiuicao;
    private String cnpj;
    private String nome;
    private String estado;
    private String cidade;
    private String endereco;
    private String cep;
    private String telefone;
    private String email;
    private String senha;
    private Map<String, String> localizacao;

    /**
     * Classe da instituicao
     */
    public UserInstituicao() {
        /*this.cnpj = "XX.XXX.XXX/YYYY-ZZ";
        this.nome = "Instituto do teste";
        this.estado = "TE";
        this.cidade = "Saint Charles";
        this.endereco = "Rua 13 de Junho";
        this.cep = "12345-123";
        this.telefone = "(xx)yyyy-zzzz";
        this.email = "teste@master";
        this.senha = "admin";*/
    }

    /**
     * Classe da instituicao
     * @param cnpj CNPJ
     * @param nome nome da instituicao
     * @param estado estado
     * @param cidade cidade
     * @param endereco endereco da instituicao
     * @param cep CEP
     * @param telefone Telefone
     * @param email e-mail
     * @param senha senha
     * @param localizacao localizacao(x,y)
     */
    public UserInstituicao(String cnpj, String nome, String estado, String cidade, String endereco, String cep, String telefone, String email, String senha, Map<String, String> localizacao) {
        this.cnpj = cnpj;
        this.nome = nome;
        this.estado = estado;
        this.cidade = cidade;
        this.endereco = endereco;
        this.cep = cep;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
        this.localizacao = localizacao;
    }

    /**
     * Salva as informacoes da instituicao no firebase
     */
    public void salvarInstituicao(){
        DatabaseReference firebase = ConfiguracaoFirebase.getFirebase();
        firebase.child("Instituicoes").child(this.idInstiuicao).setValue(this);
    }

    /** Getters
     * @Exclude -> nao adiciona no firebase
     * */

    @Exclude
    public String getIdInstiuicao() {
        return idInstiuicao;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getNome() {
        return nome;
    }

    public String getEstado() {
        return estado;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getCep() {
        return cep;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public Map<String, String> getLocalizacao(){
        return localizacao;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    /** Setters */
    public void setIdInstiuicao(String idInstiuicao) {
        this.idInstiuicao = idInstiuicao;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setLocalizacao(double latitude, double longitude){
        localizacao = new HashMap<>();
        localizacao.put("X", String.valueOf(latitude));
        localizacao.put("Y", String.valueOf(longitude));
    }
}
