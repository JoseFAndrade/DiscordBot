package discordBot.command.commands.ImageProcessing;

import discordBot.command.CommandContext;
import discordBot.command.ICommand;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.utils.FileUpload;


import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TurnIntoPNG implements ICommand {


    @Override
    public void handle(CommandContext ctx) {

        MessageReceivedEvent
 event = ctx.getEvent();
        MessageChannel channel = event.getChannel();
        List<Message.Attachment> attachmentsList = event.getMessage().getAttachments();
        System.out.println(attachmentsList.size());




        //error checking needs to go in here
        try{
            InputStream inputStream = readWebpImage(attachmentsList.get(0));
            FileUpload file = FileUpload.fromData(inputStream,"Testing.png");
            channel.sendFiles(file ).queue();
        }
        catch (Exception e){
            channel.sendMessage(e.getMessage()).queue();
            channel.sendMessage("Error").queue();
        }
    }

    public InputStream readWebpImage(Message.Attachment attachment) throws IOException, ExecutionException, InterruptedException {
        InputStream inputStream = attachment.retrieveInputStream().get();
        ImageInputStream imageInputStream = ImageIO.createImageInputStream(inputStream);
        BufferedImage image = ImageIO.read( imageInputStream );
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "png", os);
        InputStream inputStream2 = new ByteArrayInputStream(os.toByteArray());
        return inputStream2;

    }


    @Override
    public String getName() {
        return "imgToPNG";
    }

    @Override
    public String getHelp() {
        return null;
    }

    @Override
    public List<String> getAliases() {
        return new ArrayList<String>(Arrays.asList("png"));
    }
}
