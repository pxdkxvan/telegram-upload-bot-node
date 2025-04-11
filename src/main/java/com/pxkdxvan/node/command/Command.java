package com.pxkdxvan.node.command;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public abstract sealed class Command permits CancelCommand, ClearCommand, HelpCommand, LoadCommand, LogoutCommand, RegistrationCommand, StartCommand, StorageCommand {

    protected final String name;
    private final String description;

    public abstract String execute(Update update);

    @Override
    public String toString() {
        return "%s -> %s".formatted(name, description);
    }

}
