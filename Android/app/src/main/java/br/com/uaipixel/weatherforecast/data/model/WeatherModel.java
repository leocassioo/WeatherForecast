package br.com.uaipixel.weatherforecast.data.model;

/**
 * Criado por  Leonardo Figueiredo em 23/02/19.
 */
public class WeatherModel {
    private int id;
    private String main;
    private String description;
    private String icon;

    public int getId() {
        return id;
    }

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }
}
