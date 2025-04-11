package com.pxkdxvan.node.command;

import com.pxkdxvan.node.model.BotUser;
import com.pxkdxvan.node.statemachine.BotEvent;
import com.pxkdxvan.node.statemachine.BotState;
import com.pxkdxvan.node.service.CommandService;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import org.telegram.telegrambots.meta.api.objects.Update;

import static com.pxkdxvan.node.config.CommandConstants.*;

@Component(CANCEL_COMMAND)
public final class CancelCommand extends Command {

    private final CommandService commandService;

    CancelCommand(@Lazy CommandService commandService) {
        super(CANCEL_COMMAND, CANCEL_DESCRIPTION);
        this.commandService = commandService;
    }

    @Override
    public String execute(Update update) {
        BotUser botUser = commandService.getBotUser(update);

        BotState currentState = commandService.getCurrentBotState(botUser);
        if (currentState == BotState.FREE_TO_USE)
            return CANCEL_NOTHING_MESSAGE;

        commandService.changeBotState(botUser, BotEvent.CANCEL);

        return CANCEL_MESSAGE;
    }

}
