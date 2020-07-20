package com.example.docaodesangue.helper;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

public class DateCustom {

    /**
     * Retorna a data na forma (dd/mm/aaaa)
     * @return retorna a data
     */
    public static String getData(){

        long date = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");
        String data = dateFormat.format(date);

        return data;
    }


    /**
     * Funcao que retorna o periodo entre o dia do agendamento e hoje
     * @param day -> dia
     * @param month -> mes
     * @param year -> ano
     * @return perido entre o dia do agendamento e hoje
     */
    public static int calculoPeriodo(int day, int month, int year){

        //Dia do agendamento
        Calendar diaAgendamento = Calendar.getInstance();
        diaAgendamento.set(Calendar.DAY_OF_MONTH, day);
        diaAgendamento.set(Calendar.MONTH, month);//mes -> 0-11
        diaAgendamento.set(Calendar.YEAR, year);

        //Hoje
        Calendar hoje = Calendar.getInstance();

        Log.i("Teste", "Hoje: "+hoje.getTimeInMillis()/(24 * 60 * 60 * 1000));
        Log.i("Teste", "Hoje: "+diaAgendamento.getTimeInMillis()/(24 * 60 * 60 * 1000));

        //Retorna Dia do agendamento - Hoje
        return  (int)((diaAgendamento.getTimeInMillis() - hoje.getTimeInMillis())/(24 * 60 * 60 * 1000));
    }
}
