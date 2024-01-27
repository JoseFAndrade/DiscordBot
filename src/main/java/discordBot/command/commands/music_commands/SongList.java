package discordBot.command.commands.music_commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import discordBot.command.CommandContext;
import discordBot.command.ICommand;
import discordBot.helper.eventwaiter.ArrayEventWaiterIterator;
import discordBot.helper.eventwaiter.Helper;
import discordBot.helper.eventwaiter.buildEmbedFromString;
import discordBot.helper.music.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;


import java.awt.*;
import java.util.ArrayList;


/**
 * This command will display the current song list. In other words it will display the queue of the bot if there is
 * a queue. This command requires a bit more work because there is a limit to the number of text that can be sent
 * in a message so we need to take that into consideration as well as a way to add next page, previous page to not
 * sent too much information at once.
 */
public class SongList implements ICommand {


    /**
     *  This function will implement EventWaiter and use it in order to display the information in a nice way and allow
     *  the user to scroll through the different songs. This will also include embeds.
     */
    @Override
    public void handle(CommandContext ctx) {
        MessageReceivedEvent event = ctx.getEvent();
        MessageChannel channel = event.getChannel();
        PlayerManager manager = PlayerManager.getInstance();

        try{

            MusicCommandHelper.botInChannel(event);
            MusicCommandHelper.musicToPlay(event);
            MusicCommandHelper.userInChannel(event);

            ArrayList<AudioTrack> songList = manager.getTracks(manager,event);
            String[] splitBySong = obtainSongsInPlayOrder(songList);

            buildEmbedFromString function = (String s) -> {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("Song List");
                eb.setColor(Color.blue);
                eb.setDescription("```"+s+"```");
                return eb;
            };

            ArrayEventWaiterIterator songListIterator = new ArrayEventWaiterIterator(splitBySong);

            EmbedBuilder eb = function.buildEmbed(songListIterator.next());


            channel.sendMessageEmbeds( eb.build()).queue( (message) -> {
                message.addReaction(Emoji.fromUnicode("⬅️")).queue();
                message.addReaction(Emoji.fromUnicode("➡️")).queue();
                Helper.addEventWaiter(message,songListIterator,function);
            });


        }
        catch (Exception e){
            channel.sendMessage(e.getMessage()).queue();
        }
    }

    /**
     * This is a helper function that will take in the AudioTrack list and take the title of the songs and
     * add it to a string with what track number it is in the list. This will then be turned into a String list
     * and it will be used to show to the user the queued songs
     * @param audioTrackArrayList An ArrayList of Audio Tracks that are currently in the player
     * @return A String[] list which contains the title of the Audio Tracks as well as what number they are
     */
    String[] obtainSongsInPlayOrder( ArrayList<AudioTrack> audioTrackArrayList){
        String stringOfSongList = "";
        int count = 0;
        for(AudioTrack eachSong: audioTrackArrayList){
            count++;
            AudioTrackInfo audioTrackInfo = eachSong.getInfo();
            stringOfSongList += String.format("%d: %s\n", count,audioTrackInfo.title);
        }
        String[] splitBySong = stringOfSongList.split("\n");
        return splitBySong;
    }


    @Override
    public String getName() {
        return "songList";
    }

    @Override
    public String getHelp() {
        return "This command will return the list of songs that are currently in the queue. To see the current playing song use !.currentTrack.";
    }
}


