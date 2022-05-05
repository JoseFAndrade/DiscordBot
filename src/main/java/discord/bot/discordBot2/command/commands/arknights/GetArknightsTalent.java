package discord.bot.discordBot2.command.commands.arknights;

import discord.bot.discordBot2.command.CommandContext;
import discord.bot.discordBot2.command.ICommand;
import discord.bot.discordBot2.command.commands.arknights.api.Database;
import discord.bot.discordBot2.command.commands.arknights.api.talent.Talent;
import discord.bot.discordBot2.command.commands.arknights.api.talent.TalentManager;
import discord.bot.discordBot2.helper.EmbedCreator;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class GetArknightsTalent implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        GuildMessageReceivedEvent event = ctx.getEvent();
        MessageChannel channel = event.getChannel();
        List<String> args = ctx.getArgs();

        EmbedBuilder eb = getTalentEmbed(args.get(0));
        channel.sendMessageEmbeds(eb.build()).queue();
    }

    @Override
    public String getName() {
        return "talent";
    }

    @Override
    public String getHelp() {
        return "This will return the talents of the unit whose name is being requested. ";
    }

    public EmbedBuilder getTalentEmbed(String unitName){
        TalentManager talentManager = Database.getCharacterTalentInformation(unitName);
        EmbedBuilder eb = EmbedCreator.createTalentEmbed(talentManager);
        return eb;
    }
}
