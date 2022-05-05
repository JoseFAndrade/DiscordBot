package discord.bot.discordBot2.helper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Stream;

import static java.lang.StrictMath.abs;

public class RandomResponseHelper {

    /*
        Key: A string representing the ID of the user in order to be able to obtain the quotes
        Value: An arraylist of quotes that connect to the particular user
    */
    private static HashMap<String,ArrayList<String>> quotes = new HashMap<String,ArrayList<String>>();



    public static int returnRandom(){
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[1];
        random.nextBytes(bytes);
        int i = bytes[0];
        return i;
    }

    public static String returnQuote(String id){
        ArrayList<String> quoteList = quotes.get(id);
        if(quoteList == null)
            return "";
        //because returnRandom can be negative we do the absolute value of it. Then we modulo it in order to get a random quote.
        String randomQuote = quoteList.get ( abs(returnRandom()) % quoteList.size() );
        return randomQuote;
    }


    /**
     * This function returns all of the users quotes within a string.
     */
    public static String returnUserQuotes(String id){
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
     * @param id
     * @param q
     * @return
     */
    public static Boolean addQuote(String id,String q){
        try{
            if(quotes.get(id) == null)
                quotes.put(id,new ArrayList<String>());
            quotes.get(id).add(q);
        }
        catch (Exception e){
            return false;
        }
        return true;
    }


    public static void insertTesting(){
        ArrayList<String> myQuotes = new ArrayList<String>();
        myQuotes.add("What's going on here.");
        myQuotes.add("You suck fam.");
        myQuotes.add("Nope.");
        quotes.put("156771638766075905",myQuotes);
    }


    public static boolean randomSpeakChance(){
        if(returnRandom()% 10 == 0){
            return true;
        }
        else {
            return false;
        }
    }

    public static void loadQuotes(){
        String filepath = ".\\resources\\quotes.json";
        File f = new File(filepath);
        if(f.exists()){
            String json = readLineByLineJava8(filepath);
            if(json.equals(""))
                return;
            Gson gson = new Gson();
            Type collectionType = new TypeToken<HashMap<String,ArrayList<String>>>() {}.getType();
            quotes = gson.fromJson(json, collectionType);
        }
        else{
            new File("./resources").mkdir();
        }
    }

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

    public static void saveQuotes(){
        try {
            System.out.println("Save Quotes is running");
            Gson gson = new Gson();
            //clone the object so we don't run into any issues when using threads
            String json = gson.toJson(quotes.clone());

            BufferedWriter writer = new BufferedWriter(new FileWriter(".\\resources\\quotes.json"));
            writer.write(json);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
