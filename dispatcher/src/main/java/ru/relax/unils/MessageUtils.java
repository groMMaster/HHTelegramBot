package ru.relax.unils;

import lombok.var;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class MessageUtils {
    public static SendMessage generateSendMessageWithText(Update update, String text) {
        var sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText(text);
        return sendMessage;
    }
}
