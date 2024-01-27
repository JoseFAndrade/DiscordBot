package discordBot.SlashCommand.commands.music_commands;

import discordBot.helper.BaseException;

public class MusicException {

    //user not in channel
    public static class UserNotInChannel extends BaseException{

        public UserNotInChannel(String msg){
            super(msg);
        }
    }


    //bot not in channel
    public static class BotNotInChannel extends BaseException{
        public BotNotInChannel(String msg){
            super(msg);
        }
    }

    //no music left to play
    public static class NoCurrentSong extends BaseException{
        public NoCurrentSong(String msg){
            super(msg);
        }
    }


}
