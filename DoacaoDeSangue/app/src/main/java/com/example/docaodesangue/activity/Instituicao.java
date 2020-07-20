package com.example.docaodesangue.activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.docaodesangue.R;
import com.example.docaodesangue.basiclasses.UserInstituicao;
import com.example.docaodesangue.config.ConfiguracaoFirebase;
import com.example.docaodesangue.fragments.FragmentEditInstituicao;
import com.example.docaodesangue.fragments.FragmentInstituicao;
import com.example.docaodesangue.fragments.FragmentNotificarDoadores;
import com.example.docaodesangue.helper.AlertDialogCustom;
import com.example.docaodesangue.helper.Base64Custom;
import com.example.docaodesangue.helper.SharedPreferencesCustom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.ExecutionException;

public class Instituicao extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //Firebase
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getAutenticacao();
    private DatabaseReference firebase = ConfiguracaoFirebase.getFirebase();

    //Componentes
    private TextView txtNomeInstituicao;
    private TextView txtLocalizacaoInstituicao;
    private TextView txtEnderecoInstituicao;
    private TextView txtTelefoneInstituicao;

    //Classes da instituicao
    private static UserInstituicao instituicao;

    //Listener
    public static ValueEventListener valueEventListener;

    /**
     * Funcao que retorna as informacoes da instituicao
     * @return Retorna a instituicao(UserInstituicao.class)
     */
    public static UserInstituicao getInstituicao(){
        return instituicao;
    }

    /**
     * Cria a tela da instituicao
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instituicao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Tela de inicio da instituicao = notficar doadores
        FragmentInstituicao instituicao = new FragmentInstituicao();
        FragmentTransaction fragment = getSupportFragmentManager().beginTransaction();
        fragment.replace(R.id.frame_instituicao, instituicao);
        fragment.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);

        //Textos no nav_header da instituicao
        txtNomeInstituicao = header.findViewById(R.id.txtNavNomeInstituicao);
        txtLocalizacaoInstituicao = header.findViewById(R.id.txtNavLocalizacaoInstituicao);
        txtEnderecoInstituicao = header.findViewById(R.id.txtNavEnderecoInstituicao);
        txtTelefoneInstituicao = header.findViewById(R.id.txtNavTelefoneInstituicao);

        //Recupera e atualiza as informacoes
        recuperaInstituicao();

    }

    /**
     * Funcao que a pressionar o botao de voltar(do proprio celular), o usuario da logout e volta para a tela principal
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        autenticacao = ConfiguracaoFirebase.getAutenticacao();
        autenticacao.signOut();
    }

    /**
     * Cria o menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.instituicao, menu);
        return true;
    }

    /**
     * Atribui funcoes as elemntos do menu de opcoes
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //Logout da conta
        if (id == R.id.logout) {
            autenticacao = ConfiguracaoFirebase.getAutenticacao();
            autenticacao.signOut();
            finish();
        }

        //Remocao da conta
        else if(id == R.id.delete){
            AlertDialogCustom.alertDeletarConta(Instituicao.this);
            instituicao = new UserInstituicao();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //Tela principal da instituicao
        if (id == R.id.nav_instituicao) {

            FragmentInstituicao instituicao = new FragmentInstituicao();
            FragmentTransaction fragment = getSupportFragmentManager().beginTransaction();
            fragment.replace(R.id.frame_instituicao, instituicao);
            fragment.commit();

            //Tela de notificacao de doadores
        } else if (id == R.id.nav_notificar_doadores) {

            FragmentNotificarDoadores notificarDoadores = new FragmentNotificarDoadores();
            FragmentTransaction fragment = getSupportFragmentManager().beginTransaction();
            fragment.replace(R.id.frame_instituicao, notificarDoadores);
            fragment.commit();

            //Tela de editar informacoes da instituicao
        } else if (id == R.id.nav_edit_instituicao) {

            FragmentEditInstituicao editInstituicao = new FragmentEditInstituicao();
            FragmentTransaction fragment = getSupportFragmentManager().beginTransaction();
            fragment.replace(R.id.frame_instituicao, editInstituicao);
            fragment.commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Funcao que atualiza os textos do menu
     */
    public void atualizaTextos(){

        try{
            if(ConfiguracaoFirebase.deleteConta){
                return;
            }
            else{
                txtNomeInstituicao.setText(instituicao.getNome());
                txtLocalizacaoInstituicao.setText(instituicao.getCidade()+" - "+instituicao.getEstado());
                txtEnderecoInstituicao.setText(instituicao.getEndereco());
                txtTelefoneInstituicao.setText(instituicao.getTelefone());
            }
        } catch (Exception e){
            Log.i("Mensagens", "1 - "+e.getMessage()+"");
        }


    }

    /**
     * Funcao que recupera as informacoes da instituicao e ao mesmo tempo atualiza as componentes
     */
    public void recuperaInstituicao(){

       String idInstituicao = Base64Custom.encodeBase64(autenticacao.getCurrentUser().getEmail());
       DatabaseReference referenceInstituicao = firebase.child("Instituicoes").child(idInstituicao);

       valueEventListener = new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               instituicao = dataSnapshot.getValue(UserInstituicao.class);
               atualizaTextos();
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       };

        referenceInstituicao.addValueEventListener(valueEventListener);
    }
}
