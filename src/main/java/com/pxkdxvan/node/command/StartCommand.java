package com.pxkdxvan.node.command;

import org.springframework.stereotype.Component;

import org.telegram.telegrambots.meta.api.objects.Update;

import static com.pxkdxvan.node.config.CommandConstants.*;

@Component(START_COMMAND)
public final class StartCommand extends Command {

    StartCommand() {
        super(START_COMMAND, START_DESCRIPTION);
    }

    @Override
    public String execute(Update update) {
        return START_MESSAGE;
    }

}
