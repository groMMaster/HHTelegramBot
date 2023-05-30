package ru.relax.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.relax.dao.UserDAO;
import ru.relax.service.ProduceService;

@Configuration
@EnableScheduling
public class Scheduler {
    private final NodeController nodeController;
    private final UserDAO userDAO;
    private final ProduceService produceService;

    @Autowired
    public Scheduler(NodeController nodeController, UserDAO userDAO, ProduceService produceService) {
        this.nodeController = nodeController;
        this.userDAO = userDAO;
        this.produceService = produceService;
    }


    @Scheduled(fixedDelay = 3600000L)
    @Async
    public void sendNotifications() {
        for (var user: userDAO.findAll()) {
            var sendMessage = new SendMessage();
            var userId = user.getId();
            sendMessage.setChatId(userId);

            for (var vacancy : nodeController.getNewVacanciesByUser(userId)) {
                sendMessage.setText(vacancy.toString());
                produceService.produceAnswerMessage(sendMessage);
            }
        }
    }
}