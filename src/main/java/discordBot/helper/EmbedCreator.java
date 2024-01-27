package discordBot.helper;

import discordBot.command.commands.arknights.api.GeneralCharacterInformation;
import discordBot.command.commands.arknights.api.Potentials;
import discordBot.command.commands.arknights.api.Skill;
import discordBot.command.commands.arknights.api.TrustStats;
import discordBot.command.commands.arknights.api.talent.TalentBean;
import discordBot.command.commands.arknights.api.talent.TalentManager;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.List;


/**
 * The EmbedCreator class helps easily create embeds for many different things. You insert a struct like class
 * into the function and it will easily create the embed required.
 */
public class EmbedCreator {

    /**
     * Create an embed with the provided GeneralCharacterInformation class
     *
     * @param generalCharacterInformation the object from where the information will be obtained from
     * @return the EmbedBuilder that corresponds to the object inputted
     */
    public static EmbedBuilder createCharacterEmbed(GeneralCharacterInformation generalCharacterInformation){
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(generalCharacterInformation.name + " " + addStars(generalCharacterInformation.rarity), null);
        eb.setColor(Color.RED);
        eb.setImage(generalCharacterInformation.imageURL);
        eb.setDescription("");
        //eb.addBlankField(false);
        eb.addField("```General Information```","", false);
        eb.addField("Class Type",generalCharacterInformation.classType,true);
        eb.addField("Archetype",generalCharacterInformation.archetype,true);
        eb.addField("Position",generalCharacterInformation.position,true);
        eb.addField("Attack Type",generalCharacterInformation.attackType,true);
        eb.addField("Tags",generalCharacterInformation.tags,true);
        eb.addField("Trait",generalCharacterInformation.trait,false);
        eb.addBlankField(false);
        eb.addField("```Stats at max level```","", false);
        eb.addField("HP: " + generalCharacterInformation.hp, "", true);
        eb.addField("ATK: " + generalCharacterInformation.atk, "", true);
        eb.addField("DEF: " + generalCharacterInformation.def, "", true);
        eb.addField("Art Resist: " + generalCharacterInformation.artRes, "", true);
        eb.addField("Redeploy Time: " + generalCharacterInformation.redeployTime, "", true);
        eb.addField("DP Cost: " + generalCharacterInformation.dpCost, "", true);
        eb.addField("Block: " + generalCharacterInformation.block, "", true);

        return eb;
    }

    /**
     * Create an embed with the provided skill class and index of the skill. (To create a list of these embeds
     * you will need to loop through the function x amount of times. Where x is the maximum skill level.
     * @param skill object from where the information will be displayed from
     * @param index a number representing the level of the skill
     * @return will return an EmbedBuilder with the level of the Skill
     */
    public static EmbedBuilder createSkillEmbed(Skill skill, int index){
        int lastIndex = skill.spCostInfo.size() - 1;
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(skill.name, null);
        eb.setColor(Color.RED);
        //eb.setImage(generalCharacterInformation.imageURL);
        eb.setDescription("");
        //eb.addBlankField(false);
        eb.addField("```Skill Information```","", false);
        eb.addField("Skill Level", String.valueOf(index+1),true);
        eb.addField("SP Cost", skill.spCostInfo.get(index),true);
        eb.addField("Initial SP", skill.initialSpInfo.get(index),true   );
        eb.addField("SP Charge Type", skill.spChargeType, true);
        eb.addField("Skill Activation", skill.spSkillActivation, true);
        eb.addField("Duration", skill.skillUtilizationInfo.get(index),true);
        eb.addBlankField(false);
        eb.addField("Description", skill.skillEffectInfo.get(index), true);

        if(!skill.skillTiles.equals("")){
            eb.addField("Skill tiles",skill.skillTiles,true );
        }
        return eb;
    }

    /**
     * Creates a sinmple event with the Talent object that was provided
     * @param talentManager the object which contains the information that will be added onto the embed
     * @return will return an embedBuilder with the required information
     */
    public static EmbedBuilder createTalentEmbed(TalentManager talentManager){
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(talentManager.getUnitName() + " Talents");
        eb.setColor(Color.RED);
        eb.setDescription("");
        for(int i =0; i < talentManager.size(); i++){
            List<TalentBean> talentBeanList = talentManager.getTalentBeanList(i);
            //System.out.println(talentBeanList);
            eb.addField("```" + talentBeanList.get(0).getTalentName() + "```","",false);
            for(int y = 0; y < talentManager.getTalentBeanList(i).size(); y++){
                eb.addField("Talent Level: " + (y + 1),talentBeanList.get(y).getTalentDescription(),false);
            }
            //eb.addBlankField(true);
        }
        return eb;
    }

    //TODO LOOK INTO CREATE BETTER ADDFIELDS COMMANDS IN EMBEDBUILDER
    //TODO POSSIBLY LOOKIGN INTO WHAT FIELD CAN DO FOR ME

    /**
     * This will create an EmbedBuilder that contains both the potential and trustStat information of a unit.
     * @param potential The object that contains potential information of the character
     * @param trustStats The object that contains the trust stats information of the character
     * @return a join Embed builder where the information is displayed on one embed
     */
    public static EmbedBuilder createExtraStatsEmbed(Potentials potential, TrustStats trustStats){
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(potential.getUnitName() + " Extra Stats");
        eb.setColor(Color.RED);
        eb.setDescription("");

        eb.addField("```Potentials```","",false);

        for(int i = 0; i < potential.size(); i++){
            eb.addField("",potential.getDescription(i),false);
        }
        eb.addField("```Trust Stats```","",false);
        for(int i = 0; i < trustStats.size(); i++){
            eb.addField("",trustStats.getTrustStat(i),false);
        }
        return eb;
    }

    /**
     * Simple function that just returns the *'s depending on the rarity number. This function can work differently
     * depending on the way that I want the stars to look. It's an easy way to create a method that I can easily
     * change in here and it will update everywhere I used this function.
     * @param rarity an integer of the rarity of the unit
     * @return returns a string with the number of stars
     */
    public static String addStars(int rarity){
        return rarity + "*";
        /*
        String result = "";
        for(int i =0; i < rarity; i++)
            result+= "* ";
        return result;
         */
    }
}
