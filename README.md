# Discord Bot Template
By Jacobtread

This is a discord bot template written in Java to make it easy for you to start making a bot simply clone the repo and
rename the packages to whatever name you wish

(If you rename the packages make sure you rename the Main-Class attribute in build.gradle)
```groovy
def mainClass = 'org.example.bot.Bot' // YOUR MAIN CLASS HERE
```

### Properties

The bot properties are store in a file named config.properties which must be placed in the working directory. The file
is formatted as such:

```properties
# This is your bot token you got from discord
token=YOUR BOT TOKEN
# The prefix at the start of each command in this case it would be !help
cmd_prefix=!
```

Anything you put into this file can be access in your code using the properties instance

```java
// Get the properties instance from the bot instance
Properties properties = bot.getProperties();
// Get key value
String exampleValue = properties.get("example");
// Check if key exists
boolean exists = properties.has("example");
// Get with default value
String exampleWithDefault = properties.getOrDefault("example", "defaultValue");
// Storing a new value
properties.put("example", "newValue");
// Saving the properties to a file
properties.save(Paths.get("example.properties"))
```

### Commands
I have provided two commands for you two use these are a Help and Purge command they are
located in org.example.bot.command.commands. To create a command you must implement the
interface org.example.bot.Command like so

```java

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.example.bot.Bot;
import org.example.bot.command.Command;

public class ExampleCommand implements Command {

    @Override
    public String name() {
        return "example";
    }

    @Override
    public String description() {
        return "This is an example command";
    }

    @Override
    public String[] aliases() {
        return new String[]{"help", "info"};
    }

    @Override
    public void run(Bot bot, TextChannel textChannel, Message message, Member sender, String[] args) {
        // TODO: Implement you command
    }

}

```

To make your command require permissions simply add the following to your command implementation

```java
@Override
public Permission[] requiredPermissions(){
    return new Permission[]{ADD YOUR PERMISSIONS HERE};
}
```

By Jacobtread