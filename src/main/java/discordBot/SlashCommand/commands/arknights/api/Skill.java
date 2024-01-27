package discordBot.SlashCommand.commands.arknights.api;


import java.util.List;

public class Skill {
    public String name = null;
    public List<String> spCostInfo = null;
    public List<String> initialSpInfo = null;
    public List<String> skillUtilizationInfo = null;
    public List<String> skillEffectInfo = null;
    public String spChargeType = null;
    public String spSkillActivation = null;
    public String skillTiles = null;

    private int index = 0; //this will be use for the Event Waiter Embed List

    public Skill(String name, List<String> spCostInfo, List<String> initialSpInfo, List<String> skillUtilizationInfo, List<String> skillEffectInfo, String spChargeType, String spSkillActivation, String skillTiles) {
        this.name = name;
        this.spCostInfo = spCostInfo;
        this.initialSpInfo = initialSpInfo;
        this.skillUtilizationInfo = skillUtilizationInfo;
        this.skillEffectInfo = skillEffectInfo;
        this.spChargeType = spChargeType;
        this.spSkillActivation = spSkillActivation;
        this.skillTiles = skillTiles;
    }


    /*
    //TODO make sure to finish this function by actually returning an actual String because this will not work
    @Override
    public String toString(){
        String result = "";
        result += String.format("Name: %s. Charge Type: %s. Skill Activation: %s.\n",name, spChargeType,spSkillActivation);
        for(int i = 0; i < spCostInfo.size(); i++){
            result+= String.format("\tLevel %s:\n",i+1);
            result+= String.format("\t\tInitial SP: %s. SP Cost: %s. Skill Duration: %s\n\t\t%s\n",
                    initialSpInfo.get(i), spCostInfo.get(i),skillUtilizationInfo.get(i),skillEffectInfo.get(i).trim());
        }
        if(skillTiles == null)
            result+= "There is no Skill Range for this skill.\n";
        else{
            result+= "Skill Range:\n";
            for(int row = 0; row < skillTiles.length; row++){
                for(int column = 0; column < skillTiles[0].length; column++){
                    result+=skillTiles[row][column] + " ";
                }
                result +="\n";
            }
        }

        return result;
    }*/

    public int getListSize(){
        return initialSpInfo.size();
    }

    public String getSpCostInfo(int index){
        return spCostInfo.get(index);
    }

    public String getInitialSpInfo(int index){
        return initialSpInfo.get(index);
    }

    public String getSkillUtilizationInfo(int index){
        return skillUtilizationInfo.get(index);
    }

    public String getSkillEffectInfo(int index){
        return skillEffectInfo.get(index);
    }

    public String getName() {
        return name;
    }

    public String getSpChargeType() {
        return spChargeType;
    }

    public String getSpSkillActivation() {
        return spSkillActivation;
    }

    public String getSkillTiles() {
        return skillTiles;
    }

    public SkillBean getSkillBean(int index){
        SkillBean skillBean = new SkillBean();
        skillBean.setName(getName());
        skillBean.setSpCostInfo(getSpCostInfo(index));
        skillBean.setInitialSpInfo(getInitialSpInfo(index));
        skillBean.setSkillUtilizationInfo(getSkillUtilizationInfo(index));
        skillBean.setSkillEffectInfo(getSkillEffectInfo(index));
        skillBean.setSpChargeType(getSpChargeType());
        skillBean.setSpSkillActivation(getSpSkillActivation());
        skillBean.setLevel(index+1);
        skillBean.setSkillTiles(getSkillTiles());

        return skillBean;
    }

    public int size(){
        return spCostInfo.size();
    }

    //TODO At some point will need to remove this by using the helper functions as a way around this issue
    public void substractIndex(){
        index = --index;
        if(index == -1)
            index = size() -1;
    }

    public void addIndex(){
        index = ++index % size();
    }

    public int getIndex(){
        return index;
    }

}