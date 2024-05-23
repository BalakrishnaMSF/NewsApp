package com.example.FinalEvaluationTask.controllers;


import com.example.FinalEvaluationTask.constants.Constants;
import com.example.FinalEvaluationTask.responsedto.IndexNameResponse;
import com.example.FinalEvaluationTask.services.ListAllService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(Constants.API)
public class ListAllController {
    @Autowired
    private ListAllService listAllService;

    @GetMapping(Constants.LIST_ALL)
    public IndexNameResponse listAll() {
        return listAllService.getPersistedData();
    }
}

