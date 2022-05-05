package discord.bot.discordBot2.command.commands.music_commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import discord.bot.discordBot2.command.CommandContext;
import discord.bot.discordBot2.command.ICommand;
import discord.bot.discordBot2.helper.EventWaiterSingleton;
import discord.bot.discordBot2.helper.music.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class SongList implements ICommand {


    /*
        This function will implement EventWaiter and use it in order to display the information in a nice way and allow
        the user to scroll through the different songs. This will also include embeds.
     */
    @Override
    public void handle(CommandContext ctx) {
        //Def check if the bot is in a voice channel first
        //Would be nice to create a helper function for this so we can use it on the other music_command functions

        GuildMessageReceivedEvent event = ctx.getEvent();
        MessageChannel channel = event.getChannel();
        List<String> args = ctx.getArgs();
        PlayerManager manager = PlayerManager.getInstance();

        ArrayList<AudioTrack> songList = manager.getTracks(manager,event);
        String stringOfSongList = "";
        int count = 0;
        for(AudioTrack eachSong: songList){
            count++;
            AudioTrackInfo audioTrackInfo = eachSong.getInfo();
            stringOfSongList += String.format("%d: %s\n", count,audioTrackInfo.title);
        }
        //we can split up the string into splits
        //we can keep the length of the string
        //we need a way to determine where the length will be the string count
        String[] splitBySong = stringOfSongList.split("\n");

        SongListIterator songListIterator = new SongListIterator(splitBySong);
        AtomicReference<EmbedBuilder> eb = new AtomicReference<>(buildEmbedFromList(songListIterator.next()));

        channel.sendMessageEmbeds( eb.get().build()).queue( (message) -> {
            message.addReaction("⬅️").queue();
            message.addReaction("➡️").queue();
            addEventWaiter(message,channel,songListIterator,eb);
        });
    }

    /* TODO
            We can make it so that the helper function are able to hand this.
            This also gave me an idea of where I can make it so that any sort of class is able to be passed onto the EventWaiter helper function
            that contains the same next() & previous() functions in order to make it more modular and usable for any kind of code.
     */

    /**
        This is a helper function that helps contantly queue in the same event waiter action into the RestAction queue of a message.
        The reason why this is done is because the user won't only try to use it once, but multiple times.
     */
    //TODO Should make a general function that does something similar to this but in a general way
    //https://github.com/JDA-Applications/JDA-Utilities/blob/master/menu/src/main/java/com/jagrosh/jdautilities/menu/Paginator.java
    //obtained idea from the paginator class
    private void addEventWaiter(Message message, MessageChannel channel,SongListIterator songListIterator, AtomicReference<EmbedBuilder> eb){
        EventWaiterSingleton.getInstance().waitForEvent(
                GuildMessageReactionAddEvent.class,
                (e) -> e.getMessageIdLong() == message.getIdLong() && !e.getUser().isBot() &&
                        ( e.getReactionEmote().getEmoji().equals("➡️") || e.getReactionEmote().getEmoji().equals("⬅️")),
                (e) -> {
                    String emote = e.getReactionEmote().getEmoji();
                    if(emote.equals("⬅️"))
                    {
                        if(songListIterator.previous() != null )
                        {
                            eb.set(buildEmbedFromList(songListIterator.previous()));
                            message.editMessageEmbeds(eb.get().build()).queue( (newMessage) -> { //requeue this function because we want it to work multiple times
                                addEventWaiter(message,channel,songListIterator,eb);
                                }
                            );
                        }
                    }
                    else {
                        eb.set(buildEmbedFromList(songListIterator.next()));
                        message.editMessageEmbeds(eb.get().build()).queue( (newMessage) -> {
                            addEventWaiter(message, channel, songListIterator,eb);
                        });
                    }
                }
                ,
                20L, TimeUnit.SECONDS,
                () -> {}
        );
    }

    private EmbedBuilder buildEmbedFromList(String songList){
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Song List");
        eb.setColor(Color.blue);
        eb.setDescription("```"+songList+"```");
        return eb;
    }

    @Override
    public String getName() {
        return "songList";
    }

    @Override
    public String getHelp() {
        return null;
    }
}


class SongListIterator implements Iterator<String>{
    private final String[] stringSongList;
    private int pointer = 0;
    private int prevPointer = 0;

    public SongListIterator(String[] value){
        this.stringSongList = value;
    }

    @Override
    public boolean hasNext() {
        return ! (pointer > stringSongList.length);
    }

    @Override
    public String next() {
        int wordCount = 0;
        String toSend = "";
        //make sure to move up the previousPointer to the new previous.
        prevPointer = pointer;

        for(; pointer < stringSongList.length; pointer++){
            if(stringSongList[pointer].length() + wordCount >= 2000)
                return toSend;
            toSend+= stringSongList[pointer] + "\n";
            wordCount+= stringSongList[pointer].length() + 2; //it counts /n as 2 characters
        }

        if(!toSend.equals(""))
        {
            return toSend;
        }

        return null;
    }

    public String previous(){
        int wordCount = 0;
        //we need to update current pointer to the prevPointer because we need to be able to go back and forward multiple times
        pointer = prevPointer;
        int temporaryPointer = prevPointer - 1; //we need to substract one to account for 0 indexing

        StringBuilder toSend = new StringBuilder("");
        for(; temporaryPointer >= 0; temporaryPointer--){
            if(stringSongList[temporaryPointer].length() + wordCount > 2000){
                return toSend.toString();
            }
            toSend.insert(0,stringSongList[temporaryPointer]+"\n");
            wordCount += stringSongList[temporaryPointer].length() + 2; //because /n counts as two characters
        }

        if(!toSend.equals(""))
            return toSend.toString();
        return null;
    }


}
