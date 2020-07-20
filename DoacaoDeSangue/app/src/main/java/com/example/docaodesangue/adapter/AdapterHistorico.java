package com.example.docaodesangue.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.docaodesangue.R;
import com.example.docaodesangue.model.HistoricoDoador;

import java.util.List;

public class AdapterHistorico extends RecyclerView.Adapter<AdapterHistorico.MyViewHolder> {

    //Historico de doacoes
    private List<HistoricoDoador> listaHistorico;

    //Componente -> """tela""" -> context ...
    private View itemLista;

    /**
     * Configura o campo do RecyclerView
     * @param myViewHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {

        HistoricoDoador historico = listaHistorico.get(i);
        myViewHolder.instituicao.setText(historico.getNomeInstituicao());
        myViewHolder.data.setText("Data: "+historico.getData());
        myViewHolder.doador.setText("Doador: "+historico.getDoador());
    }

    /**
     * Retorna o tamanho da lista de historico
     * @return
     */
    @Override
    public int getItemCount() {
        return listaHistorico.size();
    }

    /**
     * Classe do MyViewHolder
     */
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView instituicao;
        TextView data;
        TextView doador;

        public MyViewHolder(View itemView){
            super(itemView);
            instituicao = itemView.findViewById(R.id.txtInstituicaoHistorico);
            data = itemView.findViewById((R.id.txtDataHistorico));
            doador = itemView.findViewById(R.id.txtDoadorHistorico);
        }
    }

    /**
     * Configura a lista
     * @param lista
     */
    public AdapterHistorico(List<HistoricoDoador> lista){
        this.listaHistorico = lista;
    }

    /**
     * Cria o MyViewHolder
     * @param viewGroup
     * @param i
     * @return
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        itemLista = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_historico, viewGroup, false);
        return  new MyViewHolder(itemLista);
    }

}
