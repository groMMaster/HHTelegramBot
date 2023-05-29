package ru.relax.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import ru.relax.dao.UserDAO;

@Configuration
@EnableScheduling
public class Scheduler {
    private final NotificationsController notificationsController;
    private final UserDAO userDAO;

    @Autowired
    public Scheduler(NotificationsController notificationsController, UserDAO userDAO) {
        this.notificationsController = notificationsController;
        this.userDAO = userDAO;
    }


    @Scheduled(fixedDelay = 10000L)
    @Async
    public void foo() {
        for (var user: userDAO.findAll()) {
            System.out.println(notificationsController.getNewVacanciesByUser(user.getId()));
        }
    }
}
