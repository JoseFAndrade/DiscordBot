package discord.bot.discordBot2.command.commands.music_commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import discord.bot.discordBot2.command.CommandContext;
import discord.bot.discordBot2.command.ICommand;
import discord.bot.discordBot2.helper.music.PlayerManager;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;


//todo Implement voice channel leave if empty queue
public class SkipTrack implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        GuildMessageReceivedEvent event = ctx.getEvent();
        MessageChannel channel = event.getChannel();
        List<String> args = ctx.getArgs();
        PlayerManager manager = PlayerManager.getInstance();

        if(args.size() > 1)
            channel.sendMessage("The number of argument is invalid. " +
                    "Please try again with the right usage of arguments").queue();
        else{
            List<AudioTrack> audioTrackList = manager.getTracks(manager,event);
            manager.skipTrack(manager, event);
            channel.sendMessage("Skipped to next track.").queue();
            channel.sendMessage("Now playing: " + audioTrackList.get(0).getInfo().title).queue();
        }

    }

    @Override
    public String getName() {
        return "skip";
    }

    @Override
    public String getHelp() {
        return "This command will skip the current song and start playing the next song if available. If there is no song then the bot," +
                "will stop playing and leave the voice channel.";
    }
}
