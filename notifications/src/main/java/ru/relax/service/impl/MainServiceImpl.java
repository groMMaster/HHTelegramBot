package ru.relax.service.impl;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.relax.controller.NotificationsController;
import ru.relax.service.MainService;
import ru.relax.service.ProduceService;

@Service
public class MainServiceImpl implements MainService {
    private final ProduceService produceService;
    private final NotificationsController notificationsController;

    public MainServiceImpl(ProduceService produceService, NotificationsController notificationsController) {
        this.produceService = produceService;
        this.notificationsController = notificationsController;
    }

    @Override
    public void processSearchMessageUpdates(Update update) {
        var chatId = update.getMessage().getChatId();
        var tag = update.getMessage().getText();
        notificationsController.addTagToUser(chatId, tag);

        var vacancies = notificationsController.getNewVacanciesByUser(chatId, tag);

        for (var vacancy : vacancies) {
            var sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText(vacancy.toString());
            System.out.println(vacancy.toString());
        }
    }

    @Override
    public void processStartCommandUpdates(Update update) {
        var userId = update.getMessage().getChatId();
        notificationsController.addUser(userId);
    }

    @Override
    public void processRemoveCommandUpdates(Update update) {
        var chatId = update.getMessage().getChatId();
        var tag = update.getMessage().getText();
        notificationsController.removeVacancyTag(chatId, tag);
    }
}
