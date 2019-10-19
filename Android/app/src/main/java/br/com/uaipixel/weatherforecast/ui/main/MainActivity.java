package br.com.uaipixel.weatherforecast.ui.main;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.Date;

import br.com.uaipixel.weatherforecast.Config;
import br.com.uaipixel.weatherforecast.R;
import br.com.uaipixel.weatherforecast.ui.allCities.AllCitiesActivity;
import br.com.uaipixel.weatherforecast.ui.error.ErrorNetworkActivity;
import br.com.uaipixel.weatherforecast.data.model.BaseForecastModel;
import br.com.uaipixel.weatherforecast.data.model.BaseWeatherModel;
import br.com.uaipixel.weatherforecast.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements ContractMain.View {

    // layout variables
    @BindView(R.id.viewFlipperMain)
    ViewFlipper viewFlipperMain;
    @BindView(R.id.txtCity)
    TextView txtCity;
    @BindView(R.id.txtDate)
    TextView txtDate;
    @BindView(R.id.imageWeather)
    ImageView imageWeather;
    @BindView(R.id.txtTemperature)
    TextView txtTemperature;
    @BindView(R.id.txtDescription)
    TextView txtDescription;
    @BindView(R.id.txtWind)
    TextView txtWind;
    @BindView(R.id.txtHumidity)
    TextView txtHumidity;
    @BindView(R.id.txtSunrise)
    TextView txtSunrise;
    @BindView(R.id.txtSunset)
    TextView txtSunset;
    @BindView(R.id.recyclerViewNextDays)
    RecyclerView rViewNextDays;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.linearWeather)
    LinearLayout linearWeather;

    public static boolean isLoad = false;
    private static final int VIEW_MAIN = 0;
    private static final int PROGRESS_BAR = 1;

    //global variables
    private DecimalFormat mFormatNumber;
    private DaysAdapter mDaysAdapter;

    private ContractMain.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new MainPresenter(this);

        //instantiating variables...
        mFormatNumber = new DecimalFormat("0");

        //defining size for items so that it always looks the same in all sizes
        configureLayout();

        LinearLayoutManager llManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rViewNextDays.setLayoutManager(llManager);
        //checking if this is the first time you open the application
        if(presenter.isFirstAccess()) {
            //the first time you will need to connect to the internet to get the data
            if(!Utils.isOnline(this)) {
                startActivity(new Intent(this, ErrorNetworkActivity.class));
                finish();
                return;
            }
        }

        showDialog();
        //looking for current weather forecast to the default location
        presenter.getWeather();
        //looking for weather forecast for the next 5 days, according to the default location
        presenter.getForecast();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(isLoad) {
            showDialog();
        }
        //updating data when changing default location
        presenter.getWeather();
        presenter.getForecast();
    }

    //button to change the default location
    @OnClick(R.id.btnChangeDay) public void btnChangeDay() {
        Intent intent = new Intent(this, AllCitiesActivity.class);
        startActivity(intent);
    }

    private void configureLayout() {
        //defining size for items so that it always looks the same in all sizes
        ViewGroup.LayoutParams params = linearWeather.getLayoutParams();
        Display display = getWindowManager().getDefaultDisplay();

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches= metrics.heightPixels / metrics.ydpi;
        float xInches= metrics.widthPixels / metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);
        // 7.0 inch big device
        if (diagonalInches>=7.0){
            params.width = ((display.getWidth() / 2) + (display.getWidth() / 4));
            params.height =  ((display.getWidth() / 2) + (display.getWidth() / 4));
            linearWeather.setLayoutParams(params);
        } else { // smaller device
            params.width = (display.getWidth() - 120);
            params.height = (display.getWidth() - 120);
            linearWeather.setLayoutParams(params);
        }
    }

    @Override
    public void showDialog() {
        viewFlipperMain.setDisplayedChild(PROGRESS_BAR);
    }

    @Override
    public void hiddenDialog() {
        viewFlipperMain.setDisplayedChild(VIEW_MAIN);
    }

    @Override
    public void showError() {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, R.string.no_internet_connection, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void showDataWeather(BaseWeatherModel baseWeather) {
        //get Weather icons conditions (https://openweathermap.org/weather-conditions)
        String climateUrl = Config.BASE_URL_ICONS + baseWeather.getWeather().get(0).getIcon() + ".png";

        //Timestamp Unix to Date
        Date currentDate = new Date((long) baseWeather.getDt() * 1000);
        Date dateSunrise = new Date((long) baseWeather.getSys().getSunrise() * 1000);
        Date dateSunset = new Date((long) baseWeather.getSys().getSunset() * 1000);

        //formatting date and time sunrise and sunset
        String sunrise = dateSunrise.getHours() + ":" + dateSunrise.getMinutes();
        String sunset = dateSunset.getHours() + ":" + dateSunset.getMinutes();

        //toUpperCase in description for a more friendly appearance
        String description = baseWeather.getWeather().get(0).getDescription().substring(0, 1).toUpperCase() +
                baseWeather.getWeather().get(0).getDescription().substring(1);

        txtCity.setText(String.format("%s - %s", baseWeather.getName(),
                baseWeather.getSys().getCountry()));
        txtDate.setText(String.format("Updated %s", Utils.fullDate(currentDate)));
        Glide.with(this)
                .load(climateUrl)
                .placeholder(R.drawable.ic_cloud)
                .into(imageWeather);
        txtTemperature.setText(String.format("%s°",
                mFormatNumber.format(Utils.convertCelsius(baseWeather.getMain().getTemp()))));
        txtDescription.setText(description);
        txtWind.setText(mFormatNumber.format(baseWeather.getWind().getSpeed()));
        txtHumidity.setText(mFormatNumber.format(baseWeather.getMain().getHumidity()));
        txtSunrise.setText(sunrise);
        txtSunset.setText(sunset);
    }

    //looking for weather forecast for the next 5 days, according to the default location
    @Override
    public void showDataForecast(BaseForecastModel baseForecast) {
        //checking if the default location is the same as the forecast of the next 5 days
        if(!presenter.hasCityDefault()){
            //if not, our list of 5 days will be hidden
            //So do not display the current weather forecast with the next 5 days from a different location
            if(!baseForecast.getCity().getName().toLowerCase().equals(presenter.getCityDefault())) {
                rViewNextDays.setVisibility(View.GONE);
            } else {
                rViewNextDays.setVisibility(View.VISIBLE);
            }
        }

        //populating the recyclerview of the next 5 days
        mDaysAdapter = new DaysAdapter(this, presenter.getTemperatureNextDays(baseForecast.getList()));
        rViewNextDays.setAdapter(mDaysAdapter);
        rViewNextDays.setNestedScrollingEnabled(false);
    }
}
