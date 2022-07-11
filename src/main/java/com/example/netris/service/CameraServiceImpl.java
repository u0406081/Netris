package com.example.netris.service;

import com.example.netris.dto.*;
import com.example.netris.exception.CustomException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class CameraServiceImpl implements CameraService {

    @Value("${mocky.url}")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private HttpEntity httpEntity;

    @Override
    public List<AggregateResult> getAggregateCamers() throws CustomException {
        long start = System.nanoTime();
        List<AggregateResult> result;
        try {
            List<Camera> listCameras = proceedRequest(url, new TypeReference<List<Camera>>() {});
            List<CompletableFuture<AggregateResult>> listFutures = listCameras.stream()
                    .map(this::processCamera)
                    .collect(Collectors.toList());

            result = listFutures.stream()
                    .map(CompletableFuture::join)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new CustomException("An error occurred in the method");
        }
        String ms = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start) / 1000 + " ms";
        System.out.println(ms);
        return result;
    }

    private <T> T proceedRequest(String url, TypeReference<T> responseClassTypeReference) {
        T responseDto = null;
        String body = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class).getBody();
        try {
            responseDto = mapper.readValue(body, responseClassTypeReference);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return responseDto;
    }

    @Async
    public CompletableFuture<AggregateResult> processCamera(Camera camera) {
        return getSource(camera.getSourceDataUrl())
            .thenCombine(getToken(camera.getTokenDataUrl()), (first, second) -> {
                AggregateResult aggregateResult = new AggregateResult();
                aggregateResult.setId(camera.getId());
                aggregateResult.setUrlType(first.getUrlType());
                aggregateResult.setVideoUrl(first.getVideoUrl());
                aggregateResult.setValue(second.getValue());
                aggregateResult.setTtl(second.getTtl());
                return aggregateResult;
            });
    }

    @Async
    public CompletableFuture<Source> getSource(String sourceUrl) {
        return CompletableFuture.supplyAsync(() -> proceedRequest(sourceUrl, new TypeReference<Source>() {
        }));
    }

    @Async
    public CompletableFuture<Token> getToken(String tokenUrl) {
        return CompletableFuture.supplyAsync(() -> proceedRequest(tokenUrl, new TypeReference<Token>() {
        }));
    }
}
