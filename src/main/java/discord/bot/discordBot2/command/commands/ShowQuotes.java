package discord.bot.discordBot2.command.commands;

import discord.bot.discordBot2.helper.RandomResponseHelper;
import discord.bot.discordBot2.command.CommandContext;
import discord.bot.discordBot2.command.ICommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class ShowQuotes implements ICommand {
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
        if(mentionedMembers.size() == 0)
        {
            System.err.println("showQuote ran");
            authorIDFAdding = event.getAuthor().getId();
            channel.sendMessage(RandomResponseHelper.returnUserQuotes(authorIDFAdding)).queue();
        }
        else {
            if (mentionedMembers.size() > 1)
                channel.sendMessage("Sorry, it seems like the number of mentioned users is more than one." +
                        "Try with one meniton only.").queue();
            else {
                authorIDFAdding = mentionedMembers.get(0).getUser().getId();
                channel.sendMessage(RandomResponseHelper.returnUserQuotes(authorIDFAdding)).queue();

            }
        }
    }

    @Override
    public String getName() {
        return "showQuotes";
    }

    @Override
    public String getHelp() {
        return "nope omegalul";
    }
}
