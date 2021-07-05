package dte.protectedchat.holograms.displayers;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import dte.protectedchat.holograms.ChatHologram;

public class SimpleHologramDisplayer implements HologramDisplayer
{
	@Override
	public void refreshFor(Player owner, ChatHologram ownerHologram)
	{
		Location aboveOwner = owner.getLocation().add(0, PLAYER_HEIGHT + ownerHologram.getHeight(), 0);
		
		 ownerHologram.moveTo(aboveOwner);
	}
}