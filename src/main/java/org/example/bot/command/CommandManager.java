package org.example.bot.command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.example.bot.Bot;
import org.example.bot.Properties;
import org.example.bot.command.commands.HelpCommand;
import org.example.bot.command.commands.PurgeCommand;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.logging.Logger;

public class CommandManager extends ListenerAdapter {

    // The bot logger
    private static final Logger LOGGER = Bot.LOGGER;
    // A Map of all the aliases and their corresponding command alias -> command
    private final Map<String, Command> aliasedCommands = new HashMap<>();
    // A list of all loaded commands
    private final List<Command> commands = new ArrayList<>();
    // The bot instance
    private final Bot bot;
    // The command prefix
    private final String prefix;
    // The mention string for mentioning the bot
    private final String selfMention;

    /**
     * Sets the prefix, mention and loads the commands
     *
     * @param bot The bot instance
     * @param jda The JDA instance
     */
    public CommandManager(Bot bot, JDA jda) {
        this.bot = bot;
        Properties properties = bot.getProperties();
        prefix = properties.getOrDefault("cmd_prefix", "!");
        selfMention = jda.getSelfUser().getAsMention();
        addCommands();
        LOGGER.info(String.format("Loaded %d command(s)", commands.size()));
    }

    /**
     *  Add your commands here
     */
    private void addCommands() {
        add(new HelpCommand());
        add(new PurgeCommand());
        // add(new MyCommand());
    }

    /**
     * Invoked when the bot receives a private message (DM)
     * the bot responds with a message saying that it can only
     * be used in guide channels
     *
     * @param event The private message event
     */
    @Override
    public void onPrivateMessageReceived(@NotNull PrivateMessageReceivedEvent event) {
        // Make sure the message is not from us
        if(event.getAuthor().isBot()) {
            return;
        }
        // Build a message embed to inform the user
        MessageEmbed errorMessage = new EmbedBuilder()
                .setColor(0xffe53935)
                .setTitle("You cannot do that!")
                .setDescription("This bot cannot reply to direct messages. You must use a guild channel.")
                .build();
        // Create a send rest action then queue it
        event.getChannel().sendMessage(errorMessage).queue();
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        // The text channel the message was sent to
        TextChannel textChannel = event.getTextChannel();
        // The user who created the message
        User user = event.getAuthor();
        // The member of the guild
        Member member = event.getMember();
        // Make sure the user is a member and that they aren't a bot
        if (member == null || user.isBot()) {
            return;
        }
        // The message that was send
        Message message = event.getMessage();
        // Get the raw message content
        String content = message.getContentRaw();
        // Whether or not the bot was mentioned (@TheBot) in the message
        boolean isViaMention = content.startsWith(selfMention);
        // Make sure the message starts with the prefix or the bot is mentioned
        if (content.startsWith(prefix) || isViaMention) {
            int length = content.length();
            if (isViaMention) {
                // Ignore if message is not longer than the mention
                if (length <= selfMention.length() + 1) {
                    return;
                }
                // Remove the mention from the start of the message
                content = content.substring(selfMention.length() + 1);
            } else {
                // Ignore if the message is not longer than the prefix
                if (length < prefix.length()) {
                    return;
                }
                // Remove the prefix from the start of the message
                content = content.substring(prefix.length());
            }
            // Split the message at any spaces
            String[] args = content.split(" ");
            // Ignore the message if its command is empty
            if (args.length < 1) {
                return;
            }
            // Get the command alias from the first arg
            String commandAlias = args[0];
            // Check if the command exists
            if (aliasedCommands.containsKey(commandAlias)) {
                // Retrieve the command via its alias
                Command command = aliasedCommands.get(commandAlias);
                // An array of arguments for the command
                String[] commandArgs;
                if (args.length > 1) {
                    // Copy the args array but without first argument
                    commandArgs = Arrays.copyOfRange(args, 1, args.length);
                } else {
                    // Empty array no more args where passed
                    commandArgs = new String[0];
                }
                // The permissions the command requires
                Permission[] permissions = command.requiredPermissions();
                // See if there is any permissions required
                if (permissions.length > 0) {
                    // Check if the user doesn't have the permissions
                    if (!member.hasPermission(permissions)) {
                        // Build a message embed to inform the user that they dont have permission
                        EmbedBuilder errorBuilder = new EmbedBuilder()
                                .setColor(0xffe53935)
                                .setTitle("Missing Permission")
                                .setDescription("You do not have the required permissions to use this command. It requires the following permissions:");
                        // Adds a field for each permission
                        for (Permission permission : permissions) {
                            errorBuilder.addField("", permission.getName(), false);
                        }
                        // Create a send rest action then queue it
                        textChannel.sendMessage(errorBuilder.build()).queue();
                        // Ignore the message
                        return;
                    }
                }
                // Executes the command
                command.run(bot, textChannel, message, member, commandArgs);
            } else {
                // Build a message embed to inform the user that the command doesn't exist
                MessageEmbed errorMessage = new EmbedBuilder()
                        .setColor(0xffe53935)
                        .setTitle("Unknown command")
                        .setDescription("Couldn't find a command with that alias try " + prefix + "help for help.")
                        .build();
                // Create a send rest action then queue it
                textChannel.sendMessage(errorMessage).queue();
            }
        }
    }

    /**
     * Adds the command to the commands list
     * and registers each of its aliases into the
     * aliases map
     *
     * @param command The command to add
     */
    private void add(Command command) {
        // Add the command to the commands list
        commands.add(command);
        // Loops the commands aliases
        for (String alias : command.aliases()) {
            // Check that the alias is not already taken
            if (aliasedCommands.containsKey(alias)) {
                Command existing = aliasedCommands.get(alias);
                // Throw an exception telling the user that alias is in use
                throw new IllegalArgumentException(String.format("Tried to register %s to alias %s but that alias is already in use by %s", command.name(), alias, existing.name()));
            }
            // Put the command into the map under its alias
            aliasedCommands.put(alias, command);
        }
    }


    /**
     * Returns the command prefix
     *
     * @return The prefix for commands
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Returns the command list
     *
     * @return The command list
     */
    public List<Command> getCommands() {
        return commands;
    }

}
