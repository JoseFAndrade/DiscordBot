package discordBot.SlashCommand;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.Arrays;
import java.util.List;

public interface ICommand {
    default void handle(CommandContext ctx){
        return;
    }

    default void handle(SlashCommandInteractionEvent event){
        return;
    }

    String getName();

    String getHelp();

    default public List<String> getAliases() {
        return Arrays.asList(); //List.of(); // use Arrays.asList if you are on java 8
    }

    default List<OptionData> getOptions(){
        return null;
    }

}
