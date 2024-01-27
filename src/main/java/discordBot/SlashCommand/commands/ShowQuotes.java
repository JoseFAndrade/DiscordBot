package discordBot.SlashCommand.commands;

import discordBot.SlashCommand.ICommand;
import discordBot.helper.QuoteHolder;
import discordBot.helper.QuoteManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;


import java.util.ArrayList;
import java.util.List;

/**
 * The ShowQuotes class is a very simple command where it will show the quotes for a certain user
 * 1) It can either be used for yourself by just running the command
 * 2) It can be used to see the quotes of someone else by inputting the command and a mention afterwards
 */
public class ShowQuotes implements ICommand {

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> optionDataList = new ArrayList<>();
        optionDataList.add( new OptionData(OptionType.USER, "user", "The user whose quotes you want to see",false));
        return optionDataList;
    }

    @Override
    public void handle(SlashCommandInteractionEvent event) {

        int size = event.getOptions().size();
        String authorID = (size == 0 ) ? event.getMember().getId() : event.getOption("user").getAsUser().getId();

        QuoteHolder quoteHolder = QuoteManager.getInstance().getQuoteHolder(event.getGuild().getIdLong());
        event.reply(quoteHolder.returnUserQuotes(authorID)).queue();


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
