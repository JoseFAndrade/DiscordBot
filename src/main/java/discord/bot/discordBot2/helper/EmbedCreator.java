package discord.bot.discordBot2.helper;

import discord.bot.discordBot2.command.commands.arknights.api.GeneralCharacterInformation;
import discord.bot.discordBot2.command.commands.arknights.api.Potentials;
import discord.bot.discordBot2.command.commands.arknights.api.Skill;
import discord.bot.discordBot2.command.commands.arknights.api.TrustStats;
import discord.bot.discordBot2.command.commands.arknights.api.talent.TalentBean;
import discord.bot.discordBot2.command.commands.arknights.api.talent.TalentManager;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.List;


public class EmbedCreator {

    //Create an embed with the provided GeneralCharacterInformation class
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
        eb.addField("Trait",generalCharacterInformation.trait,true);
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
