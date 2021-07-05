package dte.protectedchat.holograms.providers;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import dte.protectedchat.holograms.ChatHologram;

public interface ChatHologramProvider
{
	String getName();
	boolean isAvailable();
	ChatHologram createHologram(Player owner, Location spawnLocation);
}
