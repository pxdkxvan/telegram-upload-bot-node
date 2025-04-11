package com.pxkdxvan.node.command;

import com.pxkdxvan.node.factory.LinkFactory;
import com.pxkdxvan.node.model.BotDocument;
import com.pxkdxvan.node.model.BotFile;
import com.pxkdxvan.node.model.BotUser;
import com.pxkdxvan.node.service.CommandService;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.pxkdxvan.node.config.CommandConstants.*;

@Component(STORAGE_COMMAND)
public final class StorageCommand extends Command {

    private final CommandService commandService;
    private final LinkFactory linkFactory;

    StorageCommand(@Lazy CommandService commandService, LinkFactory linkFactory) {
        super(STORAGE_COMMAND, STORAGE_DESCRIPTION);
        this.commandService = commandService;
        this.linkFactory = linkFactory;
    }

    @Override
    public String execute(Update update) {
        BotUser botUser = commandService.getBotUser(update);
        if (!botUser.getAuthorized() || !botUser.getVerified())
            return UNAUTHORIZED_MESSAGE;

        List<BotFile> files = botUser.getFiles();
        if (files == null || files.isEmpty())
            return STORAGE_EMPTY_MESSAGE;

        List<String> filenames = files
                .stream()
                .map(bf -> (bf instanceof BotDocument) ? ((BotDocument) bf).getName() : "photo-" + bf.getShareId())
                .toList();

        List<String> links = files
                .stream()
                .map(BotFile::getShareId)
                .map(String::valueOf)
                .map(linkFactory::createSource)
                .toList();

        return STORAGE_END_MESSAGE + "\n" + IntStream
                .range(0, files.size())
                .mapToObj(i -> filenames.get(i) + " -> " + links.get(i))
                .collect(Collectors.joining("\n\n"));
    }

}
