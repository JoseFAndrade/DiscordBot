package discordBot.command.commands.arknights.api;

public class GeneralCharacterInformation {
    //unit info
    public String name;
    public int rarity;
    public String position;
    public String attackType;
    public String tags;
    public String classType;
    public String archetype;
    public String trait;
    public String attackRange;
    public String imageURL;

    //unit stats
    public String level;
    public int redeployTime;
    public double attackInterval;
    public int hp;
    public int atk;
    public int def;
    public int artRes;
    public int dpCost;
    public int block;

    public GeneralCharacterInformation() {
    }

    public GeneralCharacterInformation(String name, int rarity, String position, String attackType, String tags,
                                       String classType, String archetype, String trait, String attackRange, String level,
                                       int redeployTime, double attackInterval, int hp, int atk, int def, int artRes,
                                       int dpCost, int block,String imageURL) {
        this.name = name;
        this.rarity = rarity;
        this.position = position;
        this.attackType = attackType;
        this.tags = tags;
        this.classType = classType;
        this.archetype = archetype;
        this.trait = trait;
        this.attackRange = attackRange;
        this.level = level;
        this.redeployTime = redeployTime;
        this.attackInterval = attackInterval;
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.artRes = artRes;
        this.dpCost = dpCost;
        this.block = block;
        this.imageURL = imageURL;
    }

    public String toString(){
       return String.format("Name: %s. Rarity: %d. Position: %s. Attack Type: %s. Tags: %s. Class Type: %s." +
               "Archetype: %s. Trait: %s. Attack Range: %s. Level: %s. Redeploy Time: %d. Attack Interval: %.2f." +
               "Hp: %d. Atk: %d. Def: %d. Art Res: %d. Dp Cost: %d. Block: %d.", name, rarity, position, attackType, tags,
               classType, archetype,trait,attackRange,level,redeployTime,attackInterval,hp,atk,def,artRes,dpCost,block);
    }


}
