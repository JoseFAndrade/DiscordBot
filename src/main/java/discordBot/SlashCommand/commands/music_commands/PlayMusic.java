package discordBot.SlashCommand.commands.music_commands;

import discordBot.SlashCommand.ICommand;
import discordBot.helper.GeneralExceptions;
import discordBot.helper.music.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

/**
 * This command will allow users to play the music that they want to. It takes in a link from a youtube video or a youtube
 * playlist. There are more sources accepted, but I don't know off the top of my head.
 */
public class PlayMusic implements ICommand {
    @Override
    public List<OptionData> getOptions() {
        List<OptionData> options = new ArrayList<>();
        options.add(new OptionData(OptionType.STRING,"url","The url of the song to play.",true));
        return options;
    }

    @Override
    public void handle(SlashCommandInteractionEvent event) {
        Member member = event.getMember();
        GuildVoiceState memberVoiceState = member.getVoiceState();
        try {
            if( !memberVoiceState.inAudioChannel()){
                event.reply("You need to be in a voice channel").queue();
                return;
            }
            PlayerManager manager = PlayerManager.getInstance();
            manager.loadAndPlay(event.getChannel().asTextChannel(), event.getOption("url").getAsString(),event);
        }
        catch (Exception e){
            event.reply(e.getMessage()).queue();
        }
    }

    @Override
    public String getName() {
        return "play";
    }

    @Override
    public String getHelp() {
        return "This command plays music for the user, but it needs a link to play music from.";
    }

    private void checkArgs( List<String> args) throws GeneralExceptions.ArgumentSize {

        if( args.size() > 1 || args == null || args.size() == 0)
            throw new GeneralExceptions.ArgumentSize("That was more than one argument, please try again with only one command.");
    }
}
