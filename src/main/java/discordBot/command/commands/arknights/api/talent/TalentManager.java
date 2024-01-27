package discordBot.command.commands.arknights.api.talent;

import java.util.ArrayList;
import java.util.List;


/**
 * This can hold multiple Talents (depending if characters have one or multiple). Each Talent will contains multiple levels to it.
 */
public class TalentManager {

    String unitName = "";
    public List<Talent> talentList = new ArrayList<>();

    public TalentManager(List<Talent> talents, String unitName){
        this.unitName = unitName;
        for(Talent each: talents)
            talentList.add(each);
    }

    public TalentManager(){
    };

    public TalentManager(String unitName){
        this.unitName = unitName;
    }

    public void addTalent(Talent talent){
        talentList.add(talent);
    }

    public void addUnitName(String unitName){
        this.unitName = unitName;
    }


    public String toString(){
        String result = "Talent List:\n";
        for(Talent each: talentList){
            result += each.toString();
            result += "\n"; //just to add in an extra new line
        }
        return result.trim();
    }

    public List<TalentBean> getTalentBeanList(int index){
        List<TalentBean> talentBeanList = new ArrayList<>();
        for(int i = 0; i < talentList.get(index).size(); i++){
            talentBeanList.add (talentList.get(index).getTalentBean(i));
        }
        return talentBeanList;
    }

    public Talent getTalent(int index){
        return talentList.get(index);
    }

    public int size(){
        return talentList.size();
    }

    public String getUnitName() {
        return unitName;
    }
}
