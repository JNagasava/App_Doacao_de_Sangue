package com.example.docaodesangue.fragments;


import android.location.Location;
import android.os.Bundle;
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
import com.example.docaodesangue.adapter.AdapterNotifcacao;
import com.example.docaodesangue.basiclasses.NotificacoesInstituicao;
import com.example.docaodesangue.basiclasses.UserInstituicao;
import com.example.docaodesangue.config.ConfiguracaoFirebase;
import com.example.docaodesangue.helper.AlertDialogCustom;
import com.example.docaodesangue.helper.Base64Custom;
import com.example.docaodesangue.helper.SharedPreferencesCustom;
import com.example.docaodesangue.model.NotificacaoDoador;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentNotificacoes extends Fragment {

    //Componentes
    private View view;
    private RecyclerView recyclerNotificacao;

    //Informacoes
    private List<NotificacaoDoador> listaNotificacao = new ArrayList<>();
    private String email;

    public FragmentNotificacoes() {
        // Required empty public constructor
    }

    /**
     * Cria a tela das notificacoes
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_notificacoes, container, false);

        //Carrega as informacoes do doador
        if(SharedPreferencesCustom.StartSharedPreferencesDoador(view.getContext())){

        }
        else{
            //Caixa de alerta -> editar as informacoes do doador
            AlertDialogCustom.alertEditDoador(view.getContext(), R.layout.fragment_edit);
        }

        //Atribui o RecyclerView para uma variavel
        recyclerNotificacao = view.findViewById(R.id.recycler_notificacao);

        //Listagem das notificacoes
        criaNotificacao();

        //Configurar o recycle view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerNotificacao.setLayoutManager(layoutManager);
        recyclerNotificacao.setHasFixedSize(true);
        recyclerNotificacao.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayout.VERTICAL));
        return view;
    }


    /**
     * Cria as notificacoes
     */
    public void criaNotificacao(){

        DatabaseReference firebase = ConfiguracaoFirebase.getFirebase();
        DatabaseReference reference = firebase.child("Notificacoes");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listaNotificacao.clear();
                for(DataSnapshot x: dataSnapshot.getChildren()){
                    NotificacoesInstituicao notificacao = x.getValue(NotificacoesInstituicao.class);
                    Map<String, String> instituicaoLocal = notificacao.getLocalizacao();
                    if(instituicaoLocal.containsKey("X") == true && instituicaoLocal.containsKey("Y") == true){

                        Location doador = new Location("");
                        doador.setLatitude(Double.parseDouble(SharedPreferencesCustom.localizacaoX));
                        doador.setLongitude(Double.parseDouble(SharedPreferencesCustom.localizacaoY));

                        Location instituicao = new Location("");
                        instituicao.setLatitude(Double.parseDouble(instituicaoLocal.get("X")));
                        instituicao.setLongitude(Double.parseDouble(instituicaoLocal.get("Y")));

                        int distanciaKm = (int)(doador.distanceTo(instituicao)/1000);

                        if(distanciaKm <= notificacao.getRaio()){
                            listaNotificacao.add(new NotificacaoDoador(notificacao.getNome(), notificacao.getEmail(), notificacao.getLocalizacao()));
                        }
                    }

                }

                //Configurar o adapter
                AdapterNotifcacao adapterNotifcacao = new AdapterNotifcacao(listaNotificacao);
                recyclerNotificacao.setAdapter(adapterNotifcacao);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
