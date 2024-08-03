package rub3n120.weather_forecast.utilities;

import android.os.Handler;
import android.os.HandlerThread;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import rub3n120.weather_forecast.model.WeatherData;

public class RequestsMaker {
    private Handler messager;
    private HandlerThread forecastHandlerThread;
    private Handler forecastHandler;

    public RequestsMaker(Handler messager) {
        this.messager = messager;
    }

    public void getForecast(String link) {
        forecastHandlerThread = new HandlerThread("Forecast");
        forecastHandlerThread.start();
        forecastHandler = new Handler(forecastHandlerThread.getLooper());

        forecastHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    // URL del endpoint
                    String urlString = link;
                    URL url = new URL(urlString);

                    // Crear una conexión HTTP
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");

                    // Configurar propiedades de la conexión
                    conn.setRequestProperty("Accept", "application/json");

                    // Verificar respuesta del servidor
                    if (conn.getResponseCode() != 200) {
                        throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
                    }

                    // Leer la respuesta
                    BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                    StringBuilder response = new StringBuilder();
                    String output;
                    while ((output = br.readLine()) != null) {
                        response.append(output);
                    }
                    System.out.println(response);
                    // Cerrar conexión
                    conn.disconnect();

                    // Convertir JSON a objeto Java usando Gson
                    Gson gson = new Gson();
                    WeatherData user = gson.fromJson(response.toString(), WeatherData.class);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }
}
