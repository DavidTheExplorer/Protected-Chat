package dte.protectedchat.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import dte.protectedchat.ProtectedChat;
import dte.protectedchat.registry.ProtectionRegistry;

public class ChatProtectionListener implements Listener
{
	private final ProtectionRegistry protectionRegistry;
	
	public ChatProtectionListener(ProtectionRegistry protectionRegistry) 
	{
		this.protectionRegistry = protectionRegistry;
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event)
	{
		Player player = event.getPlayer();
		
		if(!this.protectionRegistry.isProtected(player))
			return;
		
		Bukkit.getScheduler().runTask(ProtectedChat.getInstance(), () -> 
		{
			//don't send the message, and hint the player about that
			event.setCancelled(true);
			player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
			
			this.protectionRegistry.getProtectorOf(player).onChat(player, event.getMessage());
		});
	}
}