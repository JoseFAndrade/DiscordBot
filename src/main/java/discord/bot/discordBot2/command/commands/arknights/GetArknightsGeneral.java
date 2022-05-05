package discord.bot.discordBot2.command.commands.arknights;

import discord.bot.discordBot2.command.CommandContext;
import discord.bot.discordBot2.command.ICommand;
import discord.bot.discordBot2.command.commands.arknights.api.Database;
import discord.bot.discordBot2.command.commands.arknights.api.GeneralCharacterInformation;
import discord.bot.discordBot2.helper.EmbedCreator;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;

public class GetArknightsGeneral implements ICommand {
    int index = 0;
    @Override
    public void handle(CommandContext ctx) {
        GuildMessageReceivedEvent event = ctx.getEvent();
        //Message message = event.getMessage();
        MessageChannel channel = event.getChannel();
        List<String> args = ctx.getArgs();

        Long userID = ctx.getAuthor().getIdLong();

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

    @Override
    public String getName() {
        return "general";
    }

    @Override
    public String getHelp() {
        return "This command will return General Information about the character.";
    }

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

    private EmbedBuilder getTalentEmbedBuilder(String unitName){
        GetArknightsTalent getArknightsTalent = new GetArknightsTalent();
        return getArknightsTalent.getTalentEmbed(unitName);
    }

    private EmbedBuilder getExtraStatsEmbedBuilder(String unitName){
        GetArknightsExtraStats getArknightsExtraStats = new GetArknightsExtraStats();
        return getArknightsExtraStats.getExtraStatsEmbedBuilder(unitName);
    }

    /*
    private void substractIndex(int size){
        index = --index;
        if(index == -1)
            index = size -1;
    }

    private void addIndex(int size){
        index = ++index % size;
    }

    private int getIndex(){
        return index;
    }

    private void addEventWaiter(Message message, MessageChannel channel, List<EmbedBuilder> embedBuilderList){
        EventWaiterSingleton.getInstance().waitForEvent(
                GuildMessageReactionAddEvent.class,
                (e) -> e.getMessageIdLong() == message.getIdLong() && !e.getUser().isBot() &&
                        ( e.getReactionEmote().getEmoji().equals("➡️") || e.getReactionEmote().getEmoji().equals("⬅️")),
                (e) -> {
                    String emote = e.getReactionEmote().getEmoji();
                    EmbedBuilder toSend = null;
                    if(emote.equals("⬅️"))
                    {
                        substractIndex(embedBuilderList.size());
                    }
                    else {
                        addIndex(embedBuilderList.size());
                    }

                    toSend = embedBuilderList.get(this.index);

                    message.editMessageEmbeds(toSend.build()).queue( (newMessage) -> {
                        addEventWaiter(newMessage,channel,embedBuilderList);
                    } );

                }
                ,
                20L, TimeUnit.SECONDS,
                () -> {}


                );
    }
    */

}
