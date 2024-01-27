package discordBot.SlashCommand.commands.arknights.api;

import java.util.List;

public class TrustStats {

    String unitName;
    List<String> statList = null;

    public TrustStats(List<String> statList, String unitName) {
        this.statList = statList;
        this.unitName = unitName;
    }

    public int length(){
        return statList.size();
    }

    public String getTrustStat(int index){
        return statList.get(index);
    }

    public List<String> getTrustStats(){
        return statList;
    }

    public String getUnitName() {
        return unitName;
    }

    public String toString(){
        String result = "Trust Extra Status:\n";
        for(String each:statList)
            result += String.format("\t%s",each);
        return result;
    }

    public int size(){ return statList.size();}

    public String getStatIndex(int index){return statList.get(index);}
}
