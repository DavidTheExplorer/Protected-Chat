package dte.protectedchat.holograms.tasks;

import org.bukkit.scheduler.BukkitRunnable;

import dte.protectedchat.holograms.displayer.HologramsDisplayer;
import dte.protectedchat.protectors.HologramChatProtector;
import dte.protectedchat.registry.ProtectionRegistry;

public class HologramsFollowTask extends BukkitRunnable
{
	private final ProtectionRegistry protectionRegistry;
	private final HologramsDisplayer hologramsDisplayer;
	
	public HologramsFollowTask(ProtectionRegistry protectionRegistry, HologramsDisplayer hologramsDisplayer) 
	{
		this.protectionRegistry = protectionRegistry;
		this.hologramsDisplayer = hologramsDisplayer;
	}
	
	@Override
	public void run()
	{
		this.protectionRegistry.getPlayersProtectedBy(HologramChatProtector.class).forEach((player, hologramProtector) -> 
		{
			hologramProtector.getHologramOf(player).ifPresent(hologram -> this.hologramsDisplayer.refreshFor(player, hologram));
		});
	}
}
