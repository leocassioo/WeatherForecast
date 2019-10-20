package br.com.uaipixel.weatherforecast.ui.allCities;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.List;

import br.com.uaipixel.weatherforecast.R;
import br.com.uaipixel.weatherforecast.data.model.BaseWeatherModel;
import br.com.uaipixel.weatherforecast.data.sharedpref.SharedPrefsHelper;
import br.com.uaipixel.weatherforecast.ui.error.ErrorNetworkActivity;
import br.com.uaipixel.weatherforecast.ui.main.MainActivity;
import br.com.uaipixel.weatherforecast.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AllCitiesActivity extends AppCompatActivity implements CityClickListener, ContractAllCities.View {

    //layout
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.txtTitle)
    TextView tituloToolbar;
    @BindView(R.id.editTextSearch)
    EditText editTextSearch;
    @BindView(R.id.rViewAllCities)
    RecyclerView rViewAllCities;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.viewFlipperCities)
    ViewFlipper viewFlipperCities;

    private static final int VIEW_MAIN = 0;
    private static final int PROGRESS_BAR = 1;

    //global variables
    private CitiesAdapter mCitiesAdapter;
    private List<BaseWeatherModel> mListWeatherAllCities;

    private AllCitiesPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_cities);
        ButterKnife.bind(this);

        presenter = new AllCitiesPresenter(this);

        //layoutManager customized to appear 2 at a time
        StaggeredGridLayoutManager llManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rViewAllCities.setLayoutManager(llManager);

        //checking if this is the first time you open the application
        if(presenter.getAllCities().size() == 0) {
            //the first time you will need to connect to the internet to get the data
            if(!Utils.isOnline(this)) {
                startActivity(new Intent(this, ErrorNetworkActivity.class));
                finish();
                return;
            }
        }

        //if you already have data, look for the last ones saved
        if(presenter.getAllCities() != null) {
            mListWeatherAllCities = presenter.getAllCities();
            loadAllCities(mListWeatherAllCities);
        } else {
            showDialog();
        }

        //setting toolbar
        configureToolbar();
        //searching for updated data and updating local data
        presenter.getWeatherAllCities();
    }


    //clearing all text
    @OnClick(R.id.buttonClear) public void buttonClear() {
        editTextSearch.setText("");
    }

    public void configureToolbar() {
        mToolbar.setTitle("");
        tituloToolbar.setText(R.string.title_all_cities);
        setSupportActionBar(mToolbar);

        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //defining whether there was a load on the main
                        MainActivity.isLoad = false;
                        finish();
                    }
                }
        );
    }

    @Override
    public void cityClicked(BaseWeatherModel weather) {
        //city currently clicked on
        String cityDefault = weather.getName() + "," + weather.getSys().getCountry();
        //defining a standard city
        presenter.setCityDefault(cityDefault);
        //defining whether there was a load on the main
        MainActivity.isLoad = true;
        finish();
    }

    @Override
    public void showDialog() {
        viewFlipperCities.setDisplayedChild(PROGRESS_BAR);
    }

    @Override
    public void hiddenDialog() {
        viewFlipperCities.setDisplayedChild(VIEW_MAIN);
    }

    @Override
    public void showError() {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, R.string.no_internet_connection, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void loadAllCities(List<BaseWeatherModel> listWeatherAllCities) {
        //instantiating the adapter
        mCitiesAdapter = new CitiesAdapter(this, listWeatherAllCities);
        rViewAllCities.setAdapter(mCitiesAdapter);
        rViewAllCities.setNestedScrollingEnabled(false);
        mCitiesAdapter.setClickListener(this);

        //addTextChangedListener to search city or country
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                AllCitiesActivity.this.mCitiesAdapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });

    }
}
