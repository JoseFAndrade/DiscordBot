package discordBot.command.commands.arknights.api;

import java.util.List;

public class Potentials {
    String unitName = "";
    private List<String> imagesURL = null;
    private List<String> descriptions = null;

    public Potentials(List<String> imagesURL, List<String> descriptions, String unitName){
        this.imagesURL = imagesURL;
        this.descriptions = descriptions;
        this.unitName = unitName;
    }

    public String getDescription(int index){
        return descriptions.get(index);
    }

    public String getImageURl(int index){
        return imagesURL.get(index);
    }

    public String getUnitName() {
        return unitName;
    }

    public String toString(){
        String result = "Potential List:\n";
        for(int i = 0;  i < imagesURL.size(); i++){
            result += String.format("Potential level: %s. Description: %s\n",imagesURL.get(i),descriptions.get(i));
        }
        return result.trim();
    }

    public PotentialBean generatePotentialBean(){
        PotentialBean potentialBean = new PotentialBean();
        potentialBean.setDescriptions(descriptions);
        potentialBean.setImagesURL(imagesURL);

        return potentialBean;
    }

    public int size(){ return imagesURL.size();}

    public String imagesIndex(int index){ return imagesURL.get(index);}

    public String description(int index){ return descriptions.get(index);}

}