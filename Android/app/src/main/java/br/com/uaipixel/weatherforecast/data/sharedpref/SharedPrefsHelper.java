package br.com.uaipixel.weatherforecast.data.sharedpref;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.uaipixel.weatherforecast.data.model.AllCitiesModel;
import br.com.uaipixel.weatherforecast.data.model.BaseForecastModel;
import br.com.uaipixel.weatherforecast.data.model.BaseWeatherModel;

/**
 * Criado por  Leonardo Figueiredo em 28/02/19.
 */

public class SharedPrefsHelper {
    private SharedPreferences mSharedPref;
    private Context mContext;
    private SharedPreferences.Editor mEditor;
    private Gson mGson;

    @SuppressLint("CommitPrefEdits")
    public SharedPrefsHelper(Context context) {
        mContext = context;
        mSharedPref = PreferenceManager.getDefaultSharedPreferences(mContext);
        mEditor = mSharedPref.edit();
        mGson = new Gson();
    }

    //records whether the app has had any access
    public void setFirstAccess(String isAccessed) {
        try {
            mEditor.putString("primeiroAcesso", isAccessed);
            mEditor.commit();
        } catch (Exception err) {
            Log.d("Erro", "Erro ao gravar: " + err.getMessage());
        }
    }

    //check if the application has already had any access
    public String getFirstAccess() {
        String firstAccess = "";
        try {
            firstAccess = mSharedPref.getString("primeiroAcesso", "");
        } catch (Exception err) {
            Log.d("Erro", "Error: " + err.getMessage());
        }
        return firstAccess;
    }

    //save the last weather forecast
    public void saveWeather(BaseWeatherModel weather) {
        mEditor.putString("weatherLocal", mGson.toJson(weather));
        mEditor.commit();
    }

    //retrieve the last weather forecast
    public BaseWeatherModel getWeatherLocal() {
        BaseWeatherModel weather = new BaseWeatherModel();
        try {
            if (mSharedPref.getString("weatherLocal", "") != null) {
                weather = mGson.fromJson(mSharedPref.getString("weatherLocal", ""), BaseWeatherModel.class);
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return weather;
    }

    //save the last 5 day forecast
    public void saveForecast(BaseForecastModel forecast) {
        mEditor.putString("forecastLocal", mGson.toJson(forecast));
        mEditor.commit();
    }

    //retrieve the last 5 day forecast
    public BaseForecastModel getForecast() {
        BaseForecastModel forecast = new BaseForecastModel();
        try {
            if (mSharedPref.getString("weatherLocal", "") != null) {
                forecast = mGson.fromJson(mSharedPref.getString("forecastLocal", ""), BaseForecastModel.class);
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return forecast;
    }

    //save the last default location
    public void setCityDefault(String cityDefault) {
        mEditor.putString("cityDefault", mGson.toJson(cityDefault));
        mEditor.commit();
        mEditor.apply();
    }

    //retrieve the last default location
    public String getCityDefault() {
        String cityDefault = "";
        try {
            cityDefault = mSharedPref.getString("cityDefault", "").replace("\"", "");
        } catch (Exception err) {
            Log.d("Error", "Error: " + err.getMessage());
        }
        return cityDefault;
    }

    //record local weather forecast for other cities
    public void setAllCities(List<BaseWeatherModel> listAllCities) {
        mEditor.putString("listAllCities", mGson.toJson(listAllCities));
        mEditor.commit();
    }

    //to recover local weather forecast for other cities
    public List<BaseWeatherModel> getAllCities() {
        List<BaseWeatherModel> allCities = new ArrayList<>();
        try {
            if (mSharedPref.getString("listAllCities", "") != null) {
                allCities = Arrays.asList(mGson.fromJson(mSharedPref.getString("listAllCities", ""), BaseWeatherModel[].class));
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return allCities;
    }
}
