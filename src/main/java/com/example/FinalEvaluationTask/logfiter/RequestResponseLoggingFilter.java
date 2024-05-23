package com.example.FinalEvaluationTask.logfiter;

import com.example.FinalEvaluationTask.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Slf4j
@Component
@WebFilter
@Order(1)
public class RequestResponseLoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(httpServletRequest);

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(httpServletResponse);

        // Continue with the request
        chain.doFilter(requestWrapper, responseWrapper);

        // Log the request and response data as single-line JSON
        logRequest(requestWrapper);
        logResponse(responseWrapper);

        // Copy the response body back to the original response
        responseWrapper.copyBodyToResponse();
    }

    private void logRequest(ContentCachingRequestWrapper requestWrapper) {
        String requestBody = new String(requestWrapper.getContentAsByteArray());
        // Log the request body as a single-line JSON string
        log.info(Constants.REQUEST_BODY, requestBody);
    }

    private void logResponse(ContentCachingResponseWrapper responseWrapper) {
        String responseBody = new String(responseWrapper.getContentAsByteArray());
        // Log the response body as a single-line JSON string
        log.info(Constants.RESPONSE_BODY, responseBody);
    }

}