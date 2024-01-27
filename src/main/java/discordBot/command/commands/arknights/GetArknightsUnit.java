package discordBot.command.commands.arknights;

import discordBot.command.CommandContext;
import discordBot.command.ICommand;
//import discord.bot.discordBot2.command.commands.arknights.api.Database;

import java.util.List;


/**
 * This command/class will return the information of the unit that the user is asking for. It will provide the
 * important information such as role, class type, archetype, stats (at a max level), etc.
 */
public class GetArknightsUnit implements ICommand {

    @Override
    public void handle(CommandContext ctx) {

        return;
        /*
        MessageReceivedEvent
 event = ctx.getEvent();
        MessageChannel channel = event.getChannel();
        List<String> args = ctx.getArgs();

        //todo Going to need to check how to error check this -> why did combinedStringFromList combine the args?
        try{
            checkArgs(args);

            String unitName = HelperFunctions.combinedStringFromList(args);
            Long userID = ctx.getAuthor().getIdLong();

            Helper.getGenCharInfo(unitName,userID,channel, ( name ->
            { GeneralCharacterInformation generalCharacterInformation = Database.getCharacterGeneralInformation(name);
                return (List<EmbedBuilder>) EmbedCreator.createCharacterEmbed(generalCharacterInformation);
            }) );

        }
        catch (Exception e) {
            channel.sendMessage(e.getMessage()).queue();
        }

         */

    }

    @Override
    public String getName() {
        return "getUnitTest";
    }

    @Override
    public String getHelp() {
        return "This command will return the general information of the unit whose name is given. Usage: .....";
    }

    private void checkArgs(List<String> args){
        //.....
    }


}
