package com.example.docaodesangue.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.docaodesangue.R;
import com.example.docaodesangue.fragments.FragmentAgendamentos;
import com.example.docaodesangue.fragments.FragmentAjuda;
import com.example.docaodesangue.fragments.FragmentEdit;
import com.example.docaodesangue.fragments.FragmentHistorico;
import com.example.docaodesangue.fragments.FragmentInstituicoes;
import com.example.docaodesangue.fragments.FragmentNotificacoes;
import com.example.docaodesangue.helper.AlertDialogCustom;
import com.example.docaodesangue.helper.SharedPreferencesCustom;

public class Doador extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Componentes da tela do doador
    public static View headerView;
    private TextView nome;
    private TextView tipoSangue;
    private TextView endereco;

    /**
     * Cria a tela do usuario
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doador);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Inicia com a tela(fragment) das instituicoes
        FragmentInstituicoes instituicoes = new FragmentInstituicoes();
        FragmentTransaction fragment = getSupportFragmentManager().beginTransaction();
        fragment.replace(R.id.frame_doador, instituicoes);
        fragment.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //menu
        headerView = navigationView.getHeaderView(0);

        //Resgata os textos do menu
        nome = headerView.findViewById(R.id.txtName);
        tipoSangue = headerView.findViewById(R.id.txtBloodType);
        endereco = headerView.findViewById(R.id.txtLocation);

        //Verifica se ja existe informacoes do doador
        if(SharedPreferencesCustom.StartSharedPreferencesDoador(getApplicationContext())){
            nome.setText(SharedPreferencesCustom.nome);
            tipoSangue.setText(SharedPreferencesCustom.tipoSangue);
            endereco.setText(SharedPreferencesCustom.endereco);
        }
    }

    /**
     * Botao de voltar do celular, serve para sair
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        finish();
    }

    /**
     * Cria o menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.doador, menu);
        return true;
    }

    /**
     * Aba que fica na parte superior direita -> funcao de sair
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sair) {
            finish();
            return false;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Aba de selecao de tela(fragment)
     * @param item
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //Vai para "Insitituicoes"
        if (id == R.id.nav_instituicoes) {

            FragmentInstituicoes instituicoes = new FragmentInstituicoes();
            FragmentTransaction fragment = getSupportFragmentManager().beginTransaction();
            fragment.replace(R.id.frame_doador, instituicoes);
            fragment.commit();

            //Vai para "Agendamentos"
        } else if (id == R.id.nav_agendamentos) {

            FragmentAgendamentos agendamentos = new FragmentAgendamentos();
            FragmentTransaction fragment = getSupportFragmentManager().beginTransaction();
            fragment.replace(R.id.frame_doador, agendamentos);
            fragment.commit();

            //Vai para "Historico"
        } else if (id == R.id.nav_historico) {

            FragmentHistorico historico = new FragmentHistorico();
            FragmentTransaction fragment = getSupportFragmentManager().beginTransaction();
            fragment.replace(R.id.frame_doador, historico);
            fragment.commit();

            //Vai para "Editar informacoes"
        } else if (id == R.id.nav_edit) {

            FragmentEdit edit = new FragmentEdit();
            FragmentTransaction fragment = getSupportFragmentManager().beginTransaction();
            fragment.replace(R.id.frame_doador, edit);
            fragment.commit();

            //Vai para "Notificacoes"
        } else if (id == R.id.nav_notificacoes) {

            FragmentNotificacoes notificacoes = new FragmentNotificacoes();
            FragmentTransaction fragment = getSupportFragmentManager().beginTransaction();
            fragment.replace(R.id.frame_doador, notificacoes);
            fragment.commit();

            //Vai para "Ajuda"
        } else if (id == R.id.nav_ajuda){

            FragmentAjuda ajuda = new FragmentAjuda();
            FragmentTransaction fragment = getSupportFragmentManager().beginTransaction();
            fragment.replace(R.id.frame_doador, ajuda);
            fragment.commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
