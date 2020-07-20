package com.example.docaodesangue.fragments;


import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.docaodesangue.R;
import com.example.docaodesangue.activity.Instituicao;
import com.example.docaodesangue.adapter.AdapterInstituicoes;
import com.example.docaodesangue.basiclasses.UserInstituicao;
import com.example.docaodesangue.config.ConfiguracaoFirebase;
import com.example.docaodesangue.helper.AlertDialogCustom;
import com.example.docaodesangue.helper.SharedPreferencesCustom;
import com.example.docaodesangue.model.InstituicoesDoacao;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentInstituicoes extends Fragment {

    //Componentes
    private View view;
    private RecyclerView recyclerInstituicoes;

    //Lista de instituicoes
    private List<InstituicoesDoacao> listaInstituicoes = new ArrayList<>();

    public FragmentInstituicoes() {
        // Required empty public constructor
    }

    /**
     * Cria a tela das instituicoes
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_instituicoes, container, false);

        //Carrega as informacoes do doador
        if(SharedPreferencesCustom.StartSharedPreferencesDoador(view.getContext()) == false){
            //Caixa de alerta -> editar as informacoes do doador
            AlertDialogCustom.alertEditDoador(view.getContext(), R.layout.fragment_edit);
        }

        //Atribui o RecyclerView para uma variavel
        recyclerInstituicoes = view.findViewById(R.id.recyclerInstituicoes);

        //Listagem da Instituicoes
        criaInstituicoes();

        //Configurar o recycle view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerInstituicoes.setLayoutManager(layoutManager);
        recyclerInstituicoes.setHasFixedSize(true);
        recyclerInstituicoes.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayout.VERTICAL));


        return view;
    }

    /**
     * Cria as Instituicoes
     */
    public void criaInstituicoes(){
        DatabaseReference firebase = ConfiguracaoFirebase.getFirebase();
        DatabaseReference reference = firebase.child("Instituicoes");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listaInstituicoes.clear();

                for(DataSnapshot x: dataSnapshot.getChildren()){
                    UserInstituicao inst = x.getValue(UserInstituicao.class);
                    listaInstituicoes.add(new InstituicoesDoacao(inst.getNome(), inst.getEstado(), inst.getCidade(), inst.getEndereco(), inst.getTelefone(), inst.getLocalizacao()));
                }

                //Configurar o adapter
                AdapterInstituicoes adapterInstituicoes = new AdapterInstituicoes(listaInstituicoes);
                recyclerInstituicoes.setAdapter(adapterInstituicoes);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
