package br.com.uaipixel.weatherforecast.data.model;

import java.util.List;

/**
 * Criado por  Leonardo Figueiredo em 27/02/19.
 */
public class AllCitiesModel {

    private int cnt;
    private List<BaseWeatherModel> list;

    public int getCnt() {
        return cnt;
    }

    public List<BaseWeatherModel> getList() {
        return list;
    }
}
