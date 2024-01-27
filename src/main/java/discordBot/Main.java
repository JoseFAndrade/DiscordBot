


package discordBot;

import discordBot.SlashCommand.SlashCommandManager;
import discordBot.SlashCommand.SlashListerner;
import discordBot.command.CommandManager;
import discordBot.command.Listener;
import discordBot.helper.eventwaiter.EventWaiter;
import discordBot.helper.eventwaiter.EventWaiterSingleton;
import net.dv8tion.jda.api.JDA;


public class Main {

	private static String prefix = "!.";


	public static void main(String[] args)
	{
		JDA jda = JDAController.getJDA();
		//just for testing purposes

		/*
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				// what you want to do. Not sure if i need this because its not working at the moment
				jda.shutdown();
			}
		}));
		*/
		System.out.println(jda);
		EventWaiter waiter = EventWaiterSingleton.getInstance();
		if(waiter == null)
			System.out.println("Waiter is null for some reason? Hm?");
		CommandManager cm = new CommandManager();
		SlashCommandManager cm2 = new SlashCommandManager();
		jda.addEventListener(new Listener(cm));
		jda.addEventListener(new SlashListerner(cm2));
		jda.addEventListener(waiter);


	}
		
	public static String getPrefix()
	    {
	    	return Main.prefix;
	    }
	
}