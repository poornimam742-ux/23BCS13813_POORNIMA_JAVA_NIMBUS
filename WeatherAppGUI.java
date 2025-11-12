import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.*;
import org.json.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WeatherAppGUI extends JFrame {

    private JTextField cityField;
    private JButton getWeatherButton;
    private JButton celsiusButton, fahrenheitButton;
    private JLabel resultLabel;
    private JLayeredPane layeredPane;
    private JLabel backgroundLabel;
    private String unit = "metric"; // default is Celsius

    public WeatherAppGUI() {
        setTitle("Weather App");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        setContentPane(layeredPane);

        // Load and set default background image
        loadBackgroundImage("images/default.jpeg");

        // City input label
        JLabel cityLabel = new JLabel("Enter City:");
        cityLabel.setBounds(150, 20, 80, 30);
        cityLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        cityLabel.setForeground(Color.BLACK);
        layeredPane.add(cityLabel, new Integer(1));

        // City input field
        cityField = new JTextField();
        cityField.setBounds(230, 20, 150, 30);
        layeredPane.add(cityField, new Integer(1));

        // Get weather button
        getWeatherButton = new JButton("Get Weather");
        getWeatherButton.setBounds(400, 20, 120, 30);
        layeredPane.add(getWeatherButton, new Integer(1));

        // Celsius button
        celsiusButton = new JButton("°C");
        celsiusButton.setBounds(540, 20, 60, 30);
        celsiusButton.setBackground(Color.LIGHT_GRAY);
        layeredPane.add(celsiusButton, new Integer(1));

        // Fahrenheit button
        fahrenheitButton = new JButton("°F");
        fahrenheitButton.setBounds(610, 20, 60, 30);
        layeredPane.add(fahrenheitButton, new Integer(1));

        // Weather result label
        resultLabel = new JLabel("", SwingConstants.CENTER);
        resultLabel.setOpaque(false);
        resultLabel.setForeground(Color.BLACK);
        resultLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        resultLabel.setVerticalAlignment(SwingConstants.CENTER);
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultLabel.setBounds(20, 70, 760, 470);
        resultLabel.setVerticalTextPosition(SwingConstants.CENTER);
        layeredPane.add(resultLabel, new Integer(1));

        // Button Actions
        getWeatherButton.addActionListener(e -> {
            String city = cityField.getText().trim();
            if (!city.isEmpty()) {
                fetchWeather(city);
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a city name.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        celsiusButton.addActionListener(e -> {
            unit = "metric";
            celsiusButton.setBackground(Color.LIGHT_GRAY);
            fahrenheitButton.setBackground(null);
        });

        fahrenheitButton.addActionListener(e -> {
            unit = "imperial";
            fahrenheitButton.setBackground(Color.LIGHT_GRAY);
            celsiusButton.setBackground(null);
        });
    }

    private void fetchWeather(String city) {
        String apiKey = "2d06c82809976ed7d6ad3d38b0148f26"; // Replace this with your OpenWeatherMap API key
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=" + unit;

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            Scanner sc = new Scanner(conn.getInputStream());
            String inline = "";
            while (sc.hasNext()) {
                inline += sc.nextLine();
            }
            sc.close();

            JSONObject data = new JSONObject(inline);
            JSONObject main = data.getJSONObject("main");
            double temp = main.getDouble("temp");
            double humidity = main.getDouble("humidity");
            double pressure = main.getDouble("pressure");

            JSONObject weatherObj = data.getJSONArray("weather").getJSONObject(0);
            String weather = weatherObj.getString("description").toLowerCase();

            JSONObject wind = data.getJSONObject("wind");
            double windSpeed = wind.getDouble("speed");

            String unitSymbol = unit.equals("imperial") ? "°F" : "°C";
            String windUnit = unit.equals("imperial") ? "mph" : "m/s";

            // Update result text
            resultLabel.setText("<html><div style='text-align: center;'>"
                + "City: " + city + "<br>"
                + "Temperature: " + temp + unitSymbol + "<br>"
                + "Weather: " + weather + "<br>"
                + "Humidity: " + humidity + "%<br>"
                + "Wind Speed: " + windSpeed + " " + windUnit + "<br>"
                + "Pressure: " + pressure + " hPa"
                + "</div></html>");

            // Update background image based on weather
            updateBackgroundImage(weather);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Could not fetch weather data. Check city name or internet connection.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateBackgroundImage(String weather) {
        String imagePath = "images/default.jpg";

        if (weather.contains("clear sky")) {
            imagePath = "images/clear.jpeg";
        } else if (weather.contains("cloud")) {
            imagePath = "images/clouds.jpeg";
        } else if (weather.contains("rain")) {
            imagePath = "images/rain.jpeg";
        } else if (weather.contains("overcast")) {
            imagePath = "images/snow.jpeg";
        } else if (weather.contains("mist") || weather.contains("fog")) {
            imagePath = "images/mist.jpeg";
        }

        loadBackgroundImage(imagePath);
    }

    private void loadBackgroundImage(String imagePath) {
        try {
            BufferedImage originalImage = ImageIO.read(new File(imagePath));
            Image scaledImage = originalImage.getScaledInstance(800, 600, Image.SCALE_SMOOTH);
            if (backgroundLabel == null) {
                backgroundLabel = new JLabel(new ImageIcon(scaledImage));
                backgroundLabel.setBounds(0, 0, 800, 600);
                layeredPane.add(backgroundLabel, new Integer(0));
            } else {
                backgroundLabel.setIcon(new ImageIcon(scaledImage));
            }
            backgroundLabel.repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new WeatherAppGUI().setVisible(true);
    }
}
