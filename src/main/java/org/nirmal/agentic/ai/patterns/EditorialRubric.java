package org.nirmal.agentic.ai.patterns;

import lombok.Data;

@Data
public class EditorialRubric {
    private int storytelling;      // 1-10
    private int tone;              // 1-10
    private int simplicity;        // 1-10
    private boolean biasFlag;      // Detects religion, gender, or regional bias
    private String feedback;       // Instructions for the Refiner
}