package discordBot.command.commands.music_commands;

import discordBot.command.CommandContext;
import discordBot.command.ICommand;
import discordBot.helper.GeneralExceptions;
import discordBot.helper.music.PlayerManager;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

/**
 * A simple command that gives the user the ability to change the volume of the bot when playing music.
 */
public class ChangeVolumeMusic implements ICommand {

    /**
     * This function will check the input of the user and then if the input is valid it will update the volume of
     * the bot.
     * @param ctx The CommandContext that gets passed in from CommandManager
     */
    @Override
    public void handle(CommandContext ctx) {
        MessageReceivedEvent
 event = ctx.getEvent();
        MessageChannel channel = event.getChannel();
        List<String> args = ctx.getArgs();
        PlayerManager manager = PlayerManager.getInstance();

        try{
            checkArgs(args);
            manager.changeVolume(manager, Integer.parseInt(args.get(0)), event);
        }
        catch (NumberFormatException e){
            channel.sendMessage("The input is not a number. Please try again with a number").queue();
        }
        catch (Exception e){
            channel.sendMessage(e.getMessage()).queue();
        }
    }

    @Override
    public String getName() {
        return "setVolume";
    }

    @Override
    public String getHelp() {
        return "This command will change the volume of the music. The command can be used by doing 'setVolume <number 1-100>'";
    }

    public void checkArgs(List<String> args) throws GeneralExceptions.ArgumentSize {
        if(args.size() > 1 || args == null || args.size() == 0 )
            throw new GeneralExceptions.ArgumentSize("The number of arguments is invalid. Please try again with only one argument");

        //that means that the sie has to be one so we can easily just check if the argument is a number or not with this
        int checkIfNumber = Integer.valueOf(args.get(0));

    }
}
