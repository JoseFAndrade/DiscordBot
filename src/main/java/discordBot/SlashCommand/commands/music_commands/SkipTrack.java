package discordBot.SlashCommand.commands.music_commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import discordBot.SlashCommand.ICommand;
import discordBot.helper.music.PlayerManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;


//todo Implement voice channel leave if empty queue

/**
 * This command will skip the track that is currently playing and start to play the next track. It will also notify
 * the user of what the current track that is playing is called. An issue with this command at its current moment is
 * that it is unable to disconnect from voice channel if the queue of songs is empty.
 */
public class SkipTrack implements ICommand {

    @Override
    public void handle(SlashCommandInteractionEvent event) {
        PlayerManager playerManager = PlayerManager.getInstance();

        try{
            MusicCommandHelper.botInChannel(event);
            playerManager.skipTrack(playerManager,event);
            AudioTrack audioTrack = playerManager.currentSong(playerManager,event);
            event.reply("Skipped to the next track").queue();
            event.reply("Now playing: "+ audioTrack.getInfo().title).queue();

        }
        catch (Exception e){
            event.reply(e.getMessage()).queue();
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
