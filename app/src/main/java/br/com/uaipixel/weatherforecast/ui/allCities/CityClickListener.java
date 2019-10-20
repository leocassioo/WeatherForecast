package br.com.uaipixel.weatherforecast.ui.allCities;

import android.view.View;

import br.com.uaipixel.weatherforecast.data.model.BaseWeatherModel;

/**
 * Criado por  Leonardo Figueiredo em 23/02/19.
 */
public interface CityClickListener {
    void cityClicked(BaseWeatherModel weather);
}
