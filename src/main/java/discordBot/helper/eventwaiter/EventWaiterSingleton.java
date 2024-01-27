package discordBot.helper.eventwaiter;




/**
 * Created a singleton for the event waiter class. Will only need to use one per server so this should be fine.
 */
public class EventWaiterSingleton {
    private static EventWaiterSingleton singleton = null;
    private static EventWaiter waiter = null;

    private EventWaiterSingleton(){
        waiter = new EventWaiter();
    }

    public static EventWaiter getInstance(){
        if(singleton == null)
            singleton = new EventWaiterSingleton();
        return singleton.waiter;
    }

}
