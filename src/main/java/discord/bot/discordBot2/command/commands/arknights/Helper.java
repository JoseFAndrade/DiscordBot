package discord.bot.discordBot2.command.commands.arknights;

import discord.bot.discordBot2.command.commands.arknights.api.Database;

import discord.bot.discordBot2.helper.EventWaiterSingleton;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Helper {

    //todo might be better to instead send a MessageChannelID instead of the actual Channel
    public  static void getGenCharInfo(String unitName, Long userId, MessageChannel channel, NameToEmbedsFunction function){
        String fullUnitName = unitName;

        if(Database.checkAlter(unitName)){
            offerOption(unitName,userId,channel, function);
        }
        else{
            List<EmbedBuilder> embedBuilderList = function.obtainEmbeds(unitName);
            EmbedCircularArray embedCircularArray = new EmbedCircularArray(embedBuilderList);

            /*TODO repetitive code here with a function down there, Either have all code run through the functions or
                Make another function that does both of the things here.
                Most Likely the first option would be better because it will look better and handle the issue of
                if there is a word that the user types that is extremely close to two names this will be able to handle it
                as well as any Alters if any.

             */
            if(embedCircularArray.size() == 1){
                channel.sendMessageEmbeds(embedCircularArray.get().build()).queue();
            }
            else{
                channel.sendMessageEmbeds(embedCircularArray.get().build()).queue( (message2) -> {
                    message2.addReaction("⬅️").queue();
                    message2.addReaction("➡️").queue();
                    addEmbedRotaterEventWaiter(message2,channel,embedCircularArray);
                });
            }
        }
    }

    /**
     * This function takes a name of a character and will check if there are any Alters of the character and return those options if necessary.
     * If not, then it will just continue on with the original name of the function.
     * @param characterName
     * @return Returns a string that has the full name of the character that will be returned.
     */


    private static void offerOption(String characterName, Long userID, MessageChannel channel, NameToEmbedsFunction function){
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
                (message) -> { addChoiceEventWaiter(message,channel,userID, options,function); }
        );

    }
    /*
    private static void addChoiceEventWaiter(Message message, MessageChannel channel, long userID, List<String> options){
        EventWaiterSingleton.getInstance().waitForEvent(
                GuildMessageReceivedEvent.class,
                (e) -> e.getAuthor().getIdLong() == userID && !e.getAuthor().isBot(),
                (e) -> {
                    System.err.println("It gets to the second body");
                    String messageContent = e.getMessage().getContentRaw();
                    int option = Integer.parseInt(messageContent);
                    String charName = options.get(option-1);

                    GeneralCharacterInformation generalCharacterInformation = Database.getCharacterGeneralInformation(charName);
                    channel.sendMessageEmbeds(EmbedCreator.createCharacterEmbed(generalCharacterInformation).build()).queue();
                }
                ,
                20L, TimeUnit.SECONDS,
                () -> {channel.sendMessage("No choice was made. Re-input command to get this point again.").queue();}
        );
    }
     */

    private static void addChoiceEventWaiter(Message message, MessageChannel channel, long userID,
                                             List<String> options, NameToEmbedsFunction function){
        EventWaiterSingleton.getInstance().waitForEvent(
                GuildMessageReceivedEvent.class,
                (e) -> e.getAuthor().getIdLong() == userID && !e.getAuthor().isBot(),
                (e) -> {
                    String messageContent = e.getMessage().getContentRaw();
                    int option = Integer.parseInt(messageContent);
                    String charName = options.get(option-1);

                    List<EmbedBuilder> embedBuilderList = function.obtainEmbeds(charName);
                    EmbedCircularArray embedCircularArray = new EmbedCircularArray(embedBuilderList);

                    //If it's only one embed there is no reason to send it an Event Waiter
                    if(embedCircularArray.size() == 1)
                        channel.sendMessageEmbeds(embedCircularArray.get().build()).queue();
                    else{
                        channel.sendMessageEmbeds(embedCircularArray.get().build()).queue( (message2) -> {
                            message2.addReaction("⬅️").queue();
                            message2.addReaction("➡️").queue();
                            addEmbedRotaterEventWaiter(message2,channel,embedCircularArray);
                        });
                    }
                }
                ,
                20L, TimeUnit.SECONDS,
                () -> {channel.sendMessage("No choice was made. Re-input command to get this point again.").queue();}
        );
    }

    private static void addEmbedRotaterEventWaiter(Message message, MessageChannel channel, EmbedCircularArray ebArray){
        EventWaiterSingleton.getInstance().waitForEvent(
                GuildMessageReactionAddEvent.class,
                (e) ->   e.getMessageIdLong() == message.getIdLong() && !e.getUser().isBot() &&
                        ( e.getReactionEmote().getEmoji().equals("➡️") || e.getReactionEmote().getEmoji().equals("⬅️")),
                (e) -> {
                    String emote = e.getReactionEmote().getEmoji();
                    if(emote.equals("⬅️"))
                    {
                        ebArray.decreaseIndex();
                    }
                    else {
                        ebArray.increaseIndex();
                    }
                    message.editMessageEmbeds(ebArray.get().build()).queue( (newMessage) -> {
                        addEmbedRotaterEventWaiter(newMessage,channel,ebArray);
                    } );
                }
                ,
                20L, TimeUnit.SECONDS,
                () -> {}
        );
    }




}
