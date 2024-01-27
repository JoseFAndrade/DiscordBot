package discordBot.command.commands.music_commands;

import discordBot.command.CommandContext;
import discordBot.command.ICommand;
import discordBot.helper.music.PlayerManager;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * A simple command that will pause the music that the bot is currently playing.
 */
public class PauseMusic implements ICommand {

    /**
     * This function will tell the player manager to pause the player for the applicable guild.
     * @param ctx The CommandContext that gets passed in from CommandManager
     */
    @Override
    public void handle(CommandContext ctx) {
        MessageReceivedEvent event = ctx.getEvent();
        MessageChannel channel = event.getChannel();
        PlayerManager manager = PlayerManager.getInstance();

        //Maybe change these to be exceptions?
        try{
            MusicCommandHelper.botInChannel(event);

            manager.pause(manager,event);
            channel.sendMessage("Player has now stopped.").queue();
        }
        catch ( Exception e){
            channel.sendMessage(e.getMessage()).queue();
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return "pause";
    }

    @Override
    public String getHelp() {
        return "This command will pause the music that is currently playing.";
    }
}
