package discord.bot.discordBot2.command.commands.arknights;

import net.dv8tion.jda.api.EmbedBuilder;

import java.util.List;

@FunctionalInterface
public interface NameToEmbedsFunction {

    List<EmbedBuilder> obtainEmbeds(String unitName);
}
