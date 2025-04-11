package com.pxkdxvan.node.command;

import com.pxkdxvan.node.model.BotFile;
import com.pxkdxvan.node.model.BotUser;
import com.pxkdxvan.node.service.CommandService;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static com.pxkdxvan.node.config.CommandConstants.*;

@Component(CLEAR_COMMAND)
public final class ClearCommand extends Command {

    private final CommandService commandService;

    ClearCommand(@Lazy CommandService commandService) {
        super(CLEAR_COMMAND, CLEAR_DESCRIPTION);
        this.commandService = commandService;
    }

    @Override
    public String execute(Update update) {
        BotUser botUser = commandService.getBotUser(update);
        if (!botUser.getAuthorized() || !botUser.getVerified())
            return UNAUTHORIZED_MESSAGE;

        List<BotFile> files = botUser.getFiles();
        if (files == null || files.isEmpty())
            return CLEAR_EMPTY_MESSAGE;

        botUser.getFiles().clear();

        return CLEAR_END_MESSAGE;
    }

}
