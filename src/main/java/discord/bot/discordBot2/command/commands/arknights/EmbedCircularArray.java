package discord.bot.discordBot2.command.commands.arknights;

import net.dv8tion.jda.api.EmbedBuilder;

import java.util.List;

import static java.lang.Math.abs;

//Don't even know if the best thing is to extend the ArrayList Because I'm not really using it
public class EmbedCircularArray{

    private List<EmbedBuilder> embedBuilderArrayList = null;
    private int index = 0;


    public EmbedCircularArray(List<EmbedBuilder> embedBuilderArrayList){
        this.embedBuilderArrayList = embedBuilderArrayList;
    }

    public EmbedBuilder get(){
        return embedBuilderArrayList.get(abs(index) % size());
    }

    int size() {
        return embedBuilderArrayList.size();
    }

    public void decreaseIndex(){
        index--;
    }

    public void increaseIndex(){
        index++;
    }
}
