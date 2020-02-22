package me.MyJikanBot.Events;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildMessageReceived extends ListenerAdapter {
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		event.getMessage().addReaction("�?�").queue();
		
		if (args[0].equalsIgnoreCase("!enter")) {
					
					// Get channel name and send in current channel
					String channelName = event.getChannel().getName();
					event.getChannel().sendMessage(channelName).queue();
					
					// Upload and send an image to current channel
					try {
						URL url = new URL("https://cdn.vox-cdn.com/thumbor/ErtVO6Ub8PiWxplqkehZyrXqdAQ=/0x0:2000x800/1200x800/filters:focal(854x58:1174x378)/cdn.vox-cdn.com/uploads/chorus_image/image/61878909/5433_Tier03_SeriesHeader_20C_2000x800.0.jpg");
						BufferedImage img = ImageIO.read(url);
						File file = new File("testserver_image_20thcenturyboys.jpg"); // change the '.jpg' to whatever extensions the image has
						ImageIO.write(img, "jpg", file); // again, change 'jpg' to the correct extension
						event.getChannel().sendFile(file).queue();
						
						EmbedBuilder hidden = new EmbedBuilder();
						hidden.setColor(0xff1923);
						hidden.setTitle("🔴 You deserve this welcome");
						hidden.setDescription("There are only a handful of you out there");
						event.getChannel().sendMessage(hidden.build()).queue();
					}
					catch (Exception e) {
						event.getChannel().sendMessage("Error fetching image.").queue();
					}
				}
		/* Part 5- Mute Command--> find replacement for add role and removing roles, use online documentation
		 *try this link 
		 *
		 *      https://ci.dv8tion.net/job/JDA/javadoc/net/dv8tion/jda/api/entities/Member.html#getRoles()
		 *
		 *
		 **/
		if (args[0].equalsIgnoreCase("!mute")) {
			if (args.length == 2) {
				Member member = event.getGuild().getMemberById(args[1].replace("<@", "").replace(">", ""));
				Role role = event.getGuild().getRoleById("679519729307877396");
				
				if (!member.getRoles().contains(role)) {
					// Mute user
					event.getChannel().sendMessage("Muted" + args[1] + ".").queue();
					event.getGuild().modifyMemberRoles(member, role).complete();
					// event.getGuild().getController().addRolesToMember(member, role).complete();
				}
				else {
					// Unmute user
					event.getChannel().sendMessage("Unmuted" + args[1] + ".").queue();
					event.getGuild().modifyMemberRoles(member, role).complete();
					// event.getGuild().getController().removeRolesFromMember(member, role).complete();
					
					
				}
			}
			else if (args.length == 3) {
				Member member = event.getGuild().getMemberById(args[1].replace("<@", "").replace(">", ""));
				Role role = event.getGuild().getRoleById("679519729307877396");
				
				event.getChannel().sendMessage("Muted" + args[1] + "for" + args[2] + " seconds.").queue();
				event.getGuild().modifyMemberRoles(member, role).complete();
				// event.getGuild().getController().addRolesToMember(member, role).complete();
				
				// Unmute after a few seconds
				new java.util.Timer().schedule(
						new java.util.TimerTask() {
							@Override 
							public void run() {
								// TODO Auto-generated method stub
								
								event.getChannel().sendMessage("Unmuted" + args[1] + ".").queue();
								event.getGuild().modifyMemberRoles(member, role).complete();
								// event.getGuild().getController().removeRolesFromMember(member, role).complete();
							}
						},
						Integer.parseInt(args[2]) * 1000
						
						);
			}
			else {
				event.getChannel().sendMessage("Incorrect syntax. Use `!mute [user mention] [time {optional}]`").queue();
			}
			
		}
		
	}
}