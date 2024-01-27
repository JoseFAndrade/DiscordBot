package discordBot.command;

import java.util.Arrays;
import java.util.List;

public interface ICommand {
    void handle(CommandContext ctx);

    String getName();

    String getHelp();

    default public List<String> getAliases() {
        return Arrays.asList(); //List.of(); // use Arrays.asList if you are on java 8
    }
}
