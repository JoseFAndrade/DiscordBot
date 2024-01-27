package discordBot.helper.eventwaiter;


import java.util.Iterator;

/**
 * This is an Iterator that is modified in order to work in the EventWaiter function. It's able to go back and forth
 * when needed. This is exactly what will be happening within the EventWaiter function. The user has complete control
 * of when previous() and next() will run by controlling the arrows.
 */
public class ArrayEventWaiterIterator implements Iterator<String> {
    private final String[] stringList;
    private int pointer = 0;
    private int prevPointer = 0;

    public ArrayEventWaiterIterator(String[] val){
        this.stringList = val;

    }

    @Override
    public boolean hasNext(){
        return ! (pointer > stringList.length);
    }


    /**
     * This function will return a string that will contain all of the information from the starting pointer up to
     * when it reaches the limit of 2000 words or it runs out of information to go through. This function will also
     * deal with reaching the end point of the stringList list. When it reaches the end it will just show the user
     * the latest last page that it showed them.
     * @return A String containing the information that the bot will send to the server
     */
    @Override
    public String next(){
        int wordCount = 0;
        String toSend = "";

        //to deal with the user pressing the arrow after it reaches the end
        if(pointer >= stringList.length)
            pointer = prevPointer;

        int pointerSaved = pointer;

        for(; pointer < stringList.length  && !(stringList[pointer].length() + wordCount >= 2000); pointer++){
            toSend+= stringList[pointer] + "\n";
            wordCount += stringList[pointer].length() + 2;
        }

        //for the start -> assign prevPointer to Pointer so it won't try to go before page 1
        if( pointerSaved == 0)
            prevPointer = pointer;
        else //for anywhere else -> update prev to what pointer used to be
            prevPointer = pointerSaved;

        return toSend;
    }


    /**
     * This function will return a string that will contain all of the information starting from the previousPointer
     * all the way up to when it reaches the starting point or it fills up the 2000 word count. This function will never
     * have anything that was in the current page that the bot was showing. Additionally, this function will also deal
     * with the user trying to go beyond the starting point of StringList. It will just show them the first page as
     * long as they keep trying to go back on it.
     * @return A String containing the information that the bot will send to the server
     */
    public String previous(){
        int wordCount = 0;
        StringBuilder toSend = new StringBuilder("");

        int tempPrevPointer = prevPointer ;

        for(; tempPrevPointer > 0 && !(stringList[tempPrevPointer-1].length() + wordCount >= 2000);tempPrevPointer--){
            toSend.insert(0,stringList[tempPrevPointer-1] + "\n");
            wordCount+= stringList[tempPrevPointer-1].length() + 2;
        }

        //if prev() function hasn't returned it to the first page
        if(!(tempPrevPointer == 0)){
            pointer = prevPointer;
            prevPointer = tempPrevPointer;
        }
        else{ //means we returned to the first page
            //if prev() function has returned it to the fist page -> don't let prevPointer reach 0 and update pointer to reach to what prev pointer is
            //if we don't do this pointer will start from the second page
            pointer = prevPointer;
        }
        return toSend.toString();
    }
}
