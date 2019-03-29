package com.example.premierprojetcourandroid.model.ws;

import android.os.SystemClock;

import com.example.premierprojetcourandroid.model.beans.EleveBean;

public class WSUtils {


    public static EleveBean loadEleveFromWeb() throws Exception {
        SystemClock.sleep(5000); //Attente de 5 secondes

        return new EleveBean("Toto", "Tata");
    }

}
