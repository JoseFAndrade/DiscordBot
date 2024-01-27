package discordBot.command.commands;

import discordBot.helper.QuoteHolder;
import discordBot.command.CommandContext;
import discordBot.command.ICommand;
import discordBot.helper.QuoteManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;


import java.util.List;

/**
 * The ShowQuotes class is a very simple command where it will show the quotes for a certain user
 * 1) It can either be used for yourself by just running the command
 * 2) It can be used to see the quotes of someone else by inputting the command and a mention afterwards
 */
public class ShowQuotes implements ICommand {
    @Override
    public void handle(CommandContext ctx) {

        //variables needed in order to be initialized
        MessageReceivedEvent event = ctx.getEvent();
        Message message = event.getMessage();
        MessageChannel channel = event.getChannel();

        List<Member> mentionedMembers = message.getMentions().getMembers();

        QuoteHolder quoteHolder = QuoteManager.getInstance().getQuoteHolder(event.getGuild().getIdLong());
        int mentionedMemberSize = mentionedMembers.size();

        if (mentionedMemberSize> 1)
            channel.sendMessage("Sorry, it seems like the number of mentioned users is more than one." +
                    " Try with one meniton only.").queue();
        else{
            String authorID = (mentionedMemberSize == 0 ) ? event.getAuthor().getId() : mentionedMembers.get(0).getUser().getId();
            channel.sendMessage(quoteHolder.returnUserQuotes(authorID)).queue();
        }

    }

    @Override
    public String getName() {
        return "showquotes";
    }

    @Override
    public String getHelp() {
        return "This command will return the total number of quotes that the user contains. This can either be ran for yourself or by mentioning someone else. Ex: !.showQuotes or " +
                "!.showQuotes @Someone";
    }
}
