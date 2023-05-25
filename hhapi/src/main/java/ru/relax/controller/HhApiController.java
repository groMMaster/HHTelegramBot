package ru.relax.controller;


import ru.relax.model.Vacancy;
import ru.relax.services.HhApiServiceOld;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class HhApiController {
    public static ArrayList<Long> GetVacanciesIdsByTag(String vacancyTag){
        ArrayList<Long> vacanciesIds = HhApiServiceOld.getVacanciesByTag(vacancyTag)
                .stream()
                .map(Vacancy::getId)
                .collect(Collectors.toCollection(ArrayList::new));

        return vacanciesIds;
    }

    public static ArrayList<Vacancy> GetVacanciesByTag(String vacancyTag){
        ArrayList<Vacancy> vacancies = HhApiServiceOld.getVacanciesByTag(vacancyTag);
        return vacancies;
    }
}
