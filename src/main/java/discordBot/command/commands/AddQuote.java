package discordBot.command.commands;

import discordBot.helper.QuoteHolder;
import discordBot.command.CommandContext;
import discordBot.command.ICommand;
import discordBot.helper.QuoteManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;


//TODO need to convert all of the print statements within this code into logging files in order to keep all the information
//TODO in a better place and have my code not slow down due to the number of print statements that it has to go through


/**
 * The AddQuote class holds a command function for the discord bot. It is able to do two different things.
 *
 * 1) Add a quote for the user using the function
 * 2) Add a quote for another user using the tag feature (@<name>)
 * AddQuote class will save the quote for the specific user and will use it for other commands.
 */
public class AddQuote implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        //variables needed in order to be initialized
        MessageReceivedEvent event = ctx.getEvent();
        Message message = event.getMessage();
        MessageChannel channel = event.getChannel();
        List<String> args = ctx.getArgs();

        List<Member> mentionedMembers = message.getMentions().getMembers();
        int mentionedMemberSize = mentionedMembers.size();

        //Code Logic
        if (mentionedMemberSize> 1)
            channel.sendMessage("Sorry, it seems like the number of mentioned users is more than one." +
                    "Try with one mention only or none to add a quote for yourself.").queue();

        else{
            String quote = combineQuote(mentionedMemberSize, args);

            //obtain the ID of either: The person who's adding a quote for themself or adding a quote for someone else
            String authorID = (mentionedMemberSize == 0 ) ? event.getAuthor().getId() : mentionedMembers.get(0).getUser().getId();

            QuoteManager quoteManager = QuoteManager.getInstance();
            QuoteHolder quoteHolder = quoteManager.getQuoteHolder(event.getGuild().getIdLong());
            quoteHolder.addQuote(authorID,quote, message.getGuild().getIdLong());

            channel.sendMessage("The quote seems to have been added!").queue();
            message.delete().queueAfter(2, TimeUnit.SECONDS);
        }

    }

    /**
     * This fuction will take in the arguments of a command and be able to join up the actual parts of the
     * quote (since args is split up by spaces a quote with multiple spaces will get seperated)
     *
     * We know that either there will be a Mention or Not
     * 1) !.addQuote @<Name> <Quote> Ex: !.addQuote @Jose He is a programmer
     * 2) !.addQuote <Quote> Ex: !.addQuote I am a programmer
     *
     * @param mentionedMember This number will either be 0 or 1 for the # of mentioned member
     * @param args The arguments within a command call
     * @return
     */
    private String combineQuote(int mentionedMember, List<String> args){
        String quote = "";
        for(int i = mentionedMember; i < args.size() ; i++)
            quote += args.get(i) + " ";

        return quote;
    }

    @Override
    public String getName() {
       return "addquote";
    }

    @Override
    public String getHelp() {
        return "This is a function that will add a quote on to the user... ";
    }

    @Override
    public List<String> getAliases(){ return Arrays.asList("AQ","aq");}
}
