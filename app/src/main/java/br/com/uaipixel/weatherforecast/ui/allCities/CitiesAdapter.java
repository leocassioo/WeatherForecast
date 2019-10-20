package br.com.uaipixel.weatherforecast.ui.allCities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.uaipixel.weatherforecast.Config;
import br.com.uaipixel.weatherforecast.R;
import br.com.uaipixel.weatherforecast.data.model.BaseWeatherModel;
import br.com.uaipixel.weatherforecast.utils.Utils;

/**
 * Criado por  Leonardo Figueiredo em 23/02/19.
 */
public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.ItemViewHolder> implements Filterable {
    private List<BaseWeatherModel> mListAllCities;
    private LayoutInflater mInflater;
    private Context mContext;
    private CityClickListener clicklistener = null;
    private List<BaseWeatherModel> mListOriginal;

    public CitiesAdapter(Context ctx, List<BaseWeatherModel> cities) {
        this.mContext = ctx;
        this.mListAllCities = cities;
        this.mListOriginal = this.mListAllCities;
        mInflater = LayoutInflater.from(mContext);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageWeatherCity;
        private TextView txtCityName, txtTempMax, txtTempMin;
        private LinearLayout linearWeather;

        public ItemViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            imageWeatherCity = itemView.findViewById(R.id.imageWeatherCity);
            txtCityName = itemView.findViewById(R.id.txtCityName);
            txtTempMax = itemView.findViewById(R.id.txtTempMax);
            txtTempMin = itemView.findViewById(R.id.txtTempMin);
            linearWeather = itemView.findViewById(R.id.linearWeather);
        }
        private void onBindView(BaseWeatherModel currentCity) {
            configureLayout(linearWeather);
            DecimalFormat formatNumber = new DecimalFormat("0");
            Glide.with(mContext)
                    .load(Config.BASE_URL_ICONS + currentCity.getWeather().get(0).getIcon() + ".png")
                    .placeholder(R.drawable.ic_cloud)
                    .into(imageWeatherCity);
            txtCityName.setText(currentCity.getName());
            txtTempMax.setText(String.format("%s°",
                    String.valueOf(formatNumber.format(Utils.convertCelsius(currentCity.getMain().getTemp_max())))));
                txtTempMin.setText(String.format("%s°",
                        String.valueOf(formatNumber.format(Utils.convertCelsius(currentCity.getMain().getTemp_min())))));
            }

            @Override
            public void onClick(View v) {
                if (clicklistener != null) {
                    clicklistener.cityClicked(mListAllCities.get(getAdapterPosition()));
            }
        }
    }

    private void configureLayout(LinearLayout linearLayout) {
        ViewGroup.LayoutParams params = linearLayout.getLayoutParams();
        WindowManager windowManager = ( WindowManager ) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        params.width = ((display.getWidth() / 2) - 60);
        params.height = ((display.getWidth() / 2) - 60);
        linearLayout.setLayoutParams(params);
    }

    public void setClickListener(CityClickListener listener) {
        this.clicklistener = listener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cities, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.onBindView(mListAllCities.get(position));
    }

    @Override
    public int getItemCount() {
        try {
            return mListAllCities.size();
        } catch(Exception ex) {
            return 0;
        }
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results.values == null) {
                    mListAllCities = mListOriginal;
                    notifyDataSetChanged();
                } else {
                    mListAllCities = (List<BaseWeatherModel>) results.values;
                    notifyDataSetChanged();
                }
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                ArrayList<BaseWeatherModel> FilteredArrayNames = new ArrayList<>();

                constraint = constraint.toString().toLowerCase();

                if (!constraint.equals("")) {
                    for (int i = 0; i < mListAllCities.size(); i++) {
                        BaseWeatherModel dataNames = mListAllCities.get(i);
                        //filter by name
                        if (dataNames.getName().toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrayNames.add(dataNames);
                        }
                        //filter by country
                        if (dataNames.getSys().getCountry().toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrayNames.add(dataNames);
                        }
                    }

                    results.count = FilteredArrayNames.size();
                    results.values = FilteredArrayNames;
                    Log.e("VALUES", results.values.toString());
                    return results;
                } else {
                    return results;
                }
            }
        };
        return filter;
    }
}

