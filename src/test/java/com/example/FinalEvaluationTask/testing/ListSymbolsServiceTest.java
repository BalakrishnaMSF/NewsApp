package com.example.FinalEvaluationTask.testing;

import com.example.FinalEvaluationTask.constants.Constants;
import com.example.FinalEvaluationTask.responsedto.IndexConstituentsResponse;
import com.example.FinalEvaluationTask.responsedto.SymbolApiResponse;
import com.example.FinalEvaluationTask.dto.IndexConstituent;
import com.example.FinalEvaluationTask.services.ListAllDataService;
import com.example.FinalEvaluationTask.services.ListAllService;
import com.example.FinalEvaluationTask.services.ListSymbolsService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class ListSymbolsServiceTest {

    @InjectMocks
    private ListSymbolsService listSymbolsService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ListAllDataService listAllService;

    @Value(Constants.API_KEY)
    private String apiKey;

    @Value(Constants.API_CONSTITUENT_URL)
    private String apiUrl;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFetchDataAndPersist() {
        // Mock the data that should be returned by ListAllService
        Map<String, String> labelToKeywordsMap = new HashMap<>();
        labelToKeywordsMap.put(Constants.LABEL1, Constants.KEYWORD1);
        when(listAllService.getLabelToKeywordsMap()).thenReturn(labelToKeywordsMap);

        // Mock the response from the external API
        SymbolApiResponse symbolApiResponse = new SymbolApiResponse();
        List<IndexConstituent> constituents = Collections.singletonList(
                new IndexConstituent(Constants.SYMBOL,Constants.DISPLAY_NAME , 100.0, 95.0, 5.0)
        );

        symbolApiResponse.setIndexConstituents(constituents);

        ResponseEntity<SymbolApiResponse> responseEntity = new ResponseEntity<>(symbolApiResponse, HttpStatus.OK);

        // Set up the mock response using the provided URL
        when(restTemplate.exchange(eq(apiUrl + Constants.KEYWORD), eq(HttpMethod.GET), any(HttpEntity.class), eq(SymbolApiResponse.class))).thenReturn(responseEntity);

        // Call the method being tested
        String label = Constants.LABEL1;
        IndexConstituentsResponse result = listSymbolsService.fetchDataAndPersist(label);

        // Verify the result
        assertNotNull(result);
        List<IndexConstituent> indexConstituents = result.getIndexConstituents();
        assertEquals(1, indexConstituents.size());
        assertEquals(Constants.SYMBOL, indexConstituents.get(0).getSymbol());
        assertEquals(Constants.DISPLAY_NAME, indexConstituents.get(0).getDisplayName());
        assertEquals(100.0, indexConstituents.get(0).getLtp(), 0.01);
        assertEquals(95.0, indexConstituents.get(0).getClose(), 0.01);
        assertEquals(5.0, indexConstituents.get(0).getChangePct(), 0.01);
    }
}