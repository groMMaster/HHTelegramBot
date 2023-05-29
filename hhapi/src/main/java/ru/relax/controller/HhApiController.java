package ru.relax.controller;

import ru.relax.model.Vacancy;
import ru.relax.services.HhApiService;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class HhApiController {
    public static ArrayList<Long> GetVacanciesIdsByTag(String vacancyTag){
        var vacanciesIds = HhApiService.getVacanciesByTag(vacancyTag)
                .stream()
                .map(v -> v.getId())
                .collect(Collectors.toCollection(ArrayList::new));

        return vacanciesIds;
    }

    public static ArrayList<Vacancy> GetVacanciesByTag(String vacancyTag){
        var vacancies = HhApiService.getVacanciesByTag(vacancyTag);
        return vacancies;
    }
}
