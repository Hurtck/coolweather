package com.coolweather.app.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.coolweather.app.db.CoolWeatherDB;
import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Locale;

import static android.content.ContentValues.TAG;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by 47321 on 2016/9/27 0027.
 */

public class Utility {

    /**
     *
     * @param coolWeatherDB
     * @param response
     * @return
     * 解析和处理服务器返回的省数据
     */

    public synchronized static boolean handleProvincesResponse(CoolWeatherDB coolWeatherDB,String response){
        if (!TextUtils.isEmpty(response)){
            String[] allProvinces = response.split(",");
            if (allProvinces != null && allProvinces.length > 0){
                for (String p : allProvinces){
                    String[] array= p.split("\\|");
                    Province province = new Province();
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);
                    coolWeatherDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 解析服务器返回的市级数据
     * @param coolWeatherDB
     * @param response
     * @param provinceId
     * @return
     */

    public  synchronized static boolean handleCitiesResponse(CoolWeatherDB coolWeatherDB,String response,int provinceId){
        if (!TextUtils.isEmpty(response)){
            String[] allcities = response.split(",");
            if (allcities != null&& allcities.length > 0){
                for (String p : allcities){
                    String [] array = p.split("\\|");
                    City city = new City();
                    city.setProvinceId(provinceId);
                    city.setCityName(array[1]);
                    city.setCityCode(array[0]);
                    coolWeatherDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 解析服务器返回的县级数据
     */
    public  synchronized static boolean handleCountyResponse(CoolWeatherDB coolWeatherDB,String response,int cityId){
        if (!TextUtils.isEmpty(response)){
            String[] allcounties = response.split(",");

            if (allcounties != null&& allcounties.length > 0){
                for (String p : allcounties){
                    String [] array = p.split("\\|");
                    County county = new County();
                    county.setCityId(cityId);
                    county.setCountyName(array[1]);
                    county.setCountyCode(array[0]);
                    coolWeatherDB.savecounty(county);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 解析服务器返回的JSON数据 并将数据存储到本地
     */
    public  static void handleWeatherRsponse(Context context,String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
            String cityName = weatherInfo.getString("city");
            String weatherCode = weatherInfo.getString("cityid");
            String temp1 = weatherInfo.getString("temp1");
            String temp2 = weatherInfo.getString("temp2");
            String weatherDesp = weatherInfo.getString("weather");
            String publishTime = weatherInfo.getString("ptime");
            saveWeatherInfo(context,cityName,weatherCode,temp1,temp2,weatherDesp,publishTime);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    /**
     * 将所有数据存储到SharePerences
     */
    public static void saveWeatherInfo(Context context,String cityname,String weatherCode,String temp1,String temp2,String weatherDesp,String publishTime){
       // SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日",Locale.CHINA);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日",Locale.CHINA);
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean("city_selected",true);
        editor.putString("city_name",cityname);
        editor.putString("weather_code",weatherCode);
        editor.putString("temp1",temp1);
        editor.putString("temp2",temp2);
        editor.putString("weather_desp",weatherDesp);
        editor.putString("publish_time",publishTime);
        editor.putString("current_date",sdf.format(new Date()));
        editor.commit();

    }

}
