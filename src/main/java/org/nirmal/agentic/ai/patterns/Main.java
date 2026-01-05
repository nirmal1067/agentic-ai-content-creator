package org.nirmal.agentic.ai.patterns;


import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;

import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_4_O;

public class Main {

    public static void main(String[] args) {
        // 1. Setup the LLM (Inner Engine)
        String apiKey = "<Replace with your own API Key>";


        OpenAiChatModel model = OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName(GPT_4_O)
                .logRequests(true)  // Add this
                .logResponses(true)
                .temperature(0.7) // Higher for creativity in Drafter
                .build();

        // 2. Initialize the AI Agents (Personas)
        // AiServices automatically implements your interfaces at runtime
        AgenticService agent = AiServices.builder(AgenticService.class)
                .chatLanguageModel(model)
                .build();

        // 3. Initialize the Orchestrator (The Governance Layer)
        GovernedAgentOrchestrator orchestrator = new GovernedAgentOrchestrator(agent);

        // 4. Run the governed workflow
        String topic = "The Future of Virtual Threads in Java 2026";
        System.out.println("Starting workflow for: " + topic);

        orchestrator.execute(topic);
    }
}