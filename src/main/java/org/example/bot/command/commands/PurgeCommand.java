package org.example.bot.command.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import org.example.bot.Bot;
import org.example.bot.command.Command;
import org.example.bot.command.CommandManager;

import java.util.List;

public class PurgeCommand implements Command {

    @Override
    public String name() {
        return "purge";
    }

    @Override
    public String description() {
        return "Deletes the specified amount of messages";
    }

    @Override
    public String[] aliases() {
        return new String[]{"purge", "nuke"};
    }

    @Override
    public void run(Bot bot, TextChannel textChannel, Message message, Member sender, String[] args) {
        if (args.length < 1) {
            // Command manager instance
            CommandManager commandManager = bot.getCommandManager();
            // Build a message embed to inform the user that they must provide an amount
            MessageEmbed errorMessage = new EmbedBuilder()
                    .setColor(0xffe53935)
                    .setTitle("No amount specified!")
                    .setDescription("You must specify an amount of messages to purge. " + commandManager.getPrefix() + "purge {amount}")
                    .addField("Note", "The amount must be between 1 and 100", false)
                    .build();
            // Create a send rest action then queue it
            textChannel.sendMessage(errorMessage).queue();
        } else {
            try {
                // Parse the amount the user specified
                int amount = Integer.parseInt(args[0]);
                // Make sure the amount is valid
                if (amount > 0 && amount <= 100) {
                    // Delete the message that the user sent
                    textChannel.deleteMessageById(message.getId()).queue();
                    // Retrieve the requested amount of messages from discord
                    textChannel.getHistoryBefore(message.getId(), amount).queue(messageHistory -> {
                        List<Message> messages = messageHistory.getRetrievedHistory();
                        textChannel.deleteMessages(messages).queue(v -> {
                            // Build a message embed to inform that the purge completed
                            MessageEmbed embed = new EmbedBuilder()
                                    .setColor(0xff039be5)
                                    .setTitle("Channel Purged")
                                    .setDescription(String.format("Successfully purged %d message(s)", messages.size()))
                                    .build();
                            // Create a send rest action then queue it
                            textChannel.sendMessage(embed).queue();
                        });
                    });
                } else {
                    // Build a message embed to inform the user that they provided an invalid number
                    MessageEmbed errorMessage = new EmbedBuilder()
                            .setColor(0xffe53935)
                            .setTitle("Invalid amount specified!")
                            .setDescription("The amount must be between 1 and 100")
                            .build();
                    // Create a send rest action then queue it
                    textChannel.sendMessage(errorMessage).queue();
                }
            } catch (NumberFormatException e) {
                // Build a message embed to inform the user that they provided an invalid number
                MessageEmbed errorMessage = new EmbedBuilder()
                        .setColor(0xffe53935)
                        .setTitle("Invalid amount specified!")
                        .setDescription("You must specify a number as the second argument! (Must be a whole number)")
                        .addField("Note", "The amount must be between 1 and 100", false)
                        .build();
                // Create a send rest action then queue it
                textChannel.sendMessage(errorMessage).queue();
            }
        }
    }


    @Override
    public Permission[] requiredPermissions() {
        // Require that the one requesting the purge has the permission to delete messages
        return new Permission[]{Permission.MESSAGE_MANAGE};
    }
}
