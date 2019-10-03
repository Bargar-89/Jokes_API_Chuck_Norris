package com.example.jokesapi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.PhoneStateListener;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class ConnectHandler {

    private final String USER_AGENT = "com.example.jokesapi";
    private final String URL_ADRESS = "http://api.icndb.com/jokes/random/";
    private final String GET = "GET";
    private final String KEY_ID = "id";
    private final String KEY_VALUE = "value";
    private final String KEY_JOKE = "joke";
    private static ArrayList<OneJoke>inputJokes = new ArrayList<>();
    private static int counts = 1;



    //send GET to API
    public ArrayList<OneJoke> sendGet() throws Exception {
        StringBuffer uriString = new StringBuffer();
        uriString.append(URL_ADRESS);
        uriString.append(counts);
        URL obj = new URL(uriString.toString());
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod(GET);
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
     //work with JSON, jokes to array
        if(responseCode==200){
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            StringBuffer buffer = new StringBuffer(2048);
            while ((line = in.readLine()) != null) {buffer.append(line);}
            String message = buffer.toString();
            JSONObject jsonObject = new JSONObject(message);
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_VALUE);
            for (int i = 0; i <jsonArray.length() ; i++) {
                JSONObject temp = jsonArray.getJSONObject(i);
                OneJoke newOnJoke = new OneJoke();
                newOnJoke.setId(temp.getInt(KEY_ID));
                newOnJoke.setTextOfJoke(deleteQuots(temp.getString(KEY_JOKE)));
                newOnJoke.setNumberOfJoke(String.valueOf(i));
                inputJokes.add(newOnJoke);
            }
            in.close();
            con.disconnect();
        }
        return inputJokes;
    }

    public static void setCounts(int counts) {
        ConnectHandler.counts = counts;
    }
    //replace &quot and first empty space;
    private String deleteQuots(String str){
        String respString = str.replaceAll("&quot;","\"");
        if(respString.startsWith(" ")){
            respString.replaceFirst(" ","");
        }
        return respString;
    }
    // internet connection
    public static boolean hasConnection(final Context context){
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected()){ return true;}
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected()){return true;}
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected()){return true;}
        return false;
    }
}
