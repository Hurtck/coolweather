package com.coolweather.app.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 47321 on 2016/9/27 0027.
 */

public class HttpUtil {

    public static void sendHttpRequest(final String adress,final HttpCallbackListener listener){

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try{
                    URL url = new URL(adress);
                    connection =(HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine())!=null){
                        response.append(line);
                    }
                    if(listener != null){
                        listener.Finsh(response.toString());
                    }
                }catch (Exception e){
                    if (listener != null){
                        listener.Error(e);
                    }

                }finally {
                    if (connection != null){
                        connection.disconnect();
                    }
                }


            }
        }).start();
    }
}
