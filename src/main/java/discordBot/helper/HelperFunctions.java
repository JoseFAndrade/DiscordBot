package discordBot.helper;

import java.util.List;

public class HelperFunctions {
    
    public static String combinedStringFromList(List<String> items){
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < items.size();i++){
            sb.append(items.get(i) + " ");
        }

        return sb.toString().trim();

    }
}
