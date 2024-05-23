package com.example.FinalEvaluationTask.services;

import com.example.FinalEvaluationTask.constants.Constants;
import com.example.FinalEvaluationTask.exceptions.FetchDataException;
import com.example.FinalEvaluationTask.responsedto.ApiResponse;
import com.example.FinalEvaluationTask.resttemplete.RestTemplateService;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ListAllDataService {

    @Getter
    private Map<String, String> labelToKeywordsMap;

    private final RestTemplateService restTemplateService;
    @Autowired
    public ListAllDataService(RestTemplateService restTemplateService) {
        this.restTemplateService = restTemplateService;
    }

    @Value(Constants.API_KEY)
    private String apiKey;

    @Value(Constants.API_URL)
    private String url;

    @PostConstruct
    public void fetchData() {

        HttpHeaders headers = new HttpHeaders();
        headers.set(Constants.APIKEY, apiKey);

        ResponseEntity<ApiResponse> response = restTemplateService.sendGetRequest(url, headers, ApiResponse.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            ApiResponse responseData = response.getBody();

            if (responseData != null) {

                List<String> labels = responseData.getLabels();
                List<String> keywords = responseData.getKeywords();

                labelToKeywordsMap = new HashMap<>();
                int i = 0;
                for (String label : labels) {
                    labelToKeywordsMap.put(label, keywords.get(i));
                    i++;
                }


            } else {
                throw new FetchDataException(Constants.FAIL_TO_FETCH);
            }
        } else {
            throw new FetchDataException(Constants.FAIL_TO_FETCH);
        }
    }

    public List<String> getLabels() {
        if (labelToKeywordsMap != null) {
            return new ArrayList<>(labelToKeywordsMap.keySet());
        }
        return Collections.emptyList();
    }
}

