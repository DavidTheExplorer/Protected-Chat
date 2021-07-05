package dte.protectedchat.holograms.displayers;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import dte.protectedchat.holograms.ChatHologram;

public class SimpleHologramDisplayer implements HologramDisplayer
{
	@Override
	public void refreshFor(Player player, ChatHologram ownerHologram)
	{
		 ownerHologram.moveTo(calculateLocation(player, ownerHologram));
	}

	private Location calculateLocation(Player owner, ChatHologram ownerHologram) 
	{
		return owner.getLocation().add(0, PLAYER_HEIGHT + ownerHologram.getHeight(), 0);
	}
}