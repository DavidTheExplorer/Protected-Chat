package dte.protectedchat.tasks;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import dte.protectedchat.holograms.displayers.HologramDisplayer;
import dte.protectedchat.protectors.HologramChatProtector;
import dte.protectedchat.service.ProtectionService;

public class HologramsFollowTask extends BukkitRunnable
{
	private final ProtectionService protectionService;
	private final HologramDisplayer hologramDisplayer;
	private final HologramChatProtector hologramChatProtector;
	
	public HologramsFollowTask(ProtectionService protectionService, HologramDisplayer hologramDisplayer, HologramChatProtector hologramChatProtector) 
	{
		this.protectionService = protectionService;
		this.hologramDisplayer = hologramDisplayer;
		this.hologramChatProtector = hologramChatProtector;
	}
	
	@Override
	public void run()
	{
		for(Player player : this.protectionService.getPlayersProtectedBy(this.hologramChatProtector)) 
		{
			this.hologramChatProtector.getHologramOf(player).ifPresent(hologram -> this.hologramDisplayer.refreshFor(player, hologram));
		}
	}
}