package com.example.docaodesangue.adapter;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.location.Address;
import android.location.Geocoder;
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
import com.example.docaodesangue.helper.DateCustom;
import com.example.docaodesangue.helper.SharedPreferencesCustom;
import com.example.docaodesangue.model.InstituicoesDoacao;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AdapterInstituicoes extends RecyclerView.Adapter<AdapterInstituicoes.MyViewHolder> {

    //Instituicoes para doar sangue
    private List<InstituicoesDoacao> listaInstituicoes;

    //Componente -> """tela""" -> context ...
    private View itemLista;

    /**
     * Informacoes do campo do RecyclerView
     */
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nome;
        TextView estadoCidade;
        TextView endereco;
        TextView telefone;
        Button mapa;
        Button agendar;

        /**
         * Construtor do campo do RecyclerView
         * @param itemView
         */
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.txtInstituicao);
            estadoCidade = itemView.findViewById(R.id.txtEstadoCidade);
            endereco = itemView.findViewById(R.id.txtEndereco);
            telefone = itemView.findViewById(R.id.txtTelefone);
            mapa = itemView.findViewById(R.id.btnMapa);
            agendar = itemView.findViewById(R.id.btnAgendar);

        }
    }

    /**
     * Construtor do Adapter
     * @param lista Lista de Instituicoes e suas informacoes
     */
    public AdapterInstituicoes(List<InstituicoesDoacao> lista){
        this.listaInstituicoes = lista;
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
        itemLista = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_instituicoes_doacao, viewGroup, false);
        return  new MyViewHolder(itemLista);
    }

    /**
     * Funcoes a serem executadas para cada campo
     * @param myViewHolder campo
     * @param i posicao do campo
     */
    @Override
    //@NonNull RecyclerView.ViewHolder viewHolder
    public void onBindViewHolder(final MyViewHolder myViewHolder, int i) {

        final InstituicoesDoacao instituicao = listaInstituicoes.get(i);
        myViewHolder.nome.setText(instituicao.getNome());
        myViewHolder.estadoCidade.setText(instituicao.getEstado()+" - "+instituicao.getCidade());
        myViewHolder.endereco.setText(instituicao.getEndereco());
        myViewHolder.telefone.setText(instituicao.getTelefone());

        //Verifica no mapa a localizacao da instituicao
        myViewHolder.mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> localizacao = new HashMap<>();
                localizacao = instituicao.getLocalizacao();
                if(localizacao.containsKey("X") == true && localizacao.containsKey("Y") == true){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("geo:0,0?q="+localizacao.get("X")+","+localizacao.get("Y")+"("+instituicao.getNome()+")"));
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

                        int periodo = DateCustom.calculoPeriodo(dayOfMonth, month, year);

                        //Periodo valido
                        if(periodo >= 0){

                            //Salva as informacoes
                            SharedPreferencesCustom.setDataAgendamento(itemLista.getContext(), SharedPreferencesCustom.nome_Agendamento, instituicao.getNome());
                            SharedPreferencesCustom.setDataAgendamento(itemLista.getContext(), SharedPreferencesCustom.endereco_Agendamento, instituicao.getEndereco());
                            SharedPreferencesCustom.setDataAgendamento(itemLista.getContext(), SharedPreferencesCustom.telefone_Agendamento, instituicao.getTelefone());
                            SharedPreferencesCustom.setDataAgendamento(itemLista.getContext(), SharedPreferencesCustom.data_Agendamento, dayOfMonth+"/"+month+"/"+year);
                            SharedPreferencesCustom.setDataAgendamento(itemLista.getContext(), SharedPreferencesCustom.dia_Agendamento, dayOfMonth);
                            SharedPreferencesCustom.setDataAgendamento(itemLista.getContext(), SharedPreferencesCustom.mes_Agendamento, month);
                            SharedPreferencesCustom.setDataAgendamento(itemLista.getContext(), SharedPreferencesCustom.ano_Agendamento, year);

                            Map<String, String> localizacao = instituicao.getLocalizacao();
                            if(localizacao.containsKey("X") == true && localizacao.containsKey("Y") == true){
                                SharedPreferencesCustom.setDataAgendamento(itemLista.getContext(), SharedPreferencesCustom.localizacaoX_Agendamento, localizacao.get("X"));
                                SharedPreferencesCustom.setDataAgendamento(itemLista.getContext(), SharedPreferencesCustom.localizacaoY_Agendamento, localizacao.get("Y"));
                                Log.i("Teste", localizacao.toString());
                            }

                            //Hoje
                            if(periodo == 0){
                                Toast.makeText(itemLista.getContext(), "Agendamento para "+instituicao.getNome()+" para hoje foi feito com sucesso", Toast.LENGTH_SHORT).show();
                            }

                            //Amanha
                            else if(periodo == 1){
                                Toast.makeText(itemLista.getContext(), "Agendamento para "+instituicao.getNome()+" para amanhã foi feito com sucesso", Toast.LENGTH_SHORT).show();
                            }

                            //Demais dias
                            else{
                                Toast.makeText(itemLista.getContext(), "Agendamento para "+instituicao.getNome()+" para daqui "+periodo+" dias foi feito com sucesso", Toast.LENGTH_SHORT).show();
                            }

                        }
                        //Periodo Invalido
                        else{
                            Toast.makeText(itemLista.getContext(), "Escolha um dia válido !", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, yearCalendar, monthCalendar, dayCalendar);

                datePickerDialog.show();
            }
        });
    }

    /**
     * Retorna o tamanho da Lista de Instituicoes
     * @return tamanho da lista de instituicoes
     */
    @Override
    public int getItemCount() {
       return listaInstituicoes.size();
    }



}
