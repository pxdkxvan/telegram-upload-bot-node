package com.pxkdxvan.node.command;

import com.pxkdxvan.node.client.BotClient;
import com.pxkdxvan.node.dto.MailPropertiesDTO;
import com.pxkdxvan.node.factory.RequestDTOFactory;
import com.pxkdxvan.node.model.BotUser;
import com.pxkdxvan.node.model.BotVerification;
import com.pxkdxvan.node.service.VerificationService;
import com.pxkdxvan.node.statemachine.BotEvent;
import com.pxkdxvan.node.statemachine.BotState;
import com.pxkdxvan.node.service.CommandService;

import org.apache.commons.validator.routines.EmailValidator;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.Objects;

import static com.pxkdxvan.node.config.CommandConstants.*;

@Component(REGISTRATION_COMMAND)
public final class RegistrationCommand extends Command {

    private final CommandService commandService;
    private final VerificationService verificationService;

    private final BotClient botClient;
    private final RequestDTOFactory requestDTOFactory;

    RegistrationCommand(@Lazy CommandService commandService, VerificationService verificationService,
                        BotClient botClient, RequestDTOFactory requestDTOFactory) {
        super(REGISTRATION_COMMAND, REGISTRATION_DESCRIPTION);

        this.commandService = commandService;
        this.verificationService = verificationService;

        this.botClient = botClient;
        this.requestDTOFactory = requestDTOFactory;
    }

    @Override
    public String execute(Update update) {
        BotUser botUser = commandService.getBotUser(update);
        if (botUser.getAuthorized())
            return botUser.getVerified() ?
                    REGISTRATION_ALREADY_MESSAGE :
                    REGISTRATION_EMAIL_MESSAGE;

        BotState currentState = commandService.getCurrentBotState(botUser);
        if (currentState == BotState.FREE_TO_USE) {
            commandService.changeBotState(botUser, BotEvent.REGISTRATION);
            return REGISTRATION_START_MESSAGE;
        }

        Message message = update.getMessage();
        if (!message.hasText())
            return REGISTRATION_INVALID_EMAIL_MESSAGE;

        String email = message.getText();
        if (!EmailValidator.getInstance().isValid(email))
            return REGISTRATION_INVALID_EMAIL_MESSAGE;

        boolean sameEmail = Objects.equals(email, botUser.getEmail());
        if (sameEmail) {
            botUser.setAuthorized(true);
            commandService.changeBotState(botUser, BotEvent.DATA_RECEIVED);
            return REGISTRATION_SUCCESS_MESSAGE;
        }

        BotVerification botVerification = verificationService.getOrCreateVerification(botUser);
        String verificationToken = String.valueOf(botVerification.getToken());
        MailPropertiesDTO requestDTO = requestDTOFactory.createMailProperties(verificationToken, email);
        botClient.send(requestDTO);

        botUser.setEmail(email);
        botUser.setVerified(false);
        botUser.setAuthorized(true);

        commandService.changeBotState(botUser, BotEvent.DATA_RECEIVED);

        return REGISTRATION_EMAIL_MESSAGE;
    }

}
