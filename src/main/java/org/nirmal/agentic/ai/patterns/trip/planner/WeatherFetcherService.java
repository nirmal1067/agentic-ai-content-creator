package org.nirmal.agentic.ai.patterns.trip.planner;



import dev.langchain4j.agent.tool.Tool;

import java.util.ArrayList;
import java.util.List;

/**
 * Service to fetch a 5-day weather forecast.
 * Returns a list of records for specific city/state lookups.
 */
public class WeatherFetcherService {

    // Expanded record to include the date
    public record WeatherRecord(
            String date,
            double tempCelsius,
            boolean isRaining,
            boolean isSunny,
            double windSpeedKph,
            int humidityPercentage,
            String summary
    ) {}

    /**
     * Mocks a 5-day forecast for a specific city and state.
     * Data is based on real-time March 2026 predictions for Jersey City, NJ.
     */
    @Tool("Fetches a 5-day weather forecast for a city and state")
    public List<WeatherRecord> getFiveDayForecast(String city, String state) {
        List<WeatherRecord> forecast = new ArrayList<>();

        // March 21 (Saturday) - Today
        forecast.add(new WeatherRecord(
                "2026-03-21", 14.0, false, false, 11.2, 69, "Mostly Cloudy"
        ));

        // March 22 (Sunday) - Expected Rain
        forecast.add(new WeatherRecord(
                "2026-03-22", 15.0, true, false, 16.1, 79, "Cloudy with Night Showers"
        ));

        // March 23 (Monday) - Cold Front & Sunny
        forecast.add(new WeatherRecord(
                "2026-03-23", 8.3, false, true, 22.5, 61, "Sunny and Windy"
        ));

        // March 24 (Tuesday) - Clear Skies
        forecast.add(new WeatherRecord(
                "2026-03-24", 7.2, false, true, 16.1, 40, "Clear and Sunny"
        ));

        // March 25 (Wednesday) - Returning Clouds
        forecast.add(new WeatherRecord(
                "2026-03-25", 11.1, false, false, 19.3, 60, "Mostly Cloudy"
        ));

        return forecast;
    }

    public static void main(String[] args) {
        WeatherFetcherService service = new WeatherFetcherService();
        String city = "Jersey City";
        String state = "NJ";

        List<WeatherRecord> fiveDayForecast = service.getFiveDayForecast(city, state);

        System.out.println("--- 5-DAY FORECAST AGENT: " + city.toUpperCase() + ", " + state + " ---");
        for (WeatherRecord day : fiveDayForecast) {
            System.out.printf("[%s] %-15s | %4.1f°C | Rain: %-5b | Sun: %-5b | Wind: %4.1f km/h%n",
                    day.date(), day.summary(), day.tempCelsius(), day.isRaining(), day.isSunny(), day.windSpeedKph());
        }
    }
}