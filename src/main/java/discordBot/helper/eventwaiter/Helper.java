package discordBot.helper.eventwaiter;

//import discord.bot.discordBot2.command.commands.arknights.api.Database;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;


import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * A helper class that contains a lot of methods related to Event Waiter. It also contains useful functions such as
 * offerOption. Some of the methods here can be used for anything related to Event Waiter.
 */
public class Helper {


    public  static void getGenCharInfo(String unitName, Long userId, MessageChannel channel, NameToEmbedsFunction function){

        return;

        /*
        if(Database.checkAlter(unitName)){
            offerOption(unitName,userId,channel, function);
        }
        else{
            sendEmbed(function,unitName,channel);
        }

         */
    }

    /**
     * A function that will take in a NameToEmbed function, a character name, and a channel and will determine
     * whether to send an individual messageEmbed or send it in the form of an EventWaiter with the EmebedRotator
     * class.
     * @param function A functional interface that takes in a function body where the user returns a list of EmbedBuilders
     *                 that will be used within the EmbedRotator
     * @param charName The name of the character
     * @param channel The channel where the information will be sent to
     */
    public static void sendEmbed(NameToEmbedsFunction function, String charName, MessageChannel channel){
        List<EmbedBuilder> embedBuilderList = function.obtainEmbeds(charName);
        EmbedEventWaiterCircularArray embedEventWaiterCircularArray = new EmbedEventWaiterCircularArray(embedBuilderList);

        //If it's only one embed there is no reason to send it an Event Waiter
        if(embedEventWaiterCircularArray.size() == 1)
            channel.sendMessageEmbeds(embedEventWaiterCircularArray.get().build()).queue();
        else{
            /*TODO fix reactions
            channel.sendMessageEmbeds(embedEventWaiterCircularArray.get().build()).queue( (message2) -> {
                message2.addReaction("⬅️").queue();
                message2.addReaction("➡️").queue();
                addEmbedRotaterEventWaiter(message2,channel, embedEventWaiterCircularArray);
            });

             */
        }
    }

    /**
     * Does the the same thing as the other sendEmbed except that this accepts the List of EmbedBuilders instead
     * of creating it within the function.
     * @param embedBuilderList A list of EmbedBuilder objects
     * @param channel Where the message will be sent to.
     */
    public static void sendEmbed(List<EmbedBuilder> embedBuilderList, MessageChannel channel){
        EmbedEventWaiterCircularArray embedEventWaiterCircularArray = new EmbedEventWaiterCircularArray(embedBuilderList);

        /*TODO fix this code because the emojis code got changed so i got to fix that
        //If it's only one embed there is no reason to send it an Event Waiter
        if(embedEventWaiterCircularArray.size() == 1)
            channel.sendMessageEmbeds(embedEventWaiterCircularArray.get().build()).queue();
        else{
            channel.sendMessageEmbeds(embedEventWaiterCircularArray.get().build()).queue( (message2) -> {
                message2.addReaction("⬅️").queue();
                message2.addReaction("➡️").queue();
                addEmbedRotaterEventWaiter(message2,channel, embedEventWaiterCircularArray);
            });
        }*/
    }


    /*TODO can possibly make it so that these two functions could be turned into an interface if I wanted to*/

    /**
     * This function takes a name of a character and will check if there are any Alters of the character and return those options if necessary.
     * If not, then it will just continue on with the original name of the function.
     * @param characterName
     * @return Returns a string that has the full name of the character that will be returned.
     */
     public static void offerOption(String characterName, Long userID, MessageChannel channel, NameToEmbedsFunction function){
        //offer an option to the user to select which option they want

         return;

         /*
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


          */
    }

    /**
     * This type of EventWaiter will allow the user to make a choice. This choice will be given as an argument and then
     * function will automatically be able to show that to the user. For now this function will remain a purely helper
     * function for Character selection. If this function is needed for the future I will make those changes then.
     * @param message The message sent.
     * @param channel The channel where the user is using the commands
     * @param userID The ID of the user to confirm the selection of the message
     * @param options The options that the user is able to pick from
     * @param function The NameToEmbeds functional interface
     */
    private static void addChoiceEventWaiter(Message message, MessageChannel channel, long userID,
                                             List<String> options, NameToEmbedsFunction function){
        EventWaiterSingleton.getInstance().waitForEvent(
                MessageReceivedEvent.class,
                (e) -> e.getAuthor().getIdLong() == userID && !e.getAuthor().isBot(),
                (e) -> {
                    String messageContent = e.getMessage().getContentRaw();
                    int option = Integer.parseInt(messageContent);
                    String charName = options.get(option-1);

                    sendEmbed(function,charName,channel);
                }
                ,
                20L, TimeUnit.SECONDS,
                () -> {channel.sendMessage("No choice was made. Re-input command to get this point again.").queue();}
        );
    }

    /**
     * This is the Event Waiter method that will print out the EmbedEventWaiterCircularArray and cycle through it while
     * putting multiple EventWaiter calls on top of itself.
     * @param message The original message needed in order for the bot to re-write the message
     * @param channel The MessageChannel but at the momement it is unnecessary. Might remove if this is still not used
     * @param ebArray The CircularArray that will be used to rotate between embeds.
     */
    private static void addEmbedRotaterEventWaiter(Message message, MessageChannel channel, EmbedEventWaiterCircularArray ebArray){
        /*TODO the emoji code got changed so I have to fix that
        EventWaiterSingleton.getInstance().waitForEvent(

                MessageReactionAddEvent.class,
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

         */
    }



    //https://github.com/JDA-Applications/JDA-Utilities/blob/master/menu/src/main/java/com/jagrosh/jdautilities/menu/Paginator.java
    //Came up with idea after seeing the paginator class use a concept like this one.
    /**
     * This is a helper function that helps constantly queue in the same event waiter action into the RestAction queue of a message as long
     * as a user interacts with it. This will time out after 20 seconds of no input from the user. It also uses the songListIterator
     * class inside of the event waiter package. This function is good to be used in conjunction with the ArrayEventWaiterIterator
     * @param message The message object of the bot. Used to check if the user is reacting to the bot's message
     * @param songListIterator This object will contain all of the information necessary that the EventWaiter will cycle through
     * @param function This function will help build the Embed from the string information. Contains information such as title, color, description
     */

    //side note: I did have an EmbedBuilder object being passed into this function but removed it because it would have needed to be an Atomic reference
    //instead I decided to just create a new embedBuilder every time the message changes. This could be changed in the future if performance is bad
    public static void addEventWaiter(Message message, ArrayEventWaiterIterator songListIterator, buildEmbedFromString function){
        //fix the entire event waiter function
        System.out.println("Within event waiter");
        EventWaiterSingleton.getInstance().waitForEvent(

                //GuildMessageReactionAddEvent.class,
                MessageReactionAddEvent.class,
                (e) -> {
                    System.out.println("Is this running?");
                    return e.getMessageIdLong() == message.getIdLong() && !e.getUser().isBot() &&
                            ( e.getEmoji().getName().equals("➡️") || e.getEmoji().getName().equals("⬅️"));
                },
                (e) -> {
                    System.out.println("is this working?");
                    String emote = e.getEmoji().getName();
                    if(emote.equals("⬅️"))
                    {
                        String resultsOfPrevious = songListIterator.previous();
                        if(resultsOfPrevious != null )
                        {

                            EmbedBuilder newEmbed = function.buildEmbed(resultsOfPrevious);

                            message.editMessageEmbeds( newEmbed.build()). queue( (newMessage) -> {
                                addEventWaiter(message,songListIterator,function);
                            });
                        }
                    }
                    else {
                        EmbedBuilder newEmbed = function.buildEmbed(songListIterator.next());
                        message.editMessageEmbeds(newEmbed.build()).queue( (newMessage) -> {
                            addEventWaiter(message,songListIterator,function);
                        });
                    }
                }
                ,
                20L, TimeUnit.SECONDS,
                () -> {}
        );
    }

    //slash interaction related things

    public static void addEventWaiter(InteractionHook message, ArrayEventWaiterIterator songListIterator, buildEmbedFromString function){
        //fix the entire event waiter function

        EventWaiterSingleton.getInstance().waitForEvent(
                ButtonInteractionEvent.class,
                (e) -> !e.getUser().isBot() && (e.getComponentId().equals("EmojiPrev") || e.getComponentId().equals("EmojiNext")),

                (e) -> {
                    if(e.getComponentId().equals("EmojiPrev"))
                    {
                        String resultsOfPrevious = songListIterator.previous();
                        if(resultsOfPrevious != null )
                        {

                            EmbedBuilder newEmbed = function.buildEmbed(resultsOfPrevious);
                            message.editOriginalEmbeds( newEmbed.build()). queue( (newMessage) -> {
                                addEventWaiter(message,songListIterator,function);
                            });
                        }
                    }
                    else {
                        EmbedBuilder newEmbed = function.buildEmbed(songListIterator.next());
                        message.editOriginalEmbeds(newEmbed.build()).queue( (newMessage) -> {
                            addEventWaiter(message,songListIterator,function);
                        });
                    }
                }
                ,
                20L, TimeUnit.SECONDS,
                () -> {}
        );


    }




}
