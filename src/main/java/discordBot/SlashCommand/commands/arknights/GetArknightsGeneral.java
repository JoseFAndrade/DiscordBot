package discordBot.SlashCommand.commands.arknights;

import discordBot.SlashCommand.CommandContext;
import discordBot.SlashCommand.ICommand;
import discordBot.helper.GeneralExceptions;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.List;

/**
 * This class combines all of the embed information from the other classes in the package and displays mostly everything.
 * The only embed information that isn't fully grabbed is the Skill info. It will only grab the unique skills at the
 * highest level. This is to make the embed loop shorter and concise. It also allows users to be able to call this command
 * rather than multiple other commands.
 */
public class GetArknightsGeneral implements ICommand {
    @Override
    /**
     * This function will obtain all of the information required for the embed rotator. It will then use the function
     * within the helper file in order to display the information to the user.
     */
    public void handle(CommandContext ctx) {

        return;

        /*
        MessageReceivedEvent
 event = ctx.getEvent();
        MessageChannel channel = event.getChannel();
        List<String> args = ctx.getArgs();

        Long userID = ctx.getAuthor().getIdLong();

        try{

            checkArgs(args);

            Helper.getGenCharInfo(args.get(0), userID,channel, (unitName -> {
                List<EmbedBuilder> ebList = new ArrayList<>();
                //Unit
                GeneralCharacterInformation generalCharacterInformation = Database.getCharacterGeneralInformation(unitName);
                ebList.add(EmbedCreator.createCharacterEmbed(generalCharacterInformation));
                //Skill
                for(int i = 1; i < 4; i++){
                    EmbedBuilder eb = getSkillEmbedBuilder(args.get(0),i);
                    if( !(eb == null))
                        ebList.add(eb);
                }
                //Talent
                ebList.add(getTalentEmbedBuilder(args.get(0)));
                //Extra Stats
                ebList.add(getExtraStatsEmbedBuilder(args.get(0)));
                return ebList;
            }));
        }
        catch (Exception e){
            channel.sendMessage(e.getMessage()).queue();
        }


         */

    }

    @Override
    public String getName() {
        return "general";
    }

    @Override
    public String getHelp() {
        return "This command will return General Information about the character.";
    }

    /**
     * This funtion will help the command generate the skillEmebedBuilder part of the function.
     * It uses the helper functions of the other commands.
     * @param unitName Name of the unit
     * @param skillNumber The skill number that will be obtained. Depends on rarity. Not 0 index
     * @return Returns an EmbedBuilder with the embeds
     */
    private EmbedBuilder getSkillEmbedBuilder(String unitName, int skillNumber){
        try{
            GetArknightsSkillInfo getArknightsSkillInfo = new GetArknightsSkillInfo();
            EmbedBuilder eb = getArknightsSkillInfo.getSkillEmbedBuilder(unitName,skillNumber);
            return eb;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This function will help the command generate the Talent Embed side of the embed. This is using
     * the commands helper function.
     * @param unitName The name of the unit
     * @return
     */
    private EmbedBuilder getTalentEmbedBuilder(String unitName){
        GetArknightsTalent getArknightsTalent = new GetArknightsTalent();
        return getArknightsTalent.getTalentEmbed(unitName);
    }

    /**
     * This function will help the command generate the Extra Stats Emebed side of the embed. This is using
     * the commands helper function.
     * @param unitName
     * @return
     */
    private EmbedBuilder getExtraStatsEmbedBuilder(String unitName){
        GetArknightsExtraStats getArknightsExtraStats = new GetArknightsExtraStats();
        return getArknightsExtraStats.getExtraStatsEmbedBuilder(unitName);
    }

    private void checkArgs(List<String> args) throws GeneralExceptions.ArgumentSize {
        if( args.size() == 0 | args.size() > 1 )
            throw new GeneralExceptions.ArgumentSize("Please make sure to only enter one argument. It should only be the name of the character.");
    }
}
