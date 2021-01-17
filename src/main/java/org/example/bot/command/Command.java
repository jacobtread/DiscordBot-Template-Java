package org.example.bot.command;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.example.bot.Bot;

public interface Command {

    /**
     * This is the display name the command will have. This is
     * shown in the help command.
     *
     * @return The name of the command.
     */
    String name();

    /**
     * A short description of the commands function. This is
     * shown in the help command.
     *
     * @return The description of the command.
     */
    String description();

    /**
     * An array of aliases that can be used to call this command.
     * (e.g help, h, info)
     * <p>
     * The first alias in the array is the one which appears in
     * the help command.
     *
     * @return The aliases this command has
     */
    String[] aliases();

    /**
     * The code to run when the command is called
     *
     * @param bot         An instance of the bot to access the command manager
     * @param textChannel The text channel the message was in
     * @param message     The message that was sent by the sender
     * @param sender      The sender of the message
     * @param args        Any additional arguments passed to the command
     */
    void run(Bot bot, TextChannel textChannel, Message message, Member sender, String[] args);

    /**
     * An array of permissions required for this command to be used.
     *
     * @return The command permissions
     */
    default Permission[] requiredPermissions() {
        return new Permission[0];
    }

}
