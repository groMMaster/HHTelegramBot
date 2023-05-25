package ru.relax.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.relax.model.Data;
import ru.relax.model.Vacancy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class HhApiService {
    static ObjectMapper objectMapper = new ObjectMapper();

    private static String sendRequestByTag(String vacancyTag) {
        var query = "https://api.hh.ru/vacancies?text=" + vacancyTag + "&from=suggest_post&area=3";

        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(query).openConnection();

            connection.setRequestMethod("GET");
            connection.setUseCaches(false);

            connection.connect();

            var sb = new StringBuilder();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                var in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String line;
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                    sb.append("\n");
                }

                return sb.toString();
            }

        } catch (Throwable cause) {
            cause.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        //TEMP
        return null;
    }

    private static ArrayList<Vacancy> mapToVacancies(String rawData) throws JsonProcessingException{
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        var data = objectMapper.readValue(rawData, Data.class);
        return data.getItems();
    }

    public static ArrayList<Vacancy> getVacanciesByTag(String vacancyTag) {
        var rawVacancies = sendRequestByTag(vacancyTag);
        var vacancies = new ArrayList<Vacancy>();

        try {
            vacancies = mapToVacancies(rawVacancies);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return vacancies;
    }
}
