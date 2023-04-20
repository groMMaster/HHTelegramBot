package ru.relax.service.impl;

import lombok.var;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.relax.dao.RawDataDAO;
import ru.relax.entity.RawData;
import ru.relax.service.MainService;
import ru.relax.service.ProduceService;

@Service
public class MainServiceImpl implements MainService {
    private final RawDataDAO rawDataDAO;
    private final ProduceService produceService;

    public MainServiceImpl(RawDataDAO rawDataDAO, ProduceService produceService) {
        this.rawDataDAO = rawDataDAO;
        this.produceService = produceService;
    }

    @Override
    public void processTextMessage(Update update) {
        saveRawData(update);

        var message = update.getMessage();
        var sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText("Node");
        produceService.produceAnswer(sendMessage);
    }

    private void saveRawData(Update update) {
        var rawData = RawData.builder()
                .event(update)
                .build();
        rawDataDAO.save(rawData);
    }
}
