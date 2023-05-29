package ru.relax.controller;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.relax.dao.UserDAO;
import ru.relax.dao.VacancyTagDAO;
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

    private final UserDAO userDAO;
    private final VacancyTagDAO vacancyTagDAO;


    @Autowired
    public NotificationsController(UserDAO userDAO, VacancyTagDAO vacancyTagDAO){
        this.userDAO = userDAO;
        this.vacancyTagDAO = vacancyTagDAO;
    }

    public void addUser(Long id) {
        userDAO.save(new User(id));
        userDAO.flush();
    }

    public List<Vacancy> getNewVacanciesByUser(Long userId, String... tag) {
        var user = userDAO.findById(userId).get();
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
        var user = userDAO.findById(chatId).get();
        var vacancyTags = user.getVacancyTags();
        if (vacancyTags.stream().anyMatch(v -> v.getName().equals(tagName)))
            return;
        vacancyTags.add(vacancyTag);
        user.setVacancyTags(vacancyTags);
        userDAO.save(user);
        userDAO.flush();
    }

    public void removeVacancyTag(Long chatId,  String tagName) throws NotFoundException {
        var vacancyTagOptional = userDAO.findById(chatId).get()
                .getVacancyTags()
                .stream().filter(v -> v.getName().equals(tagName)).findFirst();

        if (vacancyTagOptional.isEmpty())
            throw new NotFoundException("Vacancy tag not found");

        vacancyTagDAO.delete(vacancyTagOptional.get());
        vacancyTagDAO.flush();
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

    public List<VacancyTag> getVacancyTagsByChatId(Long chatId){
        var user = userDAO.findById(chatId).get();
        return user.getVacancyTags();
    }
}
