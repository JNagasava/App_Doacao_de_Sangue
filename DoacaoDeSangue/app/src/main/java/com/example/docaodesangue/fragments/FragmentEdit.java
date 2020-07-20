package com.example.docaodesangue.fragments;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.docaodesangue.R;
import com.example.docaodesangue.activity.Doador;
import com.example.docaodesangue.helper.AlertDialogCustom;
import com.example.docaodesangue.helper.SharedPreferencesCustom;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentEdit extends Fragment {

    //Componentes
    private View view;
    private EditText nome;
    private RadioGroup rgTipoSangue;
    private RadioGroup rgFatorRhSangue;
    private EditText endereco;
    private Button confirmar;

    //Informacoes
    private String nomeDoador = "";
    private String tipoSangueDoador = "";
    private String fatorRhSangueDoador = "";
    private String enderecoDoador = "";
    private String localizacaoX = "";
    private String localizacaoY = "";

    public FragmentEdit() {
        // Required empty public constructor
    }

    /**
     * Cria a tela de edicao do doador
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_edit, container, false);

        nome = view.findViewById(R.id.editTextNomeEdit);
        rgTipoSangue = view.findViewById(R.id.rgTipoSangue);
        rgFatorRhSangue = view.findViewById(R.id.rgFatorRhSangue);
        endereco = view.findViewById(R.id.editTextLocalizacaoDoador);

        //Carrega as informacoes do doador
        if(SharedPreferencesCustom.StartSharedPreferencesDoador(view.getContext())){
            configura();
        }

        verificaRadioGroupTipoSangueDoador();
        verificaRadioGroupFatorRhSangueDoador();

        //Atualiza as informacoes
        confirmar = view.findViewById(R.id.btnConfirmarEdit);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                verificaRadioGroupTipoSangueDoador();
                verificaRadioGroupFatorRhSangueDoador();
                nomeDoador = nome.getText().toString();
                enderecoDoador = endereco.getText().toString();

                if(nomeDoador.isEmpty()){
                    Toast.makeText(view.getContext(), "Campo nome vazio", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(tipoSangueDoador.isEmpty()){
                    Toast.makeText(view.getContext(), "Nenhum tipo sanguíneo selecionado", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(fatorRhSangueDoador.isEmpty()){
                    Toast.makeText(view.getContext(), "Nenhum fator Rh selecionado", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(enderecoDoador.isEmpty()){
                    Toast.makeText(view.getContext(), "Campo localização vazio", Toast.LENGTH_SHORT).show();
                    return;
                }

                Geocoder geo = new Geocoder(view.getContext(), Locale.getDefault());

                try{
                    List<Address> lista = geo.getFromLocationName(enderecoDoador, 1);
                    if(lista.isEmpty() == false){
                       Address address = lista.get(0);
                       localizacaoX = String.valueOf(address.getLatitude());
                       localizacaoY = String.valueOf(address.getLongitude());
                        atualizaInfo();
                        Toast.makeText(view.getContext(), "Informações alteradas com sucesso", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(view.getContext(), "Não foi possível obter a sua localização", Toast.LENGTH_SHORT).show();
                        Toast.makeText(view.getContext(), "Seja mais específico em sua localização", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e){
                    Log.i("Teste", e.getMessage());
                    Toast.makeText(view.getContext(), "Não foi possível obter a sua localização", Toast.LENGTH_SHORT).show();
                    Toast.makeText(view.getContext(), "Seja mais específico em sua localização", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        return view;
    }

    /**
     * Funcao que verifica qual radio button do tipo do sangue esta selecionado
     */
    public void verificaRadioGroupTipoSangueDoador(){
                rgTipoSangue.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId == R.id.rbtn_tipo_a){
                    tipoSangueDoador = "A";
                }

                else if(checkedId == R.id.rbtn_tipo_b){
                    tipoSangueDoador = "B";
                }

                else if(checkedId == R.id.rbtn_tipo_o){
                    tipoSangueDoador = "O";
                }

                else if(checkedId == R.id.rbtn_tipo_ab){
                    tipoSangueDoador = "AB";
                }

                Log.i("Teste", "Tipo de sangue do doador: "+tipoSangueDoador);

            }
        });
    }

    /**
     * Funcao que verifica qual radio button do fator Rh esta selecionado
     */
    public void verificaRadioGroupFatorRhSangueDoador(){
        rgFatorRhSangue.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId == R.id.rbtn_positivo){
                    fatorRhSangueDoador = "+";
                }

                else if(checkedId == R.id.rbtn_negativo){
                    fatorRhSangueDoador = "-";
                }

                Log.i("Teste", "Fator Rh do doador: "+fatorRhSangueDoador);
            }
        });
    }

    /**
     * Configura as informacoes do usuario
     */
    public void configura(){
        configuraNome();
        configuraSangue();
        configuraEndereco();
    }

    /**
     * Funcao que configura o nome de acordo com as informacoes do usuario
     */
    public void configuraNome(){
        nome.setText(SharedPreferencesCustom.nome);
    }

    /**
     * Funcao que configura o endereco de acordo com as informacoes do usuario
     */
    public void configuraEndereco(){
        endereco.setText(SharedPreferencesCustom.endereco);
    }

    /**
     * Funcao que configura os RadioButtons(Tipo Sangue e Fator Rh) de acordo com as informacoes do usuario
     */
    public void configuraSangue(){

        String tipoSangue = SharedPreferencesCustom.tipoSangue;

        //A+
        if(tipoSangue.equals("A+")){
            rgTipoSangue.check(R.id.rbtn_tipo_a);
            rgFatorRhSangue.check(R.id.rbtn_positivo);
            tipoSangueDoador = "A";
            fatorRhSangueDoador = "+";
        }

        //A-
        else if(tipoSangue.equals("A-")){
            rgTipoSangue.check(R.id.rbtn_tipo_a);
            rgFatorRhSangue.check(R.id.rbtn_negativo);
            tipoSangueDoador = "A";
            fatorRhSangueDoador = "-";
        }

        //B+
        else if(tipoSangue.equals("B+")){
            rgTipoSangue.check(R.id.rbtn_tipo_b);
            rgFatorRhSangue.check(R.id.rbtn_positivo);
            tipoSangueDoador = "B";
            fatorRhSangueDoador = "+";
        }

        //B-
        else if(tipoSangue.equals("B-")){
            rgTipoSangue.check(R.id.rbtn_tipo_b);
            rgFatorRhSangue.check(R.id.rbtn_negativo);
            tipoSangueDoador = "B";
            fatorRhSangueDoador = "-";
        }

        //O+
        else if(tipoSangue.equals("O+")){
            rgTipoSangue.check(R.id.rbtn_tipo_o);
            rgFatorRhSangue.check(R.id.rbtn_positivo);
            tipoSangueDoador = "O";
            fatorRhSangueDoador = "+";
        }

        //O-
        else if(tipoSangue.equals("O-")){
            rgTipoSangue.check(R.id.rbtn_tipo_o);
            rgFatorRhSangue.check(R.id.rbtn_negativo);
            tipoSangueDoador = "O";
            fatorRhSangueDoador = "-";
        }

        //AB+
        else if(tipoSangue.equals("AB+")){
            rgTipoSangue.check(R.id.rbtn_tipo_ab);
            rgFatorRhSangue.check(R.id.rbtn_positivo);
            tipoSangueDoador = "AB";
            fatorRhSangueDoador = "+";
        }

        //AB-
        else if(tipoSangue.equals("AB-")){
            rgTipoSangue.check(R.id.rbtn_tipo_ab);
            rgFatorRhSangue.check(R.id.rbtn_negativo);
            tipoSangueDoador = "AB";
            fatorRhSangueDoador = "-";
        }
    }

    /**
     * Atualiza as informacoes do doador(atualiza na memoria e na tela
     */
    public void atualizaInfo(){

        String sangue = tipoSangueDoador+fatorRhSangueDoador;

        SharedPreferencesCustom.setDataDoador(view.getContext(), SharedPreferencesCustom.nomeDoador, nomeDoador);
        SharedPreferencesCustom.setDataDoador(view.getContext(), SharedPreferencesCustom.tipoSangueDoador, sangue);
        SharedPreferencesCustom.setDataDoador(view.getContext(), SharedPreferencesCustom.enderecoDoador, enderecoDoador);
        SharedPreferencesCustom.setDataDoador(view.getContext(), SharedPreferencesCustom.localizacaoXDoador, localizacaoX);
        SharedPreferencesCustom.setDataDoador(view.getContext(), SharedPreferencesCustom.localizacaoYDoador, localizacaoY);

        TextView nome = Doador.headerView.findViewById(R.id.txtName);
        TextView tipoSangue = Doador.headerView.findViewById(R.id.txtBloodType);
        TextView endereco = Doador.headerView.findViewById(R.id.txtLocation);

        nome.setText(nomeDoador);
        tipoSangue.setText(sangue);
        endereco.setText(enderecoDoador);
    }
}
