package com.example.FinalEvaluationTask.responsedto;

import lombok.Data;

import java.util.List;

@Data
public class ApiResponse {
    private List<String> datasets;
    private List<String> labels;
    private List<String> keywords;

}
