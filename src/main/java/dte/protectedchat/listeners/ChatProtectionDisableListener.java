package dte.protectedchat.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import dte.protectedchat.registry.ProtectionRegistry;

public class ChatProtectionDisableListener implements Listener
{
	private final ProtectionRegistry protectionRegistry;

	public ChatProtectionDisableListener(ProtectionRegistry protectionRegistry) 
	{
		this.protectionRegistry = protectionRegistry;
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) 
	{
		Player player = event.getPlayer();
		
		if(this.protectionRegistry.isProtected(player))
			this.protectionRegistry.disable(player);
	}
}