package com.example.docaodesangue.config;

import android.util.Log;

import com.example.docaodesangue.activity.Instituicao;
import com.example.docaodesangue.helper.Base64Custom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.ExecutionException;

public class ConfiguracaoFirebase {

    //Firebase
    private static FirebaseAuth autenticacao;
    private static DatabaseReference firebase;

    //Controle para deletar uma conta
    public static boolean deleteConta = false;

    //E-mail do usuario a ser removido
    public static String deleteEmail;

    /**
     * Retorna a instancia da autenticacao Firebase
     * @return instancia da autenticacao Firebase
     */
    public static FirebaseAuth getAutenticacao(){
        if(autenticacao == null){
            autenticacao = FirebaseAuth.getInstance();
        }
        return autenticacao;
    }

    /**
     * Funcao que retorna a DatabaseReference para poder modificar os dados
     * @return DatabaseReference
     */
    public static DatabaseReference getFirebase(){
        if(firebase == null){
            firebase = FirebaseDatabase.getInstance().getReference();
        }

        return firebase;
    }

    /**
     * Funcao que remove as informacoes do usuario
     * @param email String -> e-mail do usuario
     */
    public static void removerDados(String email){
        if(firebase == null){
            firebase = FirebaseDatabase.getInstance().getReference();
        }

        //Remocao das informacoes gerais da insituicao
        try{
            firebase.child("Instituicoes").child(Base64Custom.encodeBase64(email)).removeValue();
            Log.i("Mensagens", "Remoção das informações gerais da instituição foi feita com sucesso");
        } catch (Exception e){
            Log.i("Mensagens", "Falha ao remover as informações gerais da instituição");
            Log.i("Mensagens", "Erro: "+e.getMessage());
        }

        //Remocao das informacoes de estoque da instituicao
        try{
            firebase.child("Estoque").child(Base64Custom.encodeBase64(email)).removeValue();
            Log.i("Mensagens", "Remoção das informações de estoque da instituição foi feita com sucesso");
        }catch(Exception e){
            Log.i("Mensagens", "Falha ao remover as informações de estoque da instituição");
            Log.i("Mensagens", "Erro: "+e.getMessage());
        }

        //Remocao das informacoes de notificacao da instituicao
        try{
            firebase.child("Notificacoes").child(Base64Custom.encodeBase64(email)).removeValue();
            Log.i("Mensagens", "Remoção das informações de notificação da instituição foi feita com sucesso");
        } catch (Exception e){
            Log.i("Mensagens", "Falha ao remover as informações de estoque da instituição");
            Log.i("Mensagens", "Erro: "+e.getMessage());
        }
    }
}
