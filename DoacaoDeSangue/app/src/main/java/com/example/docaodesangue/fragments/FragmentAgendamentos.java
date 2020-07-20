package com.example.docaodesangue.fragments;


import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.docaodesangue.R;
import com.example.docaodesangue.helper.AlertDialogCustom;
import com.example.docaodesangue.helper.DateCustom;
import com.example.docaodesangue.helper.SharedPreferencesCustom;

import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAgendamentos extends Fragment {

    //Componentes
    private View view;
    private TextView txtSemAgendamento;
    private TextView txtAgendamento;
    private TextView txtNome;
    private TextView txtEndereco;
    private TextView txtTelefone;
    private TextView txtData;
    private Button btnMapa;
    private Button btnRemover;

    public FragmentAgendamentos() {
        // Required empty public constructor
    }

    /**
     * Cria a tela dos agendamentos
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_agendamentos, container, false);

        txtSemAgendamento = view.findViewById(R.id.txtSemAgendamento);
        txtAgendamento = view.findViewById(R.id.txtAgendamento);
        txtNome = view.findViewById(R.id.txtNomeAgendamento);
        txtEndereco = view.findViewById(R.id.txtEnderecoAgendamento);
        txtTelefone = view.findViewById(R.id.txtTelefoneAgendamento);
        txtData = view.findViewById(R.id.txtDataAgendamento);
        btnMapa = view.findViewById(R.id.btnMapaAgendamento);
        btnRemover = view.findViewById(R.id.btnExcluirAgendamento);

        //Carrega as informacoes do doador
        if(SharedPreferencesCustom.StartSharedPreferencesDoador(view.getContext()) == false){
            //Caixa de alerta -> editar as informacoes do doador
            AlertDialogCustom.alertEditDoador(view.getContext(), R.layout.fragment_edit);
        }

        //Carrega as informacoes do agendamento
        if(SharedPreferencesCustom.StartSharedPreferencesAgendamento(view.getContext())){

            existeAgendamento(true);
            txtNome.setText(SharedPreferencesCustom.nomeAgendamento);
            txtEndereco.setText(SharedPreferencesCustom.enderecoAgendamento);
            txtTelefone.setText("Telefone: "+SharedPreferencesCustom.telefoneAgendamento);
            txtData.setText("Data: "+SharedPreferencesCustom.dataAgendamento);

            int dia = SharedPreferencesCustom.diaAgendamento;
            int mes = SharedPreferencesCustom.mesAgendamento;
            int ano = SharedPreferencesCustom.anoAgendamento;

            if(SharedPreferencesCustom.StartSharedPreferencesDoador(view.getContext()) && SharedPreferencesCustom.StartSharedPreferencesAgendamento(view.getContext()) && DateCustom.calculoPeriodo(dia, mes, ano) < 0){
                AlertDialogCustom.alertConfirmarDoacao(view.getContext(), dia, mes, ano);
                existeAgendamento(false);
            }

            //Mapa
            btnMapa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String latInstituicao = SharedPreferencesCustom.localizacaoXAgendamento;
                    String lonInstituicao = SharedPreferencesCustom.localizacaoYAgendamento;

                    String latDoador = SharedPreferencesCustom.localizacaoX;
                    String lonDoador = SharedPreferencesCustom.localizacaoY;

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://maps.google.com/maps?saddr="+latDoador+","+lonDoador+"&daddr="+latInstituicao+","+lonInstituicao));
                    if (intent.resolveActivity(((FragmentActivity)view.getContext()).getPackageManager()) != null) {
                        ((FragmentActivity)view.getContext()).startActivity(intent);
                    }
                    else{
                        Toast.makeText(view.getContext(), "Não foi possível acessar a localização da instituição", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            //Remove o agendamento
            btnRemover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    existeAgendamento(false);
                    SharedPreferencesCustom.clearSharedPreferences(view.getContext(), SharedPreferencesCustom.FILE_AGENDAMENTO);
                }
            });

        }

        else{
            existeAgendamento(false);
        }

        return view;
    }

    /**
     * Configura a tela de acordo com a existencia do agendamento
     * @param existe
     */
    public void existeAgendamento(boolean existe){

        if(existe){
            txtSemAgendamento.setVisibility(View.INVISIBLE);
            txtAgendamento.setVisibility(View.VISIBLE);
            txtNome.setVisibility(View.VISIBLE);
            txtEndereco.setVisibility(View.VISIBLE);
            txtTelefone.setVisibility(View.VISIBLE);
            txtData.setVisibility(View.VISIBLE);
            btnMapa.setVisibility(View.VISIBLE);
            btnRemover.setVisibility(View.VISIBLE);
        }

        else{
            txtSemAgendamento.setVisibility(View.VISIBLE);
            txtAgendamento.setVisibility(View.INVISIBLE);
            txtNome.setVisibility(View.INVISIBLE);
            txtEndereco.setVisibility(View.INVISIBLE);
            txtTelefone.setVisibility(View.INVISIBLE);
            txtData.setVisibility(View.INVISIBLE);
            btnMapa.setVisibility(View.INVISIBLE);
            btnRemover.setVisibility(View.INVISIBLE);
        }
    }

}
