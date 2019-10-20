package br.com.uaipixel.weatherforecast.ui.main;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.uaipixel.weatherforecast.Config;
import br.com.uaipixel.weatherforecast.data.model.BaseForecastModel;
import br.com.uaipixel.weatherforecast.data.model.BaseWeatherModel;
import br.com.uaipixel.weatherforecast.data.model.DaysModel;
import br.com.uaipixel.weatherforecast.data.model.ForecastModel;
import br.com.uaipixel.weatherforecast.data.network.ApiClient;
import br.com.uaipixel.weatherforecast.data.sharedpref.SharedPrefsHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Criado por  Leonardo Figueiredo em 02/03/19.
 */
public class MainPresenter implements ContractMain.Presenter {

    private SharedPrefsHelper mSharedPrefsHelper;
    private MainActivity mView;

    MainPresenter(MainActivity view) {
        this.mView = view;
        this.mSharedPrefsHelper = new SharedPrefsHelper(view);
    }

    @Override
    public void getWeather() {
        String city;
        //checking whether there is a standard city already recorded locally
        if(!mSharedPrefsHelper.getCityDefault().equals("")) {
            city = mSharedPrefsHelper.getCityDefault();
        } else {
            //if not, we'll have a default city to start the app
            city = "sao paulo,br";
        }
        Call<BaseWeatherModel> call = new ApiClient().getWeather().getCurrentWeather(city, Config.API_KEY);
        //verifying that the app already has local data, if it does not display a load
        if (!mSharedPrefsHelper.getFirstAccess().equals("accessed")) {
            mView.showDialog();
        }
        call.enqueue(new Callback<BaseWeatherModel>() {
            @Override
            public void onResponse(Call<BaseWeatherModel> call, Response<BaseWeatherModel> response) {
                mView.hiddenDialog();
                if(response.isSuccessful()) {
                    //response from the server with the requested data
                    BaseWeatherModel baseWeather = response.body();
                    mSharedPrefsHelper.saveWeather(baseWeather);
                    //defining that the application has already been accessed
                    mSharedPrefsHelper.setFirstAccess("accessed");
                    //populating the data on the screen
                    mView.showDataWeather(baseWeather);

                }
            }

            @Override
            public void onFailure(Call<BaseWeatherModel> call, Throwable t) {
                //request error
                mView.hiddenDialog();
                mView.showError();

                //validating if the default city is the same for the display
                //creating a regex to capture only the name of the city
                String city = mSharedPrefsHelper.getCityDefault().split(",")[0];
                for(int i = 0; i < mSharedPrefsHelper.getAllCities().size(); i++) {
                    if(mSharedPrefsHelper.getAllCities().get(i).getName().equals(city)){
                        BaseWeatherModel baseWeather = mSharedPrefsHelper.getAllCities().get(i);
                        //populating the data on the screen
                        mView.showDataWeather(baseWeather);
                    }
                }
                Log.e("Erro", "Error" + t.getMessage());
            }
        });
    }

    @Override
    public void getForecast() {
        final String city;
        //checking whether there is a standard city already recorded locally
        if(!mSharedPrefsHelper.getCityDefault().equals("")) {
            city = mSharedPrefsHelper.getCityDefault();
        } else {
            //if not, we'll have a default city to start the app
            city = "sao paulo,br";
            mSharedPrefsHelper.setCityDefault(city);
        }
        Call<BaseForecastModel> call = new ApiClient().getWeather().getForecast(city, Config.API_KEY);
        call.enqueue(new Callback<BaseForecastModel>() {
            @Override
            public void onResponse(Call<BaseForecastModel> call, Response<BaseForecastModel> response) {
                mView.hiddenDialog();
                if(response.isSuccessful()) {
                    //response from the server with the requested data
                    BaseForecastModel baseForecast = response.body();
                    mSharedPrefsHelper.saveForecast(baseForecast);
                    //setting the new default location
                    mSharedPrefsHelper.setCityDefault(city);
                    //populating the data on the screen
                    mView.showDataForecast(baseForecast);
                }
            }

            @Override
            public void onFailure(Call<BaseForecastModel> call, Throwable t) {
                //request error
                mView.hiddenDialog();
                //validating if the default city is the same for the display
                //creating a regex to capture only the name of the city
                String city = mSharedPrefsHelper.getCityDefault().split(",")[0];
                for(int i = 0; i < mSharedPrefsHelper.getAllCities().size(); i++) {
                    if(mSharedPrefsHelper.getAllCities().get(i).getName().equals(city)){
                        BaseForecastModel baseForecast = mSharedPrefsHelper.getForecast();
                        //populating the data on the screen
                        mView.showDataForecast(baseForecast);
                    }
                }
                Log.e("Erro", "Error" + t.getMessage());
            }
        });
    }

    @Override
    public boolean isFirstAccess() {
        return mSharedPrefsHelper.getFirstAccess().equals("");
    }

    @Override
    public boolean hasCityDefault() {
        return mSharedPrefsHelper.getCityDefault().equals("");
    }

    @Override
    public String getCityDefault() {
        return mSharedPrefsHelper.getCityDefault().split(",")[0].toLowerCase();
    }

    @Override
    public List<DaysModel> getTemperatureNextDays(List<ForecastModel> listForecast) {
        List<ForecastModel> mListNextDays = new ArrayList<>();
        double tempMax = 0;
        double tempMin= 0;

        String data = "";
        List<DaysModel> nextDays = new ArrayList<>();

        //looking for the maximum temperature and the minimum
        for(int i = 0; i < listForecast.size(); i++) {
            //separating the list by date
            /*
            separating the dates, it was the only way I found, because the API does not always have a standard return.
            In spite of the documentation to be mentioned that they are 38
            */
            String currentDate = listForecast.get(i).getDt_txt().split(" ")[0];
            if(!data.equals(currentDate)) {
                data = currentDate;
                mListNextDays.add(listForecast.get(i));
                tempMax = listForecast.get(i).getMain().getTemp_max();
                tempMin = listForecast.get(i).getMain().getTemp_min();
                nextDays.add(new DaysModel(currentDate, tempMax, tempMin, listForecast.get(i).getWeather().get(0).getIcon()));
            } else {
                //validating the maximum temperature
                if(nextDays.get(nextDays.size() - 1).getTemp_max() < listForecast.get(i).getMain().getTemp_max()){
                    nextDays.get(nextDays.size() - 1).setTemp_max(listForecast.get(i).getMain().getTemp_max());
                }
                //validating at the minimum temperature
                if(nextDays.get(nextDays.size() - 1).getTemp_min() > listForecast.get(i).getMain().getTemp_min()){
                    nextDays.get(nextDays.size() - 1).setTemp_min(listForecast.get(i).getMain().getTemp_min());
                }
            }
        }
        return nextDays;
    }

}
