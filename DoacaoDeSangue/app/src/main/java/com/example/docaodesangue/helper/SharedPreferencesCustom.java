package com.example.docaodesangue.helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

public class SharedPreferencesCustom {


    //Informacoes do doador
    public final static String FILE_USER_DOADOR = "Arquivo_Doador";
    public final static String nomeDoador = "nomeDoador";
    public final static String enderecoDoador = "enderecoDoador";
    public final static String tipoSangueDoador = "tipoSangueDoador";
    public final static String localizacaoXDoador = "localizacaoX";
    public final static String localizacaoYDoador = "localizacaoY";
    public final static String ERRO = "ERRO";
    public static String nome;
    public static String endereco;
    public static String tipoSangue;
    public static String localizacaoX;
    public static String localizacaoY;
    //---------------------------------------------------------------

    //Informcaoes da instituicao
    public final static String FILE_USER_INSTITUICAO ="Arquivo_Instituicao";
    public final static String emailInstituicao = "emailInstituicao";
    public static String email;
    //---------------------------------------------------------------


    //Informacoes de agendamento
    public final static String FILE_AGENDAMENTO = "Arquivo_Agendamento";
    public final static String nome_Agendamento = "nomeAgendamento";
    public final static String endereco_Agendamento = "enderecoAgendamento";
    public final static String telefone_Agendamento = "telefoneAgendamento";
    public final static String data_Agendamento = "dataAgendamento";
    public final static String dia_Agendamento = "diaAgendamento";
    public final static String mes_Agendamento = "mesAgendamento";
    public final static String ano_Agendamento = "anoAgendamento";
    public final static String localizacaoX_Agendamento = "localizacaoXAgendamento";
    public final static String localizacaoY_Agendamento = "localizacaoYAgendamento";
    public static String nomeAgendamento;
    public static String enderecoAgendamento;
    public static String telefoneAgendamento;
    public static String dataAgendamento;
    public static int diaAgendamento;
    public static int mesAgendamento;
    public static int anoAgendamento;
    public static String localizacaoXAgendamento;
    public static String localizacaoYAgendamento;
    //---------------------------------------------------------------

    //Informacoes do historico
    public final static String FILE_HISTORICO = "Arquivo_Historico";
    public final static String nome_Historico = "nomeHistorico";
    public final static String instituicao_Historico = "instituicaoHistorico";
    public final static String data_Historico = "dataHistorico";
    public final static String quantidade_Historico = "quantidadeHistorico";
    //---------------------------------------------------------------

    //Shared Preferences do usuario
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;
    //---------------------------------------------------------------

    /**
     * Funcao que remove todos as informacoes de um arquivo
     * @param context Contexto -> Activity ou view.getContext()
     * @param file String -> nome do arquivo
     */
    public static void clearSharedPreferences(Context context, String file){
        SharedPreferences preferences =  context.getSharedPreferences(file, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * Funcao que inicia o SharedPreferences, e verifica se existe arquivos no celular
     * @param context Contexto -> Activity ou view.getContext()
     * @return true se existem os arquivos(valores) no celular, falso se faltam arquivos(valores)
     */
    public static boolean StartSharedPreferencesDoador(Context context){

        SharedPreferences preferences = context.getSharedPreferences(FILE_USER_DOADOR, 0);

        if(preferences.contains(nomeDoador) == false){
            return false;
        }

        else if(preferences.contains(enderecoDoador) == false){
            return false;
        }

        else if(preferences.contains(tipoSangueDoador) == false){
            return false;
        }

        else if(preferences.contains(localizacaoXDoador) == false){
            return false;
        }

        else if(preferences.contains(localizacaoYDoador) == false){
            return false;
        }

        nome = preferences.getString(nomeDoador, ERRO);
        endereco = preferences.getString(enderecoDoador, ERRO);
        tipoSangue = preferences.getString(tipoSangueDoador, ERRO);
        localizacaoX = preferences.getString(localizacaoXDoador, ERRO);
        localizacaoY = preferences.getString(localizacaoYDoador, ERRO);

        return true;
    }

    /**
     * Funcao que adiciona uma informacao a na "memoria" do celular
     * @param context Contexto -> Activity ou view.getContext()
     * @param key Chave(String)
     * @param value Valor(String)
     */
    public static void setDataDoador(Context context, String key, String value){
        preferences = context.getSharedPreferences(FILE_USER_DOADOR, 0);
        editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * Funcao que verifica se existe informacao da instituicao(email do usuario)
     * @param context Contexto -> Activity ou view.getContext()
     * @return true se existe informacao, false se nao existe informacao
     */
    public static boolean StartSharedPreferencesInstituicao(Context context){

        preferences = context.getSharedPreferences(FILE_USER_INSTITUICAO, 0);

        if(preferences.contains(emailInstituicao) == false){
            return false;
        }

        email = preferences.getString(emailInstituicao, ERRO);

        return true;
    }

    /**
     * Funcao que insere uma informacao sobre a instituicao na "memoria" do celular
     * @param context Contexto -> Activity ou view.getContext()
     * @param key Chave(String)
     * @param value Valor(String)
     */
    public static void setDataInstituicao(Context context, String key, String value){
        preferences = context.getSharedPreferences(FILE_USER_INSTITUICAO, 0);
        editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * Funcao que retorna o email do usuario
     * @param context Contexto -> Activity ou view.getContext()
     * @return String -> email da instituicao
     */
    public static String getEmailInstituicao(Context context){
        return context.getSharedPreferences(FILE_USER_INSTITUICAO, 0).getString(emailInstituicao, ERRO);
    }

    /**
     * Funcao que verifica se existe informacao da de agendamento do doador
     * @param context Contexto -> Activity ou view.getContext()
     * @return true se existe,  false se nao existe
     */
    public static boolean StartSharedPreferencesAgendamento(Context context){

        preferences = context.getSharedPreferences(FILE_AGENDAMENTO, 0);

        if(preferences.contains(nome_Agendamento) == false){
            return false;
        }

        if(preferences.contains(endereco_Agendamento) == false){
            return false;
        }

        if(preferences.contains(telefone_Agendamento) == false){
            return false;
        }

        if(preferences.contains(data_Agendamento) == false){
            return false;
        }

        if(preferences.contains(dia_Agendamento) == false){
            return false;
        }

        if(preferences.contains(mes_Agendamento) == false){
            return false;
        }

        if(preferences.contains(ano_Agendamento) == false){
            return false;
        }

        if(preferences.contains(localizacaoX_Agendamento) == false){
            return false;
        }

        if(preferences.contains(localizacaoY_Agendamento) == false){
            return false;
        }

        nomeAgendamento = preferences.getString(nome_Agendamento, ERRO);
        enderecoAgendamento = preferences.getString(endereco_Agendamento, ERRO);
        telefoneAgendamento = preferences.getString(telefone_Agendamento, ERRO);
        dataAgendamento = preferences.getString(data_Agendamento, ERRO);
        diaAgendamento = preferences.getInt(dia_Agendamento, -1);
        mesAgendamento = preferences.getInt(mes_Agendamento, -1);
        anoAgendamento = preferences.getInt(ano_Agendamento, -1);
        localizacaoXAgendamento = preferences.getString(localizacaoX_Agendamento, ERRO);
        localizacaoYAgendamento = preferences.getString(localizacaoY_Agendamento, ERRO);

        return true;
    }

    /**
     * Funcao que adiciona uma informacao sobre o agendamento do doador na "memoria" do celular
     * @param context Contexto -> Activity ou view.getContext()
     * @param key Chave(String)
     * @param value Valor(String)
     */
    public static void setDataAgendamento(Context context, String key, String value){

        preferences = context.getSharedPreferences(FILE_AGENDAMENTO, 0);
        editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * Funcao que adiciona uma informacao sobre o agendamento do doador na "memoria" do celular
     * @param context Contexto -> Activity ou view.getContext()
     * @param key Chave(String)
     * @param value Valor(int)
     */
    public static void setDataAgendamento(Context context, String key, int value){

        preferences = context.getSharedPreferences(FILE_AGENDAMENTO, 0);
        editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * Funcao que verifica se existe um historico de doacao no celular
     * @param context Contexto -> Activity ou view.getContext()
     * @return true se existe, false se nao existe
     */
    public static boolean StartSharedPreferencesHistorico(Context context){

        preferences = context.getSharedPreferences(FILE_HISTORICO, 0);

        if(preferences.contains(quantidade_Historico) == false){
            return false;
        }

        return true;
    }

    /**
     * Funcao que retorna a quantidade de itens do historico
     * @param context Contexto -> Activity ou view.getContext()
     * @return quantidade de itens do historico
     */
    public static int getQuantidadeHistorico(Context context){

        preferences = context.getSharedPreferences(FILE_HISTORICO, 0);
        return preferences.getInt(quantidade_Historico, 0);
    }

    /**
     * Funcao que adiciona um novo item no historico
     * @param context Contexto -> Activity ou view.getContext()
     * @param nome nome do doador
     * @param instituicao instituicao em que doou
     * @param data data da doacao
     */
    public static void addHistorico(Context context, String nome, String instituicao, String data){

        preferences = context.getSharedPreferences(FILE_HISTORICO, 0);
        int quantidade = preferences.getInt(quantidade_Historico, 0);
        editor = preferences.edit();
        editor.putString(nome_Historico+quantidade, nome);
        editor.putString(instituicao_Historico+quantidade, instituicao);
        editor.putString(data_Historico+quantidade, data);
        editor.putInt(quantidade_Historico, ++quantidade);
        editor.commit();

    }

    /**
     * Funcao que retorna um nome do historico a partir do id
     * @param context Contexto -> Activity ou view.getContext()
     * @param id id do item
     * @return nome do doador, ou ERRO se nao existir o item
     */
    public static String getNomeHistorico(Context context, int id){

        preferences = context.getSharedPreferences(FILE_HISTORICO, 0);
        return preferences.getString(nome_Historico+id, ERRO);
    }

    /**
     * Funcao que retorna um nome da instituicao do historico a partir do id
     * @param context Contexto -> Activity ou view.getContext()
     * @param id id do item
     * @return nome da instituicao, ou ERRO se nao existir o item
     */
    public static String getInstituicaoHistorico(Context context, int id){

        preferences = context.getSharedPreferences(FILE_HISTORICO, 0);
        return preferences.getString(instituicao_Historico+id, ERRO);
    }

    /**
     * Funcao que retorna uma data do historico a partir do id
     * @param context Contexto -> Activity ou view.getContext()
     * @param id id do item
     * @return data da doacao, ou ERRO se nao existir o item
     */
    public static String getDataHistorico(Context context, int id){

        preferences = context.getSharedPreferences(FILE_HISTORICO, 0);
        return  preferences.getString(data_Historico+id, ERRO);
    }

}
