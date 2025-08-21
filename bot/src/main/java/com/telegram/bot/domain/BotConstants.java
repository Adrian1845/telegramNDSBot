package com.telegram.bot.domain;

public final class BotConstants {

    private BotConstants() {
    }

    public static final String greetings = """
            Hello! Welcome to the NDS ROM bot 🎮

            Ask me about one game that you would like to have a ROM!! (Must be from Nintendo DS)""";

    public static final String start = "/start";

    public static final String romsFound = "ROMS found for <strong>%s</strong>:\n\n";

    public static final String romsNotFound ="⚠️ No ROMs found for: <strong>%s</strong>.";

}
