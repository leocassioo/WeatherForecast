package br.com.uaipixel.weatherforecast.data.model;

/**
 * Criado por  Leonardo Figueiredo em 24/02/19.
 */
public class DaysModel {

    private String date;
    private double temp_max;
    private double temp_min;
    private String icon;

    public DaysModel(String dt_txt, double tempMax, double tempMim, String icon) {
        this.date = dt_txt;
        this.temp_max = tempMax;
        this.temp_min = tempMim;
        this.icon = icon;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(double temp_max) {
        this.temp_max = temp_max;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(double temp_min) {
        this.temp_min = temp_min;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
