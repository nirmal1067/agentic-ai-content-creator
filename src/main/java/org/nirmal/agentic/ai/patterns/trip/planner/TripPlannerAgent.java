package org.nirmal.agentic.ai.patterns.trip.planner;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;

import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_4_O;

public class TripPlannerAgent {
    public static String apiKey = "Paste your api key here";


    public static void main(String[] args) {
        OpenAiChatModel model = OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName(GPT_4_O)
                .logRequests(true)  // Add this
                .logResponses(true)
                .temperature(0.7) // Higher for creativity in Drafter
                .build();


        TripOrchestrator agent = AiServices.builder(TripOrchestrator.class)
                .chatLanguageModel(model)
                .tools(new HotelDetailsService(),new TravelPriceService(),new WeatherFetcherService())
                .build();

        // 3. Execute the Request
        String result = agent.planTrip("I want to visit Jersey city . I will departure from Sane Diego and date 21 march 2026");
        System.out.println(result);
    }




}
