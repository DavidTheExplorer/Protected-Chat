package dte.protectedchat.protectors.holograms.providers;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import dte.protectedchat.holograms.MessagesHologram;

public interface HologramsProvider
{
	String getName();
	boolean isAvailable();
	MessagesHologram createHologram(Player owner, Location spawnLocation);
}
