package discordBot.SlashCommand.commands.music_commands;

import discordBot.SlashCommand.ICommand;
import discordBot.helper.music.PlayerManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;


/**
 * Simple command that allows the user to resume the stopped music.
 */
public class ResumeMusic implements ICommand {

    @Override
    public void handle(SlashCommandInteractionEvent event) {
        PlayerManager playerManager = PlayerManager.getInstance();

        try{
            MusicCommandHelper.botInChannel(event);
            playerManager.resume(playerManager, event);
            event.reply("The player has now resume").queue();
        }
        catch (Exception e){
            event.reply(e.getMessage()).queue();
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
