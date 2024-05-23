package com.example.FinalEvaluationTask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndexConstituent {
    private String symbol;
    private String displayName;
    private Double ltp;
    private Double close;
    private Double changePct;
}

