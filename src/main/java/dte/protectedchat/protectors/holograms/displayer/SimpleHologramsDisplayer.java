package dte.protectedchat.protectors.holograms.displayer;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import dte.protectedchat.holograms.MessagesHologram;

public class SimpleHologramsDisplayer implements HologramsDisplayer
{
	@Override
	public void refreshFor(Player player, MessagesHologram ownerHologram)
	{
		 ownerHologram.moveTo(calculateLocation(player, ownerHologram));
	}

	private Location calculateLocation(Player owner, MessagesHologram ownerHologram) 
	{
		return owner.getLocation().add(0, PLAYER_HEIGHT + ownerHologram.getHeight(), 0);
	}
}