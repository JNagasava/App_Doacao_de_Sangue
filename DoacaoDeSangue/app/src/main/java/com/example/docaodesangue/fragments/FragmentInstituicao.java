package com.example.docaodesangue.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.docaodesangue.R;
import com.example.docaodesangue.basiclasses.EstoqueInstituicao;
import com.example.docaodesangue.config.ConfiguracaoFirebase;
import com.example.docaodesangue.helper.Base64Custom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentInstituicao extends Fragment implements AdapterView.OnItemSelectedListener{

    //Componentes
    private View view;
    private Spinner sprNivelEstoque;

    //Firebase
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getAutenticacao();
    private DatabaseReference firebase = ConfiguracaoFirebase.getFirebase();

    //Informacao
    private int counter = 0;
    private EstoqueInstituicao estoque;

    public FragmentInstituicao() {
        // Required empty public constructor
    }

    /**
     * Cria a tela da instituicao -> gerencia o estoque de sangue
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_instituicao, container, false);

        estoque = new EstoqueInstituicao();

        final DatabaseReference reference = firebase.child("Estoque").child(Base64Custom.encodeBase64(autenticacao.getCurrentUser().getEmail()));

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                estoque = dataSnapshot.getValue(EstoqueInstituicao.class);
                configuracoesEstoque();
                itemDefault();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

    /**
     * Configuracoes dos spinners de nivel de estoque de sangue
     */
    public void configuracoesEstoque(){

        //SANGUE AB+ (spinner)
        sprNivelEstoque = (Spinner) view.findViewById(R.id.spr_ab_positivo);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterAB_positivo = ArrayAdapter.createFromResource( view.getContext(), R.array.nivel_estoque_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterAB_positivo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        sprNivelEstoque.setAdapter(adapterAB_positivo);
        sprNivelEstoque.setOnItemSelectedListener(this);

        //SANGUE AB- (spinner)
        sprNivelEstoque = (Spinner) view.findViewById(R.id.spr_ab_negativo);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterAB_negativo = ArrayAdapter.createFromResource( view.getContext(), R.array.nivel_estoque_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterAB_negativo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        sprNivelEstoque.setAdapter(adapterAB_negativo);
        sprNivelEstoque.setOnItemSelectedListener(this);

        //SANGUE A+ (spinner)
        sprNivelEstoque = (Spinner) view.findViewById(R.id.spr_a_positivo);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterA_positivo = ArrayAdapter.createFromResource( view.getContext(), R.array.nivel_estoque_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterA_positivo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        sprNivelEstoque.setAdapter(adapterA_positivo);
        sprNivelEstoque.setOnItemSelectedListener(this);


        //SANGUE A- (spinner)
        sprNivelEstoque = (Spinner) view.findViewById(R.id.spr_a_negativo);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterA_negativo = ArrayAdapter.createFromResource( view.getContext(), R.array.nivel_estoque_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterA_negativo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        sprNivelEstoque.setAdapter(adapterA_negativo);
        sprNivelEstoque.setOnItemSelectedListener(this);


        //SANGUE B+ (spinner)
        sprNivelEstoque = (Spinner) view.findViewById(R.id.spr_b_positivo);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterB_positivo = ArrayAdapter.createFromResource( view.getContext(), R.array.nivel_estoque_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterB_positivo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        sprNivelEstoque.setAdapter(adapterB_positivo);
        sprNivelEstoque.setOnItemSelectedListener(this);


        //SANGUE B- (spinner)
        sprNivelEstoque = (Spinner) view.findViewById(R.id.spr_b_negativo);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterB_negativo = ArrayAdapter.createFromResource( view.getContext(), R.array.nivel_estoque_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterB_negativo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        sprNivelEstoque.setAdapter(adapterB_negativo);
        sprNivelEstoque.setOnItemSelectedListener(this);


        //SANGUE O+ (spinner)
        sprNivelEstoque = (Spinner) view.findViewById(R.id.spr_o_positivo);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterO_positivo = ArrayAdapter.createFromResource( view.getContext(), R.array.nivel_estoque_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterO_positivo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        sprNivelEstoque.setAdapter(adapterO_positivo);
        sprNivelEstoque.setOnItemSelectedListener(this);


        //SANGUE O- (spinner)
        sprNivelEstoque = (Spinner) view.findViewById(R.id.spr_o_negativo);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterO_negativo = ArrayAdapter.createFromResource( view.getContext(), R.array.nivel_estoque_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterO_negativo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        sprNivelEstoque.setAdapter(adapterO_negativo);
        sprNivelEstoque.setOnItemSelectedListener(this);

    }

    /**
     * Atualiza o estoque de sangue(mas ainda nao envia para o firebase)
     */
    public void atualizaEstoque(){

        Log.i("Teste", "AtualizaEstoque");

        //A+
        sprNivelEstoque = (Spinner) view.findViewById(R.id.spr_a_positivo);
        estoque.setSangue("A+", sprNivelEstoque.getSelectedItemPosition());

        //A-
        sprNivelEstoque = (Spinner) view.findViewById(R.id.spr_a_negativo);
        estoque.setSangue("A-", sprNivelEstoque.getSelectedItemPosition());

        //B+
        sprNivelEstoque = (Spinner) view.findViewById(R.id.spr_b_positivo);
        estoque.setSangue("B+", sprNivelEstoque.getSelectedItemPosition());

        //B-
        sprNivelEstoque = (Spinner) view.findViewById(R.id.spr_b_negativo);
        estoque.setSangue("B-", sprNivelEstoque.getSelectedItemPosition());

        //O+
        sprNivelEstoque = (Spinner) view.findViewById(R.id.spr_o_positivo);
        estoque.setSangue("O+", sprNivelEstoque.getSelectedItemPosition());

        //O-
        sprNivelEstoque = (Spinner) view.findViewById(R.id.spr_o_negativo);
        estoque.setSangue("O-", sprNivelEstoque.getSelectedItemPosition());

        //AB+
        sprNivelEstoque = (Spinner) view.findViewById(R.id.spr_ab_positivo);
        estoque.setSangue("AB+", sprNivelEstoque.getSelectedItemPosition());

        //AB-
        sprNivelEstoque = (Spinner) view.findViewById(R.id.spr_ab_negativo);
        estoque.setSangue("AB-", sprNivelEstoque.getSelectedItemPosition());
    }

    /**
     * Recupera os dados do firebase
     */
    public void itemDefault(){

        //A+
        sprNivelEstoque = (Spinner) view.findViewById(R.id.spr_a_positivo);
        sprNivelEstoque.setSelection(estoque.getNivelSangue().get("A+"));

        //A-
        sprNivelEstoque = (Spinner) view.findViewById(R.id.spr_a_negativo);
        sprNivelEstoque.setSelection(estoque.getNivelSangue().get("A-"));

        //B+
        sprNivelEstoque = (Spinner) view.findViewById(R.id.spr_b_positivo);
        sprNivelEstoque.setSelection(estoque.getNivelSangue().get("B+"));

        //B-
        sprNivelEstoque = (Spinner) view.findViewById(R.id.spr_b_negativo);
        sprNivelEstoque.setSelection(estoque.getNivelSangue().get("B-"));

        //O+
        sprNivelEstoque = (Spinner) view.findViewById(R.id.spr_o_positivo);
        sprNivelEstoque.setSelection(estoque.getNivelSangue().get("O+"));

        //O-
        sprNivelEstoque = (Spinner) view.findViewById(R.id.spr_o_negativo);
        sprNivelEstoque.setSelection(estoque.getNivelSangue().get("O-"));

        //AB+
        sprNivelEstoque = (Spinner) view.findViewById(R.id.spr_ab_positivo);
        sprNivelEstoque.setSelection(estoque.getNivelSangue().get("AB+"));

        //AB-
        sprNivelEstoque = (Spinner) view.findViewById(R.id.spr_ab_negativo);
        sprNivelEstoque.setSelection(estoque.getNivelSangue().get("AB-"));
    }

    /**
     * Listener dos spinners -> nivel de sangue
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        counter++;
        if(counter > 8){
            //Atualiza o estoque de sangue(mas ainda nao envia para o firebase)
            atualizaEstoque();
            //Salva os dados no firebase
            estoque.salvarEstoque();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
