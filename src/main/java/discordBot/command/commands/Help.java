package discordBot.command.commands;

import discordBot.command.CommandContext;
import discordBot.command.CommandManager;
import discordBot.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.List;

/**
 * This command will help users understand all of the other commands within the bot. It will offer both a list of commands available
 * as well as helping with a specific command.
 *
 * This class needs the CommandManager which is the class that contains all of the commands in there. Reason it needs this
 * is to access the help function within the other classes
 */
public class Help implements ICommand {

    private final CommandManager commandManager;

    public Help(CommandManager commandManager){
        this.commandManager = commandManager;
    }

    /**
     * This function will either return a list of commands if the user hasn't added any arguments or it will present
     * the command that the user asked for help with and show the help command of it.
     * @param ctx The CommandContext that gets passed in from CommandManager
     */
    @Override
    public void handle(CommandContext ctx) {
        MessageReceivedEvent event = ctx.getEvent();
        MessageChannel channel = event.getChannel();
        List<String> args = ctx.getArgs();
        if(args.size() == 0)
            channel.sendMessageEmbeds(allCommandsEmbed(commandManager.getCommands())).queue();
        else{
            ICommand obtained = commandManager.getCommand(args.get(0));
            if(obtained != null ){
                if(obtained.getHelp() != null)
                    channel.sendMessage(obtained.getHelp()).queue();
                else
                    channel.sendMessage("Sorry, there seems to not be an explanation for this command at the moment.").queue();
            }
            else{
                channel.sendMessage("Command was not found. Please try another command.").queue();
            }
        }
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getHelp() {
        return "This command helps understand the other commands. Write the Command Prefix with Help and then space with the " +
                "command that you need help with. You can also call the help command without any options in order to see " +
                "the list of all available commands.";
    }

    //TODO Redo this function in the future for it to look nicer
    /**
     * This will return a MessageEmbed that can be used to send to send back to the user in order to display all of the commands.
     * This embed will later be edited in order to have it look nicer and be organized.
     * @param commands This take a list of commands that will be added into the MessageEmbed
     * @return Return an already build MessageEmebed ready to be sent to the server
     */
    private MessageEmbed allCommandsEmbed(List<ICommand> commands){
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Current Bot Commands", null);
        eb.setDescription("The commands under the brackets are aliases that can also be used.");
        eb.setColor(Color.RED);
        for(int i = 0; i < commands.size(); i++){
            ICommand currentCommand = commands.get(i);
            eb.addField(currentCommand.getName(),currentCommand.getAliases().toString(),true);
        }

        return eb.build();
    }
}
