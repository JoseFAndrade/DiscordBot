package discord.bot.discordBot2.command.commands.music_commands;

import discord.bot.discordBot2.command.CommandContext;
import discord.bot.discordBot2.command.ICommand;
import discord.bot.discordBot2.helper.music.PlayerManager;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class PlayMusic implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        GuildMessageReceivedEvent event = ctx.getEvent();
        MessageChannel channel = event.getChannel();
        List<String> args = ctx.getArgs();

        if(args.size() > 1 || args == null || args.size() == 0 )
            channel.sendMessage("That was more than one argument, please try again with only one or use a different " +
                    "command");
        else {
            PlayerManager manager = PlayerManager.getInstance();
            manager.loadAndPlay(ctx.getEvent().getChannel(), args.get(0),
                    ctx.getEvent());
        }
    }

    @Override
    public String getName() {
        return "play";
    }

    @Override
    public String getHelp() {
        return "This Command Plays Music with the link.";
    }
}
