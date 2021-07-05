package dte.protectedchat.holograms.displayers;

import org.bukkit.entity.Player;

import dte.protectedchat.holograms.ChatHologram;

@FunctionalInterface
public interface HologramDisplayer
{
	void refresh(Player owner, ChatHologram ownerHologram);
	
	double PLAYER_HEIGHT = 2;
}