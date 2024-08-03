package rub3n120.weather_forecast.model;
import com.google.gson.annotations.SerializedName;

public class Rain {
    @SerializedName("1h")
    private double oneHour;

    public Rain() {
    }

    public Rain(double oneHour) {
        this.oneHour = oneHour;
    }

    public double getOneHour() {
        return oneHour;
    }

    public void setOneHour(double oneHour) {
        this.oneHour = oneHour;
    }
}
