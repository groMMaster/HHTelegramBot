package ru.relax.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import ru.relax.model.Data;
import ru.relax.model.Vacancy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;


public class HhApiService {
    static ObjectMapper objectMapper = new ObjectMapper();


    private static String sendRequestByTag(String vacancyTag) {
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://api.hh.ru/vacancies?text=" + vacancyTag + "&from=suggest_post&area=3"))
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return response.body();
    }

    private static ArrayList<Vacancy> mapToVacancies(String rawData) throws JsonProcessingException{
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        var data = objectMapper.readValue(rawData, Data.class);
        return data.getItems();
    }

    public static ArrayList<Vacancy> getVacanciesByTag(String vacancyTag) {
        var rawVacancies = sendRequestByTag(vacancyTag);
        sendRequestByTag(vacancyTag);
        var vacancies = new ArrayList<Vacancy>();

        try {
            vacancies = mapToVacancies(rawVacancies);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return vacancies;
    }
}
