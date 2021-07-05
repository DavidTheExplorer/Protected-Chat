package dte.protectedchat.holograms.displayers;

import org.bukkit.entity.Player;

import dte.protectedchat.holograms.ChatHologram;

public interface HologramsDisplayer
{
	void refreshFor(Player owner, ChatHologram ownerHologram);
	
	public static final double PLAYER_HEIGHT = 2;
}