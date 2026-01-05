package org.nirmal.agentic.ai.patterns;



public class ExecutionState {
    private double lastScore = 0.0;
    private int count = 0;
    private boolean stagnating = false;

    public void update(double currentScore) {
        count++;
        // If we've tried before and the score didn't improve, mark as stagnant
        if (count > 1 && currentScore <= lastScore) {
            this.stagnating = true;
        }
        this.lastScore = currentScore;
    }

    public boolean isStagnating() {
        return stagnating;
    }
}