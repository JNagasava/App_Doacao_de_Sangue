package com.example.docaodesangue.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.docaodesangue.R;
import com.example.docaodesangue.basiclasses.UserInstituicao;
import com.example.docaodesangue.config.ConfiguracaoFirebase;
import com.example.docaodesangue.helper.AlertDialogCustom;
import com.example.docaodesangue.helper.SharedPreferencesCustom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class LoginInstituicao extends AppCompatActivity {

    //Componentes
    private Button btnEsqueciSenha;
    private Button btnProceed;
    private EditText editEmail;
    private EditText editSenha;

    //Classe da instituicao
    private UserInstituicao instituicao;

    //Autenticacao do firebase
    private FirebaseAuth autenticacao;

    /**
     * Cria a tela de Login
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_instituicao);

        editEmail = findViewById(R.id.editTextUser);
        editSenha = findViewById(R.id.editTextPassword);

        if(SharedPreferencesCustom.StartSharedPreferencesInstituicao(getApplicationContext())){
            editEmail.setText(SharedPreferencesCustom.email);
        }

        //Botao de esqueci a senha
        btnEsqueciSenha = findViewById(R.id.btnForgotPassword);
        btnEsqueciSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogCustom.alertRedefinirSenha(LoginInstituicao.this);
            }
        });

        //Botao para logar
        btnProceed = findViewById(R.id.btnProceed);
        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textEmail = editEmail.getText().toString().replaceAll("\\n", "");
                String textSenha = editSenha.getText().toString().replaceAll("\\n", "");

                //Valida o login
                if(!textEmail.isEmpty()){

                    if(!textSenha.isEmpty()){

                        instituicao = new UserInstituicao();
                        instituicao.setEmail(textEmail);
                        instituicao.setSenha(textSenha);

                        validarLogin();
                    }

                    else{
                        Toast.makeText(getApplicationContext(), "Digite a senha", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Digite o seu e-mail", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    /**
     * Funcao que valida o login da instituicao
     */
    public void validarLogin(){

        autenticacao = ConfiguracaoFirebase.getAutenticacao();

        autenticacao.signInWithEmailAndPassword(instituicao.getEmail(), instituicao.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    SharedPreferencesCustom.setDataInstituicao(getApplicationContext(), SharedPreferencesCustom.emailInstituicao, instituicao.getEmail());

                    Intent intent = new Intent(getApplicationContext(), Instituicao.class);
                    startActivity(intent);
                    finish();

                }

                else{

                    String excecao = "";

                    try{
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e){

                        excecao = "E-mail incorreto";

                    } catch(FirebaseAuthInvalidCredentialsException e){

                        excecao = "Senha errada";

                    } catch (Exception e){
                        excecao = "Erro ao logar: " + e.getMessage().toString();
                        e.printStackTrace();
                    }

                    Toast.makeText(getApplicationContext(), excecao, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
