package discordBot.command.commands.arknights;

import discordBot.command.CommandContext;
import discordBot.command.ICommand;
//import discord.bot.discordBot2.command.commands.arknights.api.Database;
import discordBot.helper.GeneralExceptions;
import net.dv8tion.jda.api.EmbedBuilder;


import java.util.List;


/**
 * This class will give the skill information of the unit. It will allow the user to provide an input for the skill
 * that should be displayed.
 */

//todo this need error checking to be done at some point
public class GetArknightsSkillInfo implements ICommand {
    @Override
    public void handle(CommandContext ctx) {

        return;

        /*
        MessageReceivedEvent
 event = ctx.getEvent();
        MessageChannel channel = event.getChannel();
        List<String> args = ctx.getArgs();

        try {
            checkArgs(args);
            String unitName = args.get(0);
            int skillNumber = Integer.parseInt(args.get(1));
            checkSkillNumber(skillNumber);

            Skill skill = Database.getCharacterSkillInformation(unitName,skillNumber);

            List<EmbedBuilder> embedBuilderList = new ArrayList<>();

            for(int i = 0; i < skill.size(); i++)
                embedBuilderList.add( EmbedCreator.createSkillEmbed(skill, i) );

            Helper.sendEmbed(embedBuilderList,channel);
        }
        catch (Exception e){
            channel.sendMessage(e.getMessage()).queue();
        }

         */
    }

    /**
     * This will return the skill information of the specific unit (the skill depends on what number was called)
     * in the form of an EmbedBuilder that will then be sent out.
     * @param unitName A string that contains the name of the unit
     * @param skillNumber An int displaying what skill to obtain the information
     * @return An EmbedBuilder in order to be displayed to the user.
     */
    public EmbedBuilder getSkillEmbedBuilder(String unitName, int skillNumber){
        return null;
        //Skill skill = Database.getCharacterSkillInformation(unitName,skillNumber);
        //return EmbedCreator.createSkillEmbed(skill,skill.size() - 1);
    }


    @Override
    public String getName() {
        return "skill";
    }

    @Override
    public String getHelp() {
        return "Returns the information of the specific skill (at max level) that the user asked for.";
    }

    private void checkArgs(List<String> args) throws GeneralExceptions.ArgumentSize {
        if( args.size() != 2)
            throw new GeneralExceptions.ArgumentSize("Please make sure to only enter two argument. It should only be the name of the character & " +
                    "the skill number corresponding to the skill level that you want.  Ex: Ch'en 2");
    }

    private void checkSkillNumber(int number) throws GeneralExceptions.ArgumentSize {
        if( number > 3 || number < 1){
            throw new GeneralExceptions.ArgumentSize("Please make sure to only enter a number between 1 and 3.");
        }

    }

}
