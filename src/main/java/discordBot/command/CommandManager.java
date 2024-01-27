package discordBot.command;

/*
    https://www.youtube.com/watch?v=Z02_PBZzfww&list=PLWnw41ah3I4YxBetY8iCa-b9t1JwV2jsW&index=3&ab_channel=MenuDocs

    All credit goes to this video. I was trying to find a good way of progamming multiple commands into my bot without
    creating a function that contains over a thousand lines. This is one of the many ways that I have found. I will be
    changing parts of this code in order to make it a bit more like how I would have written it. Note: Not necessarily
    written it in a better way, im sure it will end up a bit worse than the code I find.

 */
//import com.sun.istack.internal.Nullable;
import discordBot.command.commands.ImageProcessing.TurnIntoPNG;
import discordBot.command.commands.TestCommands.TestCommand;
import discordBot.command.commands.TestCommands.TestUserInput;
import discordBot.helper.Constants;
import discordBot.command.commands.AddQuote;
import discordBot.command.commands.Help;
import discordBot.command.commands.ShowQuotes;
import discordBot.command.commands.arknights.*;
import discordBot.command.commands.music_commands.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class CommandManager {

    private final HashMap<String,ICommand> commands = new HashMap<>();

    public CommandManager() {
        addCommand(new ShowQuotes());
        addCommand(new AddQuote());
        addCommand(new PlayMusic());
        addCommand(new ChangeVolumeMusic());
        addCommand(new SkipTrack());
        addCommand(new TestCommand());
        //addCommand(new EmbedExample());
        addCommand(new Help(this));
        addCommand(new OldSongList());
        addCommand(new PauseMusic());
        addCommand(new CurrentTrack());
        addCommand(new ResumeMusic());
        addCommand(new GetArknightsUnit());
        addCommand(new GetArknightsSkillInfo());
        addCommand(new ShuffleMusic());
        addCommand(new TestUserInput());
        addCommand(new GetArknightsTalent());
        addCommand(new GetArknightsExtraStats());
        addCommand(new GetArknightsGeneral());
        addCommand(new SongList());
        addCommand(new TurnIntoPNG());
    }

    private void addCommand(ICommand cmd){
        if(commands.containsKey(cmd.getName()))
            System.out.println("The command you are looking for has already been added");
        else
            commands.put(cmd.getName(), cmd);
    }

    //@Nullable
    public ICommand getCommand(String commandName){
        String lowerString = commandName.toLowerCase();

        return commands.get(commandName);
    }

    public List<ICommand> getCommands(){ return new ArrayList<>(commands.values());}

    void handleCommand(MessageReceivedEvent event){
        final String prefix = Constants.PREFIXES.get(event.getGuild().getIdLong());
        //remove the prefix from the string
        String removedPrefix = event.getMessage().getContentRaw().replaceFirst("(?i)" + Pattern.quote(prefix),"");
        String[] split = removedPrefix.split(" ");
        final String inputCommandName = split[0];


        for(String commandName: commands.keySet()){
            ICommand command = commands.get(commandName);
            List<String> aliases = command.getAliases();

            //https://stackoverflow.com/questions/15824733/option-to-ignore-case-with-contains-method
            if(commandName.equalsIgnoreCase(inputCommandName) ||
                (aliases != null && aliases.stream().anyMatch(inputCommandName::equalsIgnoreCase)))
            {
                final List<String> args = Arrays.asList(split).subList(1,split.length); //return all but the command name
                CommandContext cc = new CommandContext(event,args);
                command.handle(cc);
            }
        }
    }


}
