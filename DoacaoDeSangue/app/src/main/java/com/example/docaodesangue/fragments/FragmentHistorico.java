package com.example.docaodesangue.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.docaodesangue.R;
import com.example.docaodesangue.adapter.AdapterHistorico;
import com.example.docaodesangue.helper.AlertDialogCustom;
import com.example.docaodesangue.helper.SharedPreferencesCustom;
import com.example.docaodesangue.model.HistoricoDoador;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHistorico extends Fragment {

    //Componentes
    private View view;
    private RecyclerView recyclerHistorico;
    private Button removerHistorico;
    private TextView txtHistorico;
    private TextView txtSemHistorico;

    //Informacao
    private List<HistoricoDoador> listaHistorico = new ArrayList<>();

    public FragmentHistorico() {
        // Required empty public constructor
    }

    /**
     * Cria a tela de historico
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_historico, container, false);

        //Carrega as informacoes do doador
        if(SharedPreferencesCustom.StartSharedPreferencesDoador(view.getContext()) == false){
            //Caixa de alerta -> editar as informacoes do doador
            AlertDialogCustom.alertEditDoador(view.getContext(), R.layout.fragment_edit);
        }

        //Configura os componentes
        txtHistorico = view.findViewById(R.id.txtHistorico);
        txtSemHistorico = view.findViewById(R.id.txtSemHistorico);
        recyclerHistorico = view.findViewById(R.id.recyclerHistorico);
        removerHistorico = view.findViewById(R.id.btnRemoverHistorico);

        //Listagem do historico
        criaHistorico();

        //Configurar o adapter
        AdapterHistorico adapterInstituicoes = new AdapterHistorico(listaHistorico);

        //Configurar o recycle view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerHistorico.setLayoutManager(layoutManager);
        recyclerHistorico.setHasFixedSize(true);
        recyclerHistorico.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayout.VERTICAL));
        recyclerHistorico.setAdapter(adapterInstituicoes);

        removerHistorico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesCustom.clearSharedPreferences(view.getContext(), SharedPreferencesCustom.FILE_HISTORICO);
                existeHistorico(false);
            }
        });

        return view;
    }

    /**
     * Funcao que atribui as informacoes da lista
     */
    public void criaHistorico(){

        int qtd = SharedPreferencesCustom.getQuantidadeHistorico(view.getContext());

        if(qtd == 0){
            existeHistorico(false);
            return;
        }

        else{
            existeHistorico(true);
        }

        listaHistorico.clear();
        for(int i = qtd - 1; i >= 0; i--){

            String nome = SharedPreferencesCustom.getNomeHistorico(view.getContext(), i);
            String instituicao = SharedPreferencesCustom.getInstituicaoHistorico(view.getContext(), i);
            String data = SharedPreferencesCustom.getDataHistorico(view.getContext(), i);
            listaHistorico.add(new HistoricoDoador(instituicao, data, nome));
        }
    }

    /**
     * Funcao que altera a visibiliade dos componentes de acordo com o historico
     * @param existe true se existe historico, false se nao existe historico
     */
    public void existeHistorico(boolean existe){

        if(existe){
            txtHistorico.setVisibility(View.VISIBLE);
            recyclerHistorico.setVisibility(View.VISIBLE);
            removerHistorico.setVisibility(View.VISIBLE);
            txtSemHistorico.setVisibility(View.INVISIBLE);
        }

        else{
            txtHistorico.setVisibility(View.INVISIBLE);
            recyclerHistorico.setVisibility(View.INVISIBLE);
            removerHistorico.setVisibility(View.INVISIBLE);
            txtSemHistorico.setVisibility(View.VISIBLE);
        }
    }

}
