package com.example.docaodesangue.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.docaodesangue.R;
import com.example.docaodesangue.basiclasses.NotificacoesInstituicao;
import com.example.docaodesangue.basiclasses.UserInstituicao;
import com.example.docaodesangue.config.ConfiguracaoFirebase;
import com.example.docaodesangue.helper.Base64Custom;
import com.example.docaodesangue.helper.DateCustom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentNotificarDoadores extends Fragment {

    //Componentes
    private View view;
    private TextView txtRaioNotificacao;
    private SeekBar skbRaioNotificacao;
    private Button btnNotificarDoadores;
    private CheckBox cb_a_positivo, cb_a_negativo, cb_b_positivo, cb_b_negativo, cb_ab_positivo, cb_ab_negativo, cb_o_positivo, cb_o_negativo;

    //Firebase
    private DatabaseReference notificacao;
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getAutenticacao();
    private DatabaseReference firebase = ConfiguracaoFirebase.getFirebase();

    //Informacoes -> tipo sanguineo em falta
    List<String> check = new ArrayList<String>();

    public FragmentNotificarDoadores() {
        // Required empty public constructor
    }

    /**
     * Cria a tela de notifcar doadores
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_notificar_doadores, container, false);

        //Configuracao dos checkbuttons
        cb_a_positivo = view.findViewById(R.id.cb_a_positivo);
        cb_a_negativo = view.findViewById(R.id.cb_a_negativo);
        cb_b_positivo = view.findViewById(R.id.cb_b_positivo);
        cb_b_negativo = view.findViewById(R.id.cb_b_negativo);
        cb_ab_positivo = view.findViewById(R.id.cb_ab_positivo);
        cb_ab_negativo = view.findViewById(R.id.cb_ab_negativo);
        cb_o_positivo = view.findViewById(R.id.cb_o_positivo);
        cb_o_negativo = view.findViewById(R.id.cb_o_negativo);


        //Configuracao da seekbar do raio de notificacao
        txtRaioNotificacao = view.findViewById(R.id.txtRaioNotificacao);
        skbRaioNotificacao = view.findViewById(R.id.skbRaioNotificacao);
        skbRaioNotificacao.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtRaioNotificacao.setText(skbRaioNotificacao.getProgress()+" Km");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(view.getContext(), "Raio de notificação de "+skbRaioNotificacao.getProgress()+" Km", Toast.LENGTH_SHORT).show();
            }
        });

        //Botao para notificar os doadores
        btnNotificarDoadores = view.findViewById(R.id.btnNotificarDoadores);
        btnNotificarDoadores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String idUsuario = Base64Custom.encodeBase64(autenticacao.getCurrentUser().getEmail());
                notificacao = firebase.child("Notificacoes").child(idUsuario);


                //Verifica se os campos estao selecionados corretamente
                if(verificaCheckBox()){
                    Toast.makeText(view.getContext(), "Selecione pelo menos um tipo sanguíneo para notificar", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(skbRaioNotificacao.getProgress() == 0){
                    Toast.makeText(view.getContext(), "Selecione o raio de notificação", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Salva a notificacao no firebase
                DatabaseReference reference = ConfiguracaoFirebase.getFirebase().child("Instituicoes");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot x: dataSnapshot.getChildren()){
                            UserInstituicao instituicao = x.getValue(UserInstituicao.class);
                            if(instituicao.getEmail().equals(autenticacao.getCurrentUser().getEmail())){
                                NotificacoesInstituicao notificacoesInstituicao = new NotificacoesInstituicao();
                                notificacoesInstituicao.setRaio(skbRaioNotificacao.getProgress());
                                notificacoesInstituicao.setCheck(check);
                                notificacoesInstituicao.setData(DateCustom.getData());
                                notificacoesInstituicao.setEmail(autenticacao.getCurrentUser().getEmail());
                                notificacoesInstituicao.setNome(instituicao.getNome());
                                notificacoesInstituicao.setLocalizacao(instituicao.getLocalizacao());
                                notificacao.setValue(notificacoesInstituicao);
                                Toast.makeText(view.getContext(), "Notificação enviada com sucesso", Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });

        return view;
    }

    /**
     * Verifica as CheckBox selecionadas
     * @return true se nenhuma estiver selecionada, false se houver pelo menos uma selecionada
     */
    public boolean verificaCheckBox(){

        boolean x = true;

        check.clear();

        //A+
        if(cb_a_positivo.isChecked()){
            check.add(cb_a_positivo.getText().toString());
            x = false;
        }

        //A-
        if(cb_a_negativo.isChecked()){
            check.add(cb_a_negativo.getText().toString());
            x = false;
        }

        //B+
        if(cb_b_positivo.isChecked()){
            check.add(cb_b_positivo.getText().toString());
            x = false;
        }

        //B-
        if(cb_b_negativo.isChecked()){
            check.add(cb_b_negativo.getText().toString());
            x = false;
        }

        //O+
        if(cb_o_positivo.isChecked()){
            check.add(cb_o_positivo.getText().toString());
            x = false;
        }

        //O-
        if(cb_o_negativo.isChecked()){
            check.add(cb_o_negativo.getText().toString());
            x = false;
        }

        //AB+
        if(cb_ab_positivo.isChecked()){
            check.add(cb_ab_positivo.getText().toString());
            x = false;
        }

        //AB-
        if(cb_ab_negativo.isChecked()){
            check.add(cb_ab_negativo.getText().toString());
            x = false;
        }

        return x;
    }

}
