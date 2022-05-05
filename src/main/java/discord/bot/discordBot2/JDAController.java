package discord.bot.discordBot2;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

public class JDAController {

    private static JDA obj;
    private static String token = "NTM5ODc2MjA5NDU5MzMxMDcz.DzIuiA.OSHtmeiiUTjT1ysO0Z4Bzd3--1w";

    public static JDA getJDA(){
        if(obj == null) {
            try {
                /*
                obj = JDABuilder.createDefault(token,
                        GatewayIntent.GUILD_MESSAGE_REACTIONS,
                        GatewayIntent.GUILD_EMOJIS)
                        .enableCache(CacheFlag.EMOTE)
                        .build();// JDABuilder(token).build();

                 */
                obj = JDABuilder.createDefault(token)
                        .build();// JDABuilder(token).build();
                return obj;
            } catch (LoginException e) {
                e.printStackTrace();
                return null;
            }
        }
        else {
            return obj;
        }
    }
}
