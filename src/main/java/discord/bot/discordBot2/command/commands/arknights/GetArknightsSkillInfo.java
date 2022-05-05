package discord.bot.discordBot2.command.commands.arknights;

import discord.bot.discordBot2.command.CommandContext;
import discord.bot.discordBot2.command.ICommand;
import discord.bot.discordBot2.command.commands.arknights.api.Database;
import discord.bot.discordBot2.command.commands.arknights.api.Skill;
import discord.bot.discordBot2.helper.EmbedCreator;
import discord.bot.discordBot2.helper.EventWaiterSingleton;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class GetArknightsSkillInfo implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        GuildMessageReceivedEvent event = ctx.getEvent();
        MessageChannel channel = event.getChannel();
        List<String> args = ctx.getArgs();

        try {
            if (args.size() != 2) {
                channel.sendMessage("The number of argument is invalid. Make sure to put the unit name followed by the number of the skill. Ex: Ch'en 2");
            } else {

                String unitName = args.get(0);
                int skillNumber = Integer.parseInt(args.get(1));
                if (skillNumber > 3 || skillNumber < 1)
                    throw new Exception(); //todo Create a specific exception for this
                Skill skill = Database.getCharacterSkillInformation(unitName,skillNumber);

                AtomicReference<EmbedBuilder> eb = new AtomicReference<>(EmbedCreator.createSkillEmbed(skill,0));
                //todo: Need to create a Embed for this Skill. Should do it tomorrow
                channel.sendMessageEmbeds(eb.get().build()).queue(
                        (message) -> {
                            message.addReaction("⬅️").queue();
                            message.addReaction("➡️").queue();
                            addEventWaiter(message,channel,skill,eb);
                        }
                );
            }
        }
        catch (Exception e){
            channel.sendMessage("Seems like the second argument is either not a number or is not between 1 and 3.");
        }
    }

    /** This can return null **/
    public EmbedBuilder getSkillEmbedBuilder(String unitName, int skillNumber){
        Skill skill = Database.getCharacterSkillInformation(unitName,skillNumber);
        return EmbedCreator.createSkillEmbed(skill,skill.size() - 1);
    }


    @Override
    public String getName() {
        return "skill";
    }

    @Override
    public String getHelp() {
        return "Returns the information of the specific skill (at max level) that the user asked for.";
    }

    private void addEventWaiter(Message message, MessageChannel channel, Skill skill, AtomicReference<EmbedBuilder> eb){
        EventWaiterSingleton.getInstance().waitForEvent(
                GuildMessageReactionAddEvent.class,
                (e) -> e.getMessageIdLong() == message.getIdLong() && !e.getUser().isBot() &&
                        ( e.getReactionEmote().getEmoji().equals("➡️") || e.getReactionEmote().getEmoji().equals("⬅️")),
                (e) -> {
                    String emote = e.getReactionEmote().getEmoji();
                    if(emote.equals("⬅️"))
                    {
                        skill.substractIndex();
                        int index = skill.getIndex();
                        eb.set(EmbedCreator.createSkillEmbed(skill,index));
                    }
                    else {
                        skill.addIndex();
                        int index = skill.getIndex();
                        eb.set(EmbedCreator.createSkillEmbed(skill,index));
                    }

                    message.editMessageEmbeds(eb.get().build()).queue( (newMessage) -> {
                        addEventWaiter(message,channel,skill,eb);
                    });
                }
                ,
                20L, TimeUnit.SECONDS,
                () -> {}
        );
    }
}
