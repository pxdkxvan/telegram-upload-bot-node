package com.pxkdxvan.node.command;

import com.pxkdxvan.node.factory.LinkFactory;
import com.pxkdxvan.node.model.BotFile;
import com.pxkdxvan.node.model.BotUser;
import com.pxkdxvan.node.service.BotFileService;
import com.pxkdxvan.node.service.CommandService;
import com.pxkdxvan.node.statemachine.BotEvent;
import com.pxkdxvan.node.statemachine.BotState;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import static com.pxkdxvan.node.config.CommandConstants.*;

@Component(LOAD_COMMAND)
public final class LoadCommand extends Command {

    private final CommandService commandService;
    private final BotFileService botFileService;

    private final LinkFactory linkFactory;

    LoadCommand(@Lazy CommandService commandService, BotFileService botFileService, LinkFactory linkFactory) {
        super(LOAD_COMMAND, LOAD_DESCRIPTION);

        this.commandService = commandService;
        this.botFileService = botFileService;

        this.linkFactory = linkFactory;
    }

    @Override
    public String execute(Update update) {
        BotUser botUser = commandService.getBotUser(update);
        if (!botUser.getAuthorized() || !botUser.getVerified())
            return UNAUTHORIZED_MESSAGE;

        BotState currentState = commandService.getCurrentBotState(botUser);
        if (currentState == BotState.FREE_TO_USE) {
            commandService.changeBotState(botUser, BotEvent.LOAD);
            return LOAD_START_MESSAGE;
        }

        Message message = update.getMessage();
        if (!message.hasPhoto() && !message.hasDocument())
            return LOAD_INVALID_FORMAT_MESSAGE;

        BotFile botFile = message.hasPhoto() ?
                botFileService.processAndFetchPhoto(message.getPhoto()) :
                botFileService.processAndFetchDocument(message.getDocument());

        botUser.bindFile(botFile);

        String sharedFileId = String.valueOf(botFile.getShareId());
        String sourceLink = linkFactory.createSource(sharedFileId);

        commandService.changeBotState(botUser, BotEvent.DATA_RECEIVED);

        return LOAD_END_PATTERN.formatted(sourceLink);
    }

}
