package com.pxkdxvan.node.command;

import com.pxkdxvan.node.model.BotUser;
import com.pxkdxvan.node.service.CommandService;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import org.telegram.telegrambots.meta.api.objects.Update;

import static com.pxkdxvan.node.config.CommandConstants.*;

@Component(LOGOUT_COMMAND)
public final class LogoutCommand extends Command {

    private final CommandService commandService;

    LogoutCommand(@Lazy CommandService commandService) {
        super(LOGOUT_COMMAND, LOGOUT_DESCRIPTION);
        this.commandService = commandService;
    }

    @Override
    public String execute(Update update) {
        BotUser botUser = commandService.getBotUser(update);
        if (!botUser.getAuthorized() || !botUser.getVerified())
            return UNAUTHORIZED_MESSAGE;

        botUser.setAuthorized(false);

        return LOGOUT_MESSAGE;
    }

}
