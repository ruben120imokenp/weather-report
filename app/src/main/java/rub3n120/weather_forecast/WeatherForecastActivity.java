package rub3n120.weather_forecast;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationRequest;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import rub3n120.weather_forecast.utilities.RequestsMaker;


public class WeatherForecastActivity extends AppCompatActivity {
    private Handler messager;
    private RequestsMaker requestsMaker;
    private double latitude;
    private double longitude;
    private final String KEY = "4017cf2206ff557ee727acf18e4baac5";
    private FusedLocationProviderClient fusedLocationProviderClient;
    //HUD Components
    private TextView temperatureTextView;
    private TextView locationTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.weather_forecast_layout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initComponents();
        GetCurrentWeather();

    }

    private void initComponents(){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        //Handlers
        messager = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == RequestsMaker.CURRENT_WEATHER_OKAY){
                    SetCurrentWeatherDataOnHUD();
                }
            }
        };
        requestsMaker = new RequestsMaker(messager);
        //HUD
        initComponentsOfHUD();
    }

    private void initComponentsOfHUD(){
        temperatureTextView = findViewById(R.id.temperatureTextView);
        locationTextView = findViewById(R.id.locationTextView);
    }

    private String getCurrentWeatherDataEndPoint() {
        return "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=" + KEY;
    }

    private void SetCurrentWeatherDataOnHUD(){
        temperatureTextView.setText(String.valueOf(requestsMaker.getWeatherData().getMain().getTemp()));
        locationTextView.setText(String.valueOf(requestsMaker.getWeatherData().getName()));
    }

    private void GetCurrentWeather(){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
        locationResult.addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    Location location = task.getResult();
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    //Call the API having the user's location
                    requestsMaker.getForecast(getCurrentWeatherDataEndPoint());
                } else {
                    System.out.println("Error getting location");
                }
            }
        });
    }

}