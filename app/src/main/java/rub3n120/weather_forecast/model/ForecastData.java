package rub3n120.weather_forecast.model;

import java.util.List;

public class ForecastData {
    private int cod;
    private int message;
    private int cnt;
    private List<WeatherData> list;

    public ForecastData() {
    }

    public ForecastData(int cod, int message, int cnt, List<WeatherData> list) {
        this.cod = cod;
        this.message = message;
        this.cnt = cnt;
        this.list = list;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public List<WeatherData> getList() {
        return list;
    }

    public void setList(List<WeatherData> list) {
        this.list = list;
    }
}
