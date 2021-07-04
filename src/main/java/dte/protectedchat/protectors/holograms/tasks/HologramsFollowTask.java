package dte.protectedchat.protectors.holograms.tasks;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import dte.protectedchat.protectors.holograms.HologramChatProtector;
import dte.protectedchat.protectors.holograms.displayer.HologramsDisplayer;
import dte.protectedchat.service.ProtectionService;

public class HologramsFollowTask extends BukkitRunnable
{
	private final ProtectionService protectionService;
	private final HologramsDisplayer hologramsDisplayer;
	private final HologramChatProtector hologramChatProtector;
	
	public HologramsFollowTask(ProtectionService protectionService, HologramsDisplayer hologramsDisplayer, HologramChatProtector hologramChatProtector) 
	{
		this.protectionService = protectionService;
		this.hologramsDisplayer = hologramsDisplayer;
		this.hologramChatProtector = hologramChatProtector;
	}
	
	@Override
	public void run()
	{
		for(Player player : this.protectionService.getPlayersProtectedBy(this.hologramChatProtector)) 
		{
			this.hologramChatProtector.getHologramOf(player).ifPresent(hologram -> this.hologramsDisplayer.refreshFor(player, hologram));
		}
	}
}