package dte.protectedchat.protectors;

import org.bukkit.entity.Player;

public interface ChatProtector 
{
	void onChat(Player player, String message);
	void disable(Player player);
}