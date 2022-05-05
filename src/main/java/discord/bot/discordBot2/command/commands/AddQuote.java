package discord.bot.discordBot2.command.commands;

import discord.bot.discordBot2.helper.RandomResponseHelper;
import discord.bot.discordBot2.command.CommandContext;
import discord.bot.discordBot2.command.ICommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;


//TODO need to convert all of the print statements within this code into logging files in order to keep all the information
//TODO in a better place and have my code not slow down due to the number of print statements that it has to go through
public class AddQuote implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        //variables needed in order to be initialized
        GuildMessageReceivedEvent event = ctx.getEvent();
        Message message = event.getMessage();
        MessageChannel channel = event.getChannel();
        List<String> args = ctx.getArgs();

        String quoteCombined = "";
        String authorIDFAdding = "";
        List<Member> mentionedMembers = message.getMentionedMembers();

        //logic of the code
        boolean added = false;
        if(mentionedMembers.size() == 0)
        {
            System.err.println("addQuote ran");
            for(int i = 0; i < args.size(); i++)
                quoteCombined+= args.get(i) + " ";
            authorIDFAdding = event.getAuthor().getId();
            RandomResponseHelper.addQuote(authorIDFAdding,quoteCombined);
            channel.sendMessage("The quote seems to have been added!").queue();
            added = true;
        }
        else {
            if (mentionedMembers.size() > 1)
                channel.sendMessage("Sorry, it seems like the number of mentioned users is more than one." +
                        "Try with one meniton only.").queue();
            else {
                for(int i = 1; i < args.size() ; i++)
                    quoteCombined+= args.get(i) + " ";

                authorIDFAdding = mentionedMembers.get(0).getUser().getId();
                System.err.println(authorIDFAdding);
                RandomResponseHelper.addQuote(authorIDFAdding,quoteCombined);
                channel.sendMessage("The quote seems to have been added!").queue();
                added = true;
                message.delete().queueAfter(2, TimeUnit.SECONDS);
            }
        }

        if(added){
            //run a seperate thread to make sure that we are saving the information
            Runnable runnable =
                    () -> { RandomResponseHelper.saveQuotes(); };
            Thread thread = new Thread(runnable);
            thread.start();
        }
    }

    @Override
    public String getName() {
       return "addQuote";
    }

    @Override
    public String getHelp() {
        return "This is a function that will add a quote on to the user... ";
    }

    @Override
    public List<String> getAliases(){ return Arrays.asList("AQ","aq");}
}
