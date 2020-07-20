package com.example.docaodesangue.adapter;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.docaodesangue.R;
import com.example.docaodesangue.basiclasses.UserInstituicao;
import com.example.docaodesangue.config.ConfiguracaoFirebase;
import com.example.docaodesangue.helper.DateCustom;
import com.example.docaodesangue.helper.SharedPreferencesCustom;
import com.example.docaodesangue.model.NotificacaoDoador;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterNotifcacao extends RecyclerView.Adapter<AdapterNotifcacao.MyViewHolder>{

    //Notificacoes -> instituicoes que precisam de certo tipo sanguineo
    private List<NotificacaoDoador> listaNotificacoes;

    //Componente -> """tela""" -> context ...
    private View itemLista;

    //Informacoes
    private int YEAR;
    private int DAY;
    private int MONTH;

    /**
     * Informacoes do campo do RecyclerView
     */
    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView instituicao;
        private Button mapa;
        private Button agendar;

        /**
         * Construtor do campo do RecyclerView
         * @param itemView
         */
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            instituicao = itemView.findViewById(R.id.txtInstituicaoNotificacao);
            mapa = itemView.findViewById(R.id.btnMapaNotificacao);
            agendar = itemView.findViewById(R.id.btnAgendarNotificacao);

        }
    }

    /**
     * Construtor do Adapter
     * @param lista Lista de Instituicoes e suas informacoes
     */
    public AdapterNotifcacao(List<NotificacaoDoador> lista){
        listaNotificacoes = lista;
    }

    /**
     * Criacao do LayoutInflater
     * @param viewGroup
     * @param i
     * @return
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        itemLista = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_notificacoes, viewGroup, false);
        return  new MyViewHolder(itemLista);
    }

    /**
     * Funcoes a serem executadas para cada campo
     * @param myViewHolder campo
     * @param i posicao do campo
     */
    @Override
    //@NonNull RecyclerView.ViewHolder viewHolder
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {

        final NotificacaoDoador instituicao = listaNotificacoes.get(i);
        myViewHolder.instituicao.setText("A instituiçao "+instituicao.getInstituicao()+" precisa do seu tipo sanguíneo");

        //Verifica no mapa a localizacao da instituicao
        myViewHolder.mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> localizacao;
                localizacao = instituicao.getLocalizacao();
                if(localizacao.containsKey("X") == true && localizacao.containsKey("Y") == true){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("geo:0,0?q="+localizacao.get("X")+","+localizacao.get("Y")+"("+instituicao.getInstituicao()+")"));
                    if (intent.resolveActivity(((FragmentActivity)itemLista.getContext()).getPackageManager()) != null) {
                        ((FragmentActivity)itemLista.getContext()).startActivity(intent);
                    }
                    else{
                        Toast.makeText(itemLista.getContext(), "Não foi possível acessar a localização da instituição", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(itemLista.getContext(), "Não foi possível acessar a localização da instituição", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Agendamento
        myViewHolder.agendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Calendar calendar = Calendar.getInstance();
                int yearCalendar = calendar.get(Calendar.YEAR);
                int monthCalendar = calendar.get(Calendar.MONTH);
                int dayCalendar = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(itemLista.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        YEAR = year;
                        MONTH = month;
                        DAY = dayOfMonth;

                        DatabaseReference reference = ConfiguracaoFirebase.getFirebase().child("Instituicoes");
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot x: dataSnapshot.getChildren()){
                                    UserInstituicao inst = x.getValue(UserInstituicao.class);
                                    if(inst.getEmail().equals(instituicao.getEmail())){

                                        int periodo = DateCustom.calculoPeriodo(DAY, MONTH, YEAR);

                                        //Periodo valido
                                        if(periodo >= 0){

                                            //Salva as informacoes
                                            SharedPreferencesCustom.setDataAgendamento(itemLista.getContext(), SharedPreferencesCustom.nome_Agendamento, inst.getNome());
                                            SharedPreferencesCustom.setDataAgendamento(itemLista.getContext(), SharedPreferencesCustom.endereco_Agendamento, inst.getEndereco());
                                            SharedPreferencesCustom.setDataAgendamento(itemLista.getContext(), SharedPreferencesCustom.telefone_Agendamento, inst.getTelefone());
                                            SharedPreferencesCustom.setDataAgendamento(itemLista.getContext(), SharedPreferencesCustom.data_Agendamento, DAY+"/"+MONTH+"/"+YEAR);
                                            SharedPreferencesCustom.setDataAgendamento(itemLista.getContext(), SharedPreferencesCustom.dia_Agendamento, DAY);
                                            SharedPreferencesCustom.setDataAgendamento(itemLista.getContext(), SharedPreferencesCustom.mes_Agendamento, MONTH);
                                            SharedPreferencesCustom.setDataAgendamento(itemLista.getContext(), SharedPreferencesCustom.ano_Agendamento, YEAR);

                                            Map<String, String> localizacao = instituicao.getLocalizacao();
                                            if(localizacao.containsKey("X") == true && localizacao.containsKey("Y") == true){
                                                SharedPreferencesCustom.setDataAgendamento(itemLista.getContext(), SharedPreferencesCustom.localizacaoX_Agendamento, localizacao.get("X"));
                                                SharedPreferencesCustom.setDataAgendamento(itemLista.getContext(), SharedPreferencesCustom.localizacaoY_Agendamento, localizacao.get("Y"));
                                                Log.i("Teste", localizacao.toString());
                                            }

                                            //Hoje
                                            if(periodo == 0){
                                                Toast.makeText(itemLista.getContext(), "Agendamento para "+inst.getNome()+" para hoje foi feito com sucesso", Toast.LENGTH_SHORT).show();
                                            }

                                            //Amanha
                                            else if(periodo == 1){
                                                Toast.makeText(itemLista.getContext(), "Agendamento para "+inst.getNome()+" para amanhã foi feito com sucesso", Toast.LENGTH_SHORT).show();
                                            }

                                            //Demais dias
                                            else{
                                                Toast.makeText(itemLista.getContext(), "Agendamento para "+inst.getNome()+" para daqui "+periodo+" dias foi feito com sucesso", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                        //Periodo Invalido
                                        else{
                                            Toast.makeText(itemLista.getContext(), "Escolha um dia válido !", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                }, yearCalendar, monthCalendar, dayCalendar);

                datePickerDialog.show();


            }
        });
    }

    /**
     * Retorna o tamanho da Lista de Notificacoes
     * @return tamanho da lista de Notificacoes
     */
    @Override
    public int getItemCount() {
        return listaNotificacoes.size();
    }
}


