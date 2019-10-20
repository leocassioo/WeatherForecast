package br.com.uaipixel.weatherforecast.data.model;

import java.util.ArrayList;

/**
 * Criado por  Leonardo Figueiredo em 24/02/19.
 */
public class BaseForecastModel {

    private int cod;
    private  double message;
    private int cnt;
    private ArrayList<ForecastModel> list;
    private CityModel city;

    public int getCod() {
        return cod;
    }

    public double getMessage() {
        return message;
    }

    public int getCnt() {
        return cnt;
    }

    public ArrayList<ForecastModel> getList() {
        return list;
    }

    public CityModel getCity() {
        return city;
    }
}
