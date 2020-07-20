package com.example.docaodesangue.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.docaodesangue.R;
import com.example.docaodesangue.activity.CadastroInstituicao;
import com.example.docaodesangue.activity.Doador;
import com.example.docaodesangue.activity.LoginInstituicao;
import com.example.docaodesangue.config.ConfiguracaoFirebase;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    //Componentes
    private Button btnDoador;
    private Button btnInstitution;
    private Button btnRegisterInstitution;

    //Autenticacao do firebase
    private FirebaseAuth autenticacao;

    /**
     * Cria a tela principal
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Doador
        btnDoador = findViewById(R.id.btnDoador);
        btnDoador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Doador.class);
                startActivity(intent);
            }
        });

        //Login instituicao
        btnInstitution = findViewById(R.id.btnInstitution);
        btnInstitution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), LoginInstituicao.class);
                startActivity(intent);
            }
        });

        //Cadastro da instituicao
        btnRegisterInstitution = findViewById(R.id.btnRegisterInstitution);
        btnRegisterInstitution.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), CadastroInstituicao.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Funcao que sempre executa, quando inicia esta tela
     */
    @Override
    protected void onStart() {
        super.onStart();

        try{
            //Remove as informacoes de um usuario  o sistema
            if(ConfiguracaoFirebase.deleteConta){
                ConfiguracaoFirebase.removerDados(ConfiguracaoFirebase.deleteEmail);
                Toast.makeText(getApplicationContext(), "Conta deletada", Toast.LENGTH_SHORT).show();
                ConfiguracaoFirebase.deleteEmail = "";
                ConfiguracaoFirebase.deleteConta = false;
            }
        } catch (Exception e){
            Log.i("Mensagens", e.getMessage()+"");
        }

        verificaUsuarioLogado();
    }

    /**
     * Verifica se existe um usuario logado
     */
    public void verificaUsuarioLogado(){
        autenticacao = ConfiguracaoFirebase.getAutenticacao();
        autenticacao.signOut();
        if(autenticacao.getCurrentUser() != null){
            Intent intent = new Intent(getApplicationContext(), Instituicao.class);
            startActivity(intent);
            finish();
        }
    }
}
