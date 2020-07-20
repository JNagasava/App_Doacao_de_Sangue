package com.example.docaodesangue.fragments;


import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.docaodesangue.R;
import com.example.docaodesangue.activity.Instituicao;
import com.example.docaodesangue.basiclasses.UserInstituicao;
import com.example.docaodesangue.config.ConfiguracaoFirebase;
import com.example.docaodesangue.helper.AlertDialogCustom;
import com.example.docaodesangue.helper.Base64Custom;
import com.example.docaodesangue.helper.SharedPreferencesCustom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.docaodesangue.helper.SharedPreferencesCustom.endereco;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentEditInstituicao extends Fragment implements AdapterView.OnItemSelectedListener{

    //Componentes
    private Spinner spinnerEstadoInstituicao;
    private View view;
    private Button btnAtualizar;
    private EditText editEndereco;
    private EditText editCidade;
    private EditText editNomeInstituicao;
    private EditText editTelefone;
    private EditText editCEP;

    //Firebase
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getAutenticacao();
    private DatabaseReference firebase = ConfiguracaoFirebase.getFirebase();

    //Informacoes
    private String estado;
    private UserInstituicao instituicao = Instituicao.getInstituicao();

    public FragmentEditInstituicao() {
        // Required empty public constructor
    }

    /**
     * Cria a tela editInstituicao
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_edit_instituicao, container, false);

        //Spinner do estado da instituicao
        spinnerEstadoInstituicao = (Spinner) view.findViewById(R.id.sprEstadoInstituicao);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource( view.getContext(), R.array.estados_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerEstadoInstituicao.setAdapter(adapter);
        spinnerEstadoInstituicao.setOnItemSelectedListener(this);

        editEndereco = view.findViewById(R.id.editTextEditEndereco);
        editCidade = view.findViewById(R.id.editTextEditCidade);
        editNomeInstituicao = view.findViewById(R.id.editTextEditNomeInstituicao);
        editTelefone = view.findViewById(R.id.editTextEditTelefone);
        editCEP = view.findViewById(R.id.editTextEditCEP);

        //Atribui as informacoes padroes
        configuraEdit();

        //Configura o botao para salvar/atualizar as informacoes da instituicao
        btnAtualizar = view.findViewById(R.id.btnAtualizarInstituicao);
        btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Verifica o campo estado
                if(spinnerEstadoInstituicao.getSelectedItemPosition() == 0){
                    Toast.makeText(view.getContext(), "Campo do estado está vazio", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Verifica o campo endereco
                if(editEndereco.getText().toString().isEmpty()){
                    Toast.makeText(view.getContext(), "Campo de endereço vazio", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Verifica o campo cidade
                if(editCidade.getText().toString().isEmpty()){
                    Toast.makeText(view.getContext(), "Campo da cidade vazio", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Verifica o campo nome instituicao
                if(editNomeInstituicao.getText().toString().isEmpty()){
                    Toast.makeText(view.getContext(), "Campo do nome da instituição vazio", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Verifica o campo telefone
                if(editTelefone.getText().toString().isEmpty()){
                    Toast.makeText(view.getContext(), "Campo do telefone vazio", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Verifica o campo CEP
                if(editCEP.getText().toString().isEmpty()){
                    Toast.makeText(view.getContext(), "Campo do CEP está vazio", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Atualiza as informacoes
                instituicao.setEndereco(editEndereco.getText().toString());
                instituicao.setCidade(editCidade.getText().toString());
                instituicao.setNome(editNomeInstituicao.getText().toString());
                instituicao.setTelefone(editTelefone.getText().toString());
                instituicao.setCep(editCEP.getText().toString());
                instituicao.setEstado(estado);

                //Verifica e atribui a localizacao da instituicao
                Geocoder geo = new Geocoder(view.getContext(), Locale.getDefault());
                try{
                    List<Address> lista = geo.getFromLocationName(editEndereco.getText().toString()+","+editCidade.getText().toString()+","+estado, 1);
                    if(lista.isEmpty() == false){
                        Address address = lista.get(0);
                        instituicao.setLocalizacao(address.getLatitude(), address.getLongitude());
                    }
                } catch (Exception e){
                    Toast.makeText(view.getContext(), "Não foi possível atualizar a instituição, devido ao endereço incompatível", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebase.child("Instituicoes").child(Base64Custom.encodeBase64(autenticacao.getCurrentUser().getEmail())).setValue(instituicao);
                Toast.makeText(view.getContext(), "Informações atualizadas", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    /**
     * Configura as informacoes iniciais
     */
    public void configuraEdit(){

        editEndereco.setText(instituicao.getEndereco());
        editCidade.setText(instituicao.getCidade());
        editNomeInstituicao.setText(instituicao.getNome());
        editTelefone.setText(instituicao.getTelefone());
        editCEP.setText(instituicao.getCep());
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.estados_array, android.R.layout.simple_spinner_item);
        spinnerEstadoInstituicao.setSelection(adapter.getPosition(instituicao.getEstado()));
    }

    /**
     * Atribuicao do estado
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        estado = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}

