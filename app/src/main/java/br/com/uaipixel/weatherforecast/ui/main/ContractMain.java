package br.com.uaipixel.weatherforecast.ui.main;


import java.util.List;

import br.com.uaipixel.weatherforecast.data.model.BaseForecastModel;
import br.com.uaipixel.weatherforecast.data.model.BaseWeatherModel;
import br.com.uaipixel.weatherforecast.data.model.DaysModel;
import br.com.uaipixel.weatherforecast.data.model.ForecastModel;

/**
 * Criado por  Leonardo Figueiredo em 02/03/19.
 */
interface ContractMain {
    interface Presenter {
        void getWeather();
        void getForecast();
        boolean isFirstAccess();
        boolean hasCityDefault();
        String getCityDefault();
        List<DaysModel> getTemperatureNextDays(List<ForecastModel> listForecast);
    }

    interface View {
        void showDialog();
        void hiddenDialog();
        void showError();

        void showDataWeather(BaseWeatherModel baseWeather);
        void showDataForecast(BaseForecastModel baseForecast);
    }
}

