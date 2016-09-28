package com.coolweather.app.activity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coolweather.app.R;
import com.coolweather.app.util.HttpCallbackListener;
import com.coolweather.app.util.HttpUtil;
import com.coolweather.app.util.Utility;

public class WheatherActivity extends AppCompatActivity {

    private LinearLayout weatherInfoLayout;

    /**
     * 用于显示城市名
     */
    private TextView cityNameText;
    /**
     * 用于显示发布时间
     */
    private  TextView pubilshText;

    /**
     * 用于显示天气信息
     */
    private  TextView weatherDespText;

    /**
     * 用于显示气温1
     */
    private  TextView temp1Textl;

    /**
     * 用于显示气温2
     */

    private  TextView temp2Text;
    /**
     * 用于显示当日日期
     */
    private TextView currentDateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_wheather);

        weatherInfoLayout =(LinearLayout)findViewById(R.id.weather_info_layout);
        cityNameText =(TextView)findViewById(R.id.adress_name);
        pubilshText =(TextView)findViewById(R.id.publish_txt);
        temp1Textl =(TextView)findViewById(R.id.temp1);
        temp2Text =(TextView)findViewById(R.id.temp2);
        currentDateText =(TextView)findViewById(R.id.current_date);
        String countyCode = getIntent().getStringExtra("county_code");
        if (!TextUtils.isEmpty(countyCode)){
            pubilshText.setText("同步中...");
            weatherInfoLayout.setVisibility(View.INVISIBLE);
            cityNameText.setVisibility(View.INVISIBLE);
            queryWetherCode(countyCode);
        }else{
            showWeather();
        }
    }
    /**
     * 查询县级代号对应天气
     */
    private void queryWetherCode(String countyCode){
        String adress = "http://www.weather.com.cn/data/list3/city"+countyCode+".xml";
        querFromServer(adress,"countyCode");
    }
    /**
     * 查询天气代号对应的天气
     */
    private void queryWeatherInfo(String weatherCode){
        String adress = "http://www.weather.com.cn/data/cityinfo/"+weatherCode+".html";
        querFromServer(adress,"weatherCode");

    }

    private void querFromServer(String adress,final String type){
        HttpUtil.sendHttpRequest(adress, new HttpCallbackListener() {
            @Override
            public void Finsh(String response) {
                if("countyCode".equals(type)){
                    if (TextUtils.isEmpty(response)){
                        String[] array = response.split("\\|");
                        if (array != null && array.length == 2){
                            String weatherCode = array[1];
                            queryWeatherInfo(weatherCode);
                        }
                    }
                }else if("weatherCode".equals(type)){
                    Utility.handleWeatherRsponse(WheatherActivity.this,response);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            showWeather();
                        }
                    });
                }
            }

            @Override
            public void Error(Exception e) {

                pubilshText.setText("同步失败");
            }
        });
    }

    private void showWeather(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        cityNameText.setText(prefs.getString("city_name",""));
        temp1Textl.setText(prefs.getString("temp1",""));
        temp2Text.setText(prefs.getString("temp2",""));
        weatherDespText.setText(prefs.getString("weather_desp",""));
        pubilshText.setText("今天"+prefs.getString("publish_time","")+"发布");
        currentDateText.setText(prefs.getString("current_date",""));
        weatherInfoLayout.setVisibility(View.VISIBLE);
        cityNameText.setVisibility(View.VISIBLE);

    }
}
