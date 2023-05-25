package ru.relax;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.relax.controller.HhApiController;
import ru.relax.model.Data;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HhApiApplication {

    public static void main(String[] args) {
        for (var a : HhApiController.GetVacanciesByTag("Программист")) {
            System.out.println(a);
        }
    }
}

