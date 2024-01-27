package discordBot.SlashCommand.commands.arknights;

import discordBot.SlashCommand.CommandContext;
import discordBot.SlashCommand.ICommand;
import discordBot.helper.GeneralExceptions;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

/**
 * This command will give the extra stats of the specific unit. Extra stats being Potential &
 * Trust Extra stats of the character. It will then send the information in an embed. Extremely simple function
 * that just sends a messageEmbed.
 */
public class GetArknightsExtraStats implements ICommand {

    public void handle(CommandContext ctx) {
        MessageReceivedEvent event = ctx.getEvent();
        MessageChannel channel = event.getChannel();
        List<String> args = ctx.getArgs();

        try{
            checkArgs(args);
            EmbedBuilder eb = getExtraStatsEmbedBuilder(args.get(0));
            channel.sendMessageEmbeds(eb.build()).queue();
        }
        catch (Exception e){
            channel.sendMessage(e.getMessage()).queue();
        }
    }

    @Override
    public void handle(SlashCommandInteractionEvent event) {

    }

    @Override
    public String getName() {
        return "extraStats";
    }

    @Override
    public String getHelp() {
        return "This will return Potential and Trust Extra stats of the character.";
    }

    @Override
    public List<OptionData> getOptions() {
        return null;
    }

    /**
     * This function will gather the potential and trust stat information of a character and then use the Helper functions
     * to create an embed.
     * @param unitName The name of a unit in string format
     * @return An embed builder that contains the extra stat information ( where extra stat = potential + trust stats)
     */
    public EmbedBuilder getExtraStatsEmbedBuilder(String unitName){

        return null;

        //Potentials potential = Database.getCharacterPotential(unitName);
        //TrustStats trustStats = Database.getCharacterTrustExtraStats(unitName);

        /*
        EmbedBuilder eb = EmbedCreator.createExtraStatsEmbed(potential,trustStats);
        return eb;
         */
    }

    private void checkArgs(List<String> args) throws GeneralExceptions.ArgumentSize {
        if( args.size() == 0 | args.size() > 1)
            throw new GeneralExceptions.ArgumentSize("Please make sure to only enter one argument. It should only be the name of the character.");
    }
}
