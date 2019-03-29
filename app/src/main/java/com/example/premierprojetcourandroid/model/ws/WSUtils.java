package com.example.premierprojetcourandroid.model.ws;

import android.os.SystemClock;

import com.example.premierprojetcourandroid.model.beans.CityBean;
import com.example.premierprojetcourandroid.model.beans.EleveBean;

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
        ArrayList<CityBean> resultat = new ArrayList<>();
        String UsedUrl = URL_CP + cp;

     /*   //Création de l'objet
        Gson gson= new Gson();
        //Parser un flux ou un String contenant du JSON dont le 1er élément est un Objet
        ResultBean result= gson.fromJson(monStringJson, ResultBean.class);
        //Parser un flux ou un String contenant du JSON dont le premier élément est une List
        ArrayList<CityBean> list= gson.fromJson(monStringJson,
                newTypeToken<ArrayList<MessageBean>>(){}.getType());*/
        return resultat;
    }
}
