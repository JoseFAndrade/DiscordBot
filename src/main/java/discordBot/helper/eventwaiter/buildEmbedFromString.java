package discordBot.helper.eventwaiter;

import net.dv8tion.jda.api.EmbedBuilder;

@FunctionalInterface
/**
 * This is a functional interface that contains a function that will turn a String into an embed. The user will have
 * to make sure that it is not passing the character limits of an embed. The user is able to manipulate their string
 * in any form as long as it returns an EmbedBuilder.
 */
public interface buildEmbedFromString {
    EmbedBuilder buildEmbed(String string);
}
