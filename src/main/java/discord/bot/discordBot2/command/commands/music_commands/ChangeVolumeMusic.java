package discord.bot.discordBot2.command.commands.music_commands;

import discord.bot.discordBot2.command.CommandContext;
import discord.bot.discordBot2.command.ICommand;
import discord.bot.discordBot2.helper.music.PlayerManager;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class ChangeVolumeMusic implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        GuildMessageReceivedEvent event = ctx.getEvent();
        MessageChannel channel = event.getChannel();
        List<String> args = ctx.getArgs();
        PlayerManager manager = PlayerManager.getInstance();

        if(args.size() > 1 || args == null || args.size() == 0 )
            channel.sendMessage("The number of argument is invalid. " +
                    "Please try again with the right usage of arguments").queue();
        else{
            manager.changeVolume(manager, Integer.parseInt(args.get(0)), event);
        }
    }

    @Override
    public String getName() {
        return "setVolume";
    }

    @Override
    public String getHelp() {
        return "This command will stop the current music playing. The command can be used by doing 'setVolume <number 1-100>'";
    }
}
