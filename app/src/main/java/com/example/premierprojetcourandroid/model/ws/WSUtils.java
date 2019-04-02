package com.example.premierprojetcourandroid.model.ws;

import android.os.SystemClock;
import android.util.Log;

import com.example.premierprojetcourandroid.model.beans.CityBean;
import com.example.premierprojetcourandroid.model.beans.EleveBean;
import com.example.premierprojetcourandroid.model.beans.ResultBean;
import com.example.premierprojetcourandroid.utils.OkHttpUtils;
import com.google.gson.Gson;

import java.util.ArrayList;

public class WSUtils {

    private final static String API_LOGIN = "login=webserviceexemple";
    private final static String API_KEY = "apikey=sof940dd26cf107eabf8bf6827f87c3ca8e8d82546";
    private final static String URL = "http://www.citysearch-api.com/fr/city?" + API_LOGIN + "&" + API_KEY;
    private final static String URL_CP = URL + "&cp=";

    public static EleveBean loadEleveFromWeb() throws Exception {
        SystemClock.sleep(5000); //Attente de 5 secondes

        return new EleveBean("Toto", "Tata");
    }

    public static ArrayList<CityBean> getCities(String cp) throws Exception {
        String json = OkHttpUtils.sendGetOkHttpRequest(URL_CP + cp);

        Log.w("TAG_", "reçu: " + json);

        //Parser un flux ou un String contenant du JSON dont le 1er élément est un Objet
        ResultBean resultBean = new Gson().fromJson(json, ResultBean.class);//Parser avec Gson

        //Erreur
        if (resultBean.getErrors() != null) {
            throw new Exception(resultBean.getErrors().getMessage());
        } else {
            return resultBean.getResults();
        }
    }
}
