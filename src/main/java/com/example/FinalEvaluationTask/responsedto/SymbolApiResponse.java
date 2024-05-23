package com.example.FinalEvaluationTask.responsedto;

import com.example.FinalEvaluationTask.dto.IndexConstituent;
import lombok.Data;

import java.util.List;

@Data
public class SymbolApiResponse {
    private List<IndexConstituent> indexConstituents;
}

