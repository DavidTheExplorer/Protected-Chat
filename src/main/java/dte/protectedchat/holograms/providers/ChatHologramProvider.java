package dte.protectedchat.holograms.providers;

import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import dte.protectedchat.holograms.ChatHologram;

public interface ChatHologramProvider
{
	String getName();
	boolean isAvailable();
	ChatHologram createHologram(Player owner, Location spawnLocation);

	public static final ChatHologramProvider[] ALL = {new HolographicDisplaysProvider()};
	
	public static ChatHologramProvider fromName(String providerName) 
	{
		return Arrays.stream(ALL)
				.filter(provider -> provider.getName().equalsIgnoreCase(providerName))
				.findFirst()
				.orElse(null);
	}
}
