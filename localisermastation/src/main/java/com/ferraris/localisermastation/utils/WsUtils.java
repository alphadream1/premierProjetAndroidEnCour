package com.ferraris.localisermastation.utils;

import android.util.Log;

import com.ferraris.localisermastation.bean.StationBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class WsUtils {

    private final static String URL = "https://api.jcdecaux.com/vls/v1/stations?contract=Toulouse&apiKey=2a1b07b2a523f81188fe34e348206a57ffa6f2a7";

    public static ArrayList<StationBean> getStations() throws Exception {
        String json = OkHttpUtils.sendGetOkHttpRequest(URL);
        Gson gson = new Gson();

        // test
        Log.w("Tag_", json);

        ArrayList<StationBean> list = gson.fromJson(json,
                new TypeToken<ArrayList<StationBean>>() {
                }.getType());

        if (list == null) {
            Log.w("Tag_", "Une erreur est survenu");
            throw new Exception("Erreur lors de la récupération des stations");
        } else {
            //on retire la station perdue
            for (int i = list.size() - 1; i >= 0; i--) {
                StationBean stationBean = list.get(i);
                if (stationBean.getNumber() == 1033) {
                    list.remove(i);
                }
            }

            return list;
        }

    }
}
