import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONObject;

public class WeatherApp {
    public static void main(String[] args) {
        String apiKey = "kharar";  // Replace with your OpenWeatherMap key
        Scanner scanner = new Scanner(System.in);
System.out.print("Enter city name: ");
String city = scanner.nextLine();
        String urlString = "https://api.openweathermap.org/data/2.5/weather?q=" + city +
                           "&appid=" + apiKey + "&units=metric";

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONObject weatherData = new JSONObject(response.toString());

            String weather = weatherData.getJSONArray("weather").getJSONObject(0).getString("description");
            double temp = weatherData.getJSONObject("main").getDouble("temp");
            double humidity = weatherData.getJSONObject("main").getDouble("humidity");

            System.out.println("City: " + city);
            System.out.println("Temperature: " + temp + "°C");
            System.out.println("Weather: " + weather);
            System.out.println("Humidity: " + humidity + "%");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
