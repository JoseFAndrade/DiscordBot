package discordBot.SlashCommand.commands.music_commands;

import discordBot.SlashCommand.ICommand;
import discordBot.helper.music.PlayerManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;


import java.util.ArrayList;
import java.util.List;

/**
 * A simple command that gives the user the ability to change the volume of the bot when playing music.
 */
public class ChangeVolumeMusic implements ICommand {


    @Override
    public List<OptionData> getOptions() {
        List<OptionData> options = new ArrayList<>();
        options.add( new OptionData(OptionType.INTEGER, "volume", "Enter a number between 0 and 100",true));
        return options;
    }

    @Override
    public void handle(SlashCommandInteractionEvent event) {
        PlayerManager playerManager = PlayerManager.getInstance();
        Integer volume = event.getOption("volume").getAsInt();

        if( ! ( volume >= 0 && volume <= 100 ))
            event.reply("You need to enter a value between 0 and 100.").queue();
        else{
            playerManager.changeVolume(playerManager, volume, event);
            event.reply("The volume has been adjusted to " + volume).queue();
        }
    }

    @Override
    public String getName() {
        return "volume";
    }

    @Override
    public String getHelp() {
        return "This command will change the volume of the music. The command can be used by doing 'setVolume <number 1-100>'";
    }

}
