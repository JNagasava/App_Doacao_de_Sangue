package com.example.docaodesangue.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.docaodesangue.R;
import com.example.docaodesangue.helper.AlertDialogCustom;
import com.example.docaodesangue.helper.SharedPreferencesCustom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Fragmento da tela de informações a respeito de
 * como e quem pode doar sangue.
 */
public class FragmentAjuda extends Fragment {

    //Componentes
    ListView listAjuda;
    private View view;

    private BufferedReader leitor; //Lê arquivo linha por linha e conta as linhas
    private InputStream streamam;
    private InputStream loader;
    private BufferedReader bufferloader;// Coloca as linhas na array de String
    int contaLinhas = 0;
    public FragmentAjuda() {
        // Required empty public constructor
    }

    /**
     * Cria-se elementos que aparecerão no listview.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_ajuda, container, false);

        //Carrega as informacoes do doador
        if(SharedPreferencesCustom.StartSharedPreferencesDoador(view.getContext()) == false){
            //Caixa de alerta -> editar as informacoes do doador
            AlertDialogCustom.alertEditDoador(view.getContext(), R.layout.fragment_edit);
        }

        int i;
        streamam = this.getResources().openRawResource(R.raw.info_doar_sangue);
        leitor = new BufferedReader( new InputStreamReader(streamam));
        loader = this.getResources().openRawResource(R.raw.info_doar_sangue);
        bufferloader = new BufferedReader( new InputStreamReader(loader));

        try
        {
            while (leitor.readLine()!= null)
            {
                contaLinhas++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] linhas = new String[contaLinhas];
        try
        {
            for (i = 0; i < contaLinhas; i++)
            {
                linhas[i] = bufferloader.readLine();
                Log.i("linhas",linhas[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        listAjuda = (ListView) view.findViewById(R.id.listAjuda);

        ArrayAdapter<String> itensAdaptador = new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                linhas){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.BLACK);
                return view;
            }
        };

        listAjuda.setAdapter(itensAdaptador);

        /**
         * Ações básicas ao clique nos elementos do listview
         */
        listAjuda.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String estadoSelecionado = listAjuda.getItemAtPosition(position).toString();
                Toast.makeText(view.getContext(), estadoSelecionado, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}