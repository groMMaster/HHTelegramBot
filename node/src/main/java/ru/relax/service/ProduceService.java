package ru.relax.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface ProduceService {
    void produceAnswerMessage(SendMessage sendMessage);
}
