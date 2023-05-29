package ru.relax.controller;

import lombok.extern.log4j.Log4j;
import lombok.var;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.relax.service.UpdateProducer;
import ru.relax.unils.MessageUtils;

import static ru.relax.model.RabbitQueue.*;

@Component
@Log4j
public class UpdateController {
    private boolean isRemove;

    private TelegramBot telegramBot;
    private UpdateProducer updateProducer;

    public UpdateController(UpdateProducer updateProducer) {
        this.updateProducer = updateProducer;
    }

    public void registerBot(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void processUpdate(Update update) {
        if (update == null) {
            log.error("Update is null");
            return;
        }

        if (update.hasMessage() && update.getMessage().hasText()) {
            distributeMessageByType(update);
        } else {
            log.error("Received unsupported message type");
            setUnsupportedMessageTypeView(update);
        }
    }

    private void distributeMessageByType(Update update) {
        var message = update.getMessage();
        if (isRemove) {
            processRemove(update);
            isRemove = false;
            return;
        }

        switch (message.getText()) {
            case "/start": {
                processStartCommandUpdate(update);
                setHelloMessage(update);
                break;
            }
            case "/remove": {
                isRemove = true;
                break;
            }
            case "/getall": {
                processGetAllVacancies(update);
                break;
            }
            default: {
                processSearchQueryMessageUpdate(update);
            }
        }
    }

    public void setView(SendMessage sendMessage) {
        telegramBot.sendAnswerMessage(sendMessage);
    }

    private void processRemove(Update update) {
        updateProducer.produce(REMOVE_COMMAND_UPDATE, update);
    }

    private void processGetAllVacancies(Update update) {
        updateProducer.produce(GET_ALL_VACANCIES_COMMAND, update);
    }

    private void processSearchQueryMessageUpdate(Update update) {
        updateProducer.produce(SEARCH_MESSAGE_UPDATE, update);
    }

    private void  processStartCommandUpdate(Update update) {
        updateProducer.produce(START_COMMAND_UPDATE, update);
    }

    private void setUnsupportedMessageTypeView(Update update) {
        var received = MessageUtils.generateSendMessageWithText(update, "Неподдерживаемый тип данных");
        setView(received);
    }

    private void setHelloMessage(Update update) {
        var received = MessageUtils.generateSendMessageWithText(update,
                "Привет! Прочитай инструкцию по использованию бота: \n\n" +
                "Бот может уведомлять тебя о новых вакансиях. Для этого ему нужно передать запросы об интересующих тебя вакансиях, например \"Java junior developer\"\n" +
                "Бот сохранит твои запросы и оповестит тебя, когда появится навая подходящая вакансия \n\n" +
                "Для того, чтобы передать запрос достаточно отправить его обычным сообщением. Сразу после этого ты получишь выдачу актуальных вакансий\n" +
                "А чтобы удалить запрос выбери команду /remove в меню и...\n\n" +
                "Удачи!");
        setView(received);
    }


    public void sendMessage(Long chatId, String textMessage) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(textMessage);

        try {
            telegramBot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
