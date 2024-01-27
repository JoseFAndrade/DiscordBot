package discordBot.command.commands.TestCommands;

import discordBot.command.CommandContext;
import discordBot.command.ICommand;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.util.List;

/*todo
import net.dv8tion.jda.api.events.message.guild.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.entities.Emote;
*/

public class TestCommand implements ICommand {
    private final String EMOTE = "<:omegalul:658214761728835586>";

    @Override
    public void handle(CommandContext ctx) {
        MessageReceivedEvent
 event = ctx.getEvent();
        //Message message = event.getMessage();
        MessageChannel channel = event.getChannel();
        List<String> args = ctx.getArgs();

        /*TODO emote fixing here
        List<Emote> emotes = ctx.getGuild().getEmotesByName("arrow_forward",true);
        Emote emote = emotes.get(0);
        System.out.println(emote.getAsMention());

        channel.sendMessage("React with <:omegalul:658214761728835586>")
                .queue((message) -> {
                    EventWaiterSingleton.getInstance().waitForEvent(
                            GuildMessageReactionAddEvent.class,
                            (e) -> {

                                //this checks to make sure that the reaction where the user reacted to is the same message
                                //that the bot sent. It also makes sure the user is not a bot. Additionally, it checks that
                                //the emotes that the bot reacted with vs the one the user reacted with are the same
                                return e.getMessageIdLong() == message.getIdLong() && !e.getUser().isBot() &&
                                        e.getReactionEmote().getId().equals(emote.getId());
                            },
                            (e) -> {
                                channel.sendMessage(e.getUser().getAsMention() + " Was first to react.").queue();
                            },
                            20L, TimeUnit.SECONDS,
                            () -> {channel.sendMessage("You waited too long").queue();}
                    );
                });

         */
    }

    @Override
    public String getName() {
        return "test";
    }

    @Override
    public String getHelp() {
        return "A method to test out EventWaiter";
    }
}
