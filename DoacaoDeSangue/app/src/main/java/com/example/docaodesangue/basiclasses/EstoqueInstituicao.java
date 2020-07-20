package com.example.docaodesangue.basiclasses;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.docaodesangue.config.ConfiguracaoFirebase;
import com.example.docaodesangue.helper.Base64Custom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EstoqueInstituicao {

    //Informacoes
    private Map<String, Integer> nivelSangue;
    private String idInstituicao;
    private String email;

    /**
     * Estoque padrao da instituicao -> tudo com o nivel "suficiente"
     */
    public EstoqueInstituicao(){

        nivelSangue = new HashMap<>();
        nivelSangue.put("A+", 0);
        nivelSangue.put("A-", 0);
        nivelSangue.put("B+", 0);
        nivelSangue.put("B-", 0);
        nivelSangue.put("O+", 0);
        nivelSangue.put("O-", 0);
        nivelSangue.put("AB+", 0);
        nivelSangue.put("AB-", 0);
    }

    /**
     * Salva o estoque da instituicao no firebase
     */
    public void salvarEstoque(){
        FirebaseAuth autenticacao = ConfiguracaoFirebase.getAutenticacao();
        DatabaseReference firebase = ConfiguracaoFirebase.getFirebase();
        setIdInstituicao(Base64Custom.encodeBase64(autenticacao.getCurrentUser().getEmail()));
        setEmail(autenticacao.getCurrentUser().getEmail());
        firebase.child("Estoque").child(idInstituicao).setValue(this);
    }

    //Getters

    public String getEmail() {
        return email;
    }

    public Map<String, Integer> getNivelSangue() {
        return nivelSangue;
    }

    @Exclude
    public String getIdInstituicao(){
        return idInstituicao;
    }

    //Setters

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNivelSangue(Map<String, Integer> nivelSangue) {
        this.nivelSangue = nivelSangue;
    }

    public void setIdInstituicao(String idInstituicao) {
        this.idInstituicao = idInstituicao;
    }

    public void setSangue(String tipo, int nivel){
        nivelSangue.put(tipo, nivel);
    }
}
