package ru.relax.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface MainService {
    void processSearchMessageUpdates(Update update);
    void processStartCommandUpdates(Update update);
    void processRemoveCommandUpdates(Update update);
    void processGetAllCommandUpdates(Update update);
}
