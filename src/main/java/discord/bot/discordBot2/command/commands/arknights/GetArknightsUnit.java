package discord.bot.discordBot2.command.commands.arknights;

import discord.bot.discordBot2.command.CommandContext;
import discord.bot.discordBot2.command.ICommand;
import discord.bot.discordBot2.command.commands.arknights.api.Database;
import discord.bot.discordBot2.command.commands.arknights.api.GeneralCharacterInformation;
import discord.bot.discordBot2.helper.EmbedCreator;
import discord.bot.discordBot2.helper.EventWaiterSingleton;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import discord.bot.discordBot2.helper.HelperFunctions;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class GetArknightsUnit implements ICommand {

    @Override
    public void handle(CommandContext ctx) {
        GuildMessageReceivedEvent event = ctx.getEvent();
        MessageChannel channel = event.getChannel();
        List<String> args = ctx.getArgs();
        String unitName = HelperFunctions.combinedStringFromList(args);
        Long userID = ctx.getAuthor().getIdLong();
        getGenCharInfo(unitName,userID,channel);
    }

    @Override
    public String getName() {
        return "getUnitTest";
    }

    @Override
    public String getHelp() {
        return "This command will return the general information of the unit whose name is given. Usage: .....";
    }

    //TODO rewrite this function this is bad
    private void getGenCharInfo(String unitName, Long userId, MessageChannel channel){
        String fullUnitName = unitName;
        if(Database.checkAlter(unitName)){
             offerOption(unitName,userId,channel);
        }
        else{
            GeneralCharacterInformation generalCharacterInformation = Database.getCharacterGeneralInformation(fullUnitName);
            channel.sendMessageEmbeds(EmbedCreator.createCharacterEmbed(generalCharacterInformation).build()).queue();
        }
    }

    /**
     * This function will take the name of a character that contains an alter and will allow the user to pick between
     * the original vs the alter version.
     * @param characterName
     * @return Returns a string that has the full name of the character that will be returned.
     */
    private void offerOption(String characterName, Long userID, MessageChannel channel ){
        //offer an option to the user to select which option they want
        List<String> options = Database.getMultipleCharacterNames(characterName);

        String optionsInString = "";
        int count = 1;
        for (String option: options){
            optionsInString += count + ": " + option + "\n";
            count++;
        }

        channel.sendMessage("Please select an option by selecting the number next to the character you want.\n"
                            + optionsInString).queue(
                (message) -> { addEventWaiter(message,channel,userID, options); }
        );

    }

    private void addEventWaiter(Message message, MessageChannel channel, long userID, List<String> options){
        EventWaiterSingleton.getInstance().waitForEvent(
                GuildMessageReceivedEvent.class,
                (e) -> e.getAuthor().getIdLong() == userID && !e.getAuthor().isBot(),
                (e) -> {

                    String messageContent = e.getMessage().getContentRaw();
                    int option = Integer.parseInt(messageContent);

                    GeneralCharacterInformation generalCharacterInformation = Database.getCharacterGeneralInformation(options.get(option-1));
                    channel.sendMessageEmbeds(EmbedCreator.createCharacterEmbed(generalCharacterInformation).build()).queue();
                }
                ,
                20L, TimeUnit.SECONDS,
                () -> {channel.sendMessage("No choice was made. Re-input command to get this point again.").queue();}
        );
    }

}
