package discordBot.helper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class QuoteManager {

    private static QuoteManager singleton;

    //private static HashMap<Long, HashMap<String, ArrayList<String>>> guildManager = new HashMap<>();
    private static HashMap<Long, QuoteHolder> guildManager = new HashMap<>();

    /**
     * This function will create the one instance of the QuoteManager class that is required or if already created
     * it will return the already created instance.
     * @return
     */
    public static synchronized QuoteManager getInstance(){
        if(singleton == null){
            singleton = new QuoteManager();
        }
        return singleton;
    }

    /**
     * Private constructor since this class is a singleton. It will attempt to load quotes once the class is first created.
     */
    private QuoteManager(){
        guildManager = new HashMap<>();
        loadQuotes();
    }


    /**
     * This function will return the QuoteHolder that corresponds to the guild. This function is assuming that all of
     * the quotes have been loaded in.
     *
     * This function may change in the future where we aren't assuming that all functions are loaded in. This means that
     * this function will change in a way where it would be able to call a variation of loadQuote function with a specific
     * guild id.
     * @param guildID A guildID in the form of a long
     * @return A QuoteHolder object
     */
    public QuoteHolder getQuoteHolder(long guildID){
        if(!guildManager.containsKey(guildID))
            guildManager.put(guildID, new QuoteHolder());
        return guildManager.get(guildID);
    }

    /**
     * This function will load in all of the quotes that have been saved (this includes all of the servers not just the current one).
     */
    public void loadQuotes(){
        String[] fileNames;
        String filepath = ".\\resources\\quotes\\";
        File f = new File(filepath);

        if(f.exists()){
            fileNames = f.list();
            for(String eachFile : fileNames){
                String json = readLineByLineJava8(filepath + "\\" + eachFile);
                if(json.equals(""))
                    return;
                Gson gson = new Gson();
                Type collectionType = new TypeToken<HashMap<String,ArrayList<String>>>() {}.getType();
                HashMap<String,ArrayList<String>> guildQuotes = gson.fromJson(json, collectionType);
                //we substring in order to remove .json which will always be the last 5 characters
                long guildId = Long.parseLong(eachFile.substring(0, eachFile.length()-5));
                guildManager.put( guildId, new QuoteHolder(guildQuotes));
            }
        }
        else{
            new File("./resources/quotes").mkdir();
        }
    }

    /**
     * A helper function that will read the entire file and append it into a String.
     * @param filePath The path within windows for where the Quote File is at (quotes.json)
     * @return Will return a string of everything that is inside of the file
     */
    private static String readLineByLineJava8(String filePath)
    {
        String result = "";
        try {
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                System.err.print(line);
                result += line;
            }
            myReader.close();
            System.err.println("The string was this:\n" + result);
            return result;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * This function will run whenever quotes need to be saved into the file. This can be at a set interval or whenever
     * a user adds a new quote. This function will most likely run in a separate thread, but it does not need to be modified
     * as this function just replaces the file based on the existing HashMap.
     */
    public static void saveQuotes(long guildID){
        try {
            System.out.println("Save Quotes is running");
            Gson gson = new Gson();
            //todo address this weird .clone issue where the gson function will convert it weird without the clone function call
            //todo edit this function to make it work without clone
            String json = gson.toJson(guildManager.get(guildID).getQuotes().clone());
            BufferedWriter writer = new BufferedWriter(new FileWriter(".\\resources\\quotes\\" + guildID +".json"));
            writer.write(json);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * When the bot is shutting down this function will run in order to ensure that all of the quotes from every server is being saved.
     */
    public void saveAllQuotes(){
        Set<Long> keys = guildManager.keySet();
        for(Long eachKey: keys)
            saveQuotes(eachKey);
    }


}
