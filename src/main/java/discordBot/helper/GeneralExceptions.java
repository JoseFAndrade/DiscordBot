package discordBot.helper;

public class GeneralExceptions {

    public static class ArgumentSize extends BaseException{
        public ArgumentSize(String msg){
            super(msg);
        }
    }
}
