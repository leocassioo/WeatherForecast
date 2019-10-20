package br.com.uaipixel.weatherforecast.data.model;

/**
 * Criado por  Leonardo Figueiredo em 24/02/19.
 */
public class CityModel {

    private int id;
    private String name;
    private CoordModel coord;
    private String country;
    private long population;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CoordModel getCoord() {
        return coord;
    }

    public void setCoord(CoordModel coord) {
        this.coord = coord;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }
}
