package ru.relax.service.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.relax.service.ConsumerService;
import ru.relax.service.MainService;

import static ru.relax.model.RabbitQueue.*;


@Service
@Log4j
public class ConsumerServiceImpl implements ConsumerService {
    private final MainService mainService;

    public ConsumerServiceImpl(MainService mainService) {
        this.mainService = mainService;
    }

    @Override
    @RabbitListener(queues = SEARCH_MESSAGE_UPDATE)
    public void consumeSearchMessageUpdates(Update update) {
        mainService.processSearchMessageUpdates(update);
    }

    @Override
    @RabbitListener(queues = START_COMMAND_UPDATE)
    public void consumeStartCommandUpdates(Update update) {
        mainService.processStartCommandUpdates(update);
    }

    @Override
    @RabbitListener(queues = REMOVE_COMMAND_UPDATE)
    public void consumeRemoveCommandUpdates(Update update) {
        mainService.processRemoveCommandUpdates(update);
    }

    @Override
    @RabbitListener(queues = GET_ALL_VACANCIES_COMMAND)
    public void consumeGetAllCommandUpdates(Update update) {
        mainService.processGetAllCommandUpdates(update);
    }
}
