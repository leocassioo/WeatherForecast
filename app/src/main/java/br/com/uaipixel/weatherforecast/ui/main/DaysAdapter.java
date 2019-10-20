package br.com.uaipixel.weatherforecast.ui.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import br.com.uaipixel.weatherforecast.Config;
import br.com.uaipixel.weatherforecast.R;
import br.com.uaipixel.weatherforecast.data.model.DaysModel;
import br.com.uaipixel.weatherforecast.utils.Utils;

/**
 * Criado por  Leonardo Figueiredo em 23/02/19.
 */
public class DaysAdapter extends RecyclerView.Adapter<DaysAdapter.ItemViewHolder> {
    private static List<DaysModel> mListNextDays;
    private LayoutInflater mInflater;
    private Context context;

    public DaysAdapter(Context ctx, List<DaysModel> days) {
        context = ctx;
        mListNextDays = days;
        mInflater = LayoutInflater.from(context);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageWeather;
        private TextView txtDayOfWeek, txtTempMax, txtTempMin;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageWeather = itemView.findViewById(R.id.imageWeather);
            txtDayOfWeek = itemView.findViewById(R.id.txtDayOfWeek);
            txtTempMax = itemView.findViewById(R.id.txtTempMax);
            txtTempMin = itemView.findViewById(R.id.txtTempMin);
        }
        private void onBindView(DaysModel currentDay) {
            Date date = new Date(currentDay.getDate().replace("-", "/"));
            DecimalFormat formatNumber = new DecimalFormat("0");
            Glide.with(context)
                    .load(Config.BASE_URL_ICONS + currentDay.getIcon() + ".png")
                    .placeholder(R.drawable.ic_cloud)
                    .into(imageWeather);
            txtDayOfWeek.setText(Utils.fullDate(date));
            txtTempMax.setText(String.format("%s°",
                    String.valueOf(formatNumber.format(Utils.convertCelsius(currentDay.getTemp_max())))));
                txtTempMin.setText(String.format("%s°",
                        String.valueOf(formatNumber.format(Utils.convertCelsius(currentDay.getTemp_min())))));
            }
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_days, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.onBindView(mListNextDays.get(position));
    }

    @Override
    public int getItemCount() {
        return mListNextDays.size();
    }
}

