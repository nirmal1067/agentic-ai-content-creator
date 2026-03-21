package org.nirmal.agentic.ai.patterns.trip.planner;

import dev.langchain4j.agent.tool.Tool;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Service to fetch hotel availability for a 5-day window.
 */
public class HotelDetailsService {

    public record HotelRecord(
            String name,
            double price,
            double rating,
            String location,
            String availableDate
    ) {}

    /**
     * Returns a list of available hotels over a 5-day period starting from today.
     * @param city The target city (e.g., "Jersey City")
     * @param state The target state (e.g., "NJ")
     */
    @Tool("Fetches hotel availability,ratings and prices for a 5-day window")
    public List<HotelRecord> getHotelDetails(String city, String state) {

            List<HotelRecord> fiveDayHotels = new ArrayList<>();
            LocalDate startDate = LocalDate.of(2026, 3, 21);

            // Day 1: Saturday, March 21
            fiveDayHotels.add(new HotelRecord("Sonesta Simply Suites", 143.00, 4.3, "Downtown", "2026-03-21"));
            fiveDayHotels.add(new HotelRecord("Canopy by Hilton", 237.00, 4.6, "Arts District", "2026-03-21"));

            // Day 2: Sunday, March 22
            fiveDayHotels.add(new HotelRecord("Courtyard Newport", 163.00, 4.3, "Newport", "2026-03-22"));
            fiveDayHotels.add(new HotelRecord("Hyatt Regency", 215.00, 4.3, "Exchange Place", "2026-03-22"));

            // Day 3: Monday, March 23
            fiveDayHotels.add(new HotelRecord("Residence Inn", 236.00, 4.6, "Downtown", "2026-03-23"));
            fiveDayHotels.add(new HotelRecord("Holland Hotel", 171.00, 4.4, "Holland Tunnel", "2026-03-23"));

            // Day 4: Tuesday, March 24
            fiveDayHotels.add(new HotelRecord("Sonesta Simply Suites", 168.00, 4.3, "Downtown", "2026-03-24"));
            fiveDayHotels.add(new HotelRecord("The Westin", 235.00, 4.3, "Newport", "2026-03-24"));

            // Day 5: Wednesday, March 25
            fiveDayHotels.add(new HotelRecord("Hyatt House", 149.00, 4.3, "Exchange Place", "2026-03-25"));
            fiveDayHotels.add(new HotelRecord("DoubleTree by Hilton", 201.00, 4.3, "Downtown", "2026-03-25"));

            return fiveDayHotels;
    }


}