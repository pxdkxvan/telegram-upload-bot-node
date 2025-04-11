package com.pxkdxvan.node.service;

import com.pxkdxvan.node.command.*;
import com.pxkdxvan.node.model.BotUser;
import com.pxkdxvan.node.statemachine.BotEvent;
import com.pxkdxvan.node.statemachine.BotState;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.pxkdxvan.node.config.CommandConstants.*;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class CommandService {

    private final StateMachineService<BotState, BotEvent> stateMachineService;
    private final BotUserService botUserService;

    private final Map<String, Command> commands;
    private final CancelCommand cancelCommand;
    private final LoadCommand loadCommand;
    private final RegistrationCommand registrationCommand;

    private User getTelegramUser(Update update) {
        return update
                .getMessage()
                .getFrom();
    }

    public BotUser getBotUser(Update update) {
        User telegramUser = getTelegramUser(update);
        return botUserService.getOrCreateAccount(telegramUser);
    }

    private StateMachine<BotState, BotEvent> getBotStateMachine(BotUser botUser) {
        String userId = String.valueOf(botUser.getId());
        return stateMachineService.acquireStateMachine(userId);
    }

    @Transactional
    public void changeBotState(BotUser botUser, BotEvent event) {
        StateMachine<BotState, BotEvent> stateMachine = getBotStateMachine(botUser);
        Message<BotEvent> message = MessageBuilder.withPayload(event).build();
        stateMachine.sendEvent(Mono.just(message)).subscribe();
    }

    @Transactional
    public BotState getCurrentBotState(BotUser botUser) {
        return getBotStateMachine(botUser)
                .getState()
                .getId();
    }

    public String printAvailableCommandList() {
        return commands
                .values()
                .stream()
                .map(Command::toString)
                .collect(Collectors.joining("\n"));
    }

    private String executeOrCancel(Command cmd, Command cmd2fa, Update update) {
        return (!Objects.equals(cmd, cancelCommand) ? cmd2fa : cmd).execute(update);
    }

    @Transactional
    public String findAndExecuteCommand(Update update) {
        String possibleCommand = update.getMessage().getText();
        Command command = commands.get(possibleCommand);

        BotUser botUser = getBotUser(update);
        BotState botState = getCurrentBotState(botUser);

        return switch (botState) {
            case WAITING_FOR_EMAIL -> executeOrCancel(command, registrationCommand, update);
            case WAITING_FOR_FILE -> executeOrCancel(command, loadCommand, update);
            default -> (command != null) ? command.execute(update) : UNKNOWN_COMMAND_MESSAGE;
        };
    }

}
