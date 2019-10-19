package br.com.uaipixel.weatherforecast.data.model;

import java.util.List;

/**
 * Criado por  Leonardo Figueiredo em 24/02/19.
 */
public class ForecastModel {

    private long dt;
    private MainForecastModel main;
    private List<WeatherModel> weather;
    private WindModel wind;
    private String dt_txt;

    public long getDt() {
        return dt;
    }

    public MainForecastModel getMain() {
        return main;
    }

    public List<WeatherModel> getWeather() {
        return weather;
    }

    public WindModel getWind() {
        return wind;
    }

    public String getDt_txt() {
        return dt_txt;
    }
}
