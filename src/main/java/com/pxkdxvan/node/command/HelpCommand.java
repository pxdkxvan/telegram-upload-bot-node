package com.pxkdxvan.node.command;

import com.pxkdxvan.node.service.CommandService;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import org.telegram.telegrambots.meta.api.objects.Update;

import static com.pxkdxvan.node.config.CommandConstants.*;

@Component(HELP_COMMAND)
public final class HelpCommand extends Command {

    private final CommandService commandService;

    HelpCommand(@Lazy CommandService commandService) {
        super(HELP_COMMAND, HELP_DESCRIPTION);
        this.commandService = commandService;
    }

    @Override
    public String execute(Update update) {
        return HELP_MESSAGE + commandService.printAvailableCommandList();
    }

}
