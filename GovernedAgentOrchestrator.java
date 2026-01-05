package org.nirmal.agentic.ai.patterns;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Set;

public class GovernedAgentOrchestrator {

    private static final Logger log = LoggerFactory.getLogger(GovernedAgentOrchestrator.class);

    // Dependencies
    private final AgenticService agent;

    // Policy Configuration (Governance Constants)
    private static final int MAX_RETRIES = 3;
    private static final double PASSING_SCORE = 7;
    private static final Set<String> BLOCKED_TOPICS = Set.of("religion", "gender disparity", "geopolitics", "caste");

    // Constructor for Dependency Injection
    public GovernedAgentOrchestrator(AgenticService agent) {
        this.agent = agent;
    }

    /**
     * Entry point for the governed workflow.
     */
    public void execute(String topic) {
        log.info("Starting governed workflow for topic: [{}]", topic);

        // --- LAYER 1: DETERMINISTIC INPUT GATE ---
        if (isTopicProhibited(topic)) {
            log.error("SAFETY BLOCK: Topic '{}' contains prohibited keywords. Workflow terminated.", topic);
            return;
        }

        // Generate the Initial Draft
        String currentDraft = agent.draft(topic);
        ExecutionState state = new ExecutionState();

        for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {
            log.info("Iteration {}/{} starting...", attempt, MAX_RETRIES);

            // --- LAYER 1b: DETERMINISTIC STRUCTURAL VALIDATION ---
            if (!isStructureValid(currentDraft)) {
                log.warn("Structural check failed. Re-prompting for correct formatting.");
                currentDraft = agent.draft("RETRY: Use headers 'Why', 'How', 'What' and ensure content is > 300 words.");
                continue;
            }

            // --- LAYER 2: PROBABILISTIC CRITIQUE (AI JUDGE) ---
            EditorialRubric rubric = agent.critique(currentDraft);
            double currentScore = calculateWeightedScore(rubric);

            // Update the state with the new score to check for stagnation
            state.update(currentScore);

            log.info("Critic Score: {} | Bias Flag: {}", currentScore, rubric.isBiasFlag());

            // --- LAYER 3: HUMAN-IN-THE-LOOP (SAFETY & QUALITY EXIT) ---
            if (shouldEscalate(state, rubric, attempt)) {
                handleEscalation(currentDraft, rubric, attempt);
                return;
            }

            // SUCCESS GATE
            if (currentScore >= PASSING_SCORE) {
                log.info("WORKFLOW SUCCESS: Final Score {} exceeds threshold {}.", currentScore, PASSING_SCORE);
                publish(currentDraft);
                return;
            }

            // REFINEMENT STEP
            log.info("Score below threshold. Passing feedback to Refiner agent.");
            currentDraft = agent.refine(currentDraft, rubric.getFeedback());
        }
    }

    // --- Private Helper Logic (Encapsulation) ---

    private boolean isTopicProhibited(String topic) {
        return BLOCKED_TOPICS.stream().anyMatch(topic.toLowerCase()::contains);
    }

    private boolean isStructureValid(String draft) {
        if (draft == null) return false;
        String d = draft.toLowerCase();
        int words = draft.split("\\s+").length;
        return words >= 300 && d.contains("why") && d.contains("how") && d.contains("what");
    }

    private double calculateWeightedScore(EditorialRubric r) {
        // Weighted logic: Storytelling is 40%, Tone 30%, Simplicity 30%
        return (r.getStorytelling() * 0.4) + (r.getTone() * 0.3) + (r.getSimplicity() * 0.3);
    }

    private boolean shouldEscalate(ExecutionState state, EditorialRubric rubric, int attempt) {
        // Condition 1: Direct Bias/Sensitivity detected by LLM
        if (rubric.isBiasFlag()) return true;

        // Condition 2: AI is no longer improving (Stagnation)
        if (state.isStagnating()) return true;

        // Condition 3: We have exhausted all retries
        return attempt == MAX_RETRIES;
    }

    private void handleEscalation(String draft, EditorialRubric rubric, int attempt) {
        String reason = rubric.isBiasFlag() ? "SENSITIVITY/BIAS" : "STAGNATION/MAX_RETRIES";
        log.error("--- HUMAN INTERVENTION REQUIRED ---");
        log.error("Reason: {}", reason);
        log.error("Final Feedback: {}", rubric.getFeedback());
        log.error("Draft pending review: {}", draft.substring(0, Math.min(100, draft.length())) + "...");
    }

    private void publish(String draft) {
        log.info("Draft officially published to LinkedIn/Blog Platform.");
        log.info(draft);
        // System.out.println(draft);
    }
}