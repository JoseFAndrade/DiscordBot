package discordBot.command.commands.music_commands;

import discordBot.command.CommandContext;
import discordBot.command.ICommand;
import discordBot.helper.music.PlayerManager;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;


import java.util.List;

/**
 * A command that allows the music to be shuffled if there is a queue.
 */
public class ShuffleMusic implements ICommand {

    //todo check this function for bugs since I've ran into problems in the past
    @Override
    public void handle(CommandContext ctx) {
        MessageReceivedEvent
 event = ctx.getEvent();
        MessageChannel channel = event.getChannel();
        PlayerManager manager = PlayerManager.getInstance();

       try{
           MusicCommandHelper.botInChannel(event);
           manager.shuffleSongs(manager,event);
       }
       catch (Exception e){
           channel.sendMessage(e.getMessage()).queue();
       }

    }

    @Override
    public String getName() {
        return "shuffle";
    }

    @Override
    public String getHelp() {
        return "Will shuffle the music in the playlist.";
    }

    @Override
    public List<String> getAliases() {
        return ICommand.super.getAliases();
    }
}
