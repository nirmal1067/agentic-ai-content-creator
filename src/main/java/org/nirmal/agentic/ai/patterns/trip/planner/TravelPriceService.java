package org.nirmal.agentic.ai.patterns.trip.planner;


import dev.langchain4j.agent.tool.Tool;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Service to fetch round-trip flight prices.
 * Returns data as a list of immutable flight records.
 */
public class TravelPriceService {

    // Define the FlightRecord with the requested attributes
    public record FlightRecord(
            String airline,
            double roundTripPrice,
            String destination,
            String departureDate,
            String returnDate,
            boolean isNonStop
    ) {}

    /**
     * Mocks a search for round-trip flights from Newark (EWR).
     * Prices are based on March 2026 airline market trends.
     */
    @Tool("Fetches round-trip airline prices for specific dates")
    public List<FlightRecord> getTravelPrices(String startDay, String endDay, String location) {

            // Logic would normally call a GDS or Travel API (Amadeus/Sabre)
            // Mocking results for common destinations from Jersey City/EWR
            return List.of(
                    new FlightRecord("United Airlines", 227.00, location, startDay, endDay, true),
                    new FlightRecord("JetBlue", 198.50, location, startDay, endDay, true),
                    new FlightRecord("Spirit Airlines", 124.00, location, startDay, endDay, false),
                    new FlightRecord("Delta", 245.00, location, startDay, endDay, true),
                    new FlightRecord("American Airlines", 212.00, location, startDay, endDay, false)
            );

    }

}
