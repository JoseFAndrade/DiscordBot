package discordBot.SlashCommand.commands.music_commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import discordBot.helper.music.PlayerManager;
import net.dv8tion.jda.api.audio.hooks.ConnectionStatus;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;


public class MusicCommandHelper {

    public static void userInChannel(MessageReceivedEvent event) throws MusicException.UserNotInChannel {
        Member member = event.getGuild().getMember(event.getAuthor());
        VoiceChannel vc = member.getVoiceState().getChannel().asVoiceChannel();

        if(vc == null)
        {
            throw new MusicException.UserNotInChannel("The user: " + event.getAuthor().getName() + " is not in a channel");
        }
    }

    public static void userInChannel(SlashCommandInteractionEvent event) throws MusicException.UserNotInChannel {
        Member member = event.getMember();
        VoiceChannel vc = member.getVoiceState().getChannel().asVoiceChannel();

        if(vc == null)
        {
            throw new MusicException.UserNotInChannel("The user: " + event.getMember().getNickname() + " is not in a channel");
        }
    }

    public static void botInChannel(MessageReceivedEvent event) throws MusicException.BotNotInChannel
    {
        ConnectionStatus botStatus = event.getGuild().getAudioManager().getConnectionStatus();

        if( botStatus == ConnectionStatus.NOT_CONNECTED)
            throw new MusicException.BotNotInChannel("The bot is currently not in the channel.");
    }

    public static void botInChannel(SlashCommandInteractionEvent event) throws MusicException.BotNotInChannel {
        ConnectionStatus botStatus = event.getGuild().getAudioManager().getConnectionStatus();

        if( botStatus == ConnectionStatus.NOT_CONNECTED)
            throw new MusicException.BotNotInChannel("The bot is currently not in the channel.");
    }

    public static AudioTrack musicToPlay(MessageReceivedEvent event) throws MusicException.NoCurrentSong{

        PlayerManager manager = PlayerManager.getInstance();
        AudioTrack audioTrack = manager.currentSong(manager,event);

        if(audioTrack == null)
            throw new MusicException.NoCurrentSong("There is no song playing at the moment");

        return audioTrack;

    }

    public static AudioTrack musicToPlay(SlashCommandInteractionEvent event) throws MusicException.NoCurrentSong {
        PlayerManager manager = PlayerManager.getInstance();
        AudioTrack audioTrack = manager.currentSong(manager,event);

        if(audioTrack == null)
            throw new MusicException.NoCurrentSong("There is no song playing at the moment");

        return audioTrack;
    }


}
