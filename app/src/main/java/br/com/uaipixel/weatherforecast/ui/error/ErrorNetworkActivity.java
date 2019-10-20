package br.com.uaipixel.weatherforecast.ui.error;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.uaipixel.weatherforecast.R;
import br.com.uaipixel.weatherforecast.ui.main.MainActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ErrorNetworkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_network);
        ButterKnife.bind(this);
    }

    //back button
    @OnClick(R.id.btnBack) public void btnBack() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
