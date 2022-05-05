package discord.bot.discordBot2.command;

/* this will be the class that will handle obtaining the commands frm the users and responding to them */
// ideas taken from https://github.com/MenuDocs/JDA-Tutorials/blob/Tutorials/EP11/src/main/java/me/duncte123/menuDocs/Listener.java

import discord.bot.discordBot2.helper.Constants;
import discord.bot.discordBot2.helper.RandomResponseHelper;
import discord.bot.discordBot2.helper.music.PlayerManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.RestAction;


public class Listener extends ListenerAdapter {

    private final CommandManager manager;

    public Listener(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void onReady(ReadyEvent event)
    {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        RandomResponseHelper.loadQuotes();
    }


    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){

        if(event.getAuthor().isBot()) return;

        String content = event.getMessage().getContentRaw();

        if(content.equals(Constants.PREFIX + "leave") && event.getAuthor().getIdLong() == Constants.OWNER){
            RandomResponseHelper.saveQuotes();
            RestAction<Message> action = event.getChannel().sendMessage("Exiting now.");
            Message message1 = action.complete();

            shutdown(event.getJDA());
            return;
        }
        else if(content.equals(Constants.PREFIX + "leave") && event.getAuthor().getIdLong() != Constants.OWNER) {
            event.getChannel().sendMessage("Who do you think you are").queue();

        }

        if(RandomResponseHelper.randomSpeakChance()){
            MessageChannel channel = event.getChannel();
            System.err.println("It passed the random speak chance");
            String quote = RandomResponseHelper.returnQuote(event.getAuthor().getId());
            System.err.println("Quote is: " + quote);
            if(!quote.equals(""))
                channel.sendMessage(quote).queue();
        }

        //what does this line do?
        //if the server has set a different prefix then it will load it, if not then it will create the base prefix for
        //the server
        String prefix = Constants.PREFIXES.computeIfAbsent(event.getGuild().getIdLong(), (l) -> Constants.PREFIX);
        if(content.startsWith(prefix))
            manager.handleCommand(event);
    }

    @Override
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent event) {
        System.err.println("Someone left");
        PlayerManager manager = PlayerManager.getInstance();
        manager.clear(manager,event);
    }



    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        /*
        MessageChannel channel = event.getChannel();
        //System.out.printf("This is the parent ID:%s\n",channel.getParent())
        System.out.printf("[%s]: %s. Message ID:%s. Channel Type: %s\n", event.getAuthor().getName(), event.getMessage().getContentDisplay(),event.getMessageId(),event.getChannelType().toString());
        Message message = event.getMessage();
        String content = message.getContentRaw();
        String name = event.getAuthor().getName();

        //System.err.println(message.getMentionedMembers());


        if(RandomResponseCommand.randomSpeakChance()){
            System.err.println("It passed the random speak chance");
            String quote = RandomResponseCommand.returnQuote(event.getAuthor().getId());
            System.err.println("Quote is: " + quote);
            if(!quote.equals(""))
                channel.sendMessage(quote).queue();
        }

        if(content.equals("end"))
        {
            if(event.getAuthor().getId().equals("156771638766075905")){
                RandomResponseCommand.saveQuotes();
                RestAction<Message> action = channel.sendMessage("Exiting now.");
                Message message1 = action.complete();
                System.exit(0);
            }
            else {
                channel.sendMessage("You aren't in charge of me.").queue();
            }
        }


		if(content.equals("test"))
			channel.sendMessage("<@156771638766075905>").queue();


        */
    }

    private void shutdown(JDA jda) {
        jda.shutdown();
        System.exit(0);
    }



}
