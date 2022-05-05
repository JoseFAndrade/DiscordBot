package discord.bot.discordBot2.command.commands;

import discord.bot.discordBot2.command.CommandContext;
import discord.bot.discordBot2.command.ICommand;
import discord.bot.discordBot2.helper.EventWaiterSingleton;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class TestUserInput implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        GuildMessageReceivedEvent event = ctx.getEvent();
        Message userMessage = event.getMessage();
        MessageChannel channel = event.getChannel();
        List<String> args = ctx.getArgs();
        Long userID = ctx.getAuthor().getIdLong();

        channel.sendMessage("Hello, please tell me if you want A or B.").queue(
                (message) -> { addEventWaiter(message,channel,userID ); }
        );
    }

    @Override
    public String getName() {
        return "testUserInput";
    }

    @Override
    public String getHelp() {
        return "";
    }

    private void addEventWaiter(Message message, MessageChannel channel, long userID){
        EventWaiterSingleton.getInstance().waitForEvent(
                GuildMessageReceivedEvent.class,
                (e) -> e.getAuthor().getIdLong() == userID && !e.getAuthor().isBot(),
                (e) -> {
                    String messageContent = e.getMessage().getContentRaw();
                    if(messageContent.equals("A"))
                        channel.sendMessage("So you have picked A.").queue();
                    else{
                        channel.sendMessage("So you have picked B.").queue();
                    }
                }
                ,
                20L, TimeUnit.SECONDS,
                () -> {channel.sendMessage("No choice was made. Re-input command to get this point again.").queue();}
        );
    }
}
