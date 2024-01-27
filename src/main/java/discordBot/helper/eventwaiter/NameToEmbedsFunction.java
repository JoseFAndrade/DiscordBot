package discordBot.helper.eventwaiter;

import net.dv8tion.jda.api.EmbedBuilder;

import java.util.List;


@FunctionalInterface
/**
 * A functional interface that works by giving it the name of a unit and then implementing methods to obtain a list of
 * Embeds that will be printed. I use this within my code in order to make it easier to split up the jobs of my
 * functions.
 */
public interface NameToEmbedsFunction {

    List<EmbedBuilder> obtainEmbeds(String unitName);
}
