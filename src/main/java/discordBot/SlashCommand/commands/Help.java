package discordBot.SlashCommand.commands;

import discordBot.SlashCommand.SlashCommandManager;
import discordBot.SlashCommand.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This command will help users understand all of the other commands within the bot. It will offer both a list of commands available
 * as well as helping with a specific command.
 *
 * This class needs the CommandManager which is the class that contains all of the commands in there. Reason it needs this
 * is to access the help function within the other classes
 */


//TODO because of the updated slash command version -> the helper command is almost useless
public class Help implements ICommand {

    private final SlashCommandManager commandManager;

    public Help(SlashCommandManager commandManager){
        this.commandManager = commandManager;
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> options = new ArrayList<>();
        options.add( new OptionData(OptionType.STRING, "command", "The name of the command you want more info on",false));
        return options;
    }

    /**
     * This function will either return a list of commands if the user hasn't added any arguments, or it will present
     * the command that the user asked for help with and show the help command of it.
     * @param event The Slash command event that gets passed
     */
    @Override
    public void handle(SlashCommandInteractionEvent event) {
        List<OptionMapping> options = event.getOptions();

        if( options.size() == 0 )
            event.replyEmbeds(allCommandsEmbed(commandManager.getCommands())).queue();
        else{
            ICommand obtained = commandManager.getCommand(event.getOption("command").getAsString());

            if(obtained != null){
                if(obtained.getHelp() != null)
                    event.reply(obtained.getHelp()).queue();
                else
                    event.reply("Sorry, there seems to not be an explanation for this command at the moment").queue();
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
     * This will return a MessageEmbed that can be used to send back to the user in order to display all the commands.
     * This embed will later be edited in order to have it look nicer and be organized.
     * @param commands This take a list of commands that will be added into the MessageEmbed
     * @return Return an already built MessageEmebed ready to be sent to the server
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
