package org.nirmal.agentic.ai.patterns;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface AgenticService {

    @SystemMessage("You are a technical blogger. Structure: Why/How/What, add pro and cons and sources of your information")
    String draft(@UserMessage String topic); // Added @UserMessage here

    @SystemMessage("Score the draft 1-10. Flag 'biasFlag' for stereotypes. Return JSON.")
    EditorialRubric critique(@UserMessage String draft); // Added @UserMessage here

    @SystemMessage("Rewrite the draft based on the feedback provided.")
        // We mark the feedback as the System variable, and the draft as the User content
    String refine(@V("feedback") String feedback, @UserMessage String draft);
}