package discord.bot.discordBot2.command.commands.music_commands;

import discord.bot.discordBot2.command.CommandContext;
import discord.bot.discordBot2.command.ICommand;
import discord.bot.discordBot2.helper.music.PlayerManager;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class ShuffleMusic implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        GuildMessageReceivedEvent event = ctx.getEvent();
        MessageChannel channel = event.getChannel();
        List<String> args = ctx.getArgs();
        PlayerManager manager = PlayerManager.getInstance();

        manager.shuffleSongs(manager,event);

    }

    @Override
    public String getName() {
        return "shuffle";
    }

    @Override
    public String getHelp() {
        return "Will shuffle the music in the playlist.";
    }

    @Override
    public List<String> getAliases() {
        return ICommand.super.getAliases();
    }
}
