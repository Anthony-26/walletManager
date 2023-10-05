package com.example.walletmanager.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.walletmanager.entity.Stock;
import com.example.walletmanager.exception.CustomExceptions.ResponseParseException;
import com.example.walletmanager.exception.CustomExceptions.TickerNotFoundException;
import com.example.walletmanager.service.AlphaVantageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

@Service
public class AlphaVantageServiceImpl implements AlphaVantageService {

    @Value("${alphavantage.api.key}")
    private String apiKey;

    @Override
    public Stock findStockByTicker(String ticker) {
            if (ticker == null || ticker.trim().isEmpty()) 
                throw new TickerNotFoundException("Ticker cannot be null or empty");
            

            WebClient client = WebClient.builder().baseUrl("https://www.alphavantage.co").build();
            Stock result = client.get()
                    .uri(uriBuilder -> uriBuilder.path("/query")
                            .queryParam("function", "GLOBAL_QUOTE")
                            .queryParam("symbol", ticker)
                            .queryParam("apikey", apiKey)
                            .build())
                    .retrieve()
                    .onStatus(HttpStatusCode::isError,
                            response -> Mono.error(new RuntimeException("API request failed with client error")))
                    .onStatus(HttpStatusCode::is5xxServerError,
                            response -> Mono.error(new RuntimeException("API request failed with server error")))
                    .bodyToMono(String.class)
                    .map(this::extractStockFromResponse)
                    .block();

            return result;
}

    private Stock extractStockFromResponse(String response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode globalQuoteNode = rootNode.get("Global Quote");

            if(!globalQuoteNode.has("01. symbol")) throw new TickerNotFoundException("Ticker does not exist"); 

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            Stock stock = new Stock(globalQuoteNode.get("01. symbol").asText(),
                                new BigDecimal(globalQuoteNode.get("05. price").asText()),
                                new BigDecimal(globalQuoteNode.get("02. open").asText()),
                                new BigDecimal(globalQuoteNode.get("03. high").asText()),
                                new BigDecimal(globalQuoteNode.get("04. low").asText()),
                                new BigDecimal(globalQuoteNode.get("09. change").asText()),
                                new BigDecimal(globalQuoteNode.get("10. change percent").asText().replace("%", "")),
                                LocalDate.parse(globalQuoteNode.get("07. latest trading day").asText(), formatter)
                                );
            
            return stock;
        } catch (JsonProcessingException e) {
            throw new ResponseParseException("Failed to parse the response", e);
        } 
    }
}   