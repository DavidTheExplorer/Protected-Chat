package dte.protectedchat.protectors.holograms.tasks;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import dte.protectedchat.protectors.holograms.HologramChatProtector;
import dte.protectedchat.protectors.holograms.displayer.HologramsDisplayer;
import dte.protectedchat.registry.ProtectionRegistry;

public class HologramsFollowTask extends BukkitRunnable
{
	private final ProtectionRegistry protectionRegistry;
	private final HologramsDisplayer hologramsDisplayer;
	private final HologramChatProtector hologramChatProtector;
	
	public HologramsFollowTask(ProtectionRegistry protectionRegistry, HologramsDisplayer hologramsDisplayer, HologramChatProtector hologramChatProtector) 
	{
		this.protectionRegistry = protectionRegistry;
		this.hologramsDisplayer = hologramsDisplayer;
		this.hologramChatProtector = hologramChatProtector;
	}
	
	@Override
	public void run()
	{
		for(Player player : this.protectionRegistry.getPlayersProtectedBy(this.hologramChatProtector)) 
		{
			this.hologramChatProtector.getHologramOf(player).ifPresent(hologram -> this.hologramsDisplayer.refreshFor(player, hologram));
		}
	}
}