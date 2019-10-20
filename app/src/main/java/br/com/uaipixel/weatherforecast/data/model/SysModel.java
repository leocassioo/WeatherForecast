package br.com.uaipixel.weatherforecast.data.model;

/**
 * Criado por  Leonardo Figueiredo em 23/02/19.
 */
public class SysModel {

    private int type;
    private int id;
    private double message;
    private String country;
    private int sunrise;
    private int sunset;

    public int getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public double getMessage() {
        return message;
    }

    public String getCountry() {
        return country;
    }

    public int getSunrise() {
        return sunrise;
    }

    public int getSunset() {
        return sunset;
    }
}
