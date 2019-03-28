package com.example.premierprojetcourandroid;

import android.os.SystemClock;

public class WSUtils {


    public static EleveBean loadEleveFromWeb() throws Exception {
        SystemClock.sleep(5000); //Attente de 5 secondes

        return new EleveBean("Toto", "Tata");
    }

}
