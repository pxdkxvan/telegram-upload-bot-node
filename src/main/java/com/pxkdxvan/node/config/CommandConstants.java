package com.pxkdxvan.node.config;

public final class CommandConstants {
    public static final String HELP_COMMAND = "/help";
    public static final String HELP_DESCRIPTION = "view available commands";
    public static final String HELP_MESSAGE = "List of available commands:\n";

    public static final String START_COMMAND = "/start";
    public static final String START_DESCRIPTION = "start interacting with the bot";
    public static final String START_MESSAGE = "Hello there! To view the list of commands, type " + HELP_COMMAND;

    public static final String CANCEL_COMMAND = "/cancel";
    public static final String CANCEL_DESCRIPTION = "cancel current operation";
    public static final String CANCEL_MESSAGE = "Command cancelled";
    public static final String CANCEL_NOTHING_MESSAGE = "No active command found for cancelling";

    public static final String REGISTRATION_COMMAND = "/registration";
    public static final String REGISTRATION_DESCRIPTION = "create a new registration with specified email";
    public static final String REGISTRATION_START_MESSAGE = "Enter email to complete registration. To abort, type " + CANCEL_COMMAND;
    public static final String REGISTRATION_ALREADY_MESSAGE = "You have already registered with this email";
    public static final String REGISTRATION_INVALID_EMAIL_MESSAGE = "Invalid email link, please try again";
    public static final String REGISTRATION_EMAIL_MESSAGE = "Email with the link to verify your account has been sent to your mailbox";
    public static final String REGISTRATION_SUCCESS_MESSAGE = "Successfully registered";

    public static final String LOGOUT_COMMAND = "/logout";
    public static final String LOGOUT_DESCRIPTION = "literally log out";
    public static final String LOGOUT_MESSAGE = "Successfully logged out";

    public static final String LOAD_COMMAND = "/load";
    public static final String LOAD_DESCRIPTION = "upload a file and receive a download link";
    public static final String LOAD_START_MESSAGE = "Upload a photo or document. Processing may take a few seconds. To abort, type " + CANCEL_COMMAND;
    public static final String LOAD_INVALID_FORMAT_MESSAGE = "Invalid format, please try again";
    public static final String LOAD_END_PATTERN = "File successfully loaded. Link for downloading -> %s";

    public static final String STORAGE_COMMAND = "/storage";
    public static final String STORAGE_DESCRIPTION = "get all saved file links";
    public static final String STORAGE_EMPTY_MESSAGE = "No one uploaded links found";
    public static final String STORAGE_END_MESSAGE = "List of uploaded files before:\n";

    public static final String CLEAR_COMMAND = "/clear";
    public static final String CLEAR_DESCRIPTION = "clear all saved file links";
    public static final String CLEAR_EMPTY_MESSAGE = STORAGE_EMPTY_MESSAGE;
    public static final String CLEAR_END_MESSAGE = "Saved links cleared";

    public static final String UNKNOWN_COMMAND_MESSAGE = "Unknown command! Try " + HELP_COMMAND;
    public static final String UNAUTHORIZED_MESSAGE = "Register before loading files";
}
