package com.example.FinalEvaluationTask.services;

import com.example.FinalEvaluationTask.constants.Constants;
import com.example.FinalEvaluationTask.exceptions.FetchDataException;
import com.example.FinalEvaluationTask.responsedto.ApiResponse;
import com.example.FinalEvaluationTask.resttemplete.RestTemplateService;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.FinalEvaluationTask.responsedto.IndexNameResponse;

import java.util.List;

@Slf4j
@Service
public class ListAllService {


    @Getter
    private IndexNameResponse persistedData;

    private final RestTemplateService restTemplateService;

    @Autowired
    public ListAllService(RestTemplateService restTemplateService) {
        this.restTemplateService = restTemplateService;
    }

    @Value(Constants.API_KEY)
    private String apiKey;

    @Value(Constants.API_URL)
    private String url;

    @PostConstruct
    public void fetchDataIfNotFetched() {

        HttpHeaders headers = new HttpHeaders();
        headers.set(Constants.APIKEY, apiKey);


        ResponseEntity<ApiResponse> response = restTemplateService.sendGetRequest(url, headers, ApiResponse.class);
        log.info("List All Data " + response.getBody());
        if (response.getStatusCode() == HttpStatus.OK) {
            ApiResponse responseData = response.getBody();

            if (responseData != null) {

                List<String> labels = responseData.getLabels();
                persistedData = new IndexNameResponse();
                persistedData.setIndexName(labels);

            } else {
                throw new FetchDataException(Constants.FAIL_TO_FETCH);
            }
        } else {
            throw new FetchDataException(Constants.FAIL_TO_FETCH);
        }
    }
}
