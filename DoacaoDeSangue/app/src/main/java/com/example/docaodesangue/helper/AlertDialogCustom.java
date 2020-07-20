package com.example.docaodesangue.helper;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.docaodesangue.R;
import com.example.docaodesangue.activity.Doador;
import com.example.docaodesangue.activity.Instituicao;
import com.example.docaodesangue.activity.MainActivity;
import com.example.docaodesangue.config.ConfiguracaoFirebase;
import com.example.docaodesangue.fragments.FragmentEdit;
import com.example.docaodesangue.fragments.FragmentHistorico;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.Calendar;

public class AlertDialogCustom{

    //email
    static EditText email;

    /**
     * Alerta que pede para o doador editar a suas informacoes
     * @param context Contexto -> Activity ou view.getContext()
     * @param frameId Id do frame(int)
     */
    public static void alertEditDoador(final Context context, int frameId){
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Edite suas informações");
        alert.setIcon(R.drawable.icon_alerta);
        alert.setCancelable(false);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FragmentEdit edit = new FragmentEdit();
                FragmentTransaction fragment = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                fragment.replace(R.id.frame_doador, edit);
                fragment.commit();
            }
        });

        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }

    /**
     * Input Dialog para redefinir a senha
     * @param context
     */
    public static void alertRedefinirSenha(final Context context){
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Digite o seu e-mail");
        alert.setIcon(R.drawable.icon_alerta);
        alert.setCancelable(true);
        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do nothing
            }
        });
        email = new EditText(context);
        email.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        email.setHint("E-mail");
        alert.setView(email);
        alert.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(email.getText().toString().equals("")){
                    Toast.makeText(context, "Campo de e-mail vazio", Toast.LENGTH_SHORT).show();
                }
                FirebaseAuth autenticacao = ConfiguracaoFirebase.getAutenticacao();
                autenticacao.sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(context, "Verifique o seu e-mail", Toast.LENGTH_SHORT).show();
                        }

                        else{
                            Toast.makeText(context, "Erro na verificação do e-mail", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        AlertDialog alertRedefinirSenha = alert.create();
        alertRedefinirSenha.show();
    }

    /**
     * Alerta para perguntar se o usuario realmente quer remover a instituicao
     * @param context
     */
    public static void alertDeletarConta(final Context context){
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Você quer deletar a sua conta ?");
        alert.setIcon(R.drawable.icon_alerta);
        alert.setCancelable(false);
        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Conta não deletada", Toast.LENGTH_SHORT).show();
            }
        });
        alert.setPositiveButton("Deletar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth autenticacao = ConfiguracaoFirebase.getAutenticacao();
                DatabaseReference firebase = ConfiguracaoFirebase.getFirebase();
                String email = autenticacao.getCurrentUser().getEmail();
                if(autenticacao.getCurrentUser().delete().isSuccessful() == false){
                    firebase.child("Instituicoes").child(Base64Custom.encodeBase64(email)).removeEventListener(Instituicao.valueEventListener);
                    autenticacao.signOut();
                    ((Activity)context).finish();
                    ConfiguracaoFirebase.deleteConta = true;
                    ConfiguracaoFirebase.deleteEmail = email;
                }
                else{
                    Toast.makeText(context, "Não foi possível deletar a conta", Toast.LENGTH_SHORT).show();
                }
            }
        });
        AlertDialog alertDeletarConta = alert.create();
        alertDeletarConta.show();
    }

    /**
     * Alerta de confirmacao da doacao -> aparece na aba de agendamentos, confirmando se voce doou sangue em
     * @param context
     * @param dia
     * @param mes
     * @param ano
     */
    public static void alertConfirmarDoacao(final Context context, int dia, int mes, int ano){
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Você doou sangue em: "+dia+"/"+mes+"/"+ano+" ?");
        alert.setIcon(R.drawable.icon_alerta);
        alert.setCancelable(false);
        alert.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do nothing
            }
        });
        alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferencesCustom.addHistorico(context, SharedPreferencesCustom.nome, SharedPreferencesCustom.nomeAgendamento, SharedPreferencesCustom.dataAgendamento);
            }
        });
        SharedPreferencesCustom.clearSharedPreferences(context, SharedPreferencesCustom.FILE_AGENDAMENTO);
        AlertDialog alertConfirmarDoacao = alert.create();
        alertConfirmarDoacao.show();
    }
}
