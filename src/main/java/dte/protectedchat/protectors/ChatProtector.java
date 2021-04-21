package dte.protectedchat.protectors;

import org.bukkit.entity.Player;

public interface ChatProtector 
{
	void onChat(Player protectedPlayer, String message);
	void disable(Player protectedPlayer);
}