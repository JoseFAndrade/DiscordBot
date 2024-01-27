package discordBot.helper.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

 //Note: This code is not entirely mine. Started a tutorial on youtube on guidance on how to start the music commands
 //for a discord bot. I have added my own necessary methods over time for any actions that I want my bot to do.

/**
 * This class schedules tracks for the audio player. It contains the queue of tracks.
 */
/*
TODO:
    Want to be able to tell the user that the bot is playing a song or started the next song.
    Best way to do that would be by adding it into the TrackEnded function. So that the bot can proceed to send a
    message to the channel letting it know that it will start to play another track.
    This means that I would have to add another data member, this would be the channel related text where the bot was
    called from. I think that this is the easiest way and wouldn't cause any issues because trackScheduler is specific to
    a MusicManager which is specific to each Guild.
    I'm sure there is a better way to do this but at the moment I can't think of any other idea
    ------------------------------------------------------------
    Idea Number two:
        I can create a multiton just like what the GuildMusicManager currently has
        This will make it easier and we can constantly update the textChannel that the user has called the play command
        from so that we have it always text to that channel
 */
public class TrackScheduler extends AudioEventAdapter {

    private final AudioPlayer player;
    private final BlockingQueue<AudioTrack> queue;


    private final MessageChannel messageChannel;

    /**
     * @param player The audio player this scheduler uses
     */
    public TrackScheduler(AudioPlayer player) {
        this.player = player;
        this.queue = new LinkedBlockingQueue<>();
        this.messageChannel = null;
    }

    //todo part of the complicated fix
    public TrackScheduler(AudioPlayer player, MessageChannel messageChannel){
        this.player = player;
        this.queue = new LinkedBlockingQueue<>();
        this.messageChannel = messageChannel;
    }



    /**
     * Add the next track to queue or play right away if nothing is in the queue.
     *
     * @param track The track to play or add to queue.
     */
    public void queue(AudioTrack track) {
        // Calling startTrack with the noInterrupt set to true will start the track only if nothing is currently playing. If
        // something is playing, it returns false and does nothing. In that case the player was already playing so this
        // track goes to the queue instead.
        if (!player.startTrack(track, true)) {
            queue.offer(track);
        }
    }

    /**
     * Start the next track, stopping the current one if it is playing.
     */
    public void nextTrack() {
        // Start the next track, regardless of if something is already playing or not. In case queue was empty, we are
        // giving null to startTrack, which is a valid argument and will simply stop the player.
        player.startTrack(queue.poll(), false);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        // Only start the next track if the end reason is suitable for it (FINISHED or LOAD_FAILED)
        if (endReason.mayStartNext) {
            nextTrack();
        }
    }

    public void changeVolume(int volume){
        player.setVolume(volume);

    }



    public void pause(){
        player.setPaused(true);
    }

    public void unpause(){
        player.setPaused(false);
    }

    //Need to skip to next track just in case a track is already playing.
    public void clear() {queue.clear();this.nextTrack();}

    public ArrayList<AudioTrack> returnTracks() {
        ArrayList<AudioTrack> songList = new ArrayList<>();
        for(AudioTrack eachSong: queue){
            //System.out.println(eachSong.getInfo().title);
            songList.add(eachSong);
        }
        //System.out.println();
        return songList;
    }

    //todo make this better
    public void shuffle(){
        ArrayList<AudioTrack> songList = new ArrayList<>();
        queue.drainTo(songList);
        Collections.shuffle(songList);
        for(AudioTrack each: songList)
            queue.offer(each);
    }

    public AudioTrack getPlaying() {
        return player.getPlayingTrack();
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track){
        System.out.println("track just started");
    }

}
