package discordBot.command.commands.music_commands;

import discordBot.command.CommandContext;
import discordBot.command.ICommand;
import discordBot.helper.music.PlayerManager;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * Simple command that allows the user to resume the stopped music.
 */
public class ResumeMusic implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        MessageReceivedEvent
 event = ctx.getEvent();
        MessageChannel channel = event.getChannel();
        PlayerManager manager = PlayerManager.getInstance();

        try{
            MusicCommandHelper.botInChannel(event);
            manager.resume(manager, event);
            channel.sendMessage("The player has now resume").queue();
        }
        catch (Exception e){
            channel.sendMessage(e.getMessage()).queue();
        }

    }

    @Override
    public String getName() {
        return "resume";
    }

    @Override
    public String getHelp() {
        return "Resumes the music that was previously stopped";
    }
}
