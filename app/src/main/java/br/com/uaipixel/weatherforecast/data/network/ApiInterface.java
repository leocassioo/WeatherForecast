package br.com.uaipixel.weatherforecast.data.network;

import br.com.uaipixel.weatherforecast.data.model.AllCitiesModel;
import br.com.uaipixel.weatherforecast.data.model.BaseForecastModel;
import br.com.uaipixel.weatherforecast.data.model.BaseWeatherModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Criado por  Leonardo Figueiredo em 23/02/19.
 */
public interface ApiInterface {

    @GET("weather")
    Call<BaseWeatherModel> getCurrentWeather(@Query("q") String cityCountry,
                                             @Query("APPID") String appid);

    @GET("forecast")
    Call<BaseForecastModel> getForecast(@Query("q") String cityCountry,
                                        @Query("APPID") String appid);

    @GET("group")
    Call<AllCitiesModel> getAllCities(@Query("id") String idsCities,
                                      @Query("APPID") String appid);

}
