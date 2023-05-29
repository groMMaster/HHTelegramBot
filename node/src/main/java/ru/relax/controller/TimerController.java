/*
package ru.relax.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.relax.dao.UserDAO;

import java.util.Timer;
import java.util.TimerTask;

@Component
public class TimerController {

    private final Timer timer;
    private final NotificationsController notificationsController;
    private final UserDAO userDAO;

    @Autowired
    public TimerController(NotificationsController notificationsController, UserDAO userDAO) {
        this.notificationsController = notificationsController;
        this.userDAO = userDAO;
        this.timer = new Timer();

        StartTimer();
    }

    public void StartTimer(){
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for (var user: userDAO.findAll()) {
                    System.out.println(notificationsController.getNewVacanciesByUser(user.getId()));
                }
            }
        }, 2*60*1, 2*60*1);
    }
}
*/
