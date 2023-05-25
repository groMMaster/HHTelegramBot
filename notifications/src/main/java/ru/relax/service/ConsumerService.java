package ru.relax.service;


import org.telegram.telegrambots.meta.api.objects.Update;

public interface ConsumerService {
    void consumeSearchMessageUpdates(Update update);
    void consumeStartCommandUpdates(Update update);
    void consumeRemoveCommandUpdates(Update update);
}
