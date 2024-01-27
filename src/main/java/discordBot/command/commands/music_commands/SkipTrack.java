package discordBot.command.commands.music_commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import discordBot.command.CommandContext;
import discordBot.command.ICommand;
import discordBot.helper.GeneralExceptions;
import discordBot.helper.music.PlayerManager;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;


import java.util.List;


//todo Implement voice channel leave if empty queue

/**
 * This command will skip the track that is currently playing and start to play the next track. It will also notify
 * the user of what the current track that is playing is called. An issue with this command at its current moment is
 * that it is unable to disconnect from voice channel if the queue of songs is empty.
 */
public class SkipTrack implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        MessageReceivedEvent
 event = ctx.getEvent();
        MessageChannel channel = event.getChannel();
        List<String> args = ctx.getArgs();
        PlayerManager manager = PlayerManager.getInstance();

        try{
            MusicCommandHelper.botInChannel(event);
            checkArgs(args);

            manager.skipTrack(manager, event);
            AudioTrack audioTrack = manager.currentSong(manager,event);
            channel.sendMessage("Skipped to next track.").queue();
            channel.sendMessage("Now playing: " + audioTrack.getInfo().title).queue();
        }
        catch (Exception e){
            channel.sendMessage(e.getMessage()).queue();
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

    private void checkArgs(List<String> args) throws GeneralExceptions.ArgumentSize {
        if( args.size() > 1)
            throw new GeneralExceptions.ArgumentSize("The number of arguments is invalid. Please try again with only one argument");
    }
}
