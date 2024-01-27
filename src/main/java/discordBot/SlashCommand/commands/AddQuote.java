package discordBot.SlashCommand.commands;

import discordBot.SlashCommand.ICommand;
import discordBot.helper.QuoteHolder;
import discordBot.helper.QuoteManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    public List<OptionData> getOptions() {
        List<OptionData> options = new ArrayList<>();
        options.add( new OptionData(OptionType.STRING, "quote", "The quote that you want to add", true));
        options.add( new OptionData(OptionType.USER,"member","Mention the member who you want to add the quote for or leave empty " +
                "for yourself",false));
        //then we can just use event.getOption(<name of the option data here in string>)
        return options;
    }

    @Override
    public void handle(SlashCommandInteractionEvent event) {
        List<OptionMapping> mentionedMembers = event.getOptions();
        int size = mentionedMembers.size();

        //String quote = combineQuote(size, event.getOptions());
        String quote = event.getOption("quote").getAsString();

        String authorID = (size == 1) ? event.getMember().getId() : event.getOption("member").getAsUser().getId();
        QuoteManager quoteManager = QuoteManager.getInstance();
        QuoteHolder quoteHolder = quoteManager.getQuoteHolder(event.getGuild().getIdLong());
        event.reply("The Quote is now being added.").queue();
        quoteHolder.addQuote(authorID, quote, event.getGuild().getIdLong()  );
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
