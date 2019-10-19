package br.com.uaipixel.weatherforecast.ui.allCities;

import java.util.List;

import br.com.uaipixel.weatherforecast.data.model.BaseWeatherModel;

/**
 * Criado por  Leonardo Figueiredo em 02/03/19.
 */
interface ContractAllCities {
    interface Presenter {
        void getWeatherAllCities();
        List<BaseWeatherModel> getAllCities();
        void setCityDefault(String city);
    }

    interface View {
        void showDialog();
        void hiddenDialog();
        void showError();
        void loadAllCities(List<BaseWeatherModel> mListWeatherAllCities);
    }
}
