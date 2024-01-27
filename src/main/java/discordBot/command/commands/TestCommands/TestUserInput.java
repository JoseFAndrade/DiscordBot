package discordBot.command.commands.TestCommands;

import discordBot.command.CommandContext;
import discordBot.command.ICommand;
import discordBot.helper.eventwaiter.EventWaiterSingleton;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;


import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestUserInput implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        MessageReceivedEvent
 event = ctx.getEvent();
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
                MessageReceivedEvent
.class,
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
