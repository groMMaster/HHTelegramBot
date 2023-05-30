package ru.relax.service.impl;

import javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.relax.controller.NodeController;
import ru.relax.entity.VacancyTag;
import ru.relax.service.MainService;
import ru.relax.service.ProduceService;

@Service
public class MainServiceImpl implements MainService {
    private final ProduceService produceService;
    private final NodeController nodeController;

    public MainServiceImpl(ProduceService produceService, NodeController nodeController) {
        this.produceService = produceService;
        this.nodeController = nodeController;
    }

    @Override
    public void processSearchMessageUpdates(Update update) {
        var chatId = update.getMessage().getChatId();
        var tag = update.getMessage().getText();
        nodeController.addTagToUser(chatId, tag);

        var vacancies = nodeController.getNewVacanciesByUser(chatId, tag);

        for (var vacancy : vacancies) {
            var sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText(vacancy.toString());
            produceService.produceAnswerMessage(sendMessage);
        }
    }

    @Override
    public void processStartCommandUpdates(Update update) {
        var userId = update.getMessage().getChatId();
        nodeController.addUser(userId);
    }

    @Override
    public void processRemoveCommandUpdates(Update update) {
        var chatId = update.getMessage().getChatId();
        var tag = update.getMessage().getText();
        try {
            nodeController.removeVacancyTag(chatId, tag);
        } catch (NotFoundException e) {
            var sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText("У вас не сохранено вакансии " + tag);
            produceService.produceAnswerMessage(sendMessage);
        }
    }

    @Override
    public void processGetAllCommandUpdates(Update update) {
        var chatId = update.getMessage().getChatId();
        var tags = nodeController.getVacancyTagsByChatId(chatId);
        var result = String.join("\n", tags.stream().map(VacancyTag::getName).toList());
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Ваши сохраненные запросы:\n" + result);
        produceService.produceAnswerMessage(sendMessage);
    }
}
