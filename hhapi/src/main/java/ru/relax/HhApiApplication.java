package ru.relax;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.var;
import ru.relax.model.Data;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HhApiApplication {
    static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {
        var query = "https://api.hh.ru/vacancies?text=C%23+junior&from=suggest_post&area=3";

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

                createMessage(sb.toString());
            }

        } catch (Throwable cause) {
            cause.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public static void createMessage(String content) throws JsonProcessingException {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        var data = objectMapper.readValue(content, Data.class);
        for (var vacancy : data.getItems()) {
            System.out.println(vacancy.toString() + "\n");
        }

        var t = data.getItems().get(4);
        System.out.println(t.getId());
    }
}