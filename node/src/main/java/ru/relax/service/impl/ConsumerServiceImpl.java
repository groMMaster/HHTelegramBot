package ru.relax.service.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.relax.service.ConsumerService;
import ru.relax.service.MainService;

import static ru.relax.model.RabbitQueue.SEARCH_MESSAGE_UPDATE;


@Service
@Log4j
public class ConsumerServiceImpl implements ConsumerService {
    private final MainService mainService;

    public ConsumerServiceImpl(MainService mainService) {
        this.mainService = mainService;
    }

    @Override
    @RabbitListener(queues = SEARCH_MESSAGE_UPDATE)
    public void consumeTextMessageUpdates(Update update) {
        log.debug("Text message is received");
        mainService.processTextMessage(update);
    }
}
