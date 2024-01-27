package discordBot.helper.eventwaiter;

import net.dv8tion.jda.api.EmbedBuilder;

import java.util.List;

import static java.lang.Math.abs;

/*TODO
    I can possibly fuse this class with the class in music_commands ArrayEventWaiterIterator and make a base class that
    they both extend from. The reason being is that these classes purposes are the same, but execute them out differently
    One contains an objects of Strings and the other is an object of Embeds
 */

/**
 * A simple implementation of a circular Embed array that I created to be able to easily loop through embeds. This class is also
 * best used in combination with an Event Waiter class. This is one of the options for the Event Waiter class to work with.
 * Note: This class might be removed in the future for a better option.
 */
public class EmbedEventWaiterCircularArray {

    /**
     * This list contains embed builds that will be circulated through.
     *
     * Note: It's private so that the user is unable to index it themselves.
     */
    private List<EmbedBuilder> embedBuilderArrayList = null;

    /**
     * An internal index to allow the circular indexing.
     */
    private int index = 0;


    public EmbedEventWaiterCircularArray(List<EmbedBuilder> embedBuilderArrayList){
        this.embedBuilderArrayList = embedBuilderArrayList;
    }

    /**
     * This will return you the current circular index of the item that we are in. Not the actual index because we need
     * to factor in that the index will end up being way over or under the actual size of the array.
     * @return An embed builder
     */
    public EmbedBuilder get(){
        return embedBuilderArrayList.get(abs(index));
    }


    public int size() {
        return embedBuilderArrayList.size();
    }

    int currentIndex(){
        return index;
    }
    /**
     * Will decrease the index amount by one and reset it if it goes under 0.
     */
    public void decreaseIndex(){
        index--;
        if(index < 0)
            index = embedBuilderArrayList.size() - 1;
    }

    /**
     * Will increase the index amount by one and reset it if it goes past the list size.
     */
    public void increaseIndex(){
        index++;
        if(index >= embedBuilderArrayList.size())
            index = 0;
    }
}
