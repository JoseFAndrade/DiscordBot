package discord.bot.discordBot2.command.commands;

import discord.bot.discordBot2.command.CommandContext;
import discord.bot.discordBot2.command.CommandManager;
import discord.bot.discordBot2.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.List;

public class Help implements ICommand {

    private final CommandManager commandManager;

    public Help(CommandManager commandManager){
        this.commandManager = commandManager;
    }

    @Override
    public void handle(CommandContext ctx) {
        GuildMessageReceivedEvent event = ctx.getEvent();
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
                "command that you need help with.";
    }

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
