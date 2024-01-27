//FIXME
// -remove this file because it is not in used and is repetitive with the Listener Class

package discordBot.command;

import discordBot.helper.QuoteManager;
import discordBot.helper.music.PlayerManager;

import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;


//todo remove this class because it is not doing anything
public class Commands extends ListenerAdapter
{
	//Have some sort of hashtable here that will connect something like <command.call, command.function (args)>

	@Override
	public void onReady(ReadyEvent event)
	{

		System.out.println("Working Directory = " + System.getProperty("user.dir"));
		//RandomResponseHelper.loadQuotes();
		QuoteManager.getInstance().loadQuotes();
	}


	@Override
	public void onMessageReceived(MessageReceivedEvent
 event){
		System.err.println("Does this even run?");
		if (!event.getMessage().getContentRaw().startsWith("!play")) return;

		if(event.getAuthor().isBot()) return;

		PlayerManager manager = PlayerManager.getInstance();
		//todo manager.loadAndPlay(event.getChannel(),"https://www.youtube.com/watch?v=EGa16dN0chA&ab_channel=MenuDocs", event);
	}

	/*
	@Override
	public void onMessageReceived(MessageReceivedEvent event)
	{
		MessageChannel channel = event.getChannel();
		//System.out.printf("This is the parent ID:%s\n",channel.getParent())
		System.err.printf("[%s]: %s. Message ID:%s. Channel Type: %s\n", event.getAuthor().getName(), event.getMessage().getContentDisplay(),event.getMessageId(),event.getChannelType().toString());
		Message message = event.getMessage();
		String content = message.getContentRaw();
		String name = event.getAuthor().getName();

		if(QuoteHolder.randomSpeakChance()){
			System.err.println("It passed the random speak chance");
			QuoteHolder quoteHolder = QuoteManager.getInstance().getQuoteHolder(event.getGuild().getIdLong());
			String quote = quoteHolder.returnQuote(event.getAuthor().getId());
			System.err.println("Quote is: " + quote);
			if(!quote.equals(""))
				channel.sendMessage(quote).queue();
		}

		if(content.equals("die"))
		{
			if(event.getAuthor().getId().equals("156771638766075905")){
				//RandomResponseHelper.saveQuotes(event.getGuild().getIdLong());
				QuoteManager.getInstance().saveAllQuotes();
				RestAction<Message> action = channel.sendMessage("Exiting now.");
				Message message1 = action.complete();
				System.exit(0);
			}
			else {
				channel.sendMessage("You can't end me.").queue();
			}
		}

		/*
		if(content.equals("test"))
			channel.sendMessage("<@156771638766075905>").queue();
		*/

		/*todo this
		if(content.startsWith(Main.getPrefix())){
			//channel.sendMessage("Prefix checked").queue()
			System.err.println(content.substring(2));
			String[] splitCommands = content.substring(2).split("\\s+");
			String authorIDFAdding = ""; //the ID for the author whose quote will be added
			String quoteCombined = "";
		}

		 */

		/*
		else {
		if(name.equals("TheFatalKnight"))
		channel.sendMessage("Smh.").queue();
		else if(name.equals("Xenpls"))
		channel.sendMessage("Is that him, the ultragamer1024!").queue();
		}
		if( content.equals(Main.getPrefix() + "no" ) )
		{
		channel.sendMessage("pls facepalm").queue();
		}*/
}
