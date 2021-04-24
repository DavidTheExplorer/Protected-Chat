package dte.protectedchat.holograms.displayer;

import org.bukkit.entity.Player;

import dte.protectedchat.holograms.MessagesHologram;

public interface HologramsDisplayer
{
	void refreshFor(Player owner, MessagesHologram ownerHologram);
	
	public static final double PLAYER_HEIGHT = 2;
}