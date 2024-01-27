package discordBot.SlashCommand;


//taken from
//https://github.com/duncte123/botCommons/blob/master/src/main/java/me/duncte123/botcommons/commands/ICommandContext.java#L1
/*
 *    Copyright 2019 Duncan "duncte123" Sterken
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import net.dv8tion.jda.api.sharding.ShardManager;

/**
 * Dummy class that holds the basics for a command context
 */
public interface ICommandContext {

    /**
     * Returns the {@link Guild} for the current command/event
     *
     * @return the {@link Guild} for this command/event
     */
    default Guild getGuild() {
        return this.getEvent().getGuild();
    }

    /**
     * Returns the {@link MessageReceivedEvent
 message event} that was received for this instance
     *
     * @return the {@link MessageReceivedEvent
 message event} that was received for this instance
     */
    MessageReceivedEvent
 getEvent();

    /**
     * Returns the {@link TextChannel channel} that the message for this event was send in
     *
     * @return the {@link TextChannel channel} that the message for this event was send in
     */
    default TextChannel getChannel() {
        return this.getEvent().getChannel().asTextChannel();
    }

    /**
     * Returns the {@link Message message} that triggered this event
     *
     * @return the {@link Message message} that triggered this event
     */
    default Message getMessage() {
        return this.getEvent().getMessage();
    }

    /**
     * Returns the {@link User author} of the message as user
     *
     * @return the {@link User author} of the message as user
     */
    default User getAuthor() {
        return this.getEvent().getAuthor();
    }
    /**
     * Returns the {@link Member author} of the message as member
     *
     * @return the {@link Member author} of the message as member
     */
    default Member getMember() {
        return this.getEvent().getMember();
    }

    /**
     * Returns the current {@link JDA jda} instance
     *
     * @return the current {@link JDA jda} instance
     */
    default JDA getJDA() {
        return this.getEvent().getJDA();
    }

    /**
     * Returns the current {@link ShardManager} instance
     *
     * @return the current {@link ShardManager} instance
     */
    default ShardManager getShardManager() {
        return this.getJDA().getShardManager();
    }

    /**
     * Returns the {@link User user} for the currently logged in account
     *
     * @return the {@link User user} for the currently logged in account
     */
    default User getSelfUser() {
        return this.getJDA().getSelfUser();
    }

    /**
     * Returns the {@link Member member} in the guild for the currently logged in account
     *
     * @return the {@link Member member} in the guild for the currently logged in account
     */
    default Member getSelfMember() {
        return this.getGuild().getSelfMember();
    }

}