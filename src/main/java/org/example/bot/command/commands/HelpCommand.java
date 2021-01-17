package org.example.bot.command.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.example.bot.Bot;
import org.example.bot.command.Command;
import org.example.bot.command.CommandManager;

import java.util.List;

public class HelpCommand implements Command {

    @Override
    public String name() {
        return "Help";
    }

    @Override
    public String description() {
        return "Displays a list bot commands";
    }

    @Override
    public String[] aliases() {
        return new String[]{"help", "info"};
    }

    @Override
    public void run(Bot bot, TextChannel textChannel, Message message, Member sender, String[] args) {
        CommandManager commandManager = bot.getCommandManager();
        // Get the command prefix
        String prefix = commandManager.getPrefix();
        // Get all the commands
        List<Command> commands = commandManager.getCommands();
        // Create a builder for the help command message
        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setColor(0xff039be5)
                .setTitle("Help")
                .setDescription("Here's a list of the command");
        for (Command command : commands) {
            // Add a field for each command
            embedBuilder.addField(command.name(), prefix + command.aliases()[0] + " - " + command.description(), false);
        }
        // Create a send rest action then queue it
        textChannel.sendMessage(embedBuilder.build()).queue();
    }
}
