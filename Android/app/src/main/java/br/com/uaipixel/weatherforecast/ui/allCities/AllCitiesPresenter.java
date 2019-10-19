package br.com.uaipixel.weatherforecast.ui.allCities;
import android.util.Log;

import java.util.List;

import br.com.uaipixel.weatherforecast.Config;
import br.com.uaipixel.weatherforecast.data.model.AllCitiesModel;
import br.com.uaipixel.weatherforecast.data.model.BaseWeatherModel;
import br.com.uaipixel.weatherforecast.data.network.ApiClient;
import br.com.uaipixel.weatherforecast.data.sharedpref.SharedPrefsHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Criado por  Leonardo Figueiredo em 02/03/19.
 */
public class AllCitiesPresenter implements ContractAllCities.Presenter {

    AllCitiesActivity mView;
    SharedPrefsHelper mSharedPrefsHelper;

    AllCitiesPresenter(AllCitiesActivity view) {
        this.mView = view;
        this.mSharedPrefsHelper = new SharedPrefsHelper(view);
    }

    @Override
    public void getWeatherAllCities() {
        //id of all cities
        String idAllCities = Config.ID_SILVERSTONE +
                "," + Config.ID_SAO_PAULO +
                "," + Config.ID_MELBOURNE +
                "," + Config.ID_MONTE_CARLO;

        Call<AllCitiesModel> call = new ApiClient().getWeather().getAllCities(idAllCities, Config.API_KEY);
        //show progress if you do not have local data
        if (mSharedPrefsHelper.getAllCities().size() == 0) {
            mView.showDialog();
        }
        call.enqueue(new Callback<AllCitiesModel>() {
            @Override
            public void onResponse(Call<AllCitiesModel> call, Response<AllCitiesModel> response) {
                mView.hiddenDialog();
                if(response.isSuccessful()) {
                    //response from the server with the requested data
                    List<BaseWeatherModel> mListWeatherAllCities = response.body().getList();
                    mSharedPrefsHelper.setAllCities(mListWeatherAllCities);
                    mView.loadAllCities(mListWeatherAllCities);
                }
            }

            @Override
            public void onFailure(Call<AllCitiesModel> call, Throwable t) {
                mView.showError();
                Log.d("Erro   ", "Error" + t.getMessage());
            }
        });
    }

    @Override
    public List<BaseWeatherModel> getAllCities() {
        return mSharedPrefsHelper.getAllCities();
    }

    @Override
    public void setCityDefault(String city) {
        mSharedPrefsHelper.setCityDefault(city);
    }
}
