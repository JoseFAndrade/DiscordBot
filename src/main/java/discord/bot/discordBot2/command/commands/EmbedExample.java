package discord.bot.discordBot2.command.commands;

import discord.bot.discordBot2.command.CommandContext;
import discord.bot.discordBot2.command.ICommand;
import discord.bot.discordBot2.helper.EventWaiterSingleton;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.EmbedType;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import net.dv8tion.jda.api.utils.Timestamp;

import java.awt.*;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class EmbedExample implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        GuildMessageReceivedEvent event = ctx.getEvent();
        MessageChannel messageChannel = event.getChannel();
        List<String> args = ctx.getArgs();

        Date date = new Date();
        MessageEmbed messageEmbed = new MessageEmbed("https://www.youtube.com", "Title Test","description", EmbedType.RICH, OffsetDateTime.now(),
                5, null, null, null, null, null, null, null);

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Character Name Here", null);
        eb.setColor(Color.RED);
        eb.setImage("https://gamepress.gg/arknights/sites/arknights/files/2019-12/char_017_huang_1_0.png");
        eb.setDescription("This is character test description");
        eb.addBlankField(false);
        eb.addField("Stats at max level",null , false);
        eb.addField("```HP```", "description of field", true);
        eb.addField("ATK", "description of field", true);
        eb.addField("DEF", "description of field", true);
        eb.addField("Art Resist", "description of field", true);
        eb.addField("Redeploy Time", "description of field", true);
        eb.addField("DP Cost", "description of field", true);
        eb.addField("Block", "description of field", true);
        eb.addBlankField(false);
        eb.addField("Title of Field", "description of field", false);


        EmbedBuilder skillEB = new EmbedBuilder();
        eb.setTitle("Character Skills would end up going here", null);
        eb.setColor(Color.BLUE);
        eb.addField("Skill 1", "Description for skill one.", false);

        //eb.setAuthor("Author", null);
        //eb.setFooter("Footer", null);
        //eb.setImage("https://gamepress.gg/arknights/sites/arknights/files/2019-12/char_017_huang_1_0.png");

        String arrowForwardID = "871275150060945419";

        messageChannel.sendMessageEmbeds(eb.build()).queue( (message) -> {
            message.addReaction("\u25B6").queue(); //▶️ same as this icon on the right of the comment
            EventWaiterSingleton.getInstance().waitForEvent(GuildMessageReactionAddEvent.class,
            (e) -> {
                return e.getMessageIdLong() == message.getIdLong() && !e.getUser().isBot() &&
                        e.getReactionEmote().getId().equals(arrowForwardID);
            },
            (e) -> {

            },
            60L, TimeUnit.SECONDS,
            () ->
                messageChannel.sendMessage("Nothing happened").queue()

            );
        });

    }

    @Override
    public String getName() {
        return "embedExample";
    }

    @Override
    public String getHelp() {
        return "None";
    }
}
