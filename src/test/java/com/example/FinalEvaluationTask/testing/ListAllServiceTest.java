package com.example.FinalEvaluationTask.testing;

import com.example.FinalEvaluationTask.constants.Constants;
import com.example.FinalEvaluationTask.responsedto.ApiResponse;
import com.example.FinalEvaluationTask.responsedto.IndexNameResponse;
import com.example.FinalEvaluationTask.services.ListAllService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class ListAllServiceTest {

    @Value(Constants.API_KEY)
    private String apiKey;

    @Value(Constants.API_URL)
    private String url;

    @InjectMocks
    private ListAllService listAllService;

    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFetchDataIfNotFetched_Success() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(Constants.APIKEY,apiKey); // Replace with a valid API key

        ApiResponse mockApiResponse = new ApiResponse();
        mockApiResponse.setLabels(Arrays.asList(Constants.LABEL1, Constants.LABEL2));
        mockApiResponse.setKeywords(Arrays.asList(Constants.KEYWORD1, Constants.KEYWORD2));

        ResponseEntity<ApiResponse> mockResponseEntity = new ResponseEntity<>(mockApiResponse, HttpStatus.OK);

        // Mock the behavior of the RestTemplate
        Mockito.when(restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), ApiResponse.class))
                .thenReturn(mockResponseEntity);

        listAllService.fetchDataIfNotFetched();

        // Verify that labelToKeywordsMap and persistedData are populated correctly
        assertEquals(Arrays.asList(Constants.LABEL1, Constants.LABEL2), listAllService.getPersistedData().getIndexName());
    }

    @Test(expected = RuntimeException.class)
    public void testFetchDataIfNotFetched_Failure() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(Constants.APIKEY, Constants.API_KEY_VALUE); // Replace with a valid API key

        ResponseEntity<ApiResponse> mockResponseEntity = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        // Mock the behavior of the RestTemplate
        Mockito.when(restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), ApiResponse.class))
                .thenReturn(mockResponseEntity);

        listAllService.fetchDataIfNotFetched();
    }
}
