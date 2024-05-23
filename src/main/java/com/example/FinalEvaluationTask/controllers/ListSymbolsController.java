package com.example.FinalEvaluationTask.controllers;



import com.example.FinalEvaluationTask.constants.Constants;
import com.example.FinalEvaluationTask.responsedto.IndexConstituentsResponse;
import com.example.FinalEvaluationTask.dto.IndexNameRequest;
import com.example.FinalEvaluationTask.services.ListSymbolsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.API)
public class ListSymbolsController {
    @Autowired
    private ListSymbolsService listSymbolsService;

    @PostMapping(Constants.LIST_SYMBOLS)
    public IndexConstituentsResponse listSymbols(@RequestBody IndexNameRequest labelRequest) {
        return listSymbolsService.fetchDataAndPersist(labelRequest.getIndexName());
    }
}

