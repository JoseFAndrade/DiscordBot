package discord.bot.discordBot2.command.commands.music_commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import discord.bot.discordBot2.command.CommandContext;
import discord.bot.discordBot2.command.ICommand;
import discord.bot.discordBot2.helper.music.PlayerManager;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;


public class CurrentTrack implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        GuildMessageReceivedEvent event = ctx.getEvent();
        MessageChannel channel = event.getChannel();
        PlayerManager manager = PlayerManager.getInstance();
        AudioTrack audioTrack = manager.currentSong(manager,event);

        if(audioTrack != null)
            channel.sendMessage("The current track playing is: " + audioTrack.getInfo().title).queue();
        else
            channel.sendMessage("There is no track playing at the moment.").queue();
    }

    @Override
    public String getName() {
        return "currentTrack";
    }

    @Override
    public String getHelp() {
        return "Returns the current track that is playing";
    }

}
