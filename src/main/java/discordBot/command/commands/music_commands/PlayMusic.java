package discordBot.command.commands.music_commands;

import discordBot.command.CommandContext;
import discordBot.command.ICommand;
import discordBot.helper.GeneralExceptions;
import discordBot.helper.music.PlayerManager;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;


import java.util.List;

/**
 * This command will allow users to play the music that they want to. It takes in a link from a youtube video or a youtube
 * playlist. There are more sources accepted, but I don't know off the top of my head.
 */
public class PlayMusic implements ICommand {
    /**
     * There is a simple input check and if everything is correct it will then start to play the music if possible. If
     * there is a problem the loadAndPlay function will take of handling the error and information the user of the problem.
     * @param ctx The CommandContext that gets passed in from CommandManager
     */
    @Override
    public void handle(CommandContext ctx) {
        MessageReceivedEvent event = ctx.getEvent();
        MessageChannel channel = event.getChannel();
        List<String> args = ctx.getArgs();

        try {
            MusicCommandHelper.userInChannel(event);
            checkArgs(args);

            System.out.println("test");
            PlayerManager manager = PlayerManager.getInstance();
            manager.loadAndPlay(ctx.getEvent().getChannel().asTextChannel(), args.get(0), ctx.getEvent());

        }
        catch (Exception e){
            channel.sendMessage(e.getMessage()).queue();
        }

    }

    @Override
    public String getName() {
        return "play";
    }

    @Override
    public String getHelp() {
        return "This command plays music for the user, but it needs a link to play music from.";
    }

    private void checkArgs( List<String> args) throws GeneralExceptions.ArgumentSize {

        if( args.size() > 1 || args == null || args.size() == 0)
            throw new GeneralExceptions.ArgumentSize("That was more than one argument, please try again with only one command.");
    }
}
