package com.coolweather.app.util;

import android.text.TextUtils;
import android.util.Log;

import com.coolweather.app.db.CoolWeatherDB;
import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;

import static android.content.ContentValues.TAG;

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

}
