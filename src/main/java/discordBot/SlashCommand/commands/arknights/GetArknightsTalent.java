package discordBot.SlashCommand.commands.arknights;

import discordBot.SlashCommand.CommandContext;
import discordBot.SlashCommand.ICommand;
import discordBot.helper.GeneralExceptions;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;


import java.util.List;



/**
 *  This class will obtain the talents of the unit whose name was provided by the user.
 *  This command will give the talent information to the user in a message embed.
 */
public class GetArknightsTalent implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        MessageReceivedEvent
 event = ctx.getEvent();
        MessageChannel channel = event.getChannel();
        List<String> args = ctx.getArgs();

        try{

            checkArgs(args);
            EmbedBuilder eb = getTalentEmbed(args.get(0));
            channel.sendMessageEmbeds(eb.build()).queue();
        }
        catch (Exception e){

        }

    }

    @Override
    public String getName() {
        return "talent";
    }

    @Override
    public String getHelp() {
        return "This will return the talents of the unit whose name is being requested. ";
    }

    /**
     * A function that returns the embed builder for talent information
     * @param unitName A string representing the name of the unit which I want the information from.
     * @return An embed builder with the information needed
     */
    public EmbedBuilder getTalentEmbed(String unitName){
        return null;
        /*
        TalentManager talentManager = Database.getCharacterTalentInformation(unitName);
        EmbedBuilder eb = EmbedCreator.createTalentEmbed(talentManager);
        return eb;

         */
    }

    private void checkArgs(List<String> args) throws GeneralExceptions.ArgumentSize {

        if( args.size() != 1)
            throw new GeneralExceptions.ArgumentSize("Please make sure to only enter one argument. It should only be the name of the character.");
    }
}
