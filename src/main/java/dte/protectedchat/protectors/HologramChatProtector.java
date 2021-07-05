package dte.protectedchat.protectors;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import dte.protectedchat.ProtectedChat;
import dte.protectedchat.holograms.ChatHologram;
import dte.protectedchat.holograms.displayers.HologramsDisplayer;
import dte.protectedchat.holograms.providers.ChatHologramProvider;
import dte.protectedchat.service.ProtectionService;
import dte.protectedchat.tasks.HologramsFollowTask;

public class HologramChatProtector implements ChatProtector
{
	private final Map<Player, ChatHologram> playersHolograms = new HashMap<>();
	private final HologramsDisplayer hologramsDisplayer;
	private final ChatHologramProvider hologramProvider;
	private final MessageConfiguration messageConfiguration;
	
	private static final ProtectedChat PROTECTED_CHAT = ProtectedChat.getInstance();
	
	public HologramChatProtector(ProtectionService protectionService, ChatHologramProvider hologramProvider, MessageConfiguration messageConfiguration, HologramsDisplayer hologramsDisplayer) 
	{
		this.hologramsDisplayer = hologramsDisplayer;
		this.hologramProvider = hologramProvider;
		this.messageConfiguration = messageConfiguration;
		
		new HologramsFollowTask(protectionService, hologramsDisplayer, this).runTaskTimer(PROTECTED_CHAT, 0, 5);
	}

	@Override
	public void onChat(Player protectedPlayer, String message)
	{
		ChatHologram playerHologram = this.playersHolograms.computeIfAbsent(protectedPlayer, this::createHologramFor);
		
		String formattedMessage = this.messageConfiguration.apply(message);
		playerHologram.addMessage(formattedMessage);
		
		this.hologramsDisplayer.refreshFor(protectedPlayer, playerHologram);
	}

	@Override
	public void disable(Player protectedPlayer)
	{
		ChatHologram playerHologram = this.playersHolograms.remove(protectedPlayer);
		
		//the player might not have spoken - so they don't have an hologram
		if(playerHologram != null)
			playerHologram.delete();
	}

	public Optional<ChatHologram> getHologramOf(Player owner)
	{
		return Optional.ofNullable(this.playersHolograms.get(owner));
	}

	private ChatHologram createHologramFor(Player owner)
	{
		ChatHologram hologram = this.hologramProvider.createHologram(owner, owner.getLocation());
		
		//remove the hologram after 5 seconds TODO: (make it based on the message's length)
		Bukkit.getScheduler().runTaskLater(PROTECTED_CHAT, () ->
		{
			hologram.delete();
			this.playersHolograms.remove(owner);
		}, 20 * 5);
		
		return hologram;
	}
	
	public static class MessageConfiguration
	{
		private final String format;
		
		public MessageConfiguration(String format) 
		{
			this.format = format;
		}
		public String getFormat() 
		{
			return this.format;
		}
		public String apply(String message) 
		{
			return this.format.replace("%message%", message);
		}
	}
}