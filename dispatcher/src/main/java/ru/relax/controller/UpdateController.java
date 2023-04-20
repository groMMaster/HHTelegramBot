package ru.relax.controller;

import lombok.extern.log4j.Log4j;
import lombok.var;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.relax.service.UpdateProducer;
import ru.relax.unils.MessageUtils;

import static ru.relax.model.RabbitQueue.DOC_MESSAGE_UPDATE;
import static ru.relax.model.RabbitQueue.TEXT_MESSAGE_UPDATE;

@Component
@Log4j
public class UpdateController {
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

        if (update.getMessage() != null) {
            distributeMessageByType(update);
        } else {
            log.error("Received unsupported message type");
        }
    }

    private void distributeMessageByType(Update update) {
        var message = update.getMessage();
        
        if (message.hasText()) {
            processTextMessageUpdate(update);
        } else if (message.hasDocument()) {
            processDocUpdate(update);
        } else {
            setUnsupportedMessageTypeView(update);
        }
    }

    public void setView(SendMessage sendMessage) {
        telegramBot.sendAnswerMessage(sendMessage);
    }

    private void processTextMessageUpdate(Update update) {
        updateProducer.produce(TEXT_MESSAGE_UPDATE, update);
    }

    private void processDocUpdate(Update update) {
        updateProducer.produce(DOC_MESSAGE_UPDATE, update);
        var received = MessageUtils.generateSendMessageWithText(update, "Файл получен. Обрабатывается");
        setView(received);
    }

    private void setUnsupportedMessageTypeView(Update update) {
        var received = MessageUtils.generateSendMessageWithText(update, "Неподдерживаемый тип данных");
        setView(received);
    }
}
