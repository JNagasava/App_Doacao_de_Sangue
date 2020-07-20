package com.example.docaodesangue.activity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.docaodesangue.R;
import com.example.docaodesangue.basiclasses.EstoqueInstituicao;
import com.example.docaodesangue.basiclasses.UserInstituicao;
import com.example.docaodesangue.config.ConfiguracaoFirebase;
import com.example.docaodesangue.helper.Base64Custom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import java.util.List;
import java.util.Locale;

public class CadastroInstituicao extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //Autenticacao do firebase
    private FirebaseAuth autenticacao;

    //Componentes do firebase
    private Button btnRegister;
    private Spinner spinnerEstado;
    private EditText editCNPJ;
    private EditText editNomeInstituicao;
    private EditText editCidade;
    private EditText editEndereco;
    private EditText editCEP;
    private EditText editTelefone;
    private EditText editSenha;
    private EditText editNovaSenha;
    private EditText editEmail;

    //Informacoes
    private String idInstituicao;
    private String CNPJ;
    private String nomeInstituicao;
    private String cidade;
    private String endereco;
    private String CEP;
    private String telefone;
    private String senha;
    private String novaSenha;
    private String email;
    private String estado;

    //Classes que serao salvas no firebase
    private UserInstituicao instituicao;
    private EstoqueInstituicao estoque;

    /**
     * Cria a tela de cadastro
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_instituicao);


        //Botao para cadastrar a insituicao
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(registraInstituicao()){
                    return;
                }

                cadastrarInstituicao();
            }
        });

        //Spinner do estado
        spinnerEstado = (Spinner) findViewById(R.id.sprEstado);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.estados_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerEstado.setAdapter(adapter);
        spinnerEstado.setOnItemSelectedListener(this);

        //Demais campos
        editCNPJ = findViewById(R.id.editTextCNPJ);
        editNomeInstituicao = findViewById(R.id.editTextNomeInstituicao);
        editCidade = findViewById(R.id.editTextCidade);
        editEndereco = findViewById(R.id.editTextEndereco);
        editCEP = findViewById(R.id.editTextCEP);
        editTelefone = findViewById(R.id.editTextTelefone);
        editSenha = findViewById(R.id.editTextSenha);
        editNovaSenha = findViewById(R.id.editTextNovaSenha);
        editEmail = findViewById(R.id.editTextEmail);

    }


    //Acoes do spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        estado = parent.getItemAtPosition(position).toString();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    /**
     * Realiza o registro da instituicao
     * @return true se houver um erro,  false se nao houver erro
     */
    public boolean registraInstituicao(){

        CNPJ = editCNPJ.getText().toString().replaceAll("\\n", "");
        nomeInstituicao = editNomeInstituicao.getText().toString().replaceAll("\\n", "");
        cidade = editCidade.getText().toString().replaceAll("\\n", "");
        endereco = editEndereco.getText().toString().replaceAll("\\n", "");
        CEP = editCEP.getText().toString().replaceAll("\\n", "");
        telefone = editTelefone.getText().toString().replaceAll("\\n", "");
        senha = editSenha.getText().toString().replaceAll("\\n", "");
        novaSenha = editNovaSenha.getText().toString().replaceAll("\\n", "");
        email = editEmail.getText().toString().replaceAll("\\n", "");

        //Verifica o campo do CNPJ
        if(verificaString(CNPJ, "Digite o CNPJ")){
            return true;
        }

        //Verifica o campo do Nome da Instituicao
        if(verificaString(nomeInstituicao, "Digite o Nome da Instituição")){
            return true;
        }

        //Verifica o campo estado
        if(verificaString(estado, "Selecione um Estado")){
            return true;
        }

        //Verifica o campo Cidade
        if(verificaString(cidade, "Digite a Cidade")){
            return true;
        }

        //Verifica o campo do Endereco
        if(verificaString(endereco, "Digite o Endereço")){
            return true;
        }

        //Verifica o campo CEP
        if(verificaString(CEP, "Digite o CEP")){
            return true;
        }

        //Verifica o campo Telefone
        if(verificaString(telefone, "Digite o Telefone")){
            return true;
        }

        //Verifica o campo Senha
        if(verificaString(senha, "Digite a Senha")){
            return true;
        }

        //Verifica o campo da senha de confirmacao
        if(verificaString(novaSenha, "Digite a senha para confirmação")){
            return true;
        }

        //Verifica se a senha de confirmacao eh igual a senha
        if(senha.equals(novaSenha) == false){
            Toast.makeText(getApplicationContext(), "Digite novamente a senha de confirmação", Toast.LENGTH_SHORT).show();
            return true;
        }

        //Verifica o campo e-mail
        if(verificaString(email, "Digite o e-mail")){
            return true;
        }

        //Cria a instituicao
        instituicao = new UserInstituicao();
        instituicao.setCnpj(CNPJ);
        instituicao.setNome(nomeInstituicao);
        instituicao.setCidade(cidade);
        instituicao.setEndereco(endereco);
        instituicao.setCep(CEP);
        instituicao.setTelefone(telefone);
        instituicao.setSenha(senha);
        instituicao.setEmail(email);
        instituicao.setEstado(estado);

        //Verifica e atribui a localizacao da instituicao
        Geocoder geo = new Geocoder(getApplication(), Locale.getDefault());
        try{
            List<Address> lista = geo.getFromLocationName(endereco+","+cidade+","+estado, 1);
            if(lista.isEmpty() == false){
                Address address = lista.get(0);
                Log.i("Teste", "Localizacao: "+address.getLatitude()+" "+address.getLongitude());
                instituicao.setLocalizacao(address.getLatitude(), address.getLongitude());
            }
            else{
                Toast.makeText(getApplicationContext(), "Não foi possível cadastrar a instituição, devido ao endereço incompatível", Toast.LENGTH_SHORT).show();
                return true;
            }
        } catch (Exception e){
            Log.i("Teste", e.getMessage());
            Toast.makeText(getApplicationContext(), "Não foi possível cadastrar a instituição, devido ao endereço incompatível", Toast.LENGTH_SHORT).show();
            return true;
        }


        return false;

    }

    /**
     * Verifica se a string esta vazia
     * @param info
     * @param tipoInfo
     * @return true se estiver vazio, false se nao estiver vazio
     */
    public boolean verificaString(String info, String tipoInfo){

        if(info.isEmpty()){
            Toast.makeText(getApplicationContext(), tipoInfo, Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }

    /**
     * Realiza o cadastramento do usuario
     */
    public void cadastrarInstituicao(){

        autenticacao = ConfiguracaoFirebase.getAutenticacao();
        autenticacao.createUserWithEmailAndPassword(instituicao.getEmail(), instituicao.getSenha())
        .addOnCompleteListener(CadastroInstituicao.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Instituição cadastrada", Toast.LENGTH_LONG).show();

                    //Configuracoes do child "Instituicoes"
                    idInstituicao = Base64Custom.encodeBase64(instituicao.getEmail());
                    instituicao.setIdInstiuicao(idInstituicao);
                    instituicao.salvarInstituicao();

                    //Configuracoes do child "Estoque"
                    estoque = new EstoqueInstituicao();
                    estoque.setEmail(instituicao.getEmail());
                    estoque.setIdInstituicao(idInstituicao);
                    estoque.salvarEstoque();

                    finish();
                }
                else{

                    String excecao = "";

                    try{
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e){
                        excecao = "Digite uma senha mais forte";
                    } catch (FirebaseAuthInvalidCredentialsException e){
                        excecao = "E-mail inválido!";
                    } catch (FirebaseAuthUserCollisionException e){
                        excecao = "Já existe uma instituição com esse e-mail";
                    } catch (Exception e){
                        excecao = "Erro ao cadastrar: " + e.getMessage().toString();
                        e.printStackTrace();
                    }

                    Toast.makeText(getApplicationContext(), excecao, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
