package dte.protectedchat.protectors.holograms;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import dte.protectedchat.ProtectedChat;
import dte.protectedchat.holograms.MessagesHologram;
import dte.protectedchat.protectors.ChatProtector;
import dte.protectedchat.protectors.holograms.displayer.HologramsDisplayer;
import dte.protectedchat.protectors.holograms.providers.HologramsProvider;
import dte.protectedchat.protectors.holograms.tasks.HologramsFollowTask;
import dte.protectedchat.registry.ProtectionRegistry;

public class HologramChatProtector implements ChatProtector
{
	private final Map<Player, MessagesHologram> playersHologram = new HashMap<>();
	private final HologramsDisplayer hologramsDisplayer;
	private final HologramsProvider hologramsProvider;
	private final MessageConfiguration messageConfiguration;
	
	public HologramChatProtector(ProtectionRegistry protectionRegistry, HologramsProvider hologramsProvider, MessageConfiguration messageConfiguration, HologramsDisplayer hologramsDisplayer) 
	{
		this.hologramsDisplayer = hologramsDisplayer;
		this.hologramsProvider = hologramsProvider;
		this.messageConfiguration = messageConfiguration;
		
		new HologramsFollowTask(protectionRegistry, hologramsDisplayer).runTaskTimer(ProtectedChat.getInstance(), 0, 5);
	}

	@Override
	public void onChat(Player protectedPlayer, String message)
	{
		MessagesHologram playerHologram = this.playersHologram.computeIfAbsent(protectedPlayer, this::createHologramFor);
		playerHologram.addMessage(applyConfiguration(message));
		
		this.hologramsDisplayer.refreshFor(protectedPlayer, playerHologram);
	}

	@Override
	public void disable(Player protectedPlayer)
	{
		MessagesHologram playerHologram = this.playersHologram.remove(protectedPlayer);
		
		//the player might not have spoken - so they don't have an hologram
		if(playerHologram != null)
			playerHologram.delete();
	}

	public Optional<MessagesHologram> getHologramOf(Player owner)
	{
		return Optional.ofNullable(this.playersHologram.get(owner));
	}

	private MessagesHologram createHologramFor(Player owner)
	{
		MessagesHologram hologram = this.hologramsProvider.createHologram(owner, owner.getLocation());
		
		//remove the hologram after 5 seconds TODO: (make it based on the message's length)
		Bukkit.getScheduler().runTaskLater(ProtectedChat.getInstance(), () ->
		{
			hologram.delete();
			this.playersHologram.remove(owner);
		}, 20 * 5);
		
		return hologram;
	}
	
	private String applyConfiguration(String message) 
	{
		return this.messageConfiguration.getFormat().replace("%message%", message);
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
	}
}