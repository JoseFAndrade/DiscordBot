package discordBot.SlashCommand;


import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;


public class SlashListerner extends ListenerAdapter {

    private final SlashCommandManager manager;
    private boolean randomChanceSpeaker = false;
    public SlashListerner(SlashCommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void onReady(ReadyEvent event) {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        event.getJDA().updateCommands().queue();

    }

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent guildReadyEvent){
        //we have to update the commands in here now
        guildReadyEvent.getGuild().updateCommands().queue();
        for( ICommand each : manager.getCommands()){
            if(each.getOptions() == null)
                guildReadyEvent.getGuild().upsertCommand(each.getName(),"did it update").queue();
            else
                guildReadyEvent.getGuild().upsertCommand(each.getName(), "did it update with options").addOptions(each.getOptions()).queue();
        }
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        System.err.println("testing");
        for( ICommand each: manager.getCommands()){
            if(each.getName().equals(event.getName())){
                each.handle(event);
                return;
            }
        }
    }


    @Override
    public void onButtonInteraction(ButtonInteractionEvent event){
        //todo: ....since we are just going to use event waiter we dont need to implement the buttons here

        event.deferEdit().queue();
        /*
        if (event.getComponentId().equals("EmojiPrev")){
            System.out.println(event.getInteraction().getComponentType());
        } else if (event.getComponentId().equals("EmojiNext")){

        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event){

        if(event.getAuthor().isBot()) return;

        String content = event.getMessage().getContentRaw();

        if(content.equals(Constants.PREFIX + "leave") && event.getAuthor().getIdLong() == Constants.OWNER){
            //RandomResponseHelper.saveQuotes(event.getGuild().getIdLong());
            QuoteManager.getInstance().saveAllQuotes();
            RestAction<Message> action = event.getChannel().sendMessage("Exiting now.");
            Message message1 = action.complete();

            shutdown(event.getJDA());
            return;
        }
        else if(content.equals(Constants.PREFIX + "leave") && event.getAuthor().getIdLong() != Constants.OWNER) {
            event.getChannel().sendMessage("Who do you think you are").queue();

        }

        if(QuoteHolder.randomSpeakChance() && randomChanceSpeaker){
            MessageChannel channel = event.getChannel();
            System.err.println("It passed the random speak chance");
            QuoteHolder quoteHolder = QuoteManager.getInstance().getQuoteHolder(event.getGuild().getIdLong());
            String quote = quoteHolder.returnQuote(event.getAuthor().getId());
            System.err.println("Quote is: " + quote);
            if(!quote.equals(""))
                channel.sendMessage(quote).queue();
        }

        /*
        String prefix = Constants.PREFIXES.computeIfAbsent(event.getGuild().getIdLong(), (l) -> Constants.PREFIX);
        if(content.startsWith(prefix))
            manager.handleCommand(event);*/
    }

    /*TODO update these
    @Override
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent event) {
        //need to get the bot to disconnect from voice if there are no other users left as well

        if( event.getGuild().getAudioManager().getConnectionStatus() == ConnectionStatus.CONNECTED && (
                event.getGuild().getAudioManager().getConnectedChannel().getMembers().size() == 1
        )){
            PlayerManager manager = PlayerManager.getInstance();
            manager.clear(manager, event);
            event.getGuild().getAudioManager().closeAudioConnection();
//            MessageChannel textchannel = event.getGuild().getTextChannelCache().asList().get(0);
//            textchannel.sendMessage("test").queue();

        }
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



    }

     */


    private void shutdown(JDA jda) {
        jda.shutdown();
        System.exit(0);
    }



}
