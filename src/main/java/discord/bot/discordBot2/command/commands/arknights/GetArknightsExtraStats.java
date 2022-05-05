package discord.bot.discordBot2.command.commands.arknights;

import discord.bot.discordBot2.command.CommandContext;
import discord.bot.discordBot2.command.ICommand;
import discord.bot.discordBot2.command.commands.arknights.api.Database;
import discord.bot.discordBot2.command.commands.arknights.api.Potentials;
import discord.bot.discordBot2.command.commands.arknights.api.TrustStats;
import discord.bot.discordBot2.helper.EmbedCreator;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class GetArknightsExtraStats implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        GuildMessageReceivedEvent event = ctx.getEvent();
        MessageChannel channel = event.getChannel();
        List<String> args = ctx.getArgs();

        EmbedBuilder eb = getExtraStatsEmbedBuilder(args.get(0));
        channel.sendMessageEmbeds(eb.build()).queue();

    }

    @Override
    public String getName() {
        return "extraStats";
    }

    @Override
    public String getHelp() {
        return "This will return Potential and Trust Extra stats of the character.";
    }

    public EmbedBuilder getExtraStatsEmbedBuilder(String unitName){
        Potentials potential = Database.getCharacterPotential(unitName);
        TrustStats trustStats = Database.getCharacterTrustExtraStats(unitName);

        EmbedBuilder eb = EmbedCreator.createExtraStatsEmbed(potential,trustStats);
        return eb;
    }
}
