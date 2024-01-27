package discordBot.helper;


import java.security.SecureRandom;
import java.util.*;

import static java.lang.StrictMath.abs;

/**
 *
 */
public class QuoteHolder {

    /*
        Key: A string representing the ID of the user in order to be able to obtain the quotes
        Value: An arraylist of quotes that connect to the particular user
    */
    private HashMap<String,ArrayList<String>> quotes = new HashMap<String,ArrayList<String>>();


    public QuoteHolder(HashMap<String, ArrayList<String>> quotes){
        this.quotes = quotes;
    }

    public QuoteHolder(){
        this.quotes = new HashMap<>();
    }

    /**
     * Will return a random int
     * @return an int generate randomly
     */
    public static int returnRandom(){
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[1];
        random.nextBytes(bytes);
        int i = bytes[0];
        return i;
    }

    /**
     * This function will obtain a quote at random by using the returnRandom function
     * @param id This is the id of the persons whose quotes we are obtaining
     * @return A string that contains the quote
     */
    public String returnQuote(String id){
        ArrayList<String> quoteList = quotes.get(id);
        if(quoteList == null)
            return "";
        //because returnRandom can be negative we do the absolute value of it. Then we modulo it in order to get a random quote.
        String randomQuote = quoteList.get ( abs(returnRandom()) % quoteList.size() );
        return randomQuote;
    }



    //TODO change this function to match the EventWaiter helper function
    /**
     * This function returns all of the users quotes within a string.
     */
    public String returnUserQuotes(String id){
        String result = "";
        int number = 1;
        if(quotes.get(id) == null)
            return "Sorry, no quotes available for that user.";
        else {
            for (String quote: quotes.get(id)) {
                result += number + ": "+ quote + "\n";
                number++;
            }
        }
        return result;
    }

    /**
     * This function will add a quote for the user who's id matches the id in the argument.
     * @param id In string form of the id of the user whose quote will be added to
     * @param q The quote
     * @param guildID This is the ID of the guild, needed to make sure we are adding quotes to the right server
     * @return Returns a boolean based on whether it was added or not
     */
    public Boolean addQuote(String id,String q, long guildID){
        try{
            if(quotes.get(id) == null)
                quotes.put(id,new ArrayList<String>());
            quotes.get(id).add(q);
        }
        catch (Exception e){
            return false;
        }

        Runnable runnable =
                () -> { QuoteManager.saveQuotes(guildID); };
        Thread thread = new Thread(runnable);
        thread.start();

        return true;
    }


    /**
     * OUTDATED
     * A simple tester function used to populate with quotes.
     */
    public void insertTesting(){
        ArrayList<String> myQuotes = new ArrayList<String>();
        myQuotes.add("What's going on here.");
        myQuotes.add("You suck.");
        myQuotes.add("Nope.");
        quotes.put("156771638766075905",myQuotes);
    }


    /**
     * This function will return a boolean depending on if the bot will talk or not. I made it a function so that
     * I can easily change whether I want the speak chance to be more common or not.
     * @return A boolean informing you if the bot passed the random speak chance.
     */
    public static boolean randomSpeakChance(){
        if(returnRandom()% 10 == 0){
            return true;
        }
        else {
            return false;
        }
    }

    public HashMap<String, ArrayList<String>> getQuotes(){
        return quotes;
    }
}
