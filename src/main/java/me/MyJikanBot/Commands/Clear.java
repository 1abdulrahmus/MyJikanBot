package me.MyJikanBot.Commands;

import java.util.List;

import me.MyJikanBot.Bot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Clear extends ListenerAdapter {
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");

		Role admin = event.getGuild().getRoleById("683137305409749004");
		
		if (args[0].equalsIgnoreCase(Bot.prefix + "clear") && event.getMember().getRoles().contains(admin)) {
			if (args.length < 2) {
				// Usage
				EmbedBuilder usage = new EmbedBuilder();
				usage.setColor(0xff3923);
				usage.setTitle("Specify amount to delete");
				usage.setDescription("Usage: `" + Bot.prefix + "clear [# of messages]`");
				event.getChannel().sendMessage(usage.build()).queue();
			} else {
				try {
					List<Message> messages = event.getChannel().getHistory().retrievePast(Integer.parseInt(args[1]))
							.complete();
					event.getChannel().deleteMessages(messages).queue();

					// Success
					EmbedBuilder success = new EmbedBuilder();
					success.setColor(0x22ff2a);
					success.setTitle("✅ Successfully deleted " + args[1] + " messages.");
					event.getChannel().sendMessage(success.build()).queue();
				} catch (IllegalArgumentException e) {
					if (e.toString().startsWith("java.lang.IllegalArgumentException: Message retrieval")) {
						// Too many messages
						EmbedBuilder error = new EmbedBuilder();
						error.setColor(0xff3923);
						error.setTitle("🔴 Too many messages selected");
						error.setDescription("Between 1-100 messages can be deleted at one time.");
						event.getChannel().sendMessage(error.build()).queue();
					} else {
						// Messages too old
						EmbedBuilder error = new EmbedBuilder();
						error.setColor(0xff3923);
						error.setTitle("🔴 Selected messages must be greater than 1");
						error.setDescription("Discord works so that you must delete more than one message");
						event.getChannel().sendMessage(error.build()).queue();
					}
				}
			}
		}
		else if (args[0].equalsIgnoreCase(Bot.prefix + "clear")){
			EmbedBuilder error = new EmbedBuilder();
			error.setColor(0xff3923);
			error.setTitle("🔴 You do not hold permissions to use this command");
			error.setDescription("Only the Admin can delete messages");
			event.getChannel().sendMessage(error.build()).queue();
			error.clear();
		}

	}
}
