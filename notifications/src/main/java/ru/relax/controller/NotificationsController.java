package ru.relax.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.relax.dao.UserDAO;
import ru.relax.entity.User;
import ru.relax.entity.ViewedVacancy;
import ru.relax.entity.VacancyTag;
import ru.relax.model.Vacancy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class NotificationsController {

    UserDAO userDAO;

    @Autowired
    public NotificationsController(UserDAO userDAO){
        this.userDAO = userDAO;
/*
        for (var user: userDAO.findAll()) {
            for (var vac:GetNewVacanciesByUser(user)) {
                System.out.println(vac);
            }
        }*/
    }

    public void addUser(Long id) {
        userDAO.save(new User(id));
    }

    public List<Vacancy> getNewVacanciesByUser(Long userId, String tag) {
        var user = userDAO.getById(userId);
        var allVacancies = GetVacanciesByTags(user.getVacancyTags());
        var newVacancies = removeViewedVacancies(user, allVacancies);

        if (newVacancies.size() > 0){
            user.setViewedVacancies(
                    Stream.concat(
                            user.getViewedVacancies().stream(),
                            newVacancies.stream()
                                    .map(n -> ViewedVacancy.builder().VacancyId(n.getId()).build()))
                            .toList());

            userDAO.save(user);
            userDAO.flush();
        }

        return newVacancies;
    }

    public void addTagToUser(Long chatId,  String tagName){
        var vacancyTag = new VacancyTag(tagName);
        var user = userDAO.getById(chatId);
        var vacancyTags = user.getVacancyTags();
        vacancyTags.add(vacancyTag);
        user.setVacancyTags(vacancyTags);
        userDAO.save(user);
        userDAO.flush();
    }

    public void removeVacancyTag(Long chatId,  String tagName){
        var user = userDAO.getById(chatId);
        user.getVacancyTags().removeIf(v -> v.getName().equals(tagName));
        userDAO.save(user);
        userDAO.flush();
    }

    private List<Vacancy> removeViewedVacancies(User user, ArrayList<Vacancy> allVacancies){
        return  allVacancies.stream()
                .filter(vacancy -> !user.getViewedVacancies().stream()
                        .anyMatch(a -> a.getVacancyId().equals(vacancy.getId())))
                .collect(Collectors.toList());
    }

    private ArrayList<Vacancy> GetVacanciesByTags(List<VacancyTag> vacancyTags){
        var vacancies = new ArrayList<Vacancy>();

        for (var vacancyTag:vacancyTags) {
            vacancies.addAll(HhApiController.GetVacanciesByTag(vacancyTag.getName()));
        }

        return vacancies;
    }

/*    private ArrayList<ViewedVacancy> GetVacanciesByTags(List<VacancyTag> vacancyTags){
        var result = new ArrayList<ViewedVacancy>();

        for (var vacancyTag : vacancyTags){
            if (vacancyTag.getName().equals("Программист Python")){
                result.add(ViewedVacancy
                        .builder()
                        .VacancyId(101L)
                        .build());
            }
            else if (vacancyTag.getName().equals("АНАЛИТИКА")){
                result.add(ViewedVacancy
                        .builder()
                        .VacancyId(202L)
                        .build());
            }
        }

        return result;
    }*/
}
