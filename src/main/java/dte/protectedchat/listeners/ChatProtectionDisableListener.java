package dte.protectedchat.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import dte.protectedchat.service.ProtectionService;

public class ChatProtectionDisableListener implements Listener
{
	private final ProtectionService protectionService;

	public ChatProtectionDisableListener(ProtectionService protectionService) 
	{
		this.protectionService = protectionService;
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) 
	{
		Player player = event.getPlayer();
		
		if(this.protectionService.isProtected(player))
			this.protectionService.disable(player);
	}
}