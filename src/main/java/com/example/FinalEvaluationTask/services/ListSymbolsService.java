package com.example.FinalEvaluationTask.services;

import com.example.FinalEvaluationTask.constants.Constants;
import com.example.FinalEvaluationTask.exceptions.FetchDataException;
import com.example.FinalEvaluationTask.exceptions.LabelNotFoundException;
import com.example.FinalEvaluationTask.exceptions.NoApiResponseException;
import com.example.FinalEvaluationTask.responsedto.IndexConstituentsResponse;
import com.example.FinalEvaluationTask.responsedto.SymbolApiResponse;
import com.example.FinalEvaluationTask.resttemplete.RestTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class ListSymbolsService {

    @Value(Constants.API_KEY)
    private String apiKey;

    @Value(Constants.API_CONSTITUENT_URL)
    private String constituents_url;

    private final RestTemplateService restTemplateService;
    private final ListAllDataService listAllService;

    @Autowired
    public ListSymbolsService(RestTemplateService restTemplateService, ListAllDataService listAllService) {
        this.restTemplateService = restTemplateService;
        this.listAllService = listAllService;
    }

    public IndexConstituentsResponse fetchDataAndPersist(String label) {
        List<String> labels = listAllService.getLabels();
        if (labels.contains(label)) {
            String keyword = listAllService.getLabelToKeywordsMap().get(label);
            String url = constituents_url + keyword + Constants.ONE;

            HttpHeaders headers = new HttpHeaders();
            headers.set(Constants.APIKEY, apiKey);

            ResponseEntity<SymbolApiResponse> response = restTemplateService.sendGetRequest(url, headers, SymbolApiResponse.class);
            log.info("List ALl Symbols Data " + response.getBody());
            if (response.getStatusCode() == HttpStatus.OK) {
                SymbolApiResponse responseData = response.getBody();
                if (responseData != null && responseData.getIndexConstituents() != null) {
                    IndexConstituentsResponse persistedData = new IndexConstituentsResponse();
                    persistedData.setIndexConstituents(responseData.getIndexConstituents());
                    return persistedData;
                } else {
                    throw new NoApiResponseException(Constants.NO_API_RESPONSE);
                }
            } else {
                throw new FetchDataException(Constants.FAIL_TO_FETCH);
            }
        } else {
            throw new LabelNotFoundException(Constants.LABEL_NOT_FOUND);
        }
    }

}
