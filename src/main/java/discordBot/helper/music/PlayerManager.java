package discordBot.helper.music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayerManager {

    private static PlayerManager INSTANCE;


    private final AudioPlayerManager playerManager;
    private final Map<Long, GuildMusicManager> musicManagers;


    public static synchronized PlayerManager getInstance() {
        if (INSTANCE == null)
            INSTANCE = new PlayerManager();
        return INSTANCE;
    }

    private PlayerManager(){
        this.musicManagers = new HashMap<>();

        this.playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);
    }

    public synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
        long guildId = Long.parseLong(guild.getId());
        GuildMusicManager musicManager = musicManagers.get(guildId);

        if (musicManager == null) {
            musicManager = new GuildMusicManager(playerManager);
            musicManagers.put(guildId, musicManager);
        }

        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());
        return musicManager;
    }



    public void loadAndPlay(final TextChannel channel, final String trackUrl, MessageReceivedEvent event) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());

        Guild guild = event.getGuild();

        Member member = guild.getMember(event.getAuthor());
        VoiceChannel vc = member.getVoiceState().getChannel().asVoiceChannel();

        playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                channel.sendMessage("Adding to queue " + track.getInfo().title).queue();

                play(channel.getGuild(), musicManager, track, vc);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                AudioTrack firstTrack = playlist.getSelectedTrack();

                if (firstTrack == null) {
                    firstTrack = playlist.getTracks().get(0);
                }

                channel.sendMessage("Adding to queue " + firstTrack.getInfo().title + " (first track of playlist " + playlist.getName() + ")").queue();

                for(AudioTrack each: playlist.getTracks()){
                    play(channel.getGuild(), musicManager, each, vc);
                }
            }

            @Override
            public void noMatches() {
                channel.sendMessage("Nothing found by " + trackUrl).queue();
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                channel.sendMessage("Could not play: " + exception.getMessage()).queue();
            }
        });
    }

    private void play(Guild guild, GuildMusicManager musicManager, AudioTrack track, VoiceChannel vc) {
        connectToFirstVoiceChannel(guild.getAudioManager(), vc);
        //System.err.println("Does this run every time?");
        musicManager.scheduler.queue(track);
    }

    public void pause(PlayerManager pM, MessageReceivedEvent event){
        //System.err.println("Is this function even running");
        MessageChannel channel = event.getChannel();
        GuildMusicManager musicManager = pM.getGuildAudioPlayer(event.getGuild());
        musicManager.scheduler.pause();
        //channel.sendMessage("Music is now paused.").queue();
    }

    public void pause(PlayerManager playerManager, SlashCommandInteractionEvent event) {
        GuildMusicManager musicManager = playerManager.getGuildAudioPlayer(event.getGuild());
        musicManager.scheduler.pause();

    }

    public void resume(PlayerManager pM, MessageReceivedEvent event){
        GuildMusicManager musicManager = pM.getGuildAudioPlayer(event.getGuild());
        musicManager.scheduler.unpause();
    }

    public void resume(PlayerManager pM, SlashCommandInteractionEvent event){
        GuildMusicManager musicManager = pM.getGuildAudioPlayer(event.getGuild());
        musicManager.scheduler.unpause();
    }

    public void skipTrack(PlayerManager pM, MessageReceivedEvent event) {
        MessageChannel channel = event.getChannel();
        GuildMusicManager musicManager = pM.getGuildAudioPlayer(event.getGuild());
        musicManager.scheduler.nextTrack();

        //channel.sendMessage("Skipped to next track.").queue();
    }

    public void skipTrack(PlayerManager pM, SlashCommandInteractionEvent event) {
        MessageChannel channel = event.getChannel();
        GuildMusicManager musicManager = pM.getGuildAudioPlayer(event.getGuild());
        musicManager.scheduler.nextTrack();

        //channel.sendMessage("Skipped to next track.").queue();
    }

    public void changeVolume(PlayerManager pm, int volume, SlashCommandInteractionEvent event){
        GuildMusicManager musicManager = pm.getGuildAudioPlayer(event.getGuild());
        musicManager.scheduler.changeVolume(volume);
    }

    public void changeVolume(PlayerManager pM, int volume, MessageReceivedEvent event ){
        MessageChannel channel = event.getChannel();

        if( volume < 0 || volume > 100){
            channel.sendMessage("Please keep volume between 0 and 100.").queue();
            return;
        }
        GuildMusicManager musicManager = pM.getGuildAudioPlayer(event.getGuild());
        musicManager.scheduler.changeVolume(volume);
        channel.sendMessage("Volume has now been changed to: " + volume).queue();
    }


    private static void connectToFirstVoiceChannel(AudioManager audioManager, VoiceChannel voiceChannel) {
        if (!audioManager.isConnected()) {
                audioManager.openAudioConnection(voiceChannel);
            }
    }

    public void clear(PlayerManager pM, MessageReceivedEvent event){
        MessageChannel channel = event.getChannel();
        GuildMusicManager musicManager = pM.getGuildAudioPlayer(event.getGuild());
        musicManager.scheduler.clear();

        channel.sendMessage("The queue has been cleared.").queue();
    }

    /*TODO Update the voice leave event
    public void clear(PlayerManager pM, GuildVoiceLeaveEvent event){
        GuildMusicManager musicManager = pM.getGuildAudioPlayer(event.getGuild());
        musicManager.scheduler.clear();

        //musicManager.scheduler.nextTrack();
        System.out.println("It is done");
    }
    */

    public ArrayList<AudioTrack> getTracks(PlayerManager pM, MessageReceivedEvent event){
        MessageChannel channel = event.getChannel();
        GuildMusicManager musicManager = pM.getGuildAudioPlayer(event.getGuild());
        ArrayList<AudioTrack> songList = musicManager.scheduler.returnTracks();

        return songList;
    }

    public ArrayList<AudioTrack> getTracks(PlayerManager pM, SlashCommandInteractionEvent event){
        MessageChannel channel = event.getChannel();
        GuildMusicManager musicManager = pM.getGuildAudioPlayer(event.getGuild());

        return musicManager.scheduler.returnTracks();
    }

    public AudioTrack currentSong (PlayerManager pM, MessageReceivedEvent event){
        GuildMusicManager musicManager = pM.getGuildAudioPlayer(event.getGuild());
        return musicManager.player.getPlayingTrack();
    }

    public AudioTrack currentSong(PlayerManager manager, SlashCommandInteractionEvent event) {
        GuildMusicManager musicManager = manager.getGuildAudioPlayer(event.getGuild());
        return musicManager.player.getPlayingTrack();
    }

    public void shuffleSongs(PlayerManager pM, MessageReceivedEvent event){
        GuildMusicManager musicManager = pM.getGuildAudioPlayer(event.getGuild());
        musicManager.scheduler.shuffle();
    }

    public void shuffleSongs(PlayerManager pM, SlashCommandInteractionEvent event){
        GuildMusicManager musicManager = pM.getGuildAudioPlayer(event.getGuild());
        musicManager.scheduler.shuffle();
    }



    //TODO change the functions within this code to tailor towards the event.reply function
    public void loadAndPlay(TextChannel channel, String trackUrl, SlashCommandInteractionEvent event) {

        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());

        Guild guild = event.getGuild();

        Member member = guild.getMember(event.getUser());
        VoiceChannel vc = member.getVoiceState().getChannel().asVoiceChannel();

        playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                event.reply("Adding to queue " + track.getInfo().title).queue();

                play(channel.getGuild(), musicManager, track, vc);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                AudioTrack firstTrack = playlist.getSelectedTrack();

                if (firstTrack == null) {
                    firstTrack = playlist.getTracks().get(0);
                }

                event.reply("Adding to queue " + firstTrack.getInfo().title + " (first track of playlist " + playlist.getName() + ")").queue();

                for(AudioTrack each: playlist.getTracks()){
                    play(channel.getGuild(), musicManager, each, vc);
                }
            }

            @Override
            public void noMatches() {
                channel.sendMessage("Nothing found by " + trackUrl).queue();
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                event.reply("Could not play: " + exception.getMessage()).queue();
            }
        });

    }


}
