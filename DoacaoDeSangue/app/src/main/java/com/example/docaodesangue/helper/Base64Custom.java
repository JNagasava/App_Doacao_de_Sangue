package com.example.docaodesangue.helper;

import android.util.Base64;

public class Base64Custom {

    /**
     * Funcao que transforma o texto em base 64
     * @param texto String a ser transformada
     */
    public static String encodeBase64(String texto){

        return Base64.encodeToString(texto.getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");
    }

    /**
     * Funcao que "traduz" o texto de base 64
     * @param texto String a ser traduzida
     */
    public static String decodeBase64(String texto){

        return new String(Base64.decode(texto, Base64.DEFAULT));
    }
}
