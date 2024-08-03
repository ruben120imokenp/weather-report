package rub3n120.weather_forecast.utilities;

public class TemperatureConverter {

    // Método para convertir de Fahrenheit a Celsius
    public static double fahrenheitToCelsius(double fahrenheit) {
        return (5.0 / 9.0) * (fahrenheit - 32);
    }
}