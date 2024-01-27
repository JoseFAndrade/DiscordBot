package discordBot.SlashCommand;

/*
    https://www.youtube.com/watch?v=Z02_PBZzfww&list=PLWnw41ah3I4YxBetY8iCa-b9t1JwV2jsW&index=3&ab_channel=MenuDocs

    All credit goes to this video. I was trying to find a good way of progamming multiple commands into my bot without
    creating a function that contains over a thousand lines. This is one of the many ways that I have found. I will be
    changing parts of this code in order to make it a bit more like how I would have written it. Note: Not necessarily
    written it in a better way, im sure it will end up a bit worse than the code I find.

 */

import discordBot.SlashCommand.commands.*;
import discordBot.SlashCommand.commands.music_commands.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SlashCommandManager {

    private final HashMap<String, ICommand> commands = new HashMap<>();

    public SlashCommandManager() {
        addCommand(new AddQuote());
        addCommand(new ShowQuotes());
        addCommand(new Help(this));
        addCommand(new PlayMusic());
        addCommand(new ChangeVolumeMusic());
        addCommand(new CurrentTrack());
        addCommand(new PauseMusic());
        addCommand(new PlayMusic());
        addCommand(new ResumeMusic());
        addCommand(new ShuffleMusic());
        addCommand(new SkipTrack());
        addCommand(new SongList());
        /*
        addCommand(new AddQuote());
        addCommand(new PlayMusic());
        addCommand(new ChangeVolumeMusic());

        addCommand(new SkipTrack());
        addCommand(new TestCommand());
        addCommand(new EmbedExample());
        //addCommand(new Help(this));
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

         */
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


}
