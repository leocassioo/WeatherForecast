package br.com.uaipixel.weatherforecast.data.network;

import br.com.uaipixel.weatherforecast.Config;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Criado por  Leonardo Figueiredo em 23/02/19.
 */
public class ApiClient {
    private final Retrofit mRetrofit;

    public ApiClient() {
        this.mRetrofit = new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public ApiInterface getWeather() {
        return this.mRetrofit.create(ApiInterface.class);
    }
}
