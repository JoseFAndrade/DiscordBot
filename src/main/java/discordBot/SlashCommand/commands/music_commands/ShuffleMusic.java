package discordBot.SlashCommand.commands.music_commands;

import discordBot.SlashCommand.ICommand;
import discordBot.helper.music.PlayerManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;


import java.util.List;

/**
 * A command that allows the music to be shuffled if there is a queue.
 */
public class ShuffleMusic implements ICommand {

    @Override
    public void handle(SlashCommandInteractionEvent event) {
        PlayerManager playerManager = PlayerManager.getInstance();

        try{
            MusicCommandHelper.botInChannel(event);
            playerManager.shuffleSongs(playerManager,event);
            event.reply("Songs have been shuffled").queue();
        }
        catch (Exception e){
            event.reply(e.getMessage()).queue();
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
