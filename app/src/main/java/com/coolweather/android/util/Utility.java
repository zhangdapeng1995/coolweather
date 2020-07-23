package com.coolweather.android.util;

import android.text.TextUtils;
import com.coolweather.android.db.City;
import com.coolweather.android.db.County;
import com.coolweather.android.db.Province;
import com.coolweather.android.gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

public class Utility {
    public static boolean handleProvinceResponse(String response){
        if(!TextUtils.isEmpty(response)){
            try {
                JSONArray provinceArray=new JSONArray(response);
                for (int i = 0; i < provinceArray.length(); i++) {
                    JSONObject provinceObj = provinceArray.getJSONObject(i);
                    Province province=new Province();
                    province.setProvinceName(provinceObj.getString("name"));
                    province.setProvinceCode(provinceObj.getInt("id"));
                    province.save();
                }
                return true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean handleCityResponse(int provinceId,String response){
        if(!TextUtils.isEmpty(response)){
            try{
                JSONArray cityArray=new JSONArray(response);
                for (int i = 0; i < cityArray.length(); i++) {
                    JSONObject cityObj=cityArray.getJSONObject(i);
                    City city=new City();
                    city.setCityName(cityObj.getString("name"));
                    city.setCityCode(cityObj.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    public static boolean handleCountyResponse(int cityId,String response){
        if(!TextUtils.isEmpty(response)){
            try{
                JSONArray countyArray=new JSONArray(response);
                for (int i = 0; i < countyArray.length(); i++) {
                    JSONObject countyObj=countyArray.getJSONObject(i);
                    County county=new County();
                    county.setCountyName(countyObj.getString("name"));
                    county.setWeatherId(countyObj.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    public static Weather handleWeatherResponse(String response){
        try{
            JSONObject weatherObj=new JSONObject(response);
            JSONArray jsonArray=weatherObj.getJSONArray("HeWeather");
            String weatherContent=jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent,Weather.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }
}