package discordBot.command.commands.music_commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import discordBot.command.CommandContext;
import discordBot.command.ICommand;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Simple command that will return the current track that is playing if any.
 */
public class CurrentTrack implements ICommand {

    /**
     * This function will check if the audioTrack is not null. If its not it will say the track name. If it is it will
     * let the user know there is no track playing.
     * @param ctx The CommandContext that gets passed in from CommandManager
     */
    @Override
    public void handle(CommandContext ctx) {
        MessageReceivedEvent event = ctx.getEvent();
        MessageChannel channel = event.getChannel();

        try{
            MusicCommandHelper.botInChannel(event);

            AudioTrack audioTrack = MusicCommandHelper.musicToPlay(event);
            channel.sendMessage("The current track playing is: " + audioTrack.getInfo().title).queue();
        }
        catch (Exception e){
            channel.sendMessage(e.getMessage()).queue();
        }
    }

    @Override
    public String getName() {
        return "currentTrack";
    }

    @Override
    public String getHelp() {
        return "Returns the current track that is playing";
    }

    @Override
    public List<String> getAliases() {
        return new ArrayList<>(Arrays.asList("CurrentSong", "Playing", "Current") );
    }

}
